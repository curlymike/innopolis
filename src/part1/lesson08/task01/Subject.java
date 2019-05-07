package part1.lesson08.task01;

import java.io.Serializable;

public class Subject implements Serializable {
  private static final long serialVersionUID = 1L;
  private final int number;
  private final String description;

  public Subject(int number, String description) {
    this.number = number;
    this.description = description;
  }

  public int getNumber() {
    return number;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return "Subject{" +
        "number=" + number +
        ", description='" + description + '\'' +
        '}';
  }
}
