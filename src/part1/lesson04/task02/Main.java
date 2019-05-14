package part1.lesson04.task02;

public class Main {
  public static void main(String[] args) {
    ObjectBox objectBox = new ObjectBox();
    objectBox.addObject(1);
    objectBox.addObject(2);
    objectBox.addObject(3);
    objectBox.addObject(4);
    objectBox.addObject(5);
    System.out.println(objectBox.dump());
    objectBox.deleteObject(3);
    System.out.println(objectBox.dump());
  }
}
