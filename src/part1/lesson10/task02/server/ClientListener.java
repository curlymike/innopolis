package part1.lesson10.task02.server;

import java.io.IOException;

public class ClientListener implements Runnable {

  private final Client client;
  private final Server server;

  public ClientListener(Server server, Client client) {
    this.client = client;
    this.server = server;
  }

  @Override
  public void run() {
    try {
      client.askForName();
      server.update(client, Event.CLIENT_CONNECTED);

      while (!Thread.currentThread().isInterrupted()) {
        server.update(client, Event.MESSAGE, client.getConnection().readLine());
      }

    } catch (IOException e) {
      client.setStatus(Client.Status.DISCONNECTED);
      e.printStackTrace();
    } finally {
      try {
        client.getConnection().close();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        server.update(client, Event.CLIENT_DISCONNECTED);
      }
    }
    System.out.println("LOG: " + getClass().getSimpleName() + " exit");
  }

}
