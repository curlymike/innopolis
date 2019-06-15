package part1.lesson08.task02;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Subject implements Serializable {
  private static final long serialVersionUID = 1L;

  private final int number;
  private final boolean status;
  private final String description;
  private final long value;

  private final List<String> list = new ArrayList<>();

  public Subject(int number, boolean status, String description, long value) {
    this.number = number;
    this.status = status;
    this.description = description;
    this.value = value;
  }

  public int getNumber() {
    return number;
  }

  public boolean status() {
    return status;
  }

  public String getDescription() {
    return description;
  }

  public long getValue() {
    return value;
  }

  public boolean addToList(String str) {
    return list.add(str);
  }

  public boolean removeFromList(String str) {
    return list.remove(str);
  }

  @Override
  public String toString() {
    return "Subject{" +
        "number=" + number +
        ", status=" + status +
        ", description='" + description + '\'' +
        ", value=" + value +
        ", list=" + list +
        '}';
  }
}
