package part1.lesson15.task03;

import part1.lesson15.Common;
import part1.lesson15.DBConnect;

import java.sql.*;

import static part1.lesson15.task02.Main.insertUser;
import static part1.lesson15.task02.Main.insertUsersBatch;

/***
 * ДЗ_13
 *
 * 3) Сделать параметризированную выборку по login_ID и name одновременно
 *
 */

public class Main {

  public static void main(String[] args) throws Exception {
    DriverManager.registerDriver(new org.postgresql.Driver());
    // Удаляю всех пользователей
    Common.deleteAllUsers();
    // Создаю несколько пользователей, чтобы было что выбирать
    insertUser();
    insertUsersBatch(); // static import из task02.Main
    // Получаю из базы пользователя с login_id и name как в параметрах и печатаю в консоль
    printUserByLoginAndName("homer_j", "Homer J Simpson");
  }

  /***
   *
   * @param loginId
   * @param name
   */

  public static void printUserByLoginAndName(String loginId, String name) {
    String query = "SELECT * FROM \"user\" u WHERE u.login_ID = ? AND name = ?";

    try (Connection conn = DBConnect.getConnection();
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

}
