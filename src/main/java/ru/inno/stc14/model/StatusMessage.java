package ru.inno.stc14.model;

/**
 * Данный класс описывает информационные сообщения для отображения на странице пользователю.
 */

public class StatusMessage {
  public enum Type {
    INFO("info"),
    SUCCESS("success"),
    WARNING("warning"),
    ERROR("error");

    private String value;

    Type(String value) {
      this.value = value;
    }

    public String value() {
      return value;
    }
  }

  private String message;
  private Type type;
  private String stringType;

  public StatusMessage(String message) {
    this(message, Type.INFO);
  }

  public StatusMessage(String message, Type type) {
    this.message = message;
    this.type = type;
  }

  public String getMessage() {
    return message;
  }

  public Type getType() {
    return type;
  }

  public String getTypeClass() {
    switch (type) {
      case SUCCESS: return "alert-success";
      case WARNING: return "alert-warning";
      case ERROR: return "alert-danger";
    }
    return "alert-info";
  }

  public String getStringType() {
    return type.value();
  }

}
