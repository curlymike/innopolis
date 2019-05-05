package part1.lesson07.task1;

import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Fiddle {

  static long time;

  public static void main(String[] args) {
    test2();
  }

  public static void test2() {
    int[] numbers = new int[]{11746,12150,18240,17511,14632,17321,7206,8247,8769,8446,15076,12696,18932,15998,19244,16460,1143,6924,7687,25000,226,14821,2592,5423,5451,6305,17535,7664,12456,15510,7646,19515,12366,11344,7002};

    System.out.println("Warm up run... ");
    timerStart();
    for (int n : numbers) {
      factorialNaive(n);
    }

    long timer = timerGet();

    System.out.println("done in " + timer + "ms");

    System.out.println("---");

    BigInteger[] results = new BigInteger[numbers.length];

    timerStart();

    for (int i = 0; i < numbers.length; i++) {
      results[i] = factorialNaive(numbers[i]);
    }

    timer = timerGet();

    for (int i = 0; i < results.length; i++) {
      System.out.println("factorialNaive(" + numbers[i] + ")   =" + str(results[i]));
    }

    System.out.println("---");
    System.out.println("Time: " + timer + "ms");

  }

  public static void test() {
/*
    BigInteger f4 = factorialNaive(4);

    System.out.println("F(4) =" + f4);
    System.out.println("F(5) =" + factorialNaive(5));
    System.out.println("F(6) =" + factorialNaive(6));
    System.out.println("F(7) =" + factorialNaive(7));
    System.out.println("F(8) =" + factorialNaive(8));
    System.out.println("F(16)=" + factorialNaive(16));
    System.out.println("f4*f4=" + f4.multiply(f4));

    Executors.newWorkStealingPool();

    if (true) return;
*/

    long begin = System.currentTimeMillis();
    System.out.println("factorialNaive(100_000)   =" + str(factorialNaive(100_000)));
    System.out.println("time=" + (System.currentTimeMillis() - begin));

    begin = System.currentTimeMillis();
    System.out.println("factorialNaive(100_000)   =" + str(factorialNaive(100_000)));
    System.out.println("time=" + (System.currentTimeMillis() - begin));

    begin = System.currentTimeMillis();
    System.out.println("factorialNaive(100_000)   =" + str(factorialNaive(100_000)));
    System.out.println("time=" + (System.currentTimeMillis() - begin));

    begin = System.currentTimeMillis();
    System.out.println("factorialParallel(100_000)=" + str(factorialParallelSimple(100_000, 4)));
    System.out.println("time=" + (System.currentTimeMillis() - begin));

    //simpleTest2();
  }

  public static String str(BigInteger big) {
    return big.toString().substring(0, 16) + "... (" + big.toString().length() + ")";
  }

//  public static void test() {
//    ThreadPoolExecutor tpe;
//    ExecutorService es = Executors.newWorkStealingPool(4);
//  }

  public static void simpleTest2() {
    int n = 8;
    System.out.println("factorialNaive(" + n + ")   =" + factorialNaive(n));
    System.out.println("factorialParallel(" + n + ")=" + factorialParallelSimple(n, 4));
  }

  public static void simpleTest() {
    System.out.println("factorialNaive(5)=" + factorialNaive(5));
    System.out.println("factorialNaive(6)=" + factorialNaive(6));
    System.out.println("factorialNaive(10)=" + factorialNaive(10));
    System.out.println("factorialNaive(15)=" + factorialNaive(15));
    System.out.println("factorialNaive(20)=" + factorialNaive(20));
    System.out.println("factorialNaive(100)=" + factorialNaive(100));
  }

  /***
   *
   */

  public static BigInteger factorialNaive(int n) {
    return factorialNaive(1, n);
  }

  public static BigInteger factorialNaive(int start, int n) {
    BigInteger big = BigInteger.valueOf(start);
    for (int i = start + 1; i <= n; i++) {
      big = big.multiply(BigInteger.valueOf(i));
    }
    return big;
  }

  public static BigInteger factorialParallelSimple(int n) {
    return factorialParallelSimple(n, 4);
  }

  public static BigInteger factorialParallelSimple(int n, int numberOfThreads) {
    BigInteger big = BigInteger.valueOf(1);
    Thread[] threads = new Thread[numberOfThreads];
    final BigInteger[] pieces = new BigInteger[numberOfThreads];
    if (n > numberOfThreads) {
      int pieceOfN = n / numberOfThreads;
      for (int i = 0; i < numberOfThreads; i++) {
        int indx = i;
        int from = n - pieceOfN + 1;
        int number = n;
        threads[i] = new Thread(() -> pieces[indx] = factorialNaive(from, number));
        threads[i].start();
        n -= pieceOfN;
      }
      try {
        for (int i = 0; i < numberOfThreads; i++) {
          threads[i].join();
          big = big.multiply(pieces[i]);
        }
      } catch (InterruptedException e) {

      }
    } else {
      for (int i = 1; i <= n; i++) {
        big = big.multiply(BigInteger.valueOf(i));
      }
    }
    return big;
  }

  /***
   *
   */

  public static void timerStart() {
    time = System.currentTimeMillis();
  }

  public static long timerGet() {
    return System.currentTimeMillis() - time;
  }

}
