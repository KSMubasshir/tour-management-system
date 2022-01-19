package com.example.Database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class queryExecutorUser {

    private static queryExecutorUser instance;

    public boolean validateLogin(String userName, String password) {
        boolean success = false;
        String sql = "SELECT * FROM USERS WHERE USER_NAME = ? AND PASSWORD=?";

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

    public static void createAccount(String username, String password, String email, String address) throws SQLException {
        String sql;
        int user_id = getMaxUserID();
        sql = "INSERT INTO USERS (USER_ID,USER_NAME,CONTACT_INFO,ADDRESS,PASSWORD) VALUES('" + user_id + "','" + username + "','" + email + "','" + address + "','" + password + "')";

        try {
            try (Connection con = dbConnection.getDBConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public static String getPlaceImage(String placeName) throws SQLException {
        Connection con = dbConnection.getDBConnection();
        CallableStatement stmt = con.prepareCall("{?= call GET_IMAGE_OF_PLACE(?)}");
        stmt.setString(2, placeName);
        stmt.registerOutParameter(1, Types.VARCHAR);
        stmt.execute();
        return stmt.getString(1);
    }

    public static void addTour(String placename, String username, String transportType, String transport, String accommodationType, String accommodation, String guidename) throws SQLException {
        String sql;
        int tourNo=getTourNo()+1;
        sql = "INSERT INTO TOURS (TOUR_NO,USER_NAME,PLACE_NAME,ACCOMMODATION_TYPE,ACCOMMODATION_NAME,TRANSPORT_TYPE,TRANSPORT_NAME,GUIDE_NAME)"
                + "VALUES('"+tourNo+ "','"  + username + "','" + placename + "','" + accommodationType + "','" + accommodation + "','" + transportType + "','" + transport
                + "','" + guidename + "')";
        try {
            try (Connection con = dbConnection.getDBConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public static int getMaxUserID() throws SQLException {
        String result = new String();
        String sql;
        sql = "SELECT MAX(USER_ID) FROM USERS";
        try {
            try (Connection con = dbConnection.getDBConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    result = rs.getString(1);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return Integer.parseInt(result) + 1;
    }
    
    public static int getTourNo() throws SQLException {
        String result;
        Connection con = dbConnection.getDBConnection();
        CallableStatement stmt = con.prepareCall("{?= call GET_MAX_ID_TOUR}");
        stmt.registerOutParameter(1, Types.VARCHAR);
        stmt.execute();
        result= stmt.getString(1);
        return Integer.parseInt(result) + 1;
    }

    public static List<List<String>> searchPlace(String place) throws SQLException {
        String sql;
        sql = "SELECT * FROM PLACE WHERE PLACE_NAME LIKE '%" + place + "%'";
        List<List<String>> resultList = new ArrayList<>();
        try {
            try (Connection con = dbConnection.getDBConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    List<String> row = new ArrayList<>();
                    row.add(rs.getString("PLACE_NAME"));
                    row.add(rs.getString("PLACE_LOCATION"));
                    row.add(rs.getString("PLACE_RATING"));
                    row.add(rs.getString("PLACE_SPECIALITY"));
                    resultList.add(row);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return resultList;
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

    public static List<String> getAllPlaces() throws SQLException {
        List<String> resultList;
        resultList = new ArrayList<>();
        String sql;
        sql = "SELECT PLACE_NAME FROM PLACE";
        try {
            try (Connection con = dbConnection.getDBConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    resultList.add(rs.getString("PLACE_NAME"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return resultList;
    }

    public static List<String> getTransportsOfPlace(String place, String type) throws SQLException {
        List<String> resultList;
        resultList = new ArrayList<>();
        String sql;
        sql = "SELECT TRANSPORT_NAME "
                + "FROM TRANSPORT  "
                + "WHERE PLACE_ID= (SELECT PLACE_ID FROM PLACE WHERE PLACE_NAME='" + place + "')"
                + "AND TRANSPORT_TYPE='" + type + "'";

        try {
            try (Connection con = dbConnection.getDBConnection();
                    PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    resultList.add(rs.getString("TRANSPORT_NAME"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return resultList;
    }

    public static List<String> getAccommodationsOfPlace(String place, String type) throws SQLException {
        List<String> resultList;
        resultList = new ArrayList<>();
        String sql;
        sql = "SELECT ACCOMMODATION_NAME "
                + "FROM ACCOMMODATION  "
                + "WHERE PLACE_ID= (SELECT PLACE_ID FROM PLACE WHERE PLACE_NAME='" + place + "')"
                + "AND ACCOMMODATION_TYPE='" + type + "'";

        try {
            try (Connection con = dbConnection.getDBConnection();
                    PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    resultList.add(rs.getString("ACCOMMODATION_NAME"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return resultList;
    }

    public static List<String> getGuidesOfPlace(String place) throws SQLException {
        List<String> resultList;
        resultList = new ArrayList<>();
        String sql;
        sql = "SELECT GUIDE_NAME "
                + "FROM GUIDE  "
                + "WHERE PLACE_ID= (SELECT PLACE_ID FROM PLACE WHERE PLACE_NAME='" + place + "')";

        try {
            try (Connection con = dbConnection.getDBConnection();
                    PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    resultList.add(rs.getString("GUIDE_NAME"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return resultList;
    }

    public static List<List<String>> getAllPlacesAllColumns() {
        String sql = "SELECT * FROM PLACE";
        List<List<String>> resultList = new ArrayList<>();
        try {
            try (Connection con = dbConnection.getDBConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    List<String> row = new ArrayList<>();
                    row.add(rs.getString("PLACE_NAME"));
                    row.add(rs.getString("PLACE_LOCATION"));
                    row.add(rs.getString("PLACE_RATING"));
                    row.add(rs.getString("PLACE_SPECIALITY"));
                    resultList.add(row);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return resultList;
    }

    public static List<List<String>> getAccommodationsOfPlace(String place) {
        List<List<String>> resultList = new ArrayList<>();
        String sql;
        sql = "SELECT * "
                + "FROM ACCOMMODATION  "
                + "WHERE PLACE_ID= (SELECT PLACE_ID FROM PLACE WHERE PLACE_NAME='" + place + "')";

        try {
            try (Connection con = dbConnection.getDBConnection();
                    PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    List<String> row = new ArrayList<>();
                    row.add(rs.getString(2));
                    row.add(rs.getString(3));
                    row.add(rs.getString(4));
                    row.add(rs.getString(5));
                    row.add(rs.getString(6));
                    row.add(rs.getString(7));
                    resultList.add(row);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return resultList;
    }

    public static List<List<String>> getTransportsOfPlace(String place) {
        List<List<String>> resultList = new ArrayList<>();
        String sql;
        sql = "SELECT * "
                + "FROM TRANSPORT  "
                + "WHERE PLACE_ID= (SELECT PLACE_ID FROM PLACE WHERE PLACE_NAME='" + place + "')";

        try {
            try (Connection con = dbConnection.getDBConnection();
                    PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    List<String> row = new ArrayList<>();
                    row.add(rs.getString(2));
                    row.add(rs.getString(3));
                    row.add(rs.getString(4));
                    row.add(rs.getString(5));
                    row.add(rs.getString(6));
                    row.add(rs.getString(7));
                    row.add(rs.getString(8));

                    resultList.add(row);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return resultList;
    }

    public static List<List<String>> getPoliceStationsOfPlace(String place) {
        List<List<String>> resultList = new ArrayList<>();
        String sql;
        sql = "SELECT * "
                + "FROM POLICE_STATION  "
                + "WHERE PLACE_ID= (SELECT PLACE_ID FROM PLACE WHERE PLACE_NAME='" + place + "')";

        try {
            try (Connection con = dbConnection.getDBConnection();
                    PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    List<String> row = new ArrayList<>();
                    row.add(rs.getString(1));
                    row.add(rs.getString(3));
                    row.add(rs.getString(5));
                    resultList.add(row);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return resultList;
    }

    public static List<List<String>> getRestaurantsOfPlace(String place) {
        List<List<String>> resultList = new ArrayList<>();
        String sql;
        sql = "SELECT * "
                + "FROM RESTAURANT  "
                + "WHERE PLACE_ID= (SELECT PLACE_ID FROM PLACE WHERE PLACE_NAME='" + place + "')";

        try {
            try (Connection con = dbConnection.getDBConnection();
                    PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    List<String> row = new ArrayList<>();
                    row.add(rs.getString(1));
                    row.add(rs.getString(3));
                    row.add(rs.getString(4));
                    row.add(rs.getString(5));
                    row.add(rs.getString(6));
                    resultList.add(row);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return resultList;
    }

    public static List<List<String>> getHospitalsOfPlace(String place) {
        List<List<String>> resultList = new ArrayList<>();
        String sql;
        sql = "SELECT * "
                + "FROM HOSPITAL  "
                + "WHERE PLACE_ID= (SELECT PLACE_ID FROM PLACE WHERE PLACE_NAME='" + place + "')";

        try {
            try (Connection con = dbConnection.getDBConnection();
                    PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    List<String> row = new ArrayList<>();
                    row.add(rs.getString(1));
                    row.add(rs.getString(3));
                    row.add(rs.getString(4));
                    row.add(rs.getString(5));
                    resultList.add(row);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return resultList;
    }

    public static List<List<String>> getBanksOfPlace(String place) {
        List<List<String>> resultList = new ArrayList<>();
        String sql;
        sql = "SELECT * "
                + "FROM BANK  "
                + "WHERE PLACE_ID= (SELECT PLACE_ID FROM PLACE WHERE PLACE_NAME='" + place + "')";

        try {
            try (Connection con = dbConnection.getDBConnection();
                    PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    List<String> row = new ArrayList<>();
                    row.add(rs.getString(1));
                    row.add(rs.getString(3));
                    row.add(rs.getString(5));
                    row.add(rs.getString(6));
                    row.add(rs.getString(7));
                    resultList.add(row);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return resultList;
    }

    public static List<List<String>> getAllColumnsOfGuidesOfPlace(String place) {
        List<List<String>> resultList = new ArrayList<>();
        String sql;
        sql = "SELECT * "
                + "FROM GUIDE  "
                + "WHERE PLACE_ID= (SELECT PLACE_ID FROM PLACE WHERE PLACE_NAME='" + place + "')";

        try {
            try (Connection con = dbConnection.getDBConnection();
                    PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    List<String> row = new ArrayList<>();
                    row.add(rs.getString(1));
                    row.add(rs.getString(3));
                    row.add(rs.getString(5));
                    row.add(rs.getString(6));
                    row.add(rs.getString(7));

                    resultList.add(row);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return resultList;
    }

    public static List<List<String>> getEventsOfPlace(String place) {
        List<List<String>> resultList = new ArrayList<>();
        String sql;
        sql = "SELECT * "
                + "FROM EVENT  "
                + "WHERE PLACE_ID= (SELECT PLACE_ID FROM PLACE WHERE PLACE_NAME='" + place + "')";

        try {
            try (Connection con = dbConnection.getDBConnection();
                    PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    List<String> row = new ArrayList<>();
                    row.add(rs.getString(1));
                    row.add(rs.getString(3));
                    row.add(rs.getString(5));
                    row.add(rs.getString(6));
                    row.add(rs.getString(7));
                    resultList.add(row);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return resultList;
    }

    public static List<List<String>> getAllTours(String user) {
        List<List<String>> resultList = new ArrayList<>();
        String sql;
        sql = "SELECT * "
                + "FROM TOURS  "
                + "WHERE USER_NAME='" + user + "'";

        try {
            try (Connection con = dbConnection.getDBConnection();
                    PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    List<String> row = new ArrayList<>();
                    row.add(rs.getString(3));
                    row.add(rs.getString(4));
                    row.add(rs.getString(5));
                    row.add(rs.getString(6));
                    row.add(rs.getString(7));
                    row.add(rs.getString(8));
                    resultList.add(row);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return resultList;
    }
}
