package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public static final String DELETE_SQL = """
            DELETE FROM userTable
            WHERE id = ?
            """;

    public static final String SAVE_SQL = """
            INSERT INTO userTable(first_name,last_name,age)
            VALUES (?,?,?);
            """;

    public static final String CREATE_SQL = """
            CREATE TABLE IF NOT EXISTS userTable(id SERIAL PRIMARY KEY,
            first_name VARCHAR(255) NOT NULL,last_name VARCHAR(255) NOT NULL,
            age INT NOT NULL)
            """;

    public static final String DROP_SQL = """
            DROP TABLE IF EXISTS usertable
            """;

    public static final String SELECT_SQL = """
            SELECT id, first_name, last_name, age
            FROM userTable
            """;

    public static final String CLEAN_SQL= """
            TRUNCATE TABLE userTable
            """;

    public UserDaoJDBCImpl() {
    }
    public void createUsersTable() {
        try (Connection connection = Util.open();
             PreparedStatement statement = connection.prepareStatement(CREATE_SQL)) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void dropUsersTable() {
        try (Connection connection = Util.open();
             PreparedStatement statement = connection.prepareStatement(DROP_SQL)) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();

            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void removeUserById(long id) {
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int age = resultSet.getByte("age");
                User user1 = new User(firstName,lastName,(byte)age);
                user1.setId((long) id);
                users.add(user1);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
        public void cleanUsersTable () {
            try (Connection connection = Util.open(); PreparedStatement preparedStatement =
                    connection.prepareStatement(CLEAN_SQL)) {
                preparedStatement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


