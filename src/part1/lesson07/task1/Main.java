package part1.lesson07.task1;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.*;

/***
 * Дан массив случайных чисел.
 * Написать программу для вычисления факториалов всех элементов массива.
 * Использовать пул потоков для решения задачи.
 */

public class Main {

  static final boolean VERBOSE = false;
  static long time = 0;

  public static void main(String[] args) throws Exception {

    ExecutorService es;

    // Прогревочный проход
    es = Executors.newFixedThreadPool(4);
    System.out.println("FixedThreadPool: " + test(es) + "ms");
    es.shutdown();

    es = Executors.newFixedThreadPool(4);
    System.out.println("FixedThreadPool: " + test(es) + "ms");
    es.shutdown();

    es = Executors.newWorkStealingPool();
    System.out.println("WorkStealingPool: " + test(es) + "ms");
    es.shutdown();

    es = Executors.newSingleThreadExecutor();
    System.out.println("SingleThreadExecutor: " + test(es) + "ms");
    es.shutdown();

  }

  /***
   * Метод для тестирования различных реализаций ExecutorService на одном и том же наборе чисел.
   * @param es
   * @return
   * @throws Exception
   */

  public static long test(ExecutorService es) throws Exception {
    int[] numbers = new int[]{12885,20089,20323,17441,16025,12256,19466,12833,3389,9493,4729,23169,521,22426,10686,4057,24024,22657,17560,11091,7570,22813,3938,9573,12328,24764,20448,8908,3334,1291,19826,14405,18445,22727,7001,9862,7363,15509,17560,16019};

    List<Future<Factorial>> futures = new ArrayList<>();

    for (int n : numbers) {
      futures.add(es.submit(new FactorialCallable(n)));
    }

    timerStart();

    // В процессе выполнения цикла get() блокирует до
    // тех пор пока выполнение не завершено, это нужно
    // чтобы замерить время выполнения всех заданий.
    for (Future<Factorial> future : futures) {
      future.get();
    }

    long timer = timerGet();

    if (VERBOSE) {
      for (Future<Factorial> future : futures) {
        Factorial f = future.get();
        System.out.println(
            String.format("%2d", f.getNumber()) + " - "
                + bigIntegerAsString(f.getValue()));
      }

    }

    return timer;
  }

  /***
   * Генерирует массив из n случайных чисел от 1 до upperLimit
   * @param n
   * @param upperLimit
   * @return
   */

  public static int[] randomNumbers(int n, int upperLimit) {
    int[] randomNumbers = new int[n];
    SecureRandom rand = new SecureRandom();
    for (int i = 0; i < n; i++) {
      randomNumbers[i] = rand.nextInt(upperLimit) + 1;
    }
    return randomNumbers;
  }

  /***
   * Вспомогательный метод чтобы засекать время
   * Запускает таймер.
   */

  public static void timerStart() {
    time = System.currentTimeMillis();
  }

  /***
   * Вспомогательный метод чтобы засекать время
   * Возвращает время прошедшее с последнего вызова timerStart()
   */

  public static long timerGet() {
    return System.currentTimeMillis() - time;
  }

  /***
   * Вспомогательный метод для более компактного отображения BigInteger
   */

  public static String bigIntegerAsString(BigInteger big) {
    return bigIntegerAsString(big, 40);
  }

  /***
   * Вспомогательный метод для более компактного отображения BigInteger
   * @param big
   * @param length - ограничение по длинне
   * @return
   */

  public static String bigIntegerAsString(BigInteger big, int length) {
    if (big.toString().length() > length) {
      return big.toString()
          .substring(0, length / 2 - 1) + "..." +
          big.toString().substring(big.toString().length() - length / 2 + 2);
    }
    return big.toString();
  }


}
