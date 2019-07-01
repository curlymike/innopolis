package ru.inno.stc14.service;

import ru.inno.stc14.entity.Person;

import java.util.List;

public interface PersonService {

    Person getPerson(String login, String pass);

    List<Person> getList();

    boolean addPerson(String name, String birth, String login, String email, String phone, String password);
}
