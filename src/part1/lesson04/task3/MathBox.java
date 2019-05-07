package part1.lesson04.task3;

import java.util.*;
import java.util.stream.Collectors;

/***
 * Написать класс MathBox, реализующий следующий функционал:
 * - Конструктор на вход получает массив Number. Элементы не могут повторяться. Элементы массива внутри объекта раскладываются в подходящую коллекцию (выбрать самостоятельно).
 * - Существует метод summator, возвращающий сумму всех элементов коллекции.
 * - Существует метод splitter, выполняющий поочередное деление всех хранящихся в объекте элементов на делитель, являющийся аргументом метода. Хранящиеся в объекте данные полностью заменяются результатами деления.
 * - Необходимо правильно переопределить методы toString, hashCode, equals, чтобы можно было использовать MathBox для вывода данных на экран и хранение объектов этого класса в коллекциях (например, hashMap). Выполнение контракта обязательно!
 * - Создать метод, который получает на вход Integer и если такое значение есть в коллекции, удаляет его.
 */

public class MathBox<T extends Number> extends ObjectBox {
  private final ObjectBox box = new ObjectBox();

  public MathBox(T[] numbers) {
    Arrays.asList(numbers).stream().filter(n -> n != null).forEach((n) -> {
      if (n != null) {
        box.addObject(n);
      }
    });
  }

  /***
   * Возвращает сумму всех элементов коллекции
   * @return
   */

  public double summator() {
    double sum = 0.0;

    Iterator<T> iter = box.iterator();
    while (iter.hasNext()) {
      sum += iter.next().doubleValue();
    }
    return sum;
  }

  /***
   * Делит все элементы коллекции на div
   * @param div
   */

  public void splitter(Number div) {
    // Это правильно или нет смысла заводить переменную?
    double divDouble = div.doubleValue();
    for (int i = 0; i < box.size(); i++) {
      if (box.getObject(i) != null) {
        box.setObject(i, ((Number) box.getObject(i)).doubleValue() / divDouble);
      }
    }
  }

  /***
   * Удаляет элемент n из коллекции, возвращает true если в коллекции был элемент n
   * @param n
   * @return
   */

  public boolean remove(T n) {
    return box.deleteObject(n);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MathBox mathBox = (MathBox) o;
    return box.equals(mathBox.box);
  }

  @Override
  public int hashCode() {
    return box.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("MathBox[");
    for (int i = 0; i < box.size(); i++) {
      if (i > 0) {
        sb.append(',');
      }
      sb.append(((Number) box.getObject(i)).doubleValue());
    }
    sb.append(']');
    return sb.toString();
  }

}
