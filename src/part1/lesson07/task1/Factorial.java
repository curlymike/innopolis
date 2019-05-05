package part1.lesson07.task1;

import java.math.BigInteger;

public class Factorial {
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
