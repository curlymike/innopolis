package part1.lesson07.task1;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.*;

/***
 *
 */

public class Main {

  static long time = 0;

  public static void main(String[] args) throws Exception {
    test4();
  }

  // ExperimentalFactorialCallable (with injected ExecutorService)
  public static void test4() throws Exception {
    //int[] numbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 16, 32};
    //int[] numbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
    //int[] numbers = randomNumbers(40, 20_000);
    //int[] numbers = new int[]{11746,12150,18240,17511,14632,17321,7206,8247,8769,8446,15076,12696,18932,15998,19244,16460,1143,6924,7687,5,226,14821,2592,5423,5451,6305,17535,7664,12456,15510,7646,19515,12366,11344,7002};

    int[] numbers = new int[]{10, 20, 30, 40, 50, 100, 150, 200, 250, 300, 400, 500};

    //ExecutorService es = Executors.newWorkStealingPool();
    //ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    //ExecutorService es = Executors.newFixedThreadPool(2);
    //ExecutorService es = Executors.newFixedThreadPool(4);
    ExecutorService es = Executors.newSingleThreadExecutor();

    Map<Integer, Future<Map.Entry<Integer, BigInteger>>> futures = new TreeMap<>();

    for (int n : numbers) {
      System.out.print(n + " ");
      futures.put(n, es.submit(new ExperimentalFactorialCallable(n, es)));
    }

    System.out.println();
    System.out.println("--- submitted");

    timerStart();

    for (Map.Entry<Integer, Future<Map.Entry<Integer, BigInteger>>> pair : futures.entrySet()) {
      pair.getValue().get();
    }

    long timer = timerGet();

    for (Map.Entry<Integer, Future<Map.Entry<Integer, BigInteger>>> pair : futures.entrySet()) {
      System.out.println(
          String.format("%2d", pair.getKey()) + " - "
              + bigIntegerAsShortString(pair.getValue().get().getValue()));
    }

    System.out.println("---");
    System.out.println(timer + "ms");
    //System.out.println("es.isShutdown()=" + es.isShutdown());
    es.shutdown();
  }

  public static void test3() throws Exception {
    int[] numbers = new int[]{11746,12150,18240,17511,14632,17321,7206,8247,8769,8446,15076,12696,18932,15998,19244,16460,1143,6924,7687,5,226,14821,2592,5423,5451,6305,17535,7664,12456,15510,7646,19515,12366,11344,7002};

    ForkJoinPool fjp = ForkJoinPool.commonPool();

    Map<Integer, Future<Map.Entry<Integer, BigInteger>>> futures = new TreeMap<>();

    // Work in progress...

    //es.invoke(new RecursiveTaskFactorial(10_000));

  }

  public static void test2() throws Exception {
    //Map.Entry<Integer, BigInteger>
    //int[] numbers = new int[]{11746,12150,18240,17511,14632,17321,7206,8247,8769,8446,15076,12696,18932,15998,19244,16460,1143,6924,7687,25000,226,14821,2592,5423,5451,6305,17535,7664,12456,15510,7646,19515,12366,11344,7002};

    int[] numbers = new int[]{100_000};
    //int[] numbers = new int[]{55266,56931,59584,58667};
    //int[] numbers = new int[]{85266,86931,89584,88667};
    //int[] numbers = new int[]{11746,12150,18240,17511,14632,17321,7206,8247,8769,8446,15076,12696,18932,15998,19244,16460,1143,6924,7687,5,226,14821,2592,5423,5451,6305,17535,7664,12456,15510,7646,19515,12366,11344,7002};

    List<Callable<Map.Entry<Integer, BigInteger>>> tasks = new ArrayList<>();

    for (int n : numbers) {
      System.out.print(n + " ");
      //tasks.add(new FactorialCallable(n));
      //tasks.add(new ParallelFactorialCallable(n));
      tasks.add(new ParallelFactorialCallable4(n));
    }

    System.out.println();

    //ExecutorService es = Executors.newWorkStealingPool();
    //ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    //ExecutorService es = Executors.newFixedThreadPool(1);
    //ExecutorService es = Executors.newFixedThreadPool(2);
    //ExecutorService es = Executors.newFixedThreadPool(4);
    //ExecutorService es = Executors.newSingleThreadExecutor();

    ExecutorService es = new ForkJoinPool();

    timerStart();

    List<Future<Map.Entry<Integer, BigInteger>>> results = es.invokeAll(tasks);

    while (true) {
      System.out.println("Pool size: " + ((ForkJoinPool) es).getPoolSize() + ", getQueuedTaskCount(): " + ((ForkJoinPool) es).getQueuedTaskCount() + ", getQueuedSubmissionCount(): " + ((ForkJoinPool) es).getQueuedSubmissionCount());

      boolean allDone = true;
      for (Future<Map.Entry<Integer, BigInteger>> f : results) {
        if (!f.isDone()) {
          allDone = false;
          break;
        }
      }
      if (allDone) {
        break;
      } else {
        TimeUnit.MILLISECONDS.sleep(250);
      }
    }

    System.out.println();

    for (Future<Map.Entry<Integer, BigInteger>> f : results) {
      Map.Entry<Integer, BigInteger> pair = f.get();
      System.out.println(pair.getKey() + " - " + bigIntegerAsShortString(pair.getValue()));
    }

    System.out.println("---");
    System.out.println(timerGet() + "ms");

    es.shutdown();

  }

