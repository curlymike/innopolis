package part1.lesson10.task01.server;

import java.io.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class Client {
  private static final int NAME_MAX_LENGTH = 40;

  private String name;
  Server server;
  private ClientConnection connection;
  private final long timeCreated = System.currentTimeMillis();

  // Статус будет запрашиваться из другого потока
  private volatile Status status = Status.INIT;

  public enum Status {
    INIT,
    WAITING_FOR_NAME,
    ONLINE,
    DISCONNECTED,
  }

  public Client(Server server, ClientConnection connection) throws IOException {
    this.server = server;
    this.connection = connection;
  }

  public ClientConnection getConnection() {
    return connection;
  }

  public String name() {
    return name;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public boolean isOnline() {
    return status == Status.ONLINE;
  }

  public boolean hasName() {
    return name != null;
  }

  // MOVED
  // Этот метод вызывается методом askForName() (возможно неоднократно)
  // и пока имя не будет получено статус не будет ONLINE и нить сервера
  // не должна этот метод вызывать. Сервер отправляет что-либо только
  // тем пользователям которые предоставили свое имя/ник.
  public synchronized void send(String message) throws IOException {
    connection.send(message);
  }

  // MOVED
  // Static легче тестировать
  private static boolean isValidName(String name) {
    return name != null
        && name.length() > 0
        && name.length() <= 40
        // Проверка допустимых символов требует доработки
        && name.matches("(?i)[a-zа-яА-Я]+[a-zа-я0-9_-]*[a-zа-я0-9]+")
        && !"admin".equals(name.toLowerCase())
        && !"server".equals(name.toLowerCase());
  }

  public void askForName() throws IOException {
    status = Status.WAITING_FOR_NAME;
    StringBuilder sb = new StringBuilder();
    while (true) {
      connection.send("Введите имя под которым вы будете видны в чате: ");
      while (true) {
        if (connection.ready()) {
          char n = (char) connection.read();
          if (n == '\n') {
            break;
          } else {
            sb.append(n);
          }
          if (sb.length() == NAME_MAX_LENGTH) {
            break;
          }
        } else {
          LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(50));
        }
      }

      String name = sb.toString();

      if (server.nameIsTaken(name)) {
        connection.send("Ошибка: имя уже занято (" + name + ").");
        sb.setLength(0);
      } else if (!isValidName(name)) {
        connection.send("Ошибка: недопустимое имя (" + name + ").");
        sb.setLength(0);
      } else {
        this.name = name;
        status = Status.ONLINE;
        return;
      }

    }
  }

  public void close() throws IOException {

  }

}
