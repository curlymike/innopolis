package part1.lesson10.task01.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.Collectors;

public class Server extends Thread {
  //public static final String DEFAULT_HOST = "0.0.0.0";
  public static final String DEFAULT_HOST = "127.0.0.1";
  public static final int PORT_NUMBER = 33333;
  public static final int SO_TIMEOUT_MS = 250;

  //private final Set<Client> clients = new HashSet<>();
  private final Set<Client> clients = ConcurrentHashMap.newKeySet();
  private final ExecutorService es = Executors.newCachedThreadPool();

  private final InetAddress address;
  private boolean isRunning = true;

  public static void main(String[] args) throws Exception {
    Server server = new Server(InetAddress.getByName(DEFAULT_HOST));
    server.start();
    server.join();
  }

  public Server(InetAddress address) {
    this.address = address;
  }

  public void addClient(Client client) {
    clients.add(client);
  }

  public void update(Client client, Event event) {
    update(client, event, null);
  }

  public void incomingConnection(Socket socket) {

  }

  public void update(Client client, Event event, String str) {
    switch (event) {
      case MESSAGE:
        processMessage(client, str);
        break;

      case CLIENT_CONNECTED:
        broadcastMessage(client.name() + " присоединился к чату.");
        break;

      case CLIENT_DISCONNECTED:
        broadcastMessage(client.name() + " покинул чат.");
        try {
          client.close();
        } catch (IOException e) {
          e.printStackTrace();
        } finally {
          clients.remove(client);
        }
        break;
    }
  }

  private Client getClientByName(String name) {
    for (Client client : clients) {
      if (client.isOnline() && client.name().equals(name)) {
        return client;
      }
    }
    return null;
  }

  private void processMessage(Client client, String message) {
    if (message.length() == 0) {
      return;
    }
    if (message.charAt(0) == '@') {
      String[] parts = message.split("\\s", 2);
      if (parts[0].length() < 2 || parts.length < 2 || parts[1].isEmpty()) {
        return;
      }
      try {
        // TODO: substring(...) throws IndexOutOfBoundsException
        Client recipient = getClientByName(parts[0].substring(1));
        if (recipient == null) {
          try {
            client.send("Пользователь не существует.");
          } catch (IOException e) {
            e.printStackTrace();
          }
        } else {
          try {
            recipient.send(client.name() + " (личное): " + parts[1]);
            client.send(message);
          } catch (IOException e) {
            e.printStackTrace();
            try {
              //client.send("(Ошибка) " + parts[1]);
              client.send("Ошибка отправки, попробуйте ещё раз.");
            } catch (IOException e2) {
              e.printStackTrace();
            }
          }
        }
      } catch (IndexOutOfBoundsException e) {
        try {
          client.send("Ошибка.");
        } catch (IOException e3) {
          e.printStackTrace();
        }
      }
      return;

    } else if (message.charAt(0) == '/') {
      if (message.length() < 2) {
        return;
      }
      try {
        switch (message.substring(1)) {
          case "info":
          case "help":
            client.send("------------------------");
            client.send("list - показать список участников");
            client.send("exit - покинуть чат");
            client.send("help");
            client.send("info - данная справка");
            client.send("------------------------");
            break;
          case "list":
            client.send("------------------------");
            clients.stream().sorted(Comparator.comparing(Client::name)).forEach((c) -> {
              try {
                client.send(c.name());
              } catch (IOException e) {
                e.printStackTrace();
              }
            });
            client.send("------------------------");
            break;
          default:
            client.send("Ошибка: неизвестная команда.");
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      return;
    }

    broadcastMessage(client.name() + ": " + message);
  }

  // TODO: refactor to thread-safe message queue, handled by a single thread.
  private synchronized void broadcastMessage(String message) {
    for (Client client : clients) {
      try {
        client.send(message);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void run() {
    ServerSocket serverSocket = null;
    try {
      serverSocket = new ServerSocket(PORT_NUMBER, 0, address);
      serverSocket.setSoTimeout(SO_TIMEOUT_MS);

      while (isRunning) {
        try {
          clients.add(new Client(this, serverSocket.accept()));
          System.out.println("LOG: client wants to join the chat.");
        } catch (SocketTimeoutException e) {

        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (serverSocket != null) {
        try {
          serverSocket.close();
          System.out.println("ServerSocket has been successfully closed.");
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

  }

  public void shutdown() {
    isRunning = false;
  }

}
