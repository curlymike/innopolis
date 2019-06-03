package part1.lesson10.task01.client;

import part1.lesson10.task01.server.Server;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * Простой консольный клиент к чату
 */

public class ConsoleClient {

  public static final String DEFAULT_HOST = "127.0.0.1";

  private static volatile boolean isWorking = true;

  public ConsoleClient() {
  }

  /**
   * Запускает клиент
   * @param args
   */

  public static void main(String[] args) {

    try (Socket socket = new Socket(DEFAULT_HOST, Server.PORT_NUMBER)) {

      Thread readerThread = new Thread(() -> {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

          String line;
          while ((line = reader.readLine()) != null) {
            System.out.println(line);
          }

        }
        catch (IOException e) {
          if (isWorking) {
            System.out.println("Reader exception");
            e.printStackTrace();
          }
        }
      });

      readerThread.setDaemon(true);
      readerThread.start();

      Thread writerThread = new Thread(() -> {

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
          Scanner s = new Scanner(System.in);

          while (isWorking && !socket.isClosed()) {
            if (s.hasNextLine()) {
              String line = s.nextLine();
              if ("exit".equals(line) || "quit".equals(line)) {
                isWorking = false;
                break;
              }
              if (line.length() > 0) {
                writer.write(line + '\n');
                writer.flush();
              }
            }
            else {
              LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(50));
            }
          }

        } catch (IOException e) {
          System.out.println("Writer exception");
          e.printStackTrace();
        }

      });

      writerThread.setDaemon(true);
      writerThread.start();

      //readerThread.join();
      //writerThread.join();

      while (isWorking && !socket.isClosed()) {
        LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(100));
      }

    } catch (ConnectException e) {
      System.out.println(DEFAULT_HOST + ":" + Server.PORT_NUMBER);
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("Socket exception");
      e.printStackTrace();
    }
  }

  /***
   *
   */

}
