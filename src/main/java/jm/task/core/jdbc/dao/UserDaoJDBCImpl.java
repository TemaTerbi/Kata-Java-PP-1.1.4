package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Users (Id INT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(50), Lastname VARCHAR(50), Age INT)";

        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate(sql);
            System.out.println("Table Users was created. Status OK");
        } catch (SQLException e) {
            System.out.println("Error for creating Users Table " + e);
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS FirstDatabase.Users";

        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement();) {
            statement.execute(sql);
            System.out.println("Table Users was deleted. Status OK");
        } catch (SQLException e) {
            System.out.println("Error for delete Users Table " + e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO FirstDatabase.Users (Name, Lastname, Age) VALUES (?, ?, ?)";

        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();

            System.out.println("User has been added to table USERS");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM FirstDatabase.Users WHERE Id=?";

        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        String sql = "SELECT * FROM FirstDatabase.Users;";

        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("Id"));
                user.setName(resultSet.getString("Name"));
                user.setLastName(resultSet.getString("Lastname"));
                user.setAge(resultSet.getByte("Age"));

                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Some sql ERROR " + e);
        }

        return userList;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM FirstDatabase.Users";

        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
