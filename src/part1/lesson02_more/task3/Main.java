package part1.lesson02_more.task3;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

/***
 *
 */

public class Main {
  public static void main(String[] args) {
    int limit = 10000;
    Person[] arr = new Person[limit];
    for (int i = 0; i < limit; i++) {
      arr[i] = randomPerson();
      //System.out.println(arr[i]);
    }

    Person[] arr2 = Arrays.copyOf(arr, arr.length);

    //System.out.println("---");

    Sorter s = new BubbleSort();

    long start = System.currentTimeMillis();

    s.sort(arr);

    System.out.println("Sort1: " + (System.currentTimeMillis() - start) + "ms");

    Sorter s2 = new AnotherSort();

    start = System.currentTimeMillis();

    s2.sort(arr2);

    System.out.println("Sort2: " + (System.currentTimeMillis() - start) + "ms");


//    for (int i = 0; i < arr.length; i++) {
//      System.out.println(arr[i]);
//    }

  }

  /***
   *
   */

  public static Person randomPerson() {
    int min = 3;
    int max = 10;
    char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    Random rand = new SecureRandom();
    int length = min + rand.nextInt(max - min + 1);
    char[] word = new char[length];
    for (int i = 0; i < length; i++) {
      word[i] = chars[rand.nextInt(chars.length)];
    }
    word[0] = Character.toUpperCase(word[0]);
    return new Person(new String(word), rand.nextInt(60) + 5, rand.nextInt(100) % 2 == 0 ? Person.Sex.MALE : Person.Sex.FEMALE);
  }

  /***
   *
   */

  public static class Person implements Comparable<Person> {
    private String name;
    private int age;
    private Sex sex;

    public Person(String name, int age, Sex sex) {
      this.name = name;
      this.age = age;
      this.sex = sex;
    }

    @Override
    public int compareTo(Person another) {
      int cmpSex = (sex == another.sex) ? 0 : (sex == Sex.MALE ? -1 : 1);
      if (cmpSex != 0) {
        return cmpSex;
      }
      int cmpAge = age - another.age;
      if (cmpAge != 0) {
        return cmpAge;
      }
      return name.compareTo(another.name);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Person person = (Person) o;
      return age == person.age &&
          Objects.equals(name, person.name) &&
          sex == person.sex;
    }

    @Override
    public int hashCode() {
      int result = name.hashCode();
      result = 31 * result + age;
      result = 31 * result + sex.hashCode();
      return result;
    }

    @Override
    public String toString() {
      return "Person{" +
          "name='" + name + '\'' +
          ", age=" + age +
          ", sex=" + sex +
          '}';
    }

    /***
     *
     */

    enum Sex {
      MALE("MAN"),
      FEMALE("WOMAN");

      private final String value;

      Sex(String value) {
        this.value = value;
      }

    }

  }

}
