package part1.lesson04.task03;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MathBoxTest {

  @Test
  public void summator001() {
    Number[] arr = new Number[]{1, 2, 3, 4, 5};
    MathBox box = new MathBox(arr);
    assertThat(box.summator()).isEqualTo(15);
  }

  @Test
  public void summator002() {
    Number[] arr = new Number[]{2, 4, 6, 8, 10};
    MathBox box = new MathBox(arr);
    assertThat(box.summator()).isEqualTo(30);
  }

  @Test
  public void summator003() {
    Number[] arr = new Number[]{1, 1, 2, 2, 3};
    MathBox box = new MathBox(arr);
    assertThat(box.summator()).isEqualTo(6);
  }

  @Test
  public void summator004() {
    Number[] arr = new Number[]{1, 1, 2, 2, null, 3};
    MathBox box = new MathBox(arr);
    assertThat(box.summator()).isEqualTo(6);
  }

  @Test
  public void splitter001() {
    Number[] arr = new Number[]{2, 4, 6, 8, 10};
    MathBox box = new MathBox(arr);
    box.splitter(2);
    assertThat(box.summator()).isEqualTo(15);
  }

  @Test
  public void splitter002() {
    Number[] arr = new Number[]{1, 3, 5, 7, 10};
    MathBox box = new MathBox(arr);
    box.splitter(2);
    assertThat(box.summator()).isEqualTo(13);
    System.out.println(box);
  }

  @Test
  public void splitter003() {
    Number[] arr = new Number[]{1, 2, 3};
    MathBox box = new MathBox(arr);
    box.splitter(0);
    assertThat(box.summator()).isEqualTo(Double.POSITIVE_INFINITY);
    System.out.println(box);
  }

  @Test
  public void splitter004() {
    Number[] arr = new Number[]{1, -1};
    MathBox box = new MathBox(arr);
    box.splitter(0);
    assertThat(box.summator()).isEqualTo(Float.NaN);
    System.out.println(box);
  }

  @Test
  public void remove001() {
    Number[] arr = new Number[]{1, 2, 3, 4, 5};
    MathBox box = new MathBox(arr);
    box.remove(5);
    assertThat(box.summator()).isEqualTo(10);
  }

  @Test
  public void remove002() {
    Number[] arr = new Number[]{1, 2, 3, 4, 5};
    MathBox box = new MathBox(arr);
    box.remove(1);
    assertThat(box.summator()).isEqualTo(14);
  }

  @Test
  public void remove003() {
    Number[] arr = new Number[]{1, 2, 3, 4, 5, 6};
    MathBox box = new MathBox(arr);
    box.remove(6);
    assertThat(box.summator()).isEqualTo(15);
  }

  @Test
  public void toStringTest() {
    Number[] arr = new Number[]{1, 2, 3, 4, 5, 6};
    MathBox box = new MathBox(arr);
    assertThat(box.toString()).isEqualTo("MathBox[1, 2, 3, 4, 5, 6]");
    //System.out.println(box);
  }

}
