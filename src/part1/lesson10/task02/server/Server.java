package part1.lesson10.task02.server;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Server extends Thread {
  //public static final String DEFAULT_HOST = "127.0.0.1";
  public static final String DEFAULT_HOST = "0.0.0.0";
  public static final int PORT_NUMBER = 33333;
  public static final int SO_TIMEOUT_MS = 250;
  public static final int NAME_TIMEOUT_MS = 30_000;

  private final Set<Client> clients = ConcurrentHashMap.newKeySet();
  private final ExecutorService executorService = Executors.newCachedThreadPool();

  private final InetAddress address;
  private volatile boolean isRunning = true;

  public static void main(String[] args) throws Exception {
    Server server = new Server(InetAddress.getByName(DEFAULT_HOST));
    server.start();
    Scanner s = new Scanner(System.in);
    while (!"exit".equals(s.nextLine())) {
    }
    server.shutdown();
    server.join();
  }

  public Server(InetAddress address) {
    this.address = address;
  }

  /***
   * Добавляет обект Client в коллекцию клиентов
   * @param client
   */

  public void addClient(Client client) {
    clients.add(client);
  }

  /**
   * Этот метод вызывается из основного цикла (метод run()) принимающего подключения.
   * @param socket
   */

  public void incomingConnection(Socket socket) {
    try {
      Client client = new Client(this, new ClientConnection(socket));
      clients.add(client);
      executorService.submit(new ClientListener(this, client));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * При помощи этого метода ClientListener уведомляет сервер о событиях.
   * @param client
   * @param event
   */

  public void update(Client client, Event event) {
    update(client, event, null);
  }

  /***
   * При помощи этого метода ClientListener уведомляет сервер о событиях.
   * @param client
   * @param event
   * @param str
   */

  public void update(Client client, Event event, String str) {
    switch (event) {
      case MESSAGE:
        processMessage(client, str);
        break;

      case CLIENT_CONNECTED:
        System.out.println("LOG: " + client.name() + " присоединился к чату.");
        broadcastMessage(client.name() + " присоединился к чату.", client);
        sendMessage(client, "Добро пожаловать в чат!");
        sendMessage(client, "Отправьте \"/help\" - чтобы получить информацию о доступных командах.");
        sendMessage(client, "Для отправки личного сообщения наберите");
        sendMessage(client, "@<имя пользователя> <сообщение>");
        sendMessage(client, "---------------------------------------");
        break;

      case CLIENT_DISCONNECTED:
        closeAndRemove(client);
        if (client.hasName()) {
          broadcastMessage(client.name() + " покинул чат.");
          System.out.println("LOG: " + client.name() + " покинул чат.");
        } else {
          System.out.println("LOG: пользователь отключился не залогинившись.");
        }
        break;

      case LOGIN_TIMEOUT:
        closeAndRemove(client);
        System.out.println("LOG: превышено время ожидания имени пользователя.");
        break;
    }
  }

  private void closeAndRemove(Client client) {
    try {
      client.close();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      clients.remove(client);
    }
  }

  /***
   * Проверяет занято имя или нет
   * @param name
   * @return true|false
   */

  public boolean nameIsTaken(String name) {
    return getClientByName(name) != null;
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
        Client recipient = getClientByName(parts[0].substring(1));
        if (recipient == null) {
          try {
            client.send("Пользователь не существует.");
          } catch (IOException e) {
            e.printStackTrace();
          }
        } else {
          try {
            recipient.send("> " + client.name() + " (личное): " + parts[1]);
            client.send("> " + message);
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
            client.send("/list - показать список участников");
            client.send("/help или /info - данная справка");
            client.send("------------------------");
            break;
          case "list":
            client.send("------------------------");
            clients.stream().filter(Client::isOnline).sorted(Comparator.comparing(Client::name)).forEach((c) -> {
              try {
                client.send(c.name() + (c.equals(client) ? " (вы)" : ""));
              } catch (IOException e) {
                e.printStackTrace();
              }
            });
            client.send("------------------------");
            break;
          case "exit":
            update(client, Event.CLIENT_DISCONNECTED);
            break;
          default:
            client.send("Ошибка: неизвестная команда.");
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      return;
    }

    broadcastMessage("> " + client.name() + ": " + message);
  }

  private void sendMessage(Client client, String message) {
    try {
      client.send(message);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private synchronized void broadcastMessage(String message) {
    broadcastMessage(message, null);
  }

  private synchronized void broadcastMessage(String message, Client skip) {
    for (Client client : clients) {
      if (client.isOnline() && !client.equals(skip)) {
        try {
          client.send(message);
        } catch (IOException e) {
          e.printStackTrace();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * Основной цикл сервера, принимает входящие подключения
   */

  @Override
  public void run() {
    ServerSocket serverSocket = null;
    try {
      serverSocket = new ServerSocket(PORT_NUMBER, 0, address);
      serverSocket.setSoTimeout(SO_TIMEOUT_MS);

      System.out.println("Сервер запущен: " + address + ':' + PORT_NUMBER);
      System.out.println("Наберите exit для остановки сервера.");

      while (isRunning && !Thread.currentThread().isInterrupted()) {
        try {
          incomingConnection(serverSocket.accept());
          System.out.println("LOG: someone wants to join the chat.");
        } catch (SocketTimeoutException e) {
          // ...
        }
      }

    } catch (BindException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (serverSocket != null) {
        try {
          serverSocket.close();
          System.out.println("LOG: ServerSocket has been successfully closed.");
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

  }

  /***
   * Останавливает сервер
   */

  public void shutdown() {
    broadcastMessage("Сервер останавливается, держитесь за поручни.");
    isRunning = false;
  }

}
