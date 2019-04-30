package part1.lesson05.task01;

public class Pet implements Comparable<Pet> {
  private final int id;
  private final String name;
  private final Person owner;
  private final int weight;

  public Pet(Pet pet) {
    this(pet.id, pet);
  }

  public Pet(int id, Pet pet) {
    this(id, pet.name, pet.owner, pet.weight);
  }

  public Pet(int id, String name, Person owner, int weight) {
    this.id = id;
    this.name = name;
    this.owner = owner;
    this.weight = weight;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Person getOwner() {
    return owner;
  }

  public int getWeight() {
    return weight;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Pet pet = (Pet) o;

    if (id != pet.id) return false;
    if (weight != pet.weight) return false;
    if (!name.equals(pet.name)) return false;
    return owner.equals(pet.owner);
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + name.hashCode();
    result = 31 * result + owner.hashCode();
    result = 31 * result + weight;
    return result;
  }

  @Override
  public int compareTo(Pet another) {
    int cmp = name.compareTo(another.getName());
    if (cmp != 0) {
      return cmp;
    }
    cmp = owner.compareTo(another.getOwner());
    if (cmp != 0) {
      return cmp;
    }
    return weight == another.weight ? 0 : (weight < another.weight ? -1 : 1);
  }

  @Override
  public String toString() {
    return "Pet{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", owner=" + owner +
        ", weight=" + weight +
        '}';
  }
}
