package ru.inno.stc14.util;

import ru.inno.stc14.model.StatusMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Util {

  /**
   * Устанавливает сообщение для отображения на странице со статусом по умолчанию
   * @param req
   * @param message
   */

  public static void setMessage(HttpServletRequest req, String message) {
    setMessage(req, message, StatusMessage.Type.INFO);
  }

  /**
   * Устанавливает сообщение для отображения на странице
   * @param req
   * @param message
   * @param type
   */

  public static void setMessage(HttpServletRequest req, String message, StatusMessage.Type type) {
    HttpSession session = req.getSession();
    // JSP проставит на 1 когда отпечатает сообщения
    // и MessageFilter очистит список.
    session.setAttribute("messagesprinted", 0);
    if (session.getAttribute("messages") == null) {
      session.setAttribute("messages", new ArrayList<StatusMessage>());
    }
    List<StatusMessage> messages = (List) session.getAttribute("messages");
    messages.add(new StatusMessage(message, type));
  }

  /**
   * Очищает список сообщений который хранится в сессии
   * @param req
   */

  public static void clearSessionMessages(HttpServletRequest req) {
    HttpSession session = req.getSession();
    if (session.getAttribute("messages") != null) {
      ((List) session.getAttribute("messages")).clear();
    }
  }

  /**
   * Возвращает true если параметр null или пустая строка, иначе возвращает false
   * @param str
   * @return
   */

  public static boolean isEmpty(String str) {
    return str == null || str.isEmpty();
  }

  /**
   * Возвращает true если параметр не null и не пустая строка иначе возвращает false
   * @param str
   * @return
   */

  public static boolean notEmpty(String str) {
    return !isEmpty(str);
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
      if (hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }
    return hexString.toString();
  }

}
