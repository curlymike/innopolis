package part1.lesson10.task01.server;

import java.io.*;
import java.net.Socket;

public class ClientConnection implements AutoCloseable {
  private Socket socket;
  private BufferedWriter writerToClient;
  private BufferedReader readerFromClient;
  private volatile boolean isClosed = false;

  public ClientConnection(Socket socket) throws IOException {
    this.socket = socket;
    init();
  }

  private synchronized void init() throws IOException {
    readerFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
  }

  public boolean ready() throws IOException {
    return readerFromClient.ready();
  }

  public int read() throws IOException {
    return readerFromClient.read();
  }

  public String readLine() throws IOException {
    return readerFromClient.readLine();
  }

  // Этот метод вызывается методом askForName() (возможно неоднократно)
  // и пока имя не будет получено статус не будет ONLINE и нить сервера
  // не должна этот метод вызывать. Сервер отправляет что-либо только
  // тем пользователям которые предоставили свое имя/ник.
  public synchronized void send(String message) throws IOException {
    if (writerToClient == null) {
      writerToClient = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }
    writerToClient.write(message + '\n');
    writerToClient.flush();
  }

  @Override
  public synchronized void close() throws IOException {
    if (isClosed) {
      return;
    }
    try {
      writerToClient.close();
    } finally {
      writerToClient = null;
      try {
        socket.close();
      } finally {
        socket = null;
        isClosed = true;
      }
    }
  }

}
