package part1.lesson16;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Вспомогательные функции общие для нескольких пунктов ДЗ
 */

public class Common {

  public static final int ROLE_ADMINISTRATION = 1;
  public static final int ROLE_CLIENTS = 2;
  public static final int ROLE_BILLING = 3;

  /**
   * Достаёт id из PreparedStatement и возвращает значение
   * При помощи этого метода можно получить ID записи после INSERT операции
   * @param ps - PreparedStatement
   * @return значение поля id записи, созданной в результате выполнения PreparedStatement.
   * @throws SQLException
   */

  public static int getLastInsertId(PreparedStatement ps) throws SQLException {
    try (ResultSet rs = ps.getGeneratedKeys()) {
      if (rs.next()) {
        return rs.getInt(1);
      }
    }
    throw new SQLException("Невозможно получить ID");
  }

  /***
   * Удаляет пользователя по id (не используется, но оставил для примера)
   * @param userId
   */

  public static void deleteUserById(int userId) {
    String query = "DELETE FROM \"user\" WHERE id = ?";

    try (Connection conn = DBConnect.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

      ps.setInt(1, userId);
      ps.execute();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /***
   * Удаляет пользователя по login_id (не используется, но оставил для примера)
   * @param loginId
   */

  public static void deleteUserByLoginId(String loginId) {
    String query = "DELETE FROM \"user\" WHERE login_id = ?";

    try (Connection conn = DBConnect.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

      ps.setString(1, loginId);
      ps.execute();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /***
   * Удаляет всех пользователей
   */

  public static void deleteAllUsers() {
    String query = "DELETE FROM \"user\"";

    try (Connection conn = DBConnect.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

      ps.execute();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /***
   * Печатает содержимое таблицы users в консоль
   */

  public static void printUsers() {
    String query = "SELECT u.* FROM \"user\" u";

    try (Connection conn = DBConnect.getConnection();
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
              rs.getString("description"));
        }
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /***
   * Печатает роли пользователей в консоль
   */

  public static void printRoles() {
    String query = "SELECT u.id AS user_id, u.name AS user_name, r.id AS role_id, r.name AS role_name" +
        " FROM user_role u_r" +
        " LEFT JOIN role r ON u_r.role_id = r.id" +
        " LEFT JOIN \"user\" u ON u_r.user_id = u.id";

    try (Connection conn = DBConnect.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

      ps.execute();

      try (ResultSet rs = ps.getResultSet()) {
        while (rs.next()) {
          System.out.println(
              rs.getInt("user_id") + "|" +
              rs.getString("user_name") + "|" +
              rs.getString("role_name") + "|" +
              rs.getInt("role_id"));
        }
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /***
   * Печатает в консоль количество записей в таблице users
   */

  public static void countUsers() {
    String query = "SELECT COUNT(*) AS count FROM \"user\"";

    try (Connection conn = DBConnect.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

      ps.execute();

      try (ResultSet rs = ps.getResultSet()) {
        while (rs.next()) {
          System.out.println("Users: " + rs.getInt("count"));
        }
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
