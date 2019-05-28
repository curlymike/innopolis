package part1.lesson10.task01.server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Observable;

public class ClientListener extends Observable implements Runnable {

  private static Client client;
  private InputStream inputStream;

  public ClientListener(Client client) {
    this.client = client;
  }

  // socket.getInputStream()
  @Override
  public void run() {

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      //--- test ----
      //InputStream is = socket.getInputStream();
      //is.read();
      reader.readLine();
      //--- /test ----
//      askForName(writerToClient, reader);
      String message;
      while ((message = reader.readLine()) != null) {
        System.out.println("Server.Listener: " + message);
//        server.update(this, Event.MESSAGE, message);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("Server.SocketWorker exit");
  }

}
