package part1.lesson04.task03;

import java.util.*;

/***
 * Доработать классы MathBox и ObjectBox таким образом, чтобы MathBox был наследником ObjectBox. Необходимо сделать такую связь, правильно распределить поля и методы. Функциональность в целом должна сохраниться. При попытке положить Object в MathBox должно создаваться исключение.
 */

public class MathBox extends ObjectBox<Number> {

  public MathBox(Number[] numbers) {
    Arrays.asList(numbers).stream().filter(n -> n != null).distinct().forEach(n -> addObject(n));
  }

  /***
   * Возвращает сумму всех элементов коллекции
   * @return
   */

  public double summator() {
    return stream().mapToDouble(Number::doubleValue).sum();
  }

  /***
   * Делит все элементы коллекции на div
   * @param div
   */

  public void splitter(Number div) {
    for (int i = 0; i < size(); i++) {
      if (getObject(i) != null) {
        setObject(i, new Double(getObject(i).doubleValue() / div.doubleValue()));
      }
    }
  }

  /***
   * Удаляет элемент n из коллекции, возвращает true если в коллекции был элемент n
   * @param n
   * @return
   */

  public boolean remove(Integer n) {
    return deleteObject(n);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return "MathBox[" + dump() + ']';
  }

}
