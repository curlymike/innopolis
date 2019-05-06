package part1.lesson06.task02;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;

/***
 * Занятие 6. Пакет java.io и работа с ресурсами - ДЗ_5
 * Задание 2.
 * ---
 * Создать генератор текстовых файлов, работающий по следующим правилам:
 * - Предложение состоит из 1<=n1<=15 слов. В предложении после произвольных слов могут находиться запятые.
 * - Слово состоит из 1<=n2<=15 латинских букв
 * - Слова разделены одним пробелом
 * - Предложение начинается с заглавной буквы
 * - Предложение заканчивается (.|!|?)+" "
 * - Текст состоит из абзацев. в одном абзаце 1<=n3<=20 предложений. В конце абзаца стоит разрыв строки и перенос каретки.
 * - Есть массив слов 1<=n4<=1000. Есть вероятность probability вхождения одного из слов этого массива в следующее предложение (1/probability).
 * Необходимо написать метод getFiles(String path, int n, int size, String[] words, int probability), который создаст n файлов размером size в каталоге path. words - массив слов, probability - вероятность.
 */

public class Main {

  static final SecureRandom RAND = new SecureRandom();

  public static void main(String[] args) throws Exception {
    String path = "C:\\Temp\\Innopolis\\lesson07\\task02";
    getFiles(path, 5, 2000, new String[]{"Hello", "Goodbye"}, 3);
    System.out.println("Все готово.");
    System.out.println("Вайлы записаны по этому пути: " + path);
  }

  /***
   *
   * @param path - путь к каталогу в котором надо создать файлы
   * @param n - количество файлов
   * @param size - размер файла
   * @param words - массив слов
   * @param probability - вероятность вхождения слова из words в следующее предложение
   */

  public static void getFiles(String path, int n, int size, String[] words, int probability) throws IOException {
    Path dir = Paths.get(path);
    if (!Files.exists(dir) || !Files.isDirectory(dir)) {
      throw new IOException("Каталог не существует.");
    }

    for (int i = 0; i < n; i++) {
      Path file = dir.resolve(Paths.get(String.format("file_%03d.txt", i + 1)));
      try (OutputStream os = Files.newOutputStream(file)) {
        writeText(os, size, words, probability);
      }
      //System.out.println(file);
    }

  }

  /***
   *
   */

  public static String pickWordMaybe(String[] words, int probability) {
    if (RAND.nextFloat() < 1.0 / probability) {
      return words[RAND.nextInt(words.length)];
    }
    return null;
  }

  /***
   * Данный метод генерирует бессмысленный текст длинной не менее
   * n символов "подмешивая" в него слова из массива words с
   * вероятностью probability и записывает его в OutputStream.
   *
   * @param os поток для записи.
   * @param length ограничение на общую длинну текста, обычно выходит немного больше.
   * @param words массив слов для подмещивания к случайно сгенерированным словам.
   * @param probability вероятность вхождения слова из words в предложение.
   * @throws IOException
   */

  public static void writeText(OutputStream os, int length, String[] words, int probability) throws IOException {
    while (length > 0) {
      int sentencesInParagraph = RAND.nextInt(15) + 1;
      for (int s = 0; s < sentencesInParagraph; s++) {
        if (s > 0) {
          os.write(' ');
        }
        int wordsInSentence = RAND.nextInt(20) + 1;
        String randomWord = pickWordMaybe(words, probability);
        int randomWordIndex = RAND.nextInt(wordsInSentence);
        for (int w = 0; w < wordsInSentence && length > 0; w++) {
          if (randomWord != null && w == randomWordIndex) {
            os.write(randomWord.getBytes());
            length -= randomWord.getBytes().length;
          } else {
            byte[] word = randomWord(RAND.nextInt(15) + 1, w == 0).getBytes();
            os.write(word);
            length -= word.length;
          }
          if (length <= 0) {
            break;
          }
          if (w < wordsInSentence - 1) {
            os.write(' '); // Если это не последнее слово - добавляем пробел
            length--;
          }
        }
        // Конец предложения
        os.write(pickChar(new char[]{'.', '?', '!'}));
        length--;
        if (length <= 0) {
          break;
        }
      }
      os.write('\r');
      os.write('\n');
      length -= 2;
    }
  }

  /***
   * Генерирует "слово" сдлинной length из случайных символов
   * @param length - длинна "слова"
   * @return
   */

  public static String randomWord(int length) {
    return randomWord(length, false);
  }

  public static String randomWord(int length, boolean capitalize) {
    if (length < 1) {
      throw new IllegalArgumentException();
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      if (i == 0 && capitalize) {
        sb.append(Character.toUpperCase(randomChar()));
      } else {
        sb.append(randomChar());
      }
    }
    return sb.toString();
  }

  /***
   * Возвращает случайный символ латинского алфавита
   * @return
   */

  public static char randomChar() {
    // [a-z] : [97-122] - 26 chars
    return (char) (RAND.nextInt(26) + 97);
  }

  /***
   * Возвращает отдин случайный символ из переданного массива символов
   * @param chars
   * @return
   */

  public static char pickChar(char[] chars) {
    return chars[RAND.nextInt(chars.length)];
  }

}
