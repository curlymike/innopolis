package part1.lesson15.task04b;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import part1.lesson15.Common;
import part1.lesson16.DBConnect;

import java.sql.*;

/***
 * ДЗ_13
 *
 * 4) Перевести connection в ручное управление транзакциями
 *   b) Выполнить 2-3 SQL операции на ваше усмотрение (например, Insert в 3 таблицы – USER, ROLE, USER_ROLE)
 *      между sql операциями установить точку сохранения (SAVEPOINT A),
 *      намеренно ввести некорректные данные на последней операции, чтобы
 *      транзакция откатилась к логической точке SAVEPOINT A
 *
 */

public class Main {

  private static final Logger LOG = LogManager.getLogger(Main.class);

  static final String INSERT_USER_QUERY = "INSERT INTO \"user\" (name, birthday, login_id, city, email, description) VALUES(?, ?, ?, ?, ?, ?)";
  static final String INSERT_ROLE_QUERY = "INSERT INTO user_role (user_id, role_id) VALUES(?, ?)";

  public static void main(String[] args) throws Exception {
    DriverManager.registerDriver(new org.postgresql.Driver());

    Common.deleteAllUsers();

    task4b();

    Common.printUsers();
    Common.countUsers();

    System.out.println("------------");
    System.out.println("Roles:");

    Common.printRoles();

  }

  /**
   * Достаёт id из PreparedStatement и возвращает значение
   * @param ps - PreparedStatement
   * @return значение поля id записи, созданной в результате выполнения PreparedStatement.
   * @throws SQLException
   */

  /***
   * После создания записи в таблице user выполняется запись
   * в таблицу user_role и намеренно вызывается ошибка, что
   * приводит к откату транзакции и запись о пользователе в
   * таблице user не создаётся.
   */

  public static void task4b() {
    try (Connection conn = DBConnect.getConnection();
         PreparedStatement psUser = conn.prepareStatement(INSERT_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
         PreparedStatement psRole = conn.prepareStatement(INSERT_ROLE_QUERY)) {

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

        int userId = Common.getLastInsertId(psUser);

        psRole.setInt(1, userId);
        psRole.setInt(2, Common.ROLE_CLIENTS);
        psRole.execute();

        // Попытка повторно записать в таблицу user_role приведёт к ошибке
        psRole.setInt(1, userId);
        psRole.setInt(2, Common.ROLE_CLIENTS);
        psRole.execute();

        conn.commit();

      } catch (SQLException e) {
        conn.rollback(savepoint);
        //e.printStackTrace();
        System.out.println("------------------------");
        System.out.println(e.getMessage());
        System.out.println("------------------------");
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }



}
