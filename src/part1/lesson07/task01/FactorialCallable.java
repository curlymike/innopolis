package part1.lesson07.task01;

import java.util.concurrent.Callable;

public class FactorialCallable implements Callable<Factorial> {
  private final int number;

  public FactorialCallable(int number) {
    this.number = number;
  }

  @Override
  public Factorial call() throws Exception {
    return new Factorial(number, Factorial.compute(number));
  }
}
