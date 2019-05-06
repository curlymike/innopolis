package part1.lesson07.task1;

import java.math.BigInteger;
import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.*;

//public class CountingTask extends RecursiveTask<Integer> {}

public class ExperimentalFactorialCallable implements Callable<Map.Entry<Integer, BigInteger>> {
  private final int from;
  private final int number;
  private final ExecutorService es;

  public ExperimentalFactorialCallable(int number) {
    this(1, number, null);
  }
  public ExperimentalFactorialCallable(int number, ExecutorService es) {
    this(1, number, es);
  }
  public ExperimentalFactorialCallable(int from, int number) {
    this(from, number, null);
  }
  public ExperimentalFactorialCallable(int from, int number, ExecutorService es) {
    this.from = from;
    this.number = number;
    this.es = es;
  }

  @Override
  public Map.Entry<Integer, BigInteger> call() throws Exception {
    BigInteger result = Factorial.compute(number);
    Future<Map.Entry<Integer, BigInteger>> f = es.submit(new ExperimentalFactorialCallable(100));
    System.out.println(Thread.currentThread().getName() + ": f.isDone(): " + f.isDone());
    TimeUnit.MILLISECONDS.sleep(150);
    System.out.println(Thread.currentThread().getName() + ": f.isDone(): " + f.isDone() + " (after 150ms)");

    return new AbstractMap.SimpleEntry(number, result);
  }

  public Map.Entry<Integer, BigInteger> call2() throws Exception {
    BigInteger result;
    if (number > 39 && es != null) {
      int quarter = number / 4;

      Future<Map.Entry<Integer, BigInteger>> f1 = es.submit(new ExperimentalFactorialCallable(1 * quarter + 1, 2 * quarter));
      Future<Map.Entry<Integer, BigInteger>> f2 = es.submit(new ExperimentalFactorialCallable(2 * quarter + 1, 3 * quarter));
      Future<Map.Entry<Integer, BigInteger>> f3 = es.submit(new ExperimentalFactorialCallable(3 * quarter + 1, number));

      result = Factorial.compute(quarter)
          .multiply(f1.get().getValue())
          .multiply(f2.get().getValue())
          .multiply(f3.get().getValue());

    } else {
      result = Factorial.compute(number);
    }

    return new AbstractMap.SimpleEntry(number, result);
  }
}
