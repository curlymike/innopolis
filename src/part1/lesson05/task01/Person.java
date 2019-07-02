package part1.lesson05.task01;

/***
 *
 */

public class Person implements Comparable<Person> {
  private final String name;
  private final int age;
  private final Sex sex;

  public Person(String name, int age, Sex sex) {
    this.name = name;
    this.age = age;
    this.sex = sex;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public Sex getSex() {
    return sex;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Person person = (Person) o;

    if (age != person.age) return false;
    if (!name.equals(person.name)) return false;
    return sex == person.sex;
  }

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + age;
    result = 31 * result + sex.hashCode();
    return result;
  }

  public int compareTo(Person another) {
    int cmp = name.compareTo(another.name);
    if (cmp != 0) {
      return cmp;
    }
    cmp = age == another.age ? 0 : (age < another.age ? -1 : 1);
    if (cmp != 0) {
      return cmp;
    }
    return sex == another.sex ? 0 : sex == Sex.MALE ? -1 : 1;
  }

  @Override
  public String toString() {
    return "Person{" +
        "name='" + name + '\'' +
        ", age=" + age +
        ", sex=" + sex +
        '}';
  }
}
