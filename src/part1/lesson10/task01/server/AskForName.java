package part1.lesson10.task01.server;

import java.io.IOException;

public class AskForName implements Runnable {

  private final ClientConnection connection;

  public AskForName(ClientConnection connection) {
    this.connection = connection;
  }

  @Override
  public void run() {
    try {
      connection.send("Введите имя под которым вы будете видны в чате: ");

    } catch (IOException e) {
      // TODO: do something
    }
  }
}
