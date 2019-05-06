package part1.lesson06.task01;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.TreeSet;

/***
 * Занятие 6. Пакет java.io и работа с ресурсами - ДЗ_5
 * Задание 1. Написать программу, читающую текстовый файл.
 * Программа должна составлять отсортированный по алфавиту
 * список слов, найденных в файле и сохранять его в файл-результат.
 * Найденные слова не должны повторяться, регистр не должен учитываться.
 * Одно слово в разных падежах – это разные слова.
 */

public class Main {

  static final String DIR = "C:\\Users\\Mike\\YandexDisk\\myStuff\\Java_Innopolis\\data\\lesson07\\task01";
  static final String FILE_IN = "file1.txt";
  static final String FILE_OUT = "file1_out.txt";

  public static void main(String[] args) {
    Path fileIn = buildPath(FILE_IN);
    Path fileOut = buildPath(FILE_OUT);

    // Слова должны быть отсортированы и не должны повторяться,
    // для такой задачи TreeSet - идеальный выбор.
    final Set<String> words = new TreeSet<>();

    // Читаем слова из файла
    // Используется try-with-resources таким образом
    // BufferedReader будет автоматически закрыт
    try (BufferedReader br = Files.newBufferedReader(fileIn)) {
      String line;
      // Читаю строку из файлы
      while ((line = br.readLine()) != null) {
        // В одну строку или цикл - как лучше?
        // words.addAll(Arrays.asList(line.split("\\s+")));
        // Разбираю её на слова
        for (String str : line.split("[\\s,]+")) {
          // Добавляю слова в коллекцию
          words.add(str);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }

    // Печатаем слова в консоль
    words.stream().forEach(System.out::println);

    // Записываем слова в файл-результат.
    // try-with-resources
    try (BufferedWriter bw = Files.newBufferedWriter(fileOut)) {
      for (String word : words) {
        bw.write(word);
        bw.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /***
   * Вспомогательный метод возвращающий объект Path
   * для строки содержащей имя файла, предполагается
   * что файл находится в папке DIR.
   * @param fileName
   * @return
   */

  public static Path buildPath(String fileName) {
    return Paths.get(DIR).resolve(Paths.get(fileName));
  }


}
