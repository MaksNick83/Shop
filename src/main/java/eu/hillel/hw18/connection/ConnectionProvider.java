package eu.hillel.hw18.connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionProvider {


    public static Connection getConnection() {
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream("src/main/resources/db.properties"));
        } catch (IOException e) {
            System.err.println("Can not read file" + e.getMessage());
            return null;
        }

        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/shop", properties);
        } catch (SQLException e) {

            System.err.println("Can not create connection" + e.getMessage());
            return null;
        }


    }
}
