package part1.lesson10.task01.server;

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
        // TODO: make use of client.getConnection().ready()
        //       returns true if guaranteed not to block.
        server.update(client, Event.MESSAGE, client.getConnection().readLine());
      }

    } catch (IOException e) {
      client.setStatus(Client.Status.DISCONNECTED);
      server.update(client, Event.CLIENT_DISCONNECTED);
    } finally {
      try {
        client.getConnection().close();
      } catch (IOException e) {
        // TODO: Log that
      }
    }

    System.out.println("LOG: Server.SocketWorker exit");
  }

}
