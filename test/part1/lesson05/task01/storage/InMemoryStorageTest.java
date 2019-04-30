package part1.lesson05.task01.storage;

import org.junit.Test;
import part1.lesson05.task01.Person;
import part1.lesson05.task01.Pet;
import part1.lesson05.task01.Sex;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryStorageTest {

  Person owner = new Person("John", 34, Sex.MALE);

  @Test
  public void create() throws Exception {
    InMemoryStorage storage = new InMemoryStorage();
    Pet pet = storage.create(new Pet(1, "Barsik", owner, 4));
    boolean result = storage.update(new Pet(pet.getId(), "Barsik", owner, 5));
    System.out.println("result=" + result);
    Set<Pet> set = storage.getByName("Barsik");
    set.forEach(System.out::println);
    System.out.println(storage.get(1001) + " (by id)");
  }

  @Test
  public void put() {
  }

  @Test
  public void update() {
  }
}