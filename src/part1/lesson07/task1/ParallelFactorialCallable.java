package part1.lesson07.task1;

import java.math.BigInteger;
import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.*;

//public class CountingTask extends RecursiveTask<Integer> {}

public class ParallelFactorialCallable implements Callable<Map.Entry<Integer, BigInteger>> {
  private final int number;

  public ParallelFactorialCallable(int number) {
    this.number = number;
  }

  @Override
  public Map.Entry<Integer, BigInteger> call() throws Exception {
    // FIXME: ForkJoinPool is not the same as ExecutorService'es from Executors class
    int half = number / 2;
    Future<BigInteger> f = ForkJoinPool.commonPool().submit(() -> Factorial.compute(half + 1, number));

    return new AbstractMap.SimpleEntry(number,
//              Factorial.compute(half).multiply(Factorial.compute(half + 1, number)
              Factorial.compute(half).multiply(f.get()));
  }
}
