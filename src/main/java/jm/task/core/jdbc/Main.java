package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Artem", "Solovev", (byte) 19);
        userService.saveUser("Polina", "Kuprianova", (byte) 19);
        userService.saveUser("Natalia", "Magley", (byte) 40);
        userService.saveUser("Sanek", "Taraban", (byte) 25);

        List<User> userList = userService.getAllUsers();
        System.out.println(userList);

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
