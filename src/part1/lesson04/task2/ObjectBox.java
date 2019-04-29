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
  //private final List<Integer> list2 = new ArrayList<>();

  public boolean addObject(Object obj) {
    return list.add(obj);
  }

  public boolean deleteObject(Object obj) {
    return list.remove(obj);
  }

  public void dump() {
  }
}
