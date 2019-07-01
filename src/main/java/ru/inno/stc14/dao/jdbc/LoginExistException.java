package ru.inno.stc14.dao.jdbc;

public class LoginExistException extends RuntimeException {
  public LoginExistException(String message, Throwable cause) {
    super(message, cause);
  }
}
