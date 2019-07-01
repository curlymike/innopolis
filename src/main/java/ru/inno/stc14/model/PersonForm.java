package ru.inno.stc14.model;

/**
 * Данный класс используется для передачи значений в JSP с формой.
 * С него форма берёт значения по молчанию, это нужно
 * на случай если произошла ошибка, чтобы те поля которые
 * уже заполнены не потеряли значения.
 */

public class PersonForm {
  private String name;
  private String birth;
  private String login;
  private String email;
  private String phone;
  private String password;

  public PersonForm() {
    this("", "", "", "", "", "");
  }

  public PersonForm(String name, String birth, String login, String email, String phone, String password) {
    this.name = name;
    this.birth = birth;
    this.login = login;
    this.email = email;
    this.phone = phone;
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public String getBirth() {
    return birth;
  }

  public String getLogin() {
    return login;
  }

  public String getEmail() {
    return email;
  }

  public String getPhone() {
    return phone;
  }

  public String getPassword() {
    return password;
  }
}