package part1.lesson15.task04a;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import part1.lesson15.Common;
import part1.lesson16.DBConnect;

import java.sql.*;

/***
 * ДЗ_13
 *
 * 4) Перевести connection в ручное управление транзакциями
 *   a) Выполнить 2-3 SQL операции на ваше усмотрение (например, Insert в 3 таблицы – USER, ROLE, USER_ROLE) между sql операциями установить логическую точку сохранения(SAVEPOINT)
 *
 */

public class Main {

  private static final Logger LOG = LogManager.getLogger(Main.class);

  static final String INSERT_USER_QUERY = "INSERT INTO \"user\" (name, birthday, login_id, city, email, description) VALUES(?, ?, ?, ?, ?, ?)";
  static final String INSERT_ROLE_QUERY = "INSERT INTO user_role (user_id, role_id) VALUES(?, ?)";

  public static void main(String[] args) throws Exception {
    DriverManager.registerDriver(new org.postgresql.Driver());

    Common.deleteAllUsers();

    task4a();

    Common.printUsers();
    Common.countUsers();

    System.out.println("------------");
    System.out.println("Roles:");

    Common.printRoles();

  }

  public static int task4aGetUserId(PreparedStatement psUser) throws SQLException {
    try (ResultSet rs = psUser.getGeneratedKeys()) {
      if (rs.next()) {
        return rs.getInt(1);
      }
    }
    throw new SQLException("Невозможно получить ID");
  }

  /***
   *
   */

  public static void task4a() {
    try (Connection conn = DBConnect.getConnection();
         PreparedStatement psUser = conn.prepareStatement(INSERT_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
         PreparedStatement psRole = conn.prepareStatement(INSERT_ROLE_QUERY)) {

      LOG.info("task4a()");

      conn.setAutoCommit(false);
      Savepoint savepoint = conn.setSavepoint();

      try {
        psUser.setString(1, "User 4");
        psUser.setTimestamp(2, Timestamp.valueOf("1985-03-01 00:00:00"));
        psUser.setString(3, "user4");
        psUser.setString(4, "Moscow");
        psUser.setString(5, "user4@mail.ru");
        psUser.setString(6, "User number four");
        psUser.execute();

        psRole.setInt(1, task4aGetUserId(psUser));
        psRole.setInt(2, Common.ROLE_CLIENTS);
        psRole.execute();

        psUser.setString(1, "User 5");
        psUser.setTimestamp(2, Timestamp.valueOf("1986-08-11 00:00:00"));
        psUser.setString(3, "user5");
        psUser.setString(4, "Tomsk");
        psUser.setString(5, "user5@mail.ru");
        psUser.setString(6, "User number five");
        psUser.execute();

        psRole.setInt(1, task4aGetUserId(psUser));
        psRole.setInt(2, Common.ROLE_CLIENTS);
        psRole.execute();

        psUser.setString(1, "Admin 1");
        psUser.setTimestamp(2, Timestamp.valueOf("1985-03-01 00:00:00"));
        psUser.setString(3, "admin1");
        psUser.setString(4, "Moscow");
        psUser.setString(5, "admin1@mail.ru");
        psUser.setString(6, "Admin number one");
        psUser.execute();

        psRole.setInt(1, task4aGetUserId(psUser));
        psRole.setInt(2, Common.ROLE_ADMINISTRATION);
        psRole.execute();

        psUser.setString(1, "Admin 2");
        psUser.setTimestamp(2, Timestamp.valueOf("1985-03-01 00:00:00"));
        psUser.setString(3, "admin2");
        psUser.setString(4, "Moscow");
        psUser.setString(5, "admin2@mail.ru");
        psUser.setString(6, "Admin number two, also has Billing role");
        psUser.execute();

        int userId = task4aGetUserId(psUser);

        psRole.setInt(1, userId);
        psRole.setInt(2, Common.ROLE_ADMINISTRATION);
        psRole.execute();

        psRole.setInt(1, userId);
        psRole.setInt(2, Common.ROLE_BILLING);
        psRole.execute();

        conn.commit();

      } catch (SQLException e) {
        conn.rollback(savepoint);
        LOG.info("task4a(): rollback");
        LOG.error(e);
      }

    } catch (SQLException e) {
      LOG.error(e);
    }
  }


}
