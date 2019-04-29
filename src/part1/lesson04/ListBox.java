package part1.lesson04;

import java.util.List;

public class ListBox<T> {
  List<T> list;
  public <T> void addAll(List<? extends T> list) {
    //this.list.addAll(list);
    //this.list.add(list.get(0));
  }
}
