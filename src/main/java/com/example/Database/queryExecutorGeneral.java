package com.example.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class queryExecutorGeneral {

    private static queryExecutorUser instance;

    public boolean get(String userName, String password) {
        boolean success = false;
        String sql = "SELECT * FROM LOGIN WHERE NAME = ? AND PASSWORD=?";

        try {
            try (Connection con = dbConnection.getDBConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, userName);
                pst.setString(2, password);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    success = true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return success;
    }
    public List<List<String>> getAllUsers() {
        String sql = "SELECT * FROM USERS";
        List<List<String>> resultList = new ArrayList<>();
        try {
            try (Connection con = dbConnection.getDBConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    List<String> row = new ArrayList<>();
                    row.add(rs.getString("USERNAME"));
                    row.add(rs.getString("PASSWORD"));
                    row.add(rs.getString("FULLNAME"));
                    resultList.add(row);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return resultList;
    }

    
}
