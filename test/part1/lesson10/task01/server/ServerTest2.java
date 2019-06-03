package part1.lesson10.task01.server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Time;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class ServerTest2 {

  Server server;

  @Before
  public void setUp() throws Exception {
    server = new Server(InetAddress.getByName("127.0.0.1"));
    server.start();
  }

  @After
  public void tearDown() throws Exception {
    server.shutdown();
    server.join();
  }

  public void loginClient(Client client) throws IOException {
    long timeout = System.currentTimeMillis() + 1500;
    while (client.messageCount() == 0) {
      LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(50));
      if (System.currentTimeMillis() > timeout) {
        throw new IOException("Timeout");
      }
    }
    client.send(client.name() + '\n');
  }

  @Test
  public void test001() throws Exception {
    InetAddress host = InetAddress.getByName("127.0.0.1");
    int port = Server.PORT_NUMBER;

    Client clientX = new Client(host, port).withName("MisterX").withPrintInput(true);
    clientX.start();
    loginClient(clientX);
    LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(1500));
    clientX.shutdown();
    clientX.join();
  }

  @Test
  public void test010() throws Exception {
    InetAddress host = InetAddress.getByName("127.0.0.1");
    int port = Server.PORT_NUMBER;

    Client clientX = new Client(host, port).withName("MisterX").withPrintInput(true);
    clientX.start();
    loginClient(clientX);

    Client client1 = new Client(host, port);
    Client client2 = new Client(host, port);
    Client client3 = new Client(host, port);

    System.out.println("+++ client1.name()=" + client1.name());

    client1.start();
    loginClient(client1);
    client2.start();
    loginClient(client2);
    client3.start();
    loginClient(client3);

    LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(100));

    clientX.send("/list\n");

    LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(1500));

    client1.shutdown();
    client2.shutdown();
    client3.shutdown();

    LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(500));

    clientX.shutdown();

    client1.join();
    client2.join();
    client3.join();
    clientX.join();

  }

  /***
   *
   */

  @Test
  public void test011() throws Exception {
    InetAddress host = InetAddress.getByName("127.0.0.1");
    int port = Server.PORT_NUMBER;
    int number = 10;
    Client[] clients = new Client[number];

    Client clientX = new Client(host, port).withName("MisterX").withPrintInput(true);
    clientX.start();
    loginClient(clientX);

    for (int i = 0; i < number; i++) {
      clients[i] = new Client(host, port);
      clients[i].start();
      loginClient(clients[i]);
      LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(10));
    }

    LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(100));

    clientX.send("/list\n");

    LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(1000));

    for (int i = 0; i < number; i++) {
      clients[i].shutdown();
    }

    LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(500));

    clientX.shutdown();

    for (int i = 0; i < number; i++) {
      clients[i].join();
    }

    clientX.join();

  }

  /***
   *
   * Client
   *
   */

  static class Client extends Thread {

    private final InetAddress host;
    private final int port;

    private String name;
    private boolean sendError;
    private static AtomicInteger count = new AtomicInteger();
    private final ConcurrentLinkedQueue<String> chatMessages = new ConcurrentLinkedQueue<>();
    private boolean printInput = false;
    private volatile boolean keepGoing = true;
    private volatile Socket socket;
    private volatile BufferedWriter writer;

    public Client(InetAddress host, int port) throws IOException {
      name = "TestClient_" + count.incrementAndGet();
      this.host = host;
      this.port = port;
    }

    public Client withName(String name) {
      this.name = name;
      return this;
    }

    public Client withSendError(boolean value) {
      sendError = value;
      return this;
    }

    public Client withPrintInput(boolean value) {
      printInput = value;
      return this;
    }

    public String name() {
      return name;
    }

    public boolean printInput() {
      return printInput;
    }

    public void printInput(boolean value) {
      printInput = value;
    }

    public synchronized void shutdown() {
      keepGoing = false;
    }

    public void send(String message) throws IOException {
      if (writer != null) {
        writer.write(message);
        writer.flush();
      }
    }

    public int messageCount() {
      return chatMessages.size();
    }

    @Override
    public void run() {
      try (Socket socket = new Socket("127.0.0.1", Server.PORT_NUMBER);
           InputStreamReader isr = new InputStreamReader(socket.getInputStream());
           BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
        writer = bw;
        StringBuilder stringBuilder = new StringBuilder();
        while (keepGoing && !Thread.currentThread().isInterrupted()) {
          if (isr.ready()) {
            char ch = (char) isr.read();
            if (ch == '\r' || ch == '\n') {
              if (stringBuilder.length() > 0) {
                if (printInput) {
                  System.out.println(stringBuilder.toString());
                }
                chatMessages.add(stringBuilder.toString());
                stringBuilder.setLength(0);
              }
            } else {
              stringBuilder.append(ch);
            }
          } else {
            LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(50));
          }
        }

      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        writer = null;
      }
    }

  }
}