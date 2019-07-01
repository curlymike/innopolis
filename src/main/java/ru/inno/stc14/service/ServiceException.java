package ru.inno.stc14.service;

public class ServiceException extends RuntimeException {
  public ServiceException(String message) {
    super(message);
  }
  public ServiceException(Throwable cause) {
    super(cause);
  }
  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
