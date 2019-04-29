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

public class MathBox extends ObjectBox {
//  private final List<Number> list;
  private final ObjectBox box = new ObjectBox();

  public MathBox(Number[] numbers) {

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
    Iterator<Number> iter = box.iterator();
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

  public boolean remove(Integer n) {
    return box.deleteObject(n);
  }

//  @Override
//  public boolean equals(Object o) {
//    if (this == o) return true;
//    if (o == null || getClass() != o.getClass()) return false;
//    MathBox mathBox = (MathBox) o;
//    return list.equals(mathBox.list);
//  }

//  @Override
//  public int hashCode() {
//    list.equals(null);
//    return list.hashCode();
//  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    Iterator<Number> iter = box.iterator();
    sb.append('[');
    for (int i = 0; i < box.size(); i++) {
      if (i > 0) {
        sb.append(',');
      }
      sb.append(((Number) box.getObject(i)).doubleValue());
    }
    sb.append(']');
    return "MathBox" + sb;
  }

  private String number(Object obj) {
    if (obj instanceof Byte) {
      return ((Byte) obj).toString();
    } else if (obj instanceof Short) {
      return ((Short) obj).toString();
    } else if (obj instanceof Integer) {
      return ((Integer) obj).toString();
    } else if (obj instanceof Long) {
      return ((Long) obj).toString();
    } else if (obj instanceof Float) {
      return ((Float) obj).toString();
    } else if (obj instanceof Double) {
      return ((Double) obj).toString();
    }
    return obj.toString();
  }

}
