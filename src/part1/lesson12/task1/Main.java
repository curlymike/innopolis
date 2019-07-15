package part1.lesson12.task1;

/**
 * Задание 1.
 * Необходимо создать программу, которая продемонстрирует утечку памяти в Java. При этом объекты должны не только создаваться, но и периодически частично удаляться, чтобы GC имел возможность очищать часть памяти. Через некоторое время программа должна завершиться с ошибкой OutOfMemoryError c пометкой Java Heap Space.
 *  Внимание! Программу необходимо со следующим параметром:
 *  -Xmx64m
 */

public class Main {
  public static void main(String[] args) {
    System.out.println("Hello");
    System.out.println("---");
    generateMyIntArray(50);
  }

  /**
   * Создаёт массив int[] с каждым циклом в 10 раз больше предыдущего,
   * что стремительно исчерпывает доступную память. Второй (вложенный)
   * цикл немного заполняет массив на всякий случай, хотя можно этого и не делать.
   * @param limit
   */

  public static void generateMyIntArray(int limit) {
    int multiplier = 100;
    for (int i = 1; i < limit; i++) {
      int[] myIntList = new int[multiplier];
      System.out.println("Round " + i + " Free Memory: " + Runtime.getRuntime().freeMemory() + "; myIntList.length=" + String.format("%,d", myIntList.length));
      for (int j = i; j > 1; j--) {
        myIntList[j] = i;
      }
      multiplier = multiplier * 10;
    }
  }


}
