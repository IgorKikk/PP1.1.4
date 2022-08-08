package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Users (" +
                    "Id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "Firstname VARCHAR(20), " +
                    "Lastname VARCHAR(20), " +
                    "Age INT)");
            System.out.println("Table is created");
        } catch (SQLException | IOException e) {
            System.out.println("Table is not created");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS Users");
            System.out.println("Table is dropped");
        } catch (SQLException | IOException e) {
            System.out.println("Table is not dropped");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO users (Firstname, LastName, Age) VALUES(?, ?, ?)";
        try ( PreparedStatement preparedStatement = Util.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User with name - " + name + " add to DB");
        } catch (SQLException | IOException e) {
            System.out.println("User is not add to DB");
        }
    }
    public void removeUserById(long id) {
        String query = "DELETE FROM users WHERE Id = ?";
        try ( PreparedStatement preparedStatement = Util.getConnection().prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("User with ID - " + id + " deleted from DB");
        } catch (SQLException | IOException e) {
            System.out.println("User is not deleted from DB");
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        try (Statement statement = Util.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT Id, Firstname, LastName, Age FROM users");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("Id"));
                user.setName(resultSet.getString("Firstname"));
                user.setLastName(resultSet.getString("LastName"));
                user.setAge(resultSet.getByte("Age"));
                usersList.add(user);
            }
            System.out.println(usersList);
            return usersList;
        } catch (SQLException | IOException e) {
            System.out.println("Users is not import to list");
            return null;
        }
    }

    public void cleanUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("DELETE FROM users");
            System.out.println("Table is cleared");
        } catch (SQLException | IOException e) {
            System.out.println("Table is not cleared");
        }
    }
}
