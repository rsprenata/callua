package com.callua.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {
    public Connection getConnection() {
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            //return DriverManager.getConnection("jdbc:mysql://localhost/callua", "root", "root");
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection("jdbc:postgres://localhost/callua", "postgres", "postgres");
        } catch (SQLException exception) { 
            System.out.println("Erro ao conectar no banco: " + exception);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
