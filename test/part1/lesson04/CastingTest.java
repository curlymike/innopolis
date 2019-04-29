package part1.lesson04;

import org.junit.Test;
import part1.lesson04.task1.MathBox;

import static org.assertj.core.api.Assertions.assertThat;

public class CastingTest {

  //Object obj = new Integer(5);

  public Object subject() {
    //return new Integer(5);
    //return new Float(2.5);
    //return new Long(5);
    return new Double(2.5);
  }

  public Number[] numArray() {
    return new Number[]{-1, };
  }

  @Test
  public void test001() {
    Object obj = subject();
    System.out.println("test001: " + obj.toString());
  }

  @Test
  public void test002() {
    Object obj = subject();
    System.out.println("test002: " + convert(obj));
  }

//  @Test
//  public void test003() {
//    System.out.println(obj.toString());
//  }

  /***
   *
   */

  public String convert(Object obj) {
    if (obj instanceof Byte) {
      return ((Byte) obj).toString();
    } else if (obj instanceof Short) {
      return ((Short) obj).toString();
    } else if (obj instanceof Integer) {
      return ((Integer) obj).toString();
    } else if (obj instanceof Long) {
      return ((Long) obj).toString();
    } else if (obj instanceof Float) {
      return ((Float) obj).toString();
    } else if (obj instanceof Double) {
      return ((Double) obj).toString();
    }
    return obj.toString();
  }

}
