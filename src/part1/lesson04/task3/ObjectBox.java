package part1.lesson04.task3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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

  public boolean addAllObjects(Collection collection) {
    return list.addAll(collection);
  }

  public Object getObject(int index) {
    return list.get(index);
  }

  public Object setObject(int index, Object obj) {
    return list.set(index, obj);
  }

  public boolean deleteObject(Object obj) {
    return list.remove(obj);
  }

  public int size() {
    return list.size();
  }

  public Iterator iterator() {
    return list.iterator();
  }

  public Stream stream() {
    return list.stream();
  }

  public void dump() {
    System.out.println("ObjectBox" + list);
  }
}
