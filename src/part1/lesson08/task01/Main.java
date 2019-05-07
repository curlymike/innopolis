package part1.lesson08.task01;

import java.io.*;

/***
 * Необходимо разработать класс, реализующий следующие методы:
 *   void serialize (Object object, String file);
 *   Object deSerialize(String file);
 * Методы выполняют сериализацию объекта Object в файл file и десериализацию объекта из этого файла.
 * Обязательна сериализация и десериализация "плоских" объектов (все поля объекта - примитивы, или String).
 */

public class Main {

  public static void main(String[] args) throws Exception {
    fiddle();
  }

  public static void fiddle(Object obj) throws Exception {
    System.out.println("Serializable: " + obj instanceof Serializable);
  }

  public static void fiddle() throws Exception {
    //Subject subj = new Subject(1, "Test");
    Object subj = new Subject(1, "Test");

    System.out.println("Serializable: " + subj instanceof Serializable);
    //fiddle(subj);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
      oos.writeObject(subj);
    }

    //System.out.write(baos.toByteArray());
    //System.out.println();
    System.out.println("------------");

    try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))) {
      Subject subj2 = (Subject) ois.readObject();
      System.out.println(subj2);
    }


  }

}
