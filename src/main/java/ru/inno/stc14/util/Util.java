package ru.inno.stc14.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {

  /**
   * Возвращает true если параметр не null и не пустая строка иначе возвращает false
   * @param str
   * @return
   */

  public static boolean notEmpty(String str) {
    return str != null && str.length() > 0;
  }

  /**
   * Если первый параметр не null и не пустая строка - возвращает его, в противном случае возвращает второй аргумент.
   * @param str
   * @param str2
   * @return
   */

  public static String notEmptyOrDefault(String str, String str2) {
    return notEmpty(str) ? str : str2;
  }

  /**
   * Возвращает SHA-256 хеш строки с солью по умолчанию.
   * @param str
   * @return
   * @throws NoSuchAlgorithmException
   */

  public static String hash(String str) throws NoSuchAlgorithmException {
    return hash(str, "9f3keqkLpfew");
  }

  /**
   * Возвращает SHA-256 хеш строки.
   * @param str - строка
   * @param salt - соль, можно указывать пустую строку.
   * @return
   * @throws NoSuchAlgorithmException
   */

  public static String hash(String str, String salt) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    md.update((str + salt).getBytes(StandardCharsets.UTF_8));
    byte[] mdbytes = md.digest();
    return bytesToHex(mdbytes);
  }

  /**
   * Переводит массив байт в шестнадцатеричную форму, возвращает строку.
   * @param bytes
   * @return
   */

  private static String bytesToHex(byte[] bytes) {
    StringBuffer hexString = new StringBuffer();
    for (int i = 0; i < bytes.length; i++) {
      String hex = Integer.toHexString(0xff & bytes[i]);
      if(hex.length() == 1) hexString.append('0');
      hexString.append(hex);
    }
    return hexString.toString();
  }

}
