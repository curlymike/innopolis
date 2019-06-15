package part1.lesson09;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
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
 *
 * Это можно копировать в консоль (содержимое метода doWork()):
 * System.out.println("Hello there!");
 * System.out.println("How are you?");
 * System.out.println("По-русски тоже работает.");
 *
 */

public class Main {

  public static void main(String[] args) throws Exception {
    JavaCompiler jc = ToolProvider.getSystemJavaCompiler();
    Path tempDir = Paths.get("temp");
    Path targetDir = Paths.get("temp/part1/lesson09");

    if (jc == null) {
      System.out.println("Невозможно получить объект компилятора.");
      return;
    }
    if (!Files.exists(tempDir)) {
      System.out.println("Папка temp не существует.");
      return;
    }

    if (!Files.exists(targetDir)) {
      try {
        Files.createDirectories(targetDir);
      } catch (IOException e) {
        e.printStackTrace();
        return;
      }
    }

    Path javaFilePath = targetDir.resolve("SomeClass.java");

    Scanner s = new Scanner(System.in);

    try (BufferedWriter bw = Files.newBufferedWriter(javaFilePath)) {
      bw.write("package part1.lesson09;\n\n");
      bw.write("public class SomeClass implements part1.lesson09.Worker {\n\n");
      bw.write("\tpublic void doWork() {\n");

      System.out.println("Введите содержимое метода doWork():");

      while (true) {
        String line = s.nextLine();
        if (line.equals("")) {
          break;
        }
        bw.write("\t\t" + line + '\n');
      }

      bw.write("\t}\n\n");
      bw.write("}\n");

    }

    jc.run(null, null, null, javaFilePath.toAbsolutePath().toString());

    URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { tempDir.toUri().toURL() });
    Class<?> cls = Class.forName("part1.lesson09.SomeClass", true, classLoader);
    Worker instance = (Worker) cls.newInstance();
    instance.doWork();
  }

}
