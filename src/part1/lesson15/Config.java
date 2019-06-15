package part1.lesson15;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
  //private static final String PROPS_FILE = "postgres.properties";
  private static final String PROPS_FILE = "/postgres.properties";
  private static final Config INSTANCE = new Config();

  private final Properties props;

  public static Config get() {
    return INSTANCE;
  }

  private Config() {
    try (InputStream is = Config.class.getResourceAsStream(PROPS_FILE)) {
      if (is != null) {
        props = new Properties();
        props.load(is);
        if (!props.containsKey("database.url")) {
          throw new IllegalStateException("В файле конфигурации отсутствует database.url " + PROPS_FILE);
        }
      } else {
        throw new IOException("Невозможно загрузить файл конфигурации " + PROPS_FILE);
      }
    } catch (IOException e) {
      throw new IllegalStateException("Невозможно загрузить файл конфигурации " + PROPS_FILE);
    }
  }

  public Properties getProperties() {
    return props;
  }
}
