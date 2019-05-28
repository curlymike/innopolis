package part1.lesson10.task01.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Observable;

public class ServerSocketListener extends Thread {

  private final Server server;
  private ServerSocket serverSocket;
  private boolean isRunning;

  public ServerSocketListener(Server server, ServerSocket socket) {
    this.server = server;
    this.serverSocket = socket;
  }

  @Override
  public void run() {
    //notifyObservers();
    ServerSocket serverSocket = null;
    try {
      //serverSocket = new ServerSocket(PORT_NUMBER, 0, address);
      serverSocket.setSoTimeout(250);

      while (isRunning) {
        try {
          Client client = new Client(server, serverSocket.accept());
          //client.start();
          server.addClient(client);
          System.out.println("LOG: кто-то подключается к чату.");
        } catch (SocketTimeoutException e) {
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (serverSocket != null) {
        try {
          serverSocket.close();
          System.out.println("ServerSocket успешно закрыт.");
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

}
