package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS Users (" +
                    "Id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "Firstname VARCHAR(20), " +
                    "Lastname VARCHAR(20), " +
                    "Age INT)");
            query.executeUpdate();
            transaction.commit();
            System.out.println("Table is created");
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery("DROP TABLE IF EXISTS Users");
            query.executeUpdate();
            transaction.commit();
            System.out.println("Table is not dropped");
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery("INSERT INTO users (Firstname, LastName, Age) VALUES(?, ?, ?)");
            query.setParameter(1, name);
            query.setParameter(2, lastName);
            query.setParameter(3, age);
            query.executeUpdate();
            transaction.commit();
            System.out.println("User with name - " + name + " add to DB");
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery("DELETE FROM users WHERE Id = ?");
            query.setParameter(1, id);
            query.executeUpdate();
            transaction.commit();
            System.out.println("User with ID - " + id + " deleted from DB");
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery("SELECT Id, Firstname, LastName, Age FROM users");
            List<User> usersList = new ArrayList<>();
            List<Object[]> rows =query.list();
            for(Object[] row : rows){
                User user = new User();
                user.setId(Long.parseLong(row[0].toString()));
                user.setName(row[1].toString());
                user.setLastName(row[2].toString());
                user.setAge(Byte.parseByte(row[3].toString()));
                usersList.add(user);
            }
            transaction.commit();
            System.out.println(usersList);
            return usersList;
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery("DELETE FROM users");
            query.executeUpdate();
            transaction.commit();
            System.out.println("Table is cleared");
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