  public static void test() throws Exception {

    //int[] numbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 16, 32};
    //int[] numbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
    //int[] numbers = randomNumbers(40, 20_000);
    int[] numbers = new int[]{11746,12150,18240,17511,14632,17321,7206,8247,8769,8446,15076,12696,18932,15998,19244,16460,1143,6924,7687,5,226,14821,2592,5423,5451,6305,17535,7664,12456,15510,7646,19515,12366,11344,7002};

    //int[] numbers = new int[]{6596,15266,1824,16931,7597,19584,14557,2501,18667,5726,6585,9246,17079,6017,5811,7467,1466,3696,11180,3010};
    //int[] numbers = new int[]{55266,56931,59584,58667};
    //int[] numbers = new int[]{19584};
    //int[] numbers = new int[]{19584,18667};

    //ExecutorService es = Executors.newWorkStealingPool();
    //ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    //ExecutorService es = Executors.newFixedThreadPool(2);
    //ExecutorService es = Executors.newFixedThreadPool(4);
    ExecutorService es = Executors.newSingleThreadExecutor();

    Map<Integer, Future<Map.Entry<Integer, BigInteger>>> futures = new TreeMap<>();

    for (int n : numbers) {
      System.out.print(n + " ");
      futures.put(n, es.submit(new FactorialCallable(n)));
      //futures.put(n, es.submit(new ParallelFactorialCallable(n)));
      //futures.put(n, es.submit(new ParallelFactorialCallable4(n)));
      //futures.put(n, es.submit(single));
    }

    System.out.println();
    System.out.println("--- submitted");

    timerStart();

    for (Map.Entry<Integer, Future<Map.Entry<Integer, BigInteger>>> pair : futures.entrySet()) {
      pair.getValue().get();
    }

    long timer = timerGet();

    for (Map.Entry<Integer, Future<Map.Entry<Integer, BigInteger>>> pair : futures.entrySet()) {
      System.out.println(
          String.format("%2d", pair.getKey()) + " - "
              + bigIntegerAsShortString(pair.getValue().get().getValue()));
    }

    System.out.println("---");
    System.out.println(timer + "ms");
    //System.out.println("es.isShutdown()=" + es.isShutdown());
    es.shutdown();

  }

  /***
   *
   * @param n
   * @return
   */

  public static int[] randomNumbers(int n) {
    return randomNumbers(n, 100_000);
  }

  public static int[] randomNumbers(int n, int upperLimit) {
    int[] randomNumbers = new int[n];
    SecureRandom rand = new SecureRandom();
    for (int i = 0; i < n; i++) {
      randomNumbers[i] = rand.nextInt(upperLimit);
    }
    return randomNumbers;
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

  /***
   *
   */

  public static String bigIntegerAsShortString(BigInteger big) {
    return bigIntegerAsShortString(big, 40);
  }

  public static String bigIntegerAsShortString(BigInteger big, int length) {
    if (big.toString().length() > length) {
      return big.toString()
          .substring(0, length / 2 - 1) + "..." +
          big.toString().substring(big.toString().length() - length / 2 + 2);
    }
    return big.toString();
  }


}
