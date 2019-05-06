package part1.lesson06.task02;

import java.io.*;
import java.security.SecureRandom;
import java.util.Random;

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
//    for (int i = 0; i < 50; i++) {
//      System.out.println(randomChar());
//    }

//    for (int i = 0; i < 30; i++) {
//      System.out.println(randomWord(10));
//    }

    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    writeText(baos, 2000, new String[]{"Hello", "Goodbye"}, 3);

    System.out.println("---");
    System.out.println("Got " + baos.toByteArray().length + " bytes");
    System.out.println("---");

    BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(baos.toByteArray())));

    String str;

    while ((str = br.readLine()) != null) {
      System.out.println(str);
    }

    Math.random();
    Random r = new Random();

    // ... uniformly distributed double value between 0.0 and 1.0
    // range 0.0d (inclusive) to 1.0d (exclusive)
    RAND.nextDouble();


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
   *
   */

  public static void writeText(OutputStream os, int lenght, String[] words, int probability) throws IOException {
    //int count = 0;
    //while (count < lenght) {}

    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // Осталось дописать подмешивание слов из words!
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    // Better decrement given length, this way I would alway know how much room I have left.
    while (lenght > 0) {
      int sentencesInParagraph = RAND.nextInt(15) + 1;
      System.out.println("sentencesInParagraph=" + sentencesInParagraph);
      System.out.print("wordsInSentence: ");
      for (int s = 0; s < sentencesInParagraph; s++) {
        if (s > 0) {
          os.write(' ');
        }
        int wordsInSentence = RAND.nextInt(20) + 1;
        System.out.print(wordsInSentence + " ");

        String randomWord = pickWordMaybe(words, probability);
        if (randomWord != null) {
          System.out.println("Got random word!");
        }
        int randomWordIndex = RAND.nextInt(wordsInSentence);

        for (int w = 0; w < wordsInSentence && lenght > 0; w++) {
          if (randomWord != null && w == randomWordIndex) {
            os.write(randomWord.getBytes());
            lenght -= randomWord.getBytes().length;
          } else {
            byte[] word = randomWord(RAND.nextInt(15) + 1, w == 0).getBytes();
            os.write(word);
            lenght -= word.length;
          }
          if (lenght <= 0) {
            break;
          }
          if (w < wordsInSentence - 1) {
            os.write(' '); // Если это не последнее слово - добавляем пробел
            lenght--;
          }
        }
        // Конец предложения
        os.write(randomChar(new char[]{'.', '?', '!'}));
        lenght--;
        if (lenght <= 0) {
          break;
        }
      }
      os.write('\r');
      os.write('\n');
      lenght -= 2;
      System.out.println();
      //break;
    }
  }

  /***
   *
   */

  public static void generator() {

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

  public static char randomChar(char[] chars) {
    return chars[RAND.nextInt(chars.length)];
  }

  /***
   *
   * @param path - путь к каталогу в котором надо создать файлы
   * @param n - количество файлов
   * @param size - размер файла
   * @param words - массив слов
   * @param probability - вероятность вхождения слова из words в следующее предложение
   */

  public static void getFiles(String path, int n, int size, String[] words, int probability) {

  }

  /***
   * Генерирует предложение из n слов
   * @param n - количество слов
   * @return
   */

  public static String buildSentence(int n) {
    return "";
  }


}
