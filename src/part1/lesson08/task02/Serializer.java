package part1.lesson08.task02;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Serializer {

  /***
   * Сериализует объект subj в файл file
   * @param subj
   * @param file
   * @throws IOException
   */

  public static void serialize(Subject subj, String file) throws IOException {
    try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(file)))) {
      oos.writeObject(subj);
    }
  }

  /***
   * Десериализует объект класса Subject из файла file
   * @param file
   * @return
   * @throws IOException
   */

  public static Subject deSerialize(String file) throws IOException, ClassNotFoundException {
    try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(file)))) {
      return (Subject) ois.readObject();
    }
  }

}
