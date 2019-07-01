package ru.inno.stc14.service;

import ru.inno.stc14.dao.PersonDAO;
import ru.inno.stc14.dao.jdbc.LoginExistException;
import ru.inno.stc14.dao.jdbc.PersonDAOImpl;
import ru.inno.stc14.entity.Person;
import ru.inno.stc14.util.Util;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonServiceImpl implements PersonService {
    private Logger logger = Logger.getLogger(PersonServiceImpl.class.getName());
    private final PersonDAO personDAO;

    public PersonServiceImpl(Connection con) {
        personDAO = new PersonDAOImpl(con);
    }

    @Override
    public Person getPerson(String login, String pass) {
        return personDAO.getPerson(login, pass);
    }

    @Override
    public List<Person> getList() {
        return personDAO.getList();
    }

    @Override
    public boolean addPerson(String name, String birth, String login, String email, String phone, String password) {
        Person person = new Person();
        person.setName(name);

        Date date = safeParseDate(birth);
        person.setBirthDate(date);

        person.setLogin(login);
        person.setEmail(email);
        person.setPhone(phone);

        try {
            person.setPassword(Util.hash(password));
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, "Unable to hash password", e);
            throw new ServiceException("Внутренняя ошибка сервиса", e);
        }

        try {
            return personDAO.addPerson(person);
        } catch (LoginExistException e) {
            logger.log(Level.SEVERE, "Ошибка", e);
            throw new ServiceException("Логин уже занят", e);
        }
    }

    private Date safeParseDate(String birthStr) {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        try {
            return format.parse(birthStr);
        } catch (ParseException e) {
            logger.log(Level.SEVERE, "Date parsing error", e);
            throw new ServiceException("Неверный формат даты", e);
        }
    }

}
