package part1.lesson07.task1;

import java.math.BigInteger;
import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class FactorialCallable implements Callable<Map.Entry<Integer, BigInteger>> {
  private final int number;

  public FactorialCallable(int number) {
    this.number = number;
  }

  @Override
  public Map.Entry<Integer, BigInteger> call() throws Exception {
    return new AbstractMap.SimpleEntry(number, Factorial.compute(number));
  }
}
