package part1.lesson04.task2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/***
 * Задание
 * Создать класс ObjectBox, который будет хранить коллекцию Object.
 * - У класса должен быть метод addObject, добавляющий объект в коллекцию.
 * - У класса должен быть метод deleteObject, проверяющий наличие объекта в коллекции и при наличии удаляющий его.
 * - Должен быть метод dump, выводящий содержимое коллекции в строку.
 */

public class ObjectBox {
  private final List list = new ArrayList();

  public boolean addObject(Object obj) {
    return list.add(obj);
  }

  public boolean deleteObject(Object obj) {
    return list.remove(obj);
  }

  public String dump() {
    StringBuilder sb = new StringBuilder();
    sb.append("ObjectBox[");
    for (int i = 0; i < list.size(); i++) {
      if (i > 0) {
        sb.append(',');
      }
      sb.append(list.get(i));
    }
    sb.append(']');
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ObjectBox box = (ObjectBox) o;
    return list.equals(box.list);
  }

  @Override
  public int hashCode() {
    return list.hashCode();
  }

}
