package part1.lesson15;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;

/***
 * ДЗ_13
 *
 * 1) Спроектировать базу
 * - Таблица USER содержит поля id, name, birthday, login_ID, city, email, description
 * - Таблица ROLE содержит поля id, name (принимает значения Administration, Clients, Billing), description
 * - Таблица USER_ROLE содержит поля id, user_id, role_id
 * Типы полей на ваше усмотрению, возможно использование VARCHAR(255)
 *
 * 2) Через jdbc интерфейс сделать запись данных(INSERT) в таблицу
 *   a) Используя параметризированный запрос
 *   b) Используя batch процесс
 *
 * 3) Сделать параметризированную выборку по login_ID и name одновременно
 *
 * 4) Перевести connection в ручное управление транзакциями
 *   a) Выполнить 2-3 SQL операции на ваше усмотрение (например, Insert в 3 таблицы – USER, ROLE, USER_ROLE) между sql операциями установить логическую точку сохранения(SAVEPOINT)
 *   b) Выполнить 2-3 SQL операции на ваше усмотрение (например, Insert в 3 таблицы – USER, ROLE, USER_ROLE) между sql операциями установить точку сохранения (SAVEPOINT A), намеренно ввести некорректные данные на последней операции, что бы транзакция откатилась к логической точке SAVEPOINT A
 *
 */

public class Main {

  static final String INSERT_QUERY = "INSERT INTO \"user\" (name, birthday, login_id, city, email, description) VALUES(?, ?, ?, ?, ?, ?)";

  public static void main(String[] args) throws Exception {
    DriverManager.registerDriver(new org.postgresql.Driver());

    printUsers();
    System.out.println("---");
    insertUsersBatch();
    printUsers();
    System.out.println("---");
    printUserByLoginAndName("homer", "Homer");
    printUserByLoginAndName("user3", "User 3");



//    System.out.println("---");
//    int id = insertUser();
//    System.out.println("id=" + id);
//    System.out.println("---");
//    printUsers();
//    System.out.println("---");
//    deleteHomer();
//    printUsers();


  }

  public static void deleteUserById(int userId) {
    String query = "DELETE FROM \"user\" WHERE id = ?";

    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

      ps.setInt(1, userId);
      ps.execute();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void deleteHomer() {
    String query = "DELETE FROM \"user\" WHERE login_id = ?";

    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

      ps.setString(1, "homer");
      ps.execute();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static int insertUser() {
    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
      ps.setString(1, "Homer");
      ps.setTimestamp(2, Timestamp.valueOf("1956-05-12 00:00:00"));
      ps.setString(3, "homer");
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

  public static void insertUsersBatch() {
    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(INSERT_QUERY)) {

      conn.setAutoCommit(false);

      ps.setString(1, "Homer");
      ps.setTimestamp(2, Timestamp.valueOf("1956-05-12 00:00:00"));
      ps.setString(3, "homer");
      ps.setString(4, "Springfield");
      ps.setString(5, "chunkylover53@aol.com");
      ps.setString(6, "Mellow yellow");
      ps.addBatch();

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
      ps.setString(5, "user2@mail.ru");
      ps.setString(6, "User number three");
      ps.addBatch();

      ps.executeBatch();

      conn.commit();
      conn.setAutoCommit(true);

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  public static void printUserByLoginAndName(String loginId, String name) {
    String query = "SELECT * FROM \"user\" u WHERE u.login_ID = ? AND name = ?";

    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

      ps.setString(1, loginId);
      ps.setString(2, name);
      ps.execute();

      try (ResultSet rs = ps.getResultSet()) {
        while (rs.next()) {
          System.out.println(
              rs.getInt("id") + "|" +
                  rs.getString("name") + "|" +
                  rs.getTimestamp("birthday") + "|" +
                  rs.getString("login_id") + "|" +
                  rs.getString("city") + "|" +
                  rs.getString("email") + "|" +
                  rs.getString("description")
          );
        }
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void printUsers() {
    String query = "SELECT u.* FROM \"user\" u";

    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

      ps.execute();

      try (ResultSet rs = ps.getResultSet()) {
        while (rs.next()) {
          System.out.println(
              rs.getInt("id") + "|" +
              rs.getString("name") + "|" +
              rs.getTimestamp("birthday") + "|" +
              rs.getString("login_id") + "|" +
              rs.getString("city") + "|" +
              rs.getString("email") + "|" +
              rs.getString("description")
          );
        }
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static Connection getConnection() throws SQLException {
    Properties props = Config.get().getProperties();
    return DriverManager.getConnection(
        props.getProperty("database.url"),
        props.getProperty("database.username"),
        props.getProperty("database.password"));
  }

  public static void getConnection(String url, String user, String pass) throws IOException {
    Objects.requireNonNull(url);
  }

}
