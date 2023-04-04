package com.example.pharmacymanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {
    public static Connection connectDb() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacymanagmentsystem", "root", "Admin.123");
            return connect;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
