package part1.lesson04.task03;

/***
 * Доработать классы MathBox и ObjectBox таким образом, чтобы MathBox был наследником ObjectBox. Необходимо сделать такую связь, правильно распределить поля и методы. Функциональность в целом должна сохраниться. При попытке положить Object в MathBox должно создаваться исключение.
 */

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
