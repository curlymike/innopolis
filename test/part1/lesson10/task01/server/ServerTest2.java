package part1.lesson10.task01.server;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;

public class ServerTest2 {

  Server server;
  Client client;
  // Why volatile?
  //volatile boolean sendError = false;

  @Before
  public void setUp() throws Exception {
    server = new Server(InetAddress.getByName("127.0.0.1"));
  }

  /***
   * Client
   */

  class Client {

    private String name;
    private boolean sendError;

    public Client() throws IOException {
      name = "TestClient";
    }

    public Client withName(String name) {
      this.name = name;
      return this;
    }

    public Client withSendError(boolean value) {
      sendError = value;
      return this;
    }

    public String name() {
      return name;
    }

    public void connect() throws Exception {
      try (Socket socket = new Socket("127.0.0.1", Server.PORT_NUMBER)) {
        // Work in progress...
      }
    }

    //public synchronized void send(String message) throws IOException { }
  }
}