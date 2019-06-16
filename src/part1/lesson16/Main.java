package part1.lesson16;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

  private static Logger logger = LogManager.getLogger(Main.class);

  public static void main(String[] args) {

    logger.debug("Debug log message");

    logger.info("Info log message");

    logger.error("Error log message");

  }

}
