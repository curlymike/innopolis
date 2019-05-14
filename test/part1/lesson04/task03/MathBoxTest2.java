package part1.lesson04.task03;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MathBoxTest2 {

  @Test
  public void test001() {
    MathBox mathBox = new MathBox(new Number[]{1,2,3,4,5,6,7,8,9,10});
    assertThat(mathBox.summator()).isEqualTo(55);
  }

  @Test
  public void test002() {
    MathBox mathBox = new MathBox(new Number[]{2,4,6,8,10,12,14,16,18,20});
    mathBox.splitter(2);
    assertThat(mathBox.summator()).isEqualTo(55);
  }

  @Test
  public void test003() {

  }

  @Test
  public void summator() {
  }

  @Test
  public void splitter() {
  }

  @Test
  public void remove() {
  }



}