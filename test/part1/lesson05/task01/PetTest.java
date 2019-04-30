package part1.lesson05.task01;

import org.junit.Test;

import java.util.Comparator;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

public class PetTest {

  @Test
  public void testNamesSet() {
    TreeSet<Pet> set = new TreeSet<>(Comparator.comparing(Pet::getName));
    set.add(new Pet(1, "One", null, 3));
    set.add(new Pet(2, "Two", null, 3));
    set.add(new Pet(3, "Three", null, 3));
    set.add(new Pet(4, "Four", null, 3));
    set.add(new Pet(5, "AA", null, 3));
    set.add(new Pet(6, "AB", null, 3));
    set.add(new Pet(7, "BA", null, 3));
    set.add(new Pet(8, "DA", null, 3));
    set.add(new Pet(9, "EA", null, 3));
    set.add(new Pet(10, "XA", null, 3));
    set.add(new Pet(11, "XB", null, 3));
    set.add(new Pet(12, "XC", null, 3));
    set.add(new Pet(13, "XD", null, 3));
    set.add(new Pet(14, "Zork", null, 3));

    set.subSet(new Pet(0, "Four", null, 0), null);


  }

}