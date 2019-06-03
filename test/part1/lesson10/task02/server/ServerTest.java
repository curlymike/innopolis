package part1.lesson10.task02.server;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;

import static org.assertj.core.api.Assertions.assertThat;

public class ServerTest {

  Server server;
  Client client;
  // Why volatile?
  //volatile boolean sendError = false;

  @Before
  public void setUp() throws Exception {
    server = new Server(InetAddress.getByName("127.0.0.1"));
    client = new Client();
    server.addClient(client);
  }

  Method processMessage() throws NoSuchMethodException {
    Method m = Server.class.getDeclaredMethod(
        "processMessage",
        part1.lesson10.task02.server.Client.class,
        String.class);
    m.setAccessible(true);
    return m;
  }

  @Test
  public void processMessage001() throws Exception {
    processMessage().invoke(server, client, "Hello");
  }

  @Test
  public void processMessage002() throws Exception {
    server.addClient((new Client()).withName("Mike"));
    processMessage().invoke(server, client, "Hello");
  }

  @Test
  public void processMessage003() throws Exception {
    server.addClient((new Client()).withName("Mike"));
    server.addClient((new Client()).withName("John"));
    server.addClient((new Client()).withName("Pete"));
    processMessage().invoke(server, client, "Hello");
  }

//  @Test
//  public void processMessage004() throws Exception {
//    processMessage().invoke(server, client, "Hello");
//  }

  @Test
  public void processMessage020() throws Exception {
    processMessage().invoke(server, client, "@Mike Hello");
  }

  @Test
  public void processMessage021() throws Exception {
    server.addClient((new Client()).withName("Mike"));
    processMessage().invoke(server, client, "@Mike Hello there!");
  }

  @Test
  public void processMessage022() throws Exception {
    server.addClient((new Client()).withName("Mike").withSendError(true));
    processMessage().invoke(server, client, "@Mike Hello there!");
  }

  @Test
  public void processMessage100() throws Exception {
    processMessage().invoke(server, client, "/help");
  }

  @Test
  public void processMessage101() throws Exception {
    server.addClient((new Client()).withName("Mike"));
    processMessage().invoke(server, client, "/list");
  }

  @Test
  public void processMessage102() throws Exception {
    server.addClient((new Client()).withName("Mike"));
    server.addClient((new Client()).withName("John"));
    server.addClient((new Client()).withName("Pete"));
    server.addClient((new Client()).withName("Alice"));
    processMessage().invoke(server, client, "/list");
  }

  @Test
  public void processMessage103() throws Exception {
    processMessage().invoke(server, client, "/help");
  }

  /***
   * Mock Client
   */

  class Client extends part1.lesson10.task02.server.Client {
    private String name;
    private boolean sendError;

    public Client() throws IOException {
      super(null, null);
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

    public boolean isOnline() {
      return true;
    }

    public synchronized void send(String message) throws IOException {
      if (sendError) {
        throw new IOException("Error for testing purposes.");
      }
      System.out.println("> " + message);
      //System.out.println("> " + message + " [" + name + "]");
    }
  }
}