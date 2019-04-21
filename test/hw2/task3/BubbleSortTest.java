package hw2.task3;

import org.junit.Test;

import hw2.task3.Main.Person;
import hw2.task3.Main.Person.Sex;

import static org.assertj.core.api.Assertions.assertThat;

public class BubbleSortTest {

  public Person[] testData() {
    Person[] arr = new Person[12];
    arr[0] = new Person("Alex", 28, Sex.MALE);
    arr[1] = new Person("Alex", 28, Sex.FEMALE);
    arr[2] = new Person("Bob", 27, Sex.MALE);
    arr[3] = new Person("John", 26, Sex.MALE);
    arr[4] = new Person("Bob", 26, Sex.MALE);
    arr[5] = new Person("Sarah", 25, Sex.FEMALE);
    arr[6] = new Person("Amanda", 25, Sex.FEMALE);
    arr[7] = new Person("Angela", 25, Sex.FEMALE);
    arr[8] = new Person("Lara", 24, Sex.FEMALE);
    arr[9] = new Person("Angela", 23, Sex.FEMALE);
    arr[10] = new Person("Cassandra", 29, Sex.FEMALE);
    arr[11] = new Person("Angela", 21, Sex.FEMALE);
    return arr;
  }

  @Test
  public void test001() {
    Person[] arr = testData();

    for (Person p : arr) {
      System.out.println(p);
    }

    System.out.println("------------");

    Sorter s = new BubbleSort();
    s.sort(arr);

    for (Person p : arr) {
      System.out.println(p);
    }

  }

  /***
   *
   */



}