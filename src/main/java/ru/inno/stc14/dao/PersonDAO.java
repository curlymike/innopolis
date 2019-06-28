package ru.inno.stc14.dao;

import ru.inno.stc14.entity.Person;

import java.util.List;

public interface PersonDAO {

    Person getPerson(String login, String pass);

    List<Person> getList();

    boolean addPerson(Person person);
}
