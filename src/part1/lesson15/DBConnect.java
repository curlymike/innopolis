package part1.lesson15;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnect {
  public static Connection getConnection() throws SQLException {
    Properties props = Config.get().getProperties();
    return DriverManager.getConnection(
        props.getProperty("database.url"),
        props.getProperty("database.username"),
        props.getProperty("database.password"));
  }
}
