package com.example.projet;

import java.sql.*;

public class BDconnect {
    public static Connection conn=null;

    public static Connection getCon() {
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:C:/Users/GAMING/Desktop/java-main/projet/src/main/resources/new_database.db";
            conn = DriverManager.getConnection(url);
            return conn;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null; // or throw an exception here
        }
    }

    public static ResultSet executeQuery(String query) throws SQLException {
        Statement statement = conn.createStatement();
        return statement.executeQuery(query);
    }


    public static void close() throws SQLException {
        conn.close();
    }
}
