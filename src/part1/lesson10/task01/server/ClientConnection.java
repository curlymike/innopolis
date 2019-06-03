package part1.lesson10.task01.server;

import java.io.*;
import java.net.Socket;

public class ClientConnection implements AutoCloseable {
  private Socket socket;
  private BufferedWriter writer;
  private BufferedReader reader;
  private volatile boolean isClosed = false;

  public ClientConnection(Socket socket) throws IOException {
    this.socket = socket;
    init();
  }

  private synchronized void init() throws IOException {
    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
  }

  /***
   * Tells whether this stream is ready to be read. A buffered character stream is ready if the buffer is not empty, or if the underlying character stream is ready.
   * @return True if the next read() is guaranteed not to block for input, false otherwise.
   * @throws IOException
   */

  public boolean ready() throws IOException {
    ensureOpen();
    return reader.ready();
  }

  /***
   * Reads a single character.
   * @return The character read, as an integer in the range 0 to 65535 (0x00-0xffff), or -1 if the end of the stream has been reached
   * @throws IOException
   */

  public int read() throws IOException {
    ensureOpen();
    return reader.read();
  }

  /***
   * Reads a line of text.
   * @return A String containing the contents of the line, not including any line-termination characters, or null if the end of the stream has been reached
   * @throws IOException
   */

  public String readLine() throws IOException {
    ensureOpen();
    return reader.readLine();
  }

  /**
   * Этот метод передаёт строку message клиенту.
   * Метод вызывается методом askForName() (возможно неоднократно)
   * и пока имя не будет получено статус не будет ONLINE и нить сервера
   * не должна этот метод вызывать. Сервер отправляет что-либо только
   * тем пользователям которые предоставили свое имя/ник.
   * @param message
   * @throws IOException
   */

  public synchronized void send(String message) throws IOException {
    ensureOpen();
    writer.write(message + '\n');
    writer.flush();
  }

  private void ensureOpen() throws IOException {
    if (reader == null || writer == null)
      throw new IOException("Connection closed");
  }

  /**
   * Закрывает клиентский сокет и ридеры
   * @throws IOException
   */

  @Override
  public synchronized void close() throws IOException {
    if (isClosed) {
      return;
    }
    try {
      writer.close();
    } finally {
      writer = null;
      try {
        reader.close();
      } finally {
        reader = null;
        try {
          socket.close();
        } finally {
          socket = null;
          isClosed = true;
        }
      }
    }
  }

}
