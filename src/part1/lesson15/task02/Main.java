package part1.lesson15.task02;

import part1.lesson15.Common;
import part1.lesson15.DBConnect;

import java.security.SecureRandom;
import java.sql.*;
import java.time.LocalDateTime;

/***
 * ДЗ_13
 *
 * 2) Через jdbc интерфейс сделать запись данных(INSERT) в таблицу
 *   a) Используя параметризированный запрос
 *   b) Используя batch процесс
 *
 */

public class Main {

  static final String INSERT_USER_QUERY = "INSERT INTO \"user\" (name, birthday, login_id, city, email, description) VALUES(?, ?, ?, ?, ?, ?)";

  public static void main(String[] args) throws Exception {
    DriverManager.registerDriver(new org.postgresql.Driver());

    Common.deleteAllUsers();

    insertUser();
    Common.printUsers();

    System.out.println("---");

    //insertUsersBatch();
    insertUsersBatch2();
    Common.printUsers();


  }

  /***
   * 2a) INSERT в таблицу используя параметризированный запрос
   * @return id созданного пользователя, возвращает -1 в случае ошибки.
   */

  public static int insertUser() {
    try (Connection conn = DBConnect.getConnection();
         PreparedStatement ps = conn.prepareStatement(INSERT_USER_QUERY, Statement.RETURN_GENERATED_KEYS)) {
      ps.setString(1, "Homer J Simpson");
      ps.setTimestamp(2, Timestamp.valueOf("1956-05-12 00:00:00"));
      ps.setString(3, "homer_j");
      ps.setString(4, "Springfield");
      ps.setString(5, "chunkylover53@aol.com");
      ps.setString(6, "Mellow yellow");
      ps.execute();
      try (ResultSet rs = ps.getGeneratedKeys()) {
        if (rs.next()) {
          return rs.getInt(1);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return -1;
  }

  /**
   * 2b) INSERT в таблицу используя batch процесс
   */

  public static void insertUsersBatch() {
    try (Connection conn = DBConnect.getConnection();
         PreparedStatement ps = conn.prepareStatement(INSERT_USER_QUERY)) {

      conn.setAutoCommit(false);

      ps.setString(1, "User 1");
      ps.setTimestamp(2, Timestamp.valueOf("1980-03-01 00:00:00"));
      ps.setString(3, "user1");
      ps.setString(4, "Moscow");
      ps.setString(5, "user1@mail.ru");
      ps.setString(6, "User number one");
      ps.addBatch();

      ps.setString(1, "User 2");
      ps.setTimestamp(2, Timestamp.valueOf("1981-04-05 00:00:00"));
      ps.setString(3, "user2");
      ps.setString(4, "Saint-Petersburg");
      ps.setString(5, "user2@mail.ru");
      ps.setString(6, "User number two");
      ps.addBatch();

      ps.setString(1, "User 3");
      ps.setTimestamp(2, Timestamp.valueOf("1982-05-10 00:00:00"));
      ps.setString(3, "user3");
      ps.setString(4, "Kazan");
      ps.setString(5, "user3@mail.ru");
      ps.setString(6, "User number three");
      ps.addBatch();

      ps.executeBatch();

      conn.commit();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * То же самое что пункт 2b) но чуть более креативно :-)
   */

  public static void insertUsersBatch2() {
    try (Connection conn = DBConnect.getConnection();
         PreparedStatement ps = conn.prepareStatement(INSERT_USER_QUERY)) {

      conn.setAutoCommit(false);

      String[] cities = new String[]{"Moscow", "Saint-Petersburg", "Kazan", "Innopolis", "Sochi"};

      SecureRandom secureRandom = new SecureRandom();

      LocalDateTime dateTime = LocalDateTime.of(1980, 1, 1, 0, 0);

      for (int i = 1; i <= 20; i++) {
        ps.setString(1, "User " + i);
        ps.setTimestamp(2, Timestamp.valueOf(dateTime));
        ps.setString(3, "user" + i);
        ps.setString(4, cities[secureRandom.nextInt(cities.length)]);
        ps.setString(5, "user" + i + "@mail.ru");
        ps.setString(6, "User number " + i);
        ps.addBatch();
        dateTime = dateTime.plusDays(10);
      }

      ps.executeBatch();

      conn.commit();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
