package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    public static Connection getConnection() throws SQLException, IOException {

        try(InputStream in = Files.newInputStream(Paths.get("Userdb.properties"))) {
            Properties props = new Properties();
            props.load(in);
            String url = props.getProperty("url");
            String username = props.getProperty("username");
            String password = props.getProperty("password");
            try{
                Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                Connection connection = DriverManager.getConnection(url, username, password);
                return connection;
            }
            catch(Exception e){
                System.out.println("Connection failed...");
                return null;
            }
        }
    }

    public static SessionFactory getSessionFactory() {

        try (InputStream in = Files.newInputStream(Paths.get("Hibernate.properties"))) {
            Configuration cfg = new Configuration();
            Properties props = new Properties();
            props.load(in);
            cfg.setProperties(props);
            cfg.addAnnotatedClass(User.class);
            SessionFactory sessionFactory = cfg.buildSessionFactory();
            return sessionFactory;
        } catch (Exception e) {
            return null;
        }
    }

}
