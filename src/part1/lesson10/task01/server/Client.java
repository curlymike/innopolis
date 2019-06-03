package part1.lesson10.task01.server;

import java.io.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class Client {
  private static final int NAME_MAX_LENGTH = 40;

  private String name;
  Server server;
  private ClientConnection connection;

  // Статус будет запрашиваться из другого потока
  private volatile Status status = Status.INIT;

  /**
   * Сервер рассылает сообщения только клиентам со статусом ONLINE
   */

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

  /**
   * Возвращает объект ClientConnection данного клиента
   * @return
   */

  public ClientConnection getConnection() {
    return connection;
  }

  /**
   * Возвращает имя клиента
   * @return
   */

  public String name() {
    return name;
  }

  /**
   * Возвразает статус клиента
   * @return
   */

  public Status getStatus() {
    return status;
  }

  /**
   * Устанавливает статус клиента
   * @return
   */

  public void setStatus(Status status) {
    this.status = status;
  }

  /**
   * @return true - если клиент имеет статус ONLINE
   */

  public boolean isOnline() {
    return status == Status.ONLINE;
  }

  /**
   * @return true - если клиент имеет имя (он его передал и сервер это имя принял)
   */

  public boolean hasName() {
    return name != null;
  }

  /**
   * Этот метод передаёт строку message клиенту
   * @param message
   * @throws IOException
   */

  public synchronized void send(String message) throws IOException {
    connection.send(message);
  }

  /**
   * Проверяет имя пользователя на соответствие заданным ограничениям
   * @param name
   * @return
   */
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

  /**
   * Запрашивает имя пользователя и проверяет его, блокирует выполнение до тех пор пока не будет получено валидное имя или пока не будет достигнуто ограничение по времени.
   * @throws IOException
   */

  public void askForName() throws IOException {
    status = Status.WAITING_FOR_NAME;
    StringBuilder sb = new StringBuilder();
    long timeout = System.currentTimeMillis() + Server.NAME_TIMEOUT_MS;
    while (true) {
      connection.send("Введите имя под которым вы будете видны в чате: ");
      while (true) {
        if (System.currentTimeMillis() > timeout) {
          connection.send("Превышено время ожидания имени пользователя.");
          server.update(this, Event.LOGIN_TIMEOUT);
        }
        if (connection.ready()) {
          char n = (char) connection.read();
          if (n == -1) {
            break;
          }
          if (n == '\n' || n == '\r') {
            if (sb.length() > 0) {
              break;
            }
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

  /**
   * Закрывает соединение с пользователем
   * @throws IOException
   */

  public void close() throws IOException {
    connection.close();
  }

}
