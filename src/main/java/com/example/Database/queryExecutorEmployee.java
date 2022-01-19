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

public class queryExecutorEmployee {

    private static queryExecutorUser instance;

    public boolean validateLogin(String userName, String password) {
        boolean success = false;
        String sql = "SELECT * FROM EMPLOYEE WHERE EMPLOYEE_NAME = ? AND EMPLOYEE_PASSWORD=?";

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

    public static void createAccount(String username, String password, String eamil, String address) {
        String sql;
        sql = "INSERT INTO LOGIN (ID,NAME,TYPE,PASSWORD) VALUES('1617072996099','" + username + "','USER','" + password + "')";

        try {
            try (Connection con = dbConnection.getDBConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public static void addTour(String placename, String username, String transportType, String transport, String accommodationType, String accommodation, String guidename) throws SQLException {
        String sql;
        int tourNo = getTourNo() + 1;
        sql = "INSERT INTO TOURS "
                + "(TOUR_NO,USER_NAME,PLACE_NAME,ACCOMMODATION_TYPE,ACCOMMODATION_NAME,TRANSPORT_TYPE,TRANSPORT_NAME,GUIDE_NAME)"
                + "VALUES('" + tourNo + "','" + username + "','" + placename + "','" + accommodationType + "','" + accommodation + "','"
                + transportType + "','" + transport
                + "','" + guidename + "')";
        try {
            try (Connection con = dbConnection.getDBConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
            }
        } catch (SQLException e) {
            throw e;
        }

    }

    public static void addUser(String username, String contact, String address, String password) throws SQLException {
        String sql;
        int user_id = getMaxUserID();

        sql = "INSERT INTO USERS "
                + "(USER_ID,USER_NAME,CONTACT_INFO,ADDRESS,PASSWORD)"
                + "VALUES('" + user_id + "','" + username + "','" + contact + "','" + address + "','" + password + "')";
        try {
            try (Connection con = dbConnection.getDBConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
            }
        } catch (SQLException e) {
            throw e;
        }

    }

    public static void addPlace(String placeName, String location, String rating, String speciality) throws SQLException {
        String sql;
        int placeId = getMaxPlaceId();
        sql = "INSERT INTO PLACE "
                + "(PLACE_ID,PLACE_NAME,PLACE_LOCATION,PLACE_RATING,PLACE_SPECIALITY)"
                + "VALUES('" + placeId + "','" + placeName + "','" + location + "','" + rating + "','" + speciality + "')";
        try {
            try (Connection con = dbConnection.getDBConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
            }
        } catch (SQLException e) {
            throw e;
        }

    }

    public static void addAccommodation(String name, String location, String rating,
            String cost, String contact, String type, String place) throws SQLException {
        String sql;
        int accommodationId = getAccommodationId();
        sql = "INSERT INTO ACCOMMODATION "
                + "(ACCOMMODATION_ID,ACCOMMODATION_NAME,ACCOMMODATION_LOCATION,ACCOMMODATION_RATING,ACCOMMODATION_COST_PER_DAY, "
                + "ACCOMMODATION_CONTACT,ACCOMMODATION_TYPE,PLACE_ID)"
                + "VALUES('" + accommodationId + "','" + name + "','" + location + "','" + rating + "','"
                + cost + "','" + contact + "','" + type + "','" + queryExecutorEmployee.getPlaceId(place) + "')";
        try {
            try (Connection con = dbConnection.getDBConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
            }
        } catch (SQLException e) {
            throw e;
        }

    }

    public static void addTransport(String name, String contact, String startingPlace,
            String startingTime, String rating, String cost, String type, String place) throws SQLException {
        String sql;
        int transportId=getTransportId();
        sql = "INSERT INTO TRANSPORT "
                + "(TRANSPORT_ID,TRANSPORT_NAME,TRANSPORT_CONTACT,TRANSPORT_STARTING_PLACE,TRANSPORT_STARTING_TIME,"
                + "TRANSPORT_RATING,TRANSPORT_COST,"
                + "TRANSPORT_TYPE,PLACE_ID)"
                + "VALUES('" +transportId+ "','" + name + "','" + contact + "','" + startingPlace + "','" + startingTime
                + "','" + rating + "','" + cost + "','" + type + "','" + queryExecutorEmployee.getPlaceId(place) + "')";
        try {
            try (Connection con = dbConnection.getDBConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
            }
        } catch (SQLException e) {
            throw e;
        }

    }

    public static void addPoliceStation(String name, String location, String contact, String place) throws SQLException {
        String sql;
        int policeStationId=getPoliceStationId();
        sql = "INSERT INTO POLICE_STATION "
                + "(POLICE_STATION__ID,POLICE_STATION_NAME,POLICE_STATION__LOCATION,POLICE_STATION__CONTACT,PLACE_ID)"
                + "VALUES('" + policeStationId+ "','" +name + "','" + location + "','" + contact + "','" + queryExecutorEmployee.getPlaceId(place) + "')";
        try {
            try (Connection con = dbConnection.getDBConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
            }
        } catch (SQLException e) {
            throw e;
        }

    }

    public static void addRestaurant(String name, String location, String rating,
            String costMin, String costMax, String place) throws SQLException {
        String sql;
        int restaurantId=getRestaurantId();
        sql = "INSERT INTO RESTAURANT "
                + "(RESTAURANT_ID,RESTAURANT_NAME,RESTAURANT_LOCATION,RESTAURANT_RATING,RESTAURANT_COST_MIN,"
                + "RESTAURANT_COST_MAX,PLACE_ID)"
                + "VALUES('" +restaurantId+ "','" + name + "','" + location + "','" + rating + "','"
                + costMin + "','" + costMax + "','" + queryExecutorEmployee.getPlaceId(place) + "')";
        try {
            try (Connection con = dbConnection.getDBConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
            }
        } catch (SQLException e) {
            throw e;
        }

    }

    public static void addHospital(String name, String location, String contact,
            String cost, String place) throws SQLException {
        String sql;
        int hospitalId=getHospitalId();
        sql = "INSERT INTO HOSPITAL "
                + "(HOSPITAL_ID,HOSPITAL_NAME,HOSPITAL_AREA,HOSPITAL_CONTACT_NO,HOSPITAL_COST,PLACE_ID)"
                + "VALUES('" + hospitalId+ "','" +name + "','" + location + "','" + contact + "','"
                + cost + "','" + queryExecutorEmployee.getPlaceId(place) + "')";
        try {
            try (Connection con = dbConnection.getDBConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
            }
        } catch (SQLException e) {
            throw e;
        }

    }

    public static void addBank(String name, String location, String contact,
            String holiday, String openingTime, String place) throws SQLException {
        String sql;
        int bankId=getBankId();
        sql = "INSERT INTO BANK "
                + "(BANK_ID,BANK_NAME,BANK__LOCATION,BANK__CONTACT,BANK_HOLIDAY,BANK_OPENING_TIME,PLACE_ID)"
                + "VALUES('" +bankId + "','" + name + "','" + location + "','" + contact + "','"
                + holiday + "','" + openingTime + "','" + queryExecutorEmployee.getPlaceId(place) + "')";
        try {
            try (Connection con = dbConnection.getDBConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
            }
        } catch (SQLException e) {
            throw e;
        }

    }

    public static void addGuide(String name, String location, String contact,
            String rating, String cost, String place) throws SQLException {
        String sql;
        int guideId=getGuideId();
        sql = "INSERT INTO GUIDE "
                + "(GUIDE__ID,GUIDE_NAME,GUIDE__LOCATION,GUIDE__CONTACT,"
                + "GUIDE_RATING,GUIDE_COST,PLACE_ID)"
                + "VALUES('" + guideId+ "','" +name + "','" + location + "','" + contact + "','"
                + rating + "','" + cost + "','" + queryExecutorEmployee.getPlaceId(place) + "')";
        try {
            try (Connection con = dbConnection.getDBConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
            }
        } catch (SQLException e) {
            throw e;
        }

    }

    public static void addEvent(String name, String location, String contact,
            String startDate, String duration, String place) throws SQLException {
        String sql;
        int eventId=getEventId();
        sql = "INSERT INTO EVENT "
                + "(EVENT__ID,EVENT_NAME,EVENT__LOCATION,PLACE_ID,EVENT__CONTACT,"
                + "EVENT_START_DATE,EVENT_DURATION_DAYS)"
                + "VALUES('" + eventId+ "','" +name + "','" + location + "','" + queryExecutorEmployee.getPlaceId(place)
                + "','" + contact + "','"
                + startDate + "','" + duration + "')";
        try {
            try (Connection con = dbConnection.getDBConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
            }
        } catch (SQLException e) {
            throw e;
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
            throw e;
        }
        return Integer.parseInt(result) + 1;
    }

    public static String getPlaceId(String place) throws SQLException {
        String result = new String();
        String sql;
        sql = "SELECT PLACE_ID FROM PLACE WHERE PLACE_NAME LIKE '%" + place + "%'";
        try {
            try (Connection con = dbConnection.getDBConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    result = rs.getString("PLACE_ID");
                }
            }
        } catch (SQLException e) {
            throw e;
        }
        return result;
    }

    public static String getPlaceImage(String placeName) throws SQLException {
        Connection con = dbConnection.getDBConnection();
        CallableStatement stmt = con.prepareCall("{?= call GET_IMAGE_OF_PLACE(?)}");
        stmt.setString(2, placeName);
        stmt.registerOutParameter(1, Types.VARCHAR);
        stmt.execute();
        return stmt.getString(1);
    }

    public static int getTourNo() throws SQLException {
        String result;
        Connection con = dbConnection.getDBConnection();
        CallableStatement stmt = con.prepareCall("{?= call GET_MAX_ID_TOUR}");
        stmt.registerOutParameter(1, Types.VARCHAR);
        stmt.execute();
        result = stmt.getString(1);
        return Integer.parseInt(result) + 1;
    }

    public static int getMaxPlaceId() throws SQLException {
        String result;
        Connection con = dbConnection.getDBConnection();
        CallableStatement stmt = con.prepareCall("{?= call GET_MAX_ID_PLACE}");
        stmt.registerOutParameter(1, Types.VARCHAR);
        stmt.execute();
        result = stmt.getString(1);
        return Integer.parseInt(result) + 1;
    }
    
    public static int getAccommodationId() throws SQLException {
        String result;
        Connection con = dbConnection.getDBConnection();
        CallableStatement stmt = con.prepareCall("{?= call GET_MAX_ID_ACCOMMODATION}");
        stmt.registerOutParameter(1, Types.VARCHAR);
        stmt.execute();
        result = stmt.getString(1);
        return Integer.parseInt(result) + 1;
    }
    
    public static int getTransportId() throws SQLException {
        String result;
        Connection con = dbConnection.getDBConnection();
        CallableStatement stmt = con.prepareCall("{?= call GET_MAX_ID_TRANSPORT}");
        stmt.registerOutParameter(1, Types.VARCHAR);
        stmt.execute();
        result = stmt.getString(1);
        return Integer.parseInt(result) + 1;
    }
    
    public static int getPoliceStationId() throws SQLException {
        String result;
        Connection con = dbConnection.getDBConnection();
        CallableStatement stmt = con.prepareCall("{?= call GET_MAX_ID_POLICE_STATION}");
        stmt.registerOutParameter(1, Types.VARCHAR);
        stmt.execute();
        result = stmt.getString(1);
        return Integer.parseInt(result) + 1;
    }
    
    public static int getRestaurantId() throws SQLException {
        String result;
        Connection con = dbConnection.getDBConnection();
        CallableStatement stmt = con.prepareCall("{?= call GET_MAX_ID_RESTAURANT}");
        stmt.registerOutParameter(1, Types.VARCHAR);
        stmt.execute();
        result = stmt.getString(1);
        return Integer.parseInt(result) + 1;
    }
    
    public static int getHospitalId() throws SQLException {
        String result;
        Connection con = dbConnection.getDBConnection();
        CallableStatement stmt = con.prepareCall("{?= call GET_MAX_ID_HOSPITAL}");
        stmt.registerOutParameter(1, Types.VARCHAR);
        stmt.execute();
        result = stmt.getString(1);
        return Integer.parseInt(result) + 1;
    }
    
    public static int getBankId() throws SQLException {
        String result;
        Connection con = dbConnection.getDBConnection();
        CallableStatement stmt = con.prepareCall("{?= call GET_MAX_ID_BANK}");
        stmt.registerOutParameter(1, Types.VARCHAR);
        stmt.execute();
        result = stmt.getString(1);
        return Integer.parseInt(result) + 1;
    }
    
    public static int getGuideId() throws SQLException {
        String result;
        Connection con = dbConnection.getDBConnection();
        CallableStatement stmt = con.prepareCall("{?= call GET_MAX_ID_GUIDE}");
        stmt.registerOutParameter(1, Types.VARCHAR);
        stmt.execute();
        result = stmt.getString(1);
        return Integer.parseInt(result) + 1;
    }
    
    public static int getEventId() throws SQLException {
        String result;
        Connection con = dbConnection.getDBConnection();
        CallableStatement stmt = con.prepareCall("{?= call GET_MAX_ID_EVENT}");
        stmt.registerOutParameter(1, Types.VARCHAR);
        stmt.execute();
        result = stmt.getString(1);
        return Integer.parseInt(result) + 1;
    }
    
    public static List<String> searchPlace(String place) throws SQLException {
        List<String> resultList;
        resultList = new ArrayList<>();
        String sql;
        sql = "SELECT PLACE_NAME FROM PLACE WHERE PLACE_NAME LIKE '%" + place + "%'";
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

    public static List<List<String>> getAllUsers() {
        String sql = "SELECT * FROM USERS";
        List<List<String>> resultList = new ArrayList<>();
        try {
            try (Connection con = dbConnection.getDBConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    List<String> row = new ArrayList<>();
                    row.add(rs.getString(2));
                    row.add(rs.getString(3));
                    row.add(rs.getString(4));
                    resultList.add(row);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return resultList;
    }

    public static List<List<String>> getDeletedUsers() {
        String sql = "SELECT * FROM DELETED_USERS";
        List<List<String>> resultList = new ArrayList<>();
        try {
            try (Connection con = dbConnection.getDBConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    List<String> row = new ArrayList<>();
                    row.add(rs.getString(2));
                    row.add(rs.getString(3));
                    row.add(rs.getString(4));
                    resultList.add(row);
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

    public static List<List<String>> getDeletedPlaces() {
        String sql = "SELECT * FROM DELETED_PLACE";
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

    public static List<List<String>> getAllTours() {
        List<List<String>> resultList = new ArrayList<>();
        String sql;
        sql = "SELECT * "
                + "FROM TOURS";
        try {
            try (Connection con = dbConnection.getDBConnection();
                    PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    List<String> row = new ArrayList<>();
                    row.add(rs.getString(1));
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

    public static List<List<String>> getDeletedTours() {
        List<List<String>> resultList = new ArrayList<>();
        String sql;
        sql = "SELECT * "
                + "FROM DELETED_TOURS";
        try {
            try (Connection con = dbConnection.getDBConnection();
                    PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    List<String> row = new ArrayList<>();
                    row.add(rs.getString(1));
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

    public static void deletePlace(String placeName) throws SQLException {
        Connection con = dbConnection.getDBConnection();
        CallableStatement stmt = con.prepareCall("{ call DELETE_PLACE(?)}");
        stmt.setString(1, placeName);
        stmt.execute();
    }

    public static void restorePlace(String placeName) throws SQLException {
        Connection con = dbConnection.getDBConnection();
        CallableStatement stmt = con.prepareCall("{ call RESTORE_PLACE(?)}");
        stmt.setString(1, placeName);
        stmt.execute();
    }

    public static void deleteUser(String userName) throws SQLException {
        Connection con = dbConnection.getDBConnection();
        CallableStatement stmt = con.prepareCall("{ call DELETE_USER(?)}");
        stmt.setString(1, userName);
        stmt.execute();
    }

    public static void restoreUser(String userName) throws SQLException {
        Connection con = dbConnection.getDBConnection();
        CallableStatement stmt = con.prepareCall("{ call RESTORE_USER(?)}");
        stmt.setString(1, userName);
        stmt.execute();
    }

    public static void deleteTour(String tourNo) throws SQLException {
        Connection con = dbConnection.getDBConnection();
        CallableStatement stmt = con.prepareCall("{ call DELETE_TOUR(?)}");
        stmt.setString(1, tourNo);
        stmt.execute();
    }

    public static void restoreTour(String tourNo) throws SQLException {
        Connection con = dbConnection.getDBConnection();
        CallableStatement stmt = con.prepareCall("{ call RESTORE_TOUR(?)}");
        stmt.setString(1, tourNo);
        stmt.execute();
    }
}
