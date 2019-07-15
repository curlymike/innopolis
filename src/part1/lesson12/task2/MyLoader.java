package part1.lesson12.task2;

public class MyLoader extends ClassLoader {
  public Class define(String name, byte[] bytes, int offset, int length) {
    return defineClass(name, bytes, offset, length);
  }
}
