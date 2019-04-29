package part1.lesson04;

public class GenericBox<T> {
  private T value;

  public GenericBox(T value) {
    this.value = value;
  }

  public T getValue() { return value; }

  public void setVaule(T value) { this.value = value; }

}
