package part1.lesson15;

import java.io.IOException;
import java.io.InputStream;

/***
 * ДЗ_13
 *
 * 2) Через jdbc интерфейс сделать запись данных(INSERT) в таблицу
 * a) Используя параметризированный запрос
 * b) Используя batch процесс
 *
 */

public class Main {
  public static void main(String[] args) throws Exception {
    //Class.class.getResource("/resources/test.txt")
    getConnection();
  }

  public static void getConnection() throws Exception {
    //try (InputStream is = Main.class.getResourceAsStream("/resources/lesson15/postgres.properties")) {
    //  System.out.println(is);
    //}
    // Does not work...
    System.out.println(Main.class.getResource("/resources/lesson15/postgres.properties"));
    System.out.println(Main.class.getResource("lesson15/postgres.properties"));
  }

}
