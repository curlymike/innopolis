package part1.lesson12.task2;

/**
 * Задание 2.
 * Доработать программу так, чтобы ошибка OutOfMemoryError возникала в Metaspace /Permanent Generation
 * Внимание! Программу необходимо запускать со следующими параметрами:
 * -Xmx100m -XX:MaxMetaspaceSize=32m
 */

public class Main {

  static final String pkg = "ru.innopolis.stc16";
  static final MyLoader classLoader = new MyLoader();

  public static void main(String[] args) throws Exception {
    generateClasses();
  }

  /**
   * Данный метод компилирует Java код который берётся из метода classCode()
   * И затем подменяет в полученном байткоде название класса и загружает его.
   * Таким образом получается ситуация когда в пямять загружаются тысячи
   * классов заполняя всю область Metaspace.
   * @throws Exception
   */

  public static void generateClasses() throws Exception {
    String placeHolder = "HelloWorld_XXXXXXX";
    byte[] bytecode = MyCompiler.compile(pkg, placeHolder, classCode(placeHolder));
    long time = System.currentTimeMillis();

    for (int i = 0; i < 250_000; i++) {
      String className = "HelloWorld_" + String.format("%07d", i);
      byte[] bytecode2 = Util.replace(bytecode, placeHolder.getBytes(), className.getBytes());
      Class clazz = classLoader.define(pkg + "." + className, bytecode2, 0, bytecode2.length);
      Object obj = clazz.newInstance();

      if (System.currentTimeMillis() - time > 500) {
        System.out.println("i=" + i + "; " + obj);
        time = System.currentTimeMillis();
      }
      //System.out.println(obj);
    }
  }

  /**
   * Метод возвращает код Java класса, подставив в нужные места название пакета и название класса.
   * @param className название класса
   * @return Java код класса с именем className
   */

  private static String classCode(String className) {
    StringBuilder source = new StringBuilder();
    source.append("package " + pkg + ";\n\n");
    source.append("public class " + className + " {\n\n");
    source.append("  private String s;\n");
    source.append("  private int a;\n");
    source.append("  private int b;\n");
    source.append("  private int c;\n\n");
    source.append("  public String toString() {\n");
    source.append("    return \"" + className + "{} Java Dynamic Class Creation written by Michael Golubtsov.\";\n");
    source.append("  }\n");
    source.append("}\n");

    return source.toString();
  }

}
