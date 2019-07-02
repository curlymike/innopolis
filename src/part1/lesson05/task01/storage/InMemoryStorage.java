package part1.lesson05.task01.storage;

import part1.lesson05.task01.Pet;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryStorage {
  private final Map<Integer, Pet> map = new HashMap<>();
  private final Map<String, Set<Pet>> names = new HashMap<>(); // Аналог индекса на поле name

  private static final AtomicInteger idCounter = new AtomicInteger(1000);

  /***
   * Создает новый объект класса Pet и помещаем его в коллекцию
   * @param pet
   * @throws DuplicateEntryException
   */

  public Pet create(Pet pet) throws DuplicateEntryException {
    if (existsIgnoreId(pet)) {
      throw new DuplicateEntryException("Pet already exists");
    }
    Pet petWithId = new Pet(idCounter.incrementAndGet(), pet);
    put(petWithId);
    return petWithId;
  }

  /***
   * Помещает объект класса Pet в коллекцию
   * @param pet
   * @throws DuplicateEntryException
   */

  public void put(Pet pet) throws DuplicateEntryException {
    if (map.containsKey(pet.getId())) {
      throw new DuplicateEntryException("Pet already exists");
    }
    map.put(pet.getId(), pet);
    addToNames(pet);
  }

  /***
   * @param pet
   */

  private void addToNames(Pet pet) {
    names.computeIfAbsent(pet.getName(), k -> new TreeSet<>()).add(pet);
  }

  private void removeFromNames(Pet pet) {
    names.get(pet.getName()).remove(pet);
  }

  /***
   * Обновляет информацию о животном
   * @param pet
   * @return true если в результате выполнения данные изменились
   * @throws PetDoesNotExistException
   */

  public boolean update(Pet pet) throws PetDoesNotExistException {
    Pet existingPet = map.get(pet.getId());
    if (existingPet == null) {
      throw new PetDoesNotExistException("Pet does not exist " + pet);
    }
    if (existingPet.equals(pet)) {
      return false;
    }
    removeFromNames(existingPet);
    addToNames(pet);
    existingPet = map.put(pet.getId(), pet);
    return !existingPet.equals(pet);
  }

  /***
   * Возвращает животного по id
   * @param id
   * @return Pet или null если животного с таким id не существует
   */

  public Pet get(int id) {
    return map.get(id);
  }

  /***
   * @param name - кличка животного
   * @return Set всех животных с кличкой name
   */

  public Set<Pet> getByName(String name) {
    return names.getOrDefault(name, Collections.emptySet());
  }

  public List<Pet> getAll(Comparator<Pet> comparator) {
    return map.values().stream().sorted(comparator).collect(Collectors.toList());
  }

  /***
   * @param pet
   * @return если в хранилище уже есть животное у которого все поля кроме id совпадают с pet - возвращает true иначе false
   */

  public boolean existsIgnoreId(Pet pet) {
    return map.values().stream().filter(p -> petFieldsAreEqual(p, pet)).count() > 0;
  }

  /***
   * Сравнивает животных по всем полям кроме id
   * @param p1
   * @param p2
   * @return
   */

  private boolean petFieldsAreEqual(Pet p1, Pet p2) {
    if (!p1.getName().equals(p2.getName())) {
      return false;
    }
    if (!p1.getOwner().equals(p2.getOwner())) {
      return false;
    }
    if (p1.getWeight() != p2.getWeight()) {
      return false;
    }
    return true;
  }

}
