package part1.lesson02.task3;

import part1.lesson02.task3.Main.Person;
import part1.lesson02.task3.Main.Person.Sex;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonTest {

  @Test
  public void compareStatic001() {
    assertThat(
        new Person("Alex", 33, Sex.MALE).compareTo(
        new Person("Alex", 33, Sex.FEMALE))
    ).isEqualTo(-1);
  }

  @Test
  public void compareStatic002() {
    assertThat(
        new Person("Alex", 33, Sex.FEMALE).compareTo(
        new Person("Alex", 33, Sex.MALE))
    ).isEqualTo(1);
  }

  @Test
  public void compareStatic003() {
    assertThat(
        new Person("Alex", 33, Sex.MALE).compareTo(
        new Person("John", 33, Sex.MALE))
    ).isEqualTo(0);
  }

  @Test
  public void compareStatic004() {
    assertThat(
        new Person("Alex", 33, Sex.FEMALE).compareTo(
        new Person("Sonya", 33, Sex.FEMALE))
    ).isEqualTo(0);
  }

  @Test
  public void compareStatic005() {
    assertThat(
        new Person("John", 33, Sex.MALE).compareTo(
        new Person("Sonya", 33, Sex.FEMALE))
    ).isEqualTo(-1);
  }

  /***
   * Age tests
   */

  @Test
  public void compareStatic100() {
    assertThat(
        new Person("John", 33, Sex.MALE).compareTo(
        new Person("John", 30, Sex.MALE))
    ).isEqualTo(-3);
  }

  @Test
  public void compareStatic101() {
    assertThat(
        new Person("John", 30, Sex.MALE).compareTo(
        new Person("John", 33, Sex.MALE))
    ).isEqualTo(3);
  }

  @Test
  public void compareStatic102() {
    assertThat(
        new Person("John", 33, Sex.MALE).compareTo(
        new Person("John", 33, Sex.MALE))
    ).isEqualTo(0);
  }

  /***
   *
   */



}