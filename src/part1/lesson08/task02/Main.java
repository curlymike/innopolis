package part1.lesson08.task02;

import java.io.IOException;

/***
 * Предусмотреть работу c любыми типами полей (полями могут быть ссылочные типы).
 */

public class Main {

  public static void main(String[] args) {
    String filePathStr = "data/lesson08/task02_subject.bin";

    Subject subj = new Subject(1, true, "Test subject #1", 100_000L);
    subj.addToList("one");
    subj.addToList("two");
    subj.addToList("three");

    try {
      Serializer.serialize(subj, filePathStr);
      Subject subj2 = Serializer.deSerialize(filePathStr);

      System.out.println(subj);
      System.out.println("---");
      System.out.println(subj2);
      System.out.println("---");
      System.out.println("equals: " + subj.equals(subj));
      System.out.println("---");
      System.out.println(System.identityHashCode(subj));
      System.out.println(System.identityHashCode(subj2));

    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }

  }

}
