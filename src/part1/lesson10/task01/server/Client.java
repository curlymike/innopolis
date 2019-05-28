package part1.lesson10.task01.server;

import java.io.*;
import java.net.Socket;

public class Client {
  private String name;
  // MOVED
  private Socket socket;
  private BufferedWriter writerToClient;
  private ClientConnection clientConnection;
  Server server;

  // Статус будет запрашиваться из другого потока
  private volatile Status status = Status.INIT;

  public enum Status {
    INIT,
    WAITING_FOR_NAME,
    ONLINE,
    DISCONNECTED,
  }

  public Client(Server server, Socket socket) throws IOException {
    this.server = server;
    this.socket = socket;
    //socket.setSoTimeout();
    clientConnection = new ClientConnection(socket);
  }

  // getName is final
  public String name() {
    return name;
  }

  public Status getStatus() {
    return status;
  }

  public boolean isOnline() {
    return status == Status.ONLINE;
  }

  // MOVED
  // Этот метод вызывается методом askForName() (возможно неоднократно)
  // и пока имя не будет получено статус не будет ONLINE и нить сервера
  // не должна этот метод вызывать. Сервер отправляет что-либо только
  // тем пользователям которые предоставили свое имя/ник.
  public synchronized void send(String message) throws IOException {
    if (writerToClient == null) {
      writerToClient = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }
    writerToClient.write(message + '\n');
    writerToClient.flush();
  }

  // MOVED
  // Static легче тестировать
  private static boolean isValidName(String name) {
    return name != null
        && name.length() > 0
        && name.length() <= 40
        && name.matches("(?i)[a-zа-я]+[a-zа-я0-9_-]*[a-zа-я0-9]+")
        && !"admin".equals(name.toLowerCase())
        && !"server".equals(name.toLowerCase());
  }

  // MOVED
  private void askForName(BufferedWriter writer, BufferedReader reader) throws IOException {
    status = Status.WAITING_FOR_NAME;
    while (true) {
      writer.write("Введите имя под которым вы будете видны в чате: ");
      writer.flush();
      String name = reader.readLine();
      if (isValidName(name)) {
        this.name = name;
        status = Status.ONLINE;
        return;
      } else {
        writer.write("Ошибка: недопустимое имя.");
      }
    }
  }

  public void close() throws IOException {

  }

}
