package com.newsletter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

public class DatabaseManager {
    static String url;
    static String user;
    static String password;

    static {
        Properties config = ConfigLoader.load();

        url = config.getProperty("db.url");
        user = config.getProperty("db.user");
        password = config.getProperty("db.password");
    }

    public static Connection createConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("Error trying to connect to the database");
            e.printStackTrace();
            return null;
        }
    }

    public static String[] getRecipients() {
        String sql = "SELECT * FROM recipients";

        try (Connection conn = createConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery()) {

            ArrayList<String> result = new ArrayList<String>();

            while (resultSet.next()) {
                String email = resultSet.getString("email");
                System.out.println(email);

                result.add(email);
            }

            return result.toArray(new String[0]);
            
        } catch (Exception e) {
            e.printStackTrace();
            return new String[0];
        }
    }
}
