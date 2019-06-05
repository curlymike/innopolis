package part1.lesson11.part01;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Для использования Flight Recorder в JMC добавьте флаги:
 * -XX:+UnlockCommercialFeatures -XX:+FlightRecorder
 * Пример 1: -XX:+UseSerialGC -Xmx100m
 * Пример 2: -XX:+UseParallelGC -Xmx100m
 * Пример 3: -XX:+UseConcMarkSweepGC -Xmx100m
 * Пример 3: -XX:+UseG1GC -Xmx100m
 * ---
 * -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -Xmx64m
 *
 */

public class Main {

  public static void main(String[] args) {
    //System.out.println(factorial(5));
    test();
  }

  public static void test() {
    int n = 1000;
    BigInteger[] bigNumbers = new BigInteger[n];
    //for (int i = 0; i < bigNumbers.length; i++) {
    //}
    int i = 0;
    while (true) {
      bigNumbers[i] = factorial(randomNumber());
      if (++i == bigNumbers.length) {
        i = 0;
      }
    }

  }

  public static int randomNumber() {
    return randomNumber(1, 1000);
  }

  public static int randomNumber(int from, int to) {
    SecureRandom sr = new SecureRandom();
    return sr.nextInt(to - from + 1) + from;
  }

  public static BigInteger factorial(int n) {
    BigInteger big = BigInteger.valueOf(1);
    for (int i = 2; i <= n; i++) {
      big = big.multiply(BigInteger.valueOf(i));
    }
    return big;
  }

}
