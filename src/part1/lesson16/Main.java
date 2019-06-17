package part1.lesson16;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

// TODO: удалить этот файл

public class Main {

  private static Logger logger = LogManager.getLogger(Main.class);

  public static void main(String[] args) {

    Class clazz = MethodHandles.lookup().lookupClass();
    System.out.println("clazz: " + clazz.getName());

    if (true) return;

//    logger.debug("Debug log message");

//    logger.info("Info log message");

    logger.warn("Warn log message");

    //logger.error("Error with exception", new IOException("This is an artificial exception for testing. Это "));
    logger.error("Error with exception", new IOException("Это искусственное исключение для теста."));

//    logger.error("Error log message");

  }

}
