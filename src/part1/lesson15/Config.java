package part1.lesson15;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
  private static final String PROPS = "/resources/postgres.properties";
  private static final Config INSTANCE = new Config();

  public static Config get() {
    return INSTANCE;
  }

  private Config() {
    try (InputStream is = Config.class.getResourceAsStream(PROPS)) {
      Properties props = new Properties();
      props.load(is);
//      storage = new SqlStorage(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
    } catch (IOException e) {
      throw new IllegalStateException("Invalid config file " + PROPS);
    }
  }

//  public File getStorageDir() {
//    return storageDir;
//  }
//
//  public Storage getStorage() {
//    return storage;
//  }
}
