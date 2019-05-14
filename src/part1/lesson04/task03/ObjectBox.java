package part1.lesson04.task03;

import java.util.ArrayList;
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

public class ObjectBox<T extends Number> {
  private final List<T> list = new ArrayList();

  public boolean addObject(T obj) {
    return list.add(obj);
  }

  public T getObject(int index) {
    return list.get(index);
  }

  public T setObject(int index, T obj) {
    return list.set(index, obj);
  }

  public boolean deleteObject(T obj) {
    return list.remove(obj);
  }

  public int size() {
    return list.size();
  }

  public Iterator<T> iterator() {
    return list.iterator();
  }

  public Stream<T> stream() {
    return list.stream();
  }

  public String dump() {
    return list.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ObjectBox objectBox = (ObjectBox) o;
    return list.equals(objectBox.list);
  }

  @Override
  public int hashCode() {
    return list.hashCode();
  }

  @Override
  public String toString() {
    return "ObjectBox{" +
        "list=" + list +
        '}';
  }

}
