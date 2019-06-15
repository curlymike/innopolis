package part1.lesson08.task01;

/***
 * Необходимо разработать класс, реализующий следующие методы:
 *   void serialize (Object object, String file);
 *   Object deSerialize(String file);
 * Методы выполняют сериализацию объекта Object в файл file и десериализацию объекта из этого файла.
 * Обязательна сериализация и десериализация "плоских" объектов (все поля объекта - примитивы, или String).
 */

public class Main {

  public static void main(String[] args) throws Exception {
    String filePathStr = "data/lesson08/task01_subject.bin";

    Subject subj = new Subject(1, true, "Test subject #1", 100_000L);

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
  }

}
