package part1.lesson11.part01;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;

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
    //test2();
  }

  /***
   *
   */

  public static void test2() {
    BigInteger[] testArr = new BigInteger[10];
    int n = 100;
    for (int i = 0; i < testArr.length; i++) {
      testArr[i] = factorial(n++);
    }
    for (BigInteger big : testArr) {
      System.out.println(trimBigInt(big));
    }
    System.out.println("------------");
    testArr = grow(testArr, testArr.length + 10);
    for (int i = 0; i < testArr.length; i++) {
      if (testArr[i] == null) {
        testArr[i] = factorial(n++);
      }
    }
    for (BigInteger big : testArr) {
      System.out.println(trimBigInt(big));
    }
  }

  public static String trimBigInt(BigInteger big) {
    return trimBigInt(big, 40);
  }

  public static String trimBigInt(BigInteger big, int maxLength) {
    String str = big.toString();
    if (str.length() < maxLength) {
      return str;
    }
    String glue = " ... ";
    String tail = " [" + str.length() + ']';
    int numLength = (maxLength - glue.length() - tail.length());
    int lim = numLength / 2;
    return str.substring(0, lim) + glue + str.substring(str.length() - numLength + lim) + tail;
  }

  public static BigInteger[] grow(BigInteger[] arr, int newSize) {
    if (arr.length >= newSize) {
      return arr;
    }
    return Arrays.copyOf(arr, newSize);
  }

  /***
   *
   */

  public static void test() {
    int initialSize = 1000;
    int growBy = 500;
    BigInteger[] bigNumbers = new BigInteger[initialSize];
    //for (int i = 0; i < bigNumbers.length; i++) {}
    int i = 0;
    int f = 0;
    int fLimit = 2;
    while (true) {
      bigNumbers[i] = factorial(randomNumber());
      if (++i == bigNumbers.length) {
        i = 0;
        System.out.println(bigNumbers.length + " factorials has been calculated.");
        if (++f == fLimit) {
          f = 0;
          // grow bigNumbers array here
          bigNumbers = grow(bigNumbers, bigNumbers.length + growBy);
        }
      }
    }

  }

  public static int randomNumber() {
    return randomNumber(1000, 1100);
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
