package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService= new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Ivan","Ivanov", (byte) 30);
        userService.saveUser("Vladimir","Lenin", (byte) 55);
        userService.saveUser("Timur","Shamoyan", (byte) 27);
        userService.saveUser("Nikolay","Zaycev", (byte) 55);
        List<User>users=userService.getAllUsers();
        users.forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();



        // реализуйте алгоритм здесь
    }
}
