package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl extends Util implements UserDao {
    Connection connection = getConnection();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Users (Id INT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(50), Lastname VARCHAR(50), Age INT)";

        try (Statement statement = connection.createStatement();) {
            statement.executeUpdate(sql);
            System.out.println("Table Users was created. Status OK");
        } catch (SQLException e) {
            System.out.println("Error for creating Users Table " + e);
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS FirstDatabase.Users";

        try (Statement statement = connection.createStatement();) {
            statement.execute(sql);
            System.out.println("Table Users was deleted. Status OK");
        } catch (SQLException e) {
            System.out.println("Error for delete Users Table " + e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        User user = new User(name, lastName, age);

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            System.out.println("Error for add new user " + e);
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        Session session = getSessionFactory().getCurrentSession();
        try {
            transaction = session.beginTransaction();
            User user;

            user = (User)session.load(User.class, id);
            session.delete(user);

            session.flush();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            System.out.println("Error for deleting user by Id" + e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> userList = new ArrayList<>();
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            userList = session.createQuery("from User", User.class).list();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            System.out.println("Error for getting all users " + e);
        }

        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error for clean Users table " + e);
        }
    }
}
