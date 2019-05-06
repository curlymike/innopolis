package part1.lesson07.task1;

import java.math.BigInteger;
import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

//public class CountingTask extends RecursiveTask<Integer> {}

public class ParallelFactorialCallable4 implements Callable<Map.Entry<Integer, BigInteger>> {
  private final int number;

  public ParallelFactorialCallable4(int number) {
    this.number = number;
  }

  @Override
  public Map.Entry<Integer, BigInteger> call() throws Exception {
    // FIXME: this is totally wrong!
    // ForkJoinPool is not the same as ExecutorService'es from Executors class
    int quarter = number / 4;
    Future<BigInteger> f1 = ForkJoinPool.commonPool().submit(() -> Factorial.compute(1 * quarter + 1, 2 * quarter));
    Future<BigInteger> f2 = ForkJoinPool.commonPool().submit(() -> Factorial.compute(2 * quarter + 1, 3 * quarter));
    Future<BigInteger> f3 = ForkJoinPool.commonPool().submit(() -> Factorial.compute(3 * quarter + 1, number));

    return new AbstractMap.SimpleEntry(number,
              Factorial.compute(quarter).multiply(f1.get()).multiply(f2.get()).multiply(f3.get()));
  }
}
