package part1.lesson08.task01;

import java.io.Serializable;

public class Subject implements Serializable {
  private static final long serialVersionUID = 1L;

  private final int number;
  private final boolean status;
  private final String description;
  private final long value;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Subject subject = (Subject) o;

    if (number != subject.number) return false;
    if (status != subject.status) return false;
    if (value != subject.value) return false;
    return description != null ? description.equals(subject.description) : subject.description == null;
  }

  @Override
  public String toString() {
    return "Subject{" +
        "number=" + number +
        ", status=" + status +
        ", description='" + description + '\'' +
        ", value=" + value +
        '}';
  }
}
