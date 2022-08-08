package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;


import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        // реализуйте алгоритм здесь

        UserDao userDaoHiber = new UserDaoHibernateImpl();
        UserDao userDaoJDBC = new UserDaoJDBCImpl();
        userDaoHiber.createUsersTable();

        userDaoHiber.saveUser("Ivan", "Ivanov", (byte) 20);
        userDaoHiber.saveUser("Igor", "Kikkoev", (byte) 32);
        userDaoHiber.saveUser("Petr", "Petrov", (byte) 31);
        userDaoHiber.saveUser("Sergey", "Sergeev", (byte) 38);

        userDaoHiber.removeUserById(1);
        userDaoHiber.getAllUsers();
        userDaoHiber.cleanUsersTable();
        userDaoHiber.dropUsersTable();
    }
}
