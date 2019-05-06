package part1.lesson07.task1;

import java.math.BigInteger;

public class Factorial {

  private final int number;
  private final BigInteger value;

  public Factorial(int number, BigInteger value) {
    this.number = number;
    this.value = value;
  }

  /***
   * Getters
   */

  public int getNumber() {
    return number;
  }

  public BigInteger getValue() {
    return value;
  }

  /**
   * Вычисляет факториал числа n
   * @param n
   * @return
   */

  public static BigInteger compute(int n) {
    return compute(1, n);
  }

  public static BigInteger compute(int start, int n) {
    BigInteger big = BigInteger.valueOf(start);
    for (int i = start + 1; i <= n; i++) {
      big = big.multiply(BigInteger.valueOf(i));
    }
    return big;
  }

}
