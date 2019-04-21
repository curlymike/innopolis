package hw2.task1;

public class Main {

  // Написать программу ”Hello, World!”. В ходе выполнения программы она должна выбросить исключение и завершиться с ошибкой.
  // 1) Смоделировав ошибку «NullPointerException»
  // 2) Смоделировав ошибку «ArrayIndexOutOfBoundsException»
  // 3) Вызвав свой вариант ошибки через оператор throw

  public static void main(String[] args) {
    err1();
    err2();
    err3();
  }

  // 1) NullPointerException
  public static void err1() {
    "".contains(null);
  }

  // 2) ArrayIndexOutOfBoundsException
  public static void err2() {
    int[] n = new int[5];
    int m = n[5];
  }

  // 3) свой вариант ошибки через оператор throw
  public static void err3() {
    throw new RuntimeException("Неизвестная ошибка, обратитесь к администратору.");
  }

}
