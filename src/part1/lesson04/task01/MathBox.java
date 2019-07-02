package part1.lesson04.task01;

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

public class MathBox {
  private final List<Number> list;

  public MathBox(Number[] numbers) {
    list = new ArrayList<>(
      Arrays.asList(numbers).stream().filter(n -> n != null).collect(Collectors.toCollection(TreeSet::new))
    );
  }

  /***
   * Возвращает сумму всех элементов коллекции
   * @return
   */

  public double summator() {
    return list.stream().mapToDouble(Number::doubleValue).sum();
  }

  /***
   * Делит все элементы коллекции на div
   * @param div
   */

  public void splitter(Number div) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i) != null) {
        list.set(i, new Double(list.get(i).doubleValue() / div.doubleValue()));
      }
    }
  }

  /***
   * Удаляет элемент n из коллекции, возвращает true если в коллекции был элемент n
   * @param n
   * @return
   */

  public boolean remove(Integer n) {
    return list.remove(n);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MathBox mathBox = (MathBox) o;
    return list.equals(mathBox.list);
  }

  @Override
  public int hashCode() {
    return list.hashCode();
  }

  @Override
  public String toString() {
    return "MathBox" + list;
  }
}
