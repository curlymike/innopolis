package hw2.task2;

import java.security.SecureRandom;

/***
 * Составить программу, генерирующую N случайных чисел.
 * Для каждого числа k вычислить квадратный корень q.
 * Если квадрат целой части q числа равен k, то вывести это число на экран.
 * Предусмотреть что первоначальные числа могут быть отрицательные,
 * в этом случае генерировать исключение.
 */

public class Main {
  public static void main(String[] args) {
    int n = 20;
    SecureRandom rand = new SecureRandom();
    for (int i = 0; i < n; i++) {
      int k = rand.nextInt(1000);
      if (k < 0) {
        throw new ArithmeticException();
      } else {
        double sqrt = Math.sqrt(k);
        int sqrt2 = (int) sqrt;
        if (sqrt2 * sqrt2 == k) {
          System.out.println(k);
        }
        if ((int) sqrt * (int) sqrt == k) {
          System.out.println(k + " +++");
        } else {
          System.out.println(k + " ---");
        }
      }
    }
  }
}
