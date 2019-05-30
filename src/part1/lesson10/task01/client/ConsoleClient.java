package part1.lesson10.task01.client;

import part1.lesson10.task01.server.Server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class ConsoleClient {

  private static volatile boolean isWorking = true;

  public ConsoleClient() {
  }

  public static void main(String[] args) {

    try (Socket socket = new Socket("127.0.0.1", Server.PORT_NUMBER)) {

      Thread readerThread = new Thread(() -> {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

          String line;
          while ((line = reader.readLine()) != null) {
            System.out.println(line);
          }

        } catch (IOException e) {
          if (isWorking) {
            e.printStackTrace();
          }
        }
      });

      readerThread.setDaemon(true);
      readerThread.start();

      Thread writerThread = new Thread(() -> {

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
          Scanner s = new Scanner(System.in);

//          InputStreamReader isr = new InputStreamReader(System.in);
//          if (isr.ready()) {
//
//          } else {
//            LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(50));
//          }

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


//      Scanner s = new Scanner(System.in);
//
//      while (true) {
//        String line = s.nextLine();
//        if ("exit".equals(line) || "quit".equals(line)) {
//          break;
//        }
//        if (line.length() > 0) {
//          writer.write(line + '\n');
//          writer.flush();
//        }
//      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /***
   *
   */

}
