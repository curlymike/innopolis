package part1.lesson09;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.BufferedWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/***
 * Дан интерфейс
 *
 * public interface Worker {
 *   void doWork();
 * }
 *
 * Необходимо написать программу, выполняющую следующее:
 * Программа с консоли построчно считывает код метода doWork. Код не должен требовать импорта дополнительных классов.
 *После ввода пустой строки считывание прекращается и считанные строки добавляются в тело метода public void doWork() в файле SomeClass.java.
 * Файл SomeClass.java компилируется программой (в рантайме) в файл SomeClass.class.
 * Полученный файл подгружается в программу с помощью кастомного загрузчика
 * Метод, введенный с консоли, исполняется в рантайме (вызывается у экземпляра объекта подгруженного класса)
 */

public class Main {

  public static void main(String[] args) throws Exception {

    Path rootPath = Paths.get("data/lesson09");
    Path javaFilePath = rootPath.resolve("part1/lesson09/task01/SomeClass.java");

    Scanner s = new Scanner(System.in);

    try (BufferedWriter bw = Files.newBufferedWriter(javaFilePath)) {
      bw.write("package part1.lesson09.task01;\n");
      bw.write("public class SomeClass implements part1.lesson09.Worker {\n");
      bw.write("public void doWork() {\n");

      //System.out.println("Hello!");

      while (true) {
        String line = s.nextLine();
        if (line.equals("")) {
          break;
        }
        bw.write(line);
      }

      bw.write("}\n");
      bw.write("}");

    }

    JavaCompiler jc = ToolProvider.getSystemJavaCompiler();

    jc.run(null, null, null, javaFilePath.toAbsolutePath().toString());

    URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { rootPath.toUri().toURL() });
    Class<?> cls = Class.forName("part1.lesson09.task01.SomeClass", true, classLoader);
    Worker instance = (Worker) cls.newInstance();
    instance.doWork();
  }

}
