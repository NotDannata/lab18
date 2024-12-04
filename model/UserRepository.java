package com.example.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    
    private Connection connection;

    public UserRepository(String dbPath) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        createTable();
    }

    private void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                     "id INTEGER PRIMARY KEY, " +
                     "name TEXT NOT NULL, " +
                     "email TEXT NOT NULL)";
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
    }

    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO users (id, name, email) VALUES (?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, user.getId());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getEmail());
        pstmt.executeUpdate();
    }

    public List<User> getUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            users.add(new User(rs.getInt("id"), rs.getString("name"), rs.getString("email")));
        }
        return users;
    }
}