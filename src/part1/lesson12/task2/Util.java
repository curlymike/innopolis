package part1.lesson12.task2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Это утильный класс который позволяет в массиве байт заменить одну последовательность байт на другую.
 * Работает по аналогии со String.replace(...) только оперирует массивами байт.
 * Это нужно чтобы не компилировать Java код 100500 раз, а сделать это единожды и потом только подменивать в байткоде название класса.
 */

public class Util {

  public static byte[] replace(byte[] arr, byte[] subj, byte[] replacement) throws Exception {
    ByteArrayInputStream bais = new ByteArrayInputStream(arr);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    int[] buff = new int[subj.length];
    int n;

    int m = buff.length - 1;
    while ((n = bais.read()) != -1) {
      shift(buff);
      buff[buff.length - 1] = n;
      if (m == 0) {
        if (isEqual(buff, subj)) {
          baos.write(replacement);
          m = buff.length - 1;
        } else {
          baos.write(buff[0]);
        }
      } else {
        m--;
      }
    }

    for (int i = m + 1; i < buff.length; i++) {
      baos.write(buff[i]);
    }

    //baos.flush();
    baos.close();

    return baos.toByteArray();
  }

  private static boolean isEqual(int[] one, byte[] two) {
    if (one.length != two.length) {
      return false;
    }
    for (int i = 0; i < one.length; i++) {
      if (one[i] != two[i]) {
        return false;
      }
    }
    return true;
  }

  private static void shift(int[] arr) {
    for (int i = 1; i < arr.length; i++) {
      arr[i - 1] = arr[i];
    }
  }

}
