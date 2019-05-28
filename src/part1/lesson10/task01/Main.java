package part1.lesson10.task01;

public class Main {
  public static void main(String[] args) {
    String[] str = "@Mike Hello, there, man!".split("\\s", 2);
    System.out.println(str[0]);
    System.out.println(str[1]);
  }
}
