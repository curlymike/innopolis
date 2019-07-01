package ru.inno.stc14.dao.jdbc;

import ru.inno.stc14.dao.PersonDAO;
import ru.inno.stc14.entity.Person;
import ru.inno.stc14.util.Util;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonDAOImpl implements PersonDAO {
    private static Logger logger = Logger.getLogger(PersonDAOImpl.class.getName());
    private final Connection connection;

    public PersonDAOImpl(Connection con) {
        this.connection = con;
    }

    private static final String INSERT_PERSON_SQL_TEMPLATE =
            "INSERT INTO person (name, birth_date, login, email, phone, pass) values (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_PERSON_SQL_TEMPLATE =
            "SELECT id, name, birth_date, login, email, phone, pass FROM person";
    private static final String LOGIN_PASSWORD_SQL_CONDITION =
        " WHERE login = ? AND pass = ?";

    /**
     * Проставляет обекту Person данные из ResultSet
     * @param resultSet
     * @param person
     * @return
     */

    private Person setValues(ResultSet resultSet, Person person) throws SQLException {
        person.setId(resultSet.getInt(1));
        person.setName(resultSet.getString(2));
        person.setBirthDate(new Date(resultSet.getLong(3)));
        person.setLogin(resultSet.getString(4));
        person.setEmail(resultSet.getString(5));
        person.setPhone(resultSet.getString(6));
        person.setPassword(resultSet.getString(7));
        return person;
    }

    @Override
    public Person getPerson(String login, String pass) {
        Person person = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_PERSON_SQL_TEMPLATE + LOGIN_PASSWORD_SQL_CONDITION)) {
            statement.setString(1, login);
            statement.setString(2, Util.hash(pass));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    person = setValues(resultSet, new Person());
                }
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, "An exception occurred on the DAO layer.", e);
        }
        return person;
    }

    @Override
    public List<Person> getList() {
        List<Person> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_PERSON_SQL_TEMPLATE)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Person person = setValues(resultSet, new Person());
                    result.add(person);
                }
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An exception occurred on the DAO layer.", e);
        }
        return result;
    }

    @Override
    public boolean addPerson(Person person) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_PERSON_SQL_TEMPLATE)) {
            statement.setString(1, person.getName());
            if (person.getBirthDate() == null) {
                statement.setNull(2, Types.BIGINT);
            } else {
                statement.setLong(2, person.getBirthDate().getTime());
            }
            statement.setString(3, person.getLogin());
            statement.setString(4, person.getEmail());
            statement.setString(5, person.getPhone());
            statement.setString(6, person.getPassword());
            statement.execute();
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An exception occurred on the DAO layer.", e);
            if ("23505".equals(e.getSQLState())) {
                throw new LoginExistException("Логин уже занят.", e);
            }
            return false;
        }
    }
}
