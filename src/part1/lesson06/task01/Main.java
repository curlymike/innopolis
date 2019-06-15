package part1.lesson06.task01;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.TreeSet;

/***
 * Занятие 6. Пакет java.io и работа с ресурсами - ДЗ_5
 * Задание 1.
 * ---
 * Написать программу, читающую текстовый файл.
 * Программа должна составлять отсортированный по алфавиту
 * список слов, найденных в файле и сохранять его в файл-результат.
 * Найденные слова не должны повторяться, регистр не должен учитываться.
 * Одно слово в разных падежах – это разные слова.
 */

public class Main {

  public static void main(String[] args) {
    Path fileIn = Paths.get("data/lesson06/task01/file1.txt");
    Path fileOut = Paths.get("data/lesson06/task01/file1_out.txt");

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
        // Разбиваю её на слова, они разделяются пробелом или запятой.
        for (String str : line.split("[\\s,]+")) {
          // Добавляю слова в коллекцию
          words.add(str);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }

    // Печатаем слова в консоль (на всякий случай)
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

}
