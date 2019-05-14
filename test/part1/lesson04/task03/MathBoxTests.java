package part1.lesson04.task03;

import org.junit.Test;

import java.util.stream.IntStream;

public class MathBoxTests {

  @Test
  public void summator001() {
    int n = 2_000_000;
    Number[] arr = new Number[n];

    IntStream.range(1, 5_000_000).toArray();

    long begin = System.currentTimeMillis();
    for (int i = 0; i < n; i++) {
      arr[i] = i;
    }
    long timeArr = System.currentTimeMillis() - begin;

    begin = System.currentTimeMillis();
    MathBox box = new MathBox(arr);
    long timeLoad = System.currentTimeMillis() - begin;

    begin = System.currentTimeMillis();
    double sum1 = box.summator();
    long timeSum = System.currentTimeMillis() - begin;

    System.out.println("timeArr=" + timeArr);
    System.out.println("timeLoad=" + timeLoad);
    System.out.println("timeSum=" + timeSum);

    //assertThat(box.summator()).isEqualTo(15);
  }

}
