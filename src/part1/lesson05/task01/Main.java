package part1.lesson05.task01;

import part1.lesson05.task01.storage.DuplicateEntryException;
import part1.lesson05.task01.storage.InMemoryStorage;

import java.util.Comparator;
import java.util.List;

public class Main {
  public static void main(String[] args) throws Exception {
    InMemoryStorage storage = new InMemoryStorage();

    storage.create(new Pet(0, "Барсик", new Person("Vasya", 25, Sex.MALE), 3));
    storage.create(new Pet(0, "Барсик", new Person("Lena", 35, Sex.FEMALE), 4));
    storage.create(new Pet(0, "Барсик", new Person("Lena", 35, Sex.FEMALE), 5));
    storage.create(new Pet(0, "Мурзик", new Person("Lena", 35, Sex.FEMALE), 3));
    storage.create(new Pet(0, "Барсик", new Person("Lena", 24, Sex.FEMALE), 4));
    Pet zhuzha = storage.create(new Pet(0, "Жужа", new Person("Оля", 28, Sex.FEMALE), 2));

    List<Pet> list = storage.getAll(
        Comparator.comparing(Pet::getOwner)
            .thenComparing(Pet::getName)
            .thenComparing(Pet::getWeight)
    );

    for (Pet pet : list) {
      System.out.println(pet);
    }

    boolean result = storage.update(new Pet(zhuzha.getId(), zhuzha.getName(), zhuzha.getOwner(), 3));

    System.out.println("--- (" + result + ")");

    list = storage.getAll(
        Comparator.comparing(Pet::getOwner)
            .thenComparing(Pet::getName)
            .thenComparing(Pet::getWeight)
    );

    for (Pet pet : list) {
      System.out.println(pet);
    }

    System.out.println("---");

    // Добавление дубликата
    try {
      storage.create(new Pet(0, "Барсик", new Person("Lena", 35, Sex.FEMALE), 4));
    } catch (DuplicateEntryException e) {
      System.out.println("DuplicateEntryException! Именно так и должно быть!");
      //e.printStackTrace();
    }

    System.out.println("---");

    // Поиск по кличке
    storage.getByName("Барсик").forEach(System.out::println);

  }
}
