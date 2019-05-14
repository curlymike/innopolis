package part1.lesson04.task01;

public class Main {
  public static void main(String[] args) {
    MathBox mathBox = new MathBox(new Number[]{2, 4, 6, 8, 10});
    System.out.println(mathBox);
    System.out.println(mathBox.summator());

    mathBox.splitter(2);
    System.out.println(mathBox);
    System.out.println(mathBox.summator());
  }
}
