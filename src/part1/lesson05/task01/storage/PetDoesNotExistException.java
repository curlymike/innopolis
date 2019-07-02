package part1.lesson05.task01.storage;

import java.io.IOException;

public class PetDoesNotExistException extends IOException {
  public PetDoesNotExistException(String message) {
    super(message);
  }
}
