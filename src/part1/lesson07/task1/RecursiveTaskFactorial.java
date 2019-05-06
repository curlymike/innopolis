package part1.lesson07.task1;

import java.math.BigInteger;
import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class RecursiveTaskFactorial extends RecursiveTask<Map.Entry<Integer, BigInteger>> {
  private final int from;
  private final int number;

  public RecursiveTaskFactorial(int number) {
    this(1, number);
  }

  public RecursiveTaskFactorial(int from, int number) {
    this.from = from;
    this.number = number;
  }

  @Override
  protected Map.Entry<Integer, BigInteger> compute() {
    BigInteger result;
    if (number > 39) {
      int quarter = number / 4;
      ForkJoinTask<Map.Entry<Integer, BigInteger>> f1 = new RecursiveTaskFactorial(1 * quarter + 1, 2 * quarter).fork();
      ForkJoinTask<Map.Entry<Integer, BigInteger>> f2 = new RecursiveTaskFactorial(2 * quarter + 1, 3 * quarter).fork();
      ForkJoinTask<Map.Entry<Integer, BigInteger>> f3 = new RecursiveTaskFactorial(3 * quarter + 1, number).fork();
      result = Factorial.compute(quarter)
          .multiply(f1.join().getValue())
          .multiply(f2.join().getValue())
          .multiply(f3.join().getValue());
    } else {
      result = Factorial.compute(from, number);
    }
    return new AbstractMap.SimpleEntry(number, result);
  }
}
