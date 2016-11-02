package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

    /** the list of all registered profiles for this database */
    private final ObservableList<Profile> profiles = FXCollections.observableArrayList();

    /** a list of all the water source reports*/
    private final ObservableList<WaterSourceReport> waterSourceReports = FXCollections.observableArrayList();

    /** a list of all the water purity reports*/
    private final ObservableList<WaterPurityReport> waterPurityReports = FXCollections.observableArrayList();

    private Connection con;
    private PreparedStatement st;
    private ResultSet rs;

    /**
     * * connects to a database
     */
    public Database() {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            String url = "jdbc:mysql://localhost/test?autoReconnect=true&useSSL=false";
//            Class.forName ("com.mysql.jdbc.Driver").newInstance ();
//            con = DriverManager.getConnection (url, "root", "edwin10285");
//            System.out.println("connection set");
//        } catch(Exception e) {
//            System.out.println("Error " + e);
//        }
    }

    /**
     * adds the requested profile
     * @param profile the profile to add to the database
     * @return true if success, false if profile already in the database
     */
    public boolean addProfile(Profile profile) {
        for (Profile p : profiles) {
            if (p.getUsername().equals(profile.getUsername())) {
                // found duplicate username
                return false;
            }
        }

        //never found the profile so safe to add it
        profiles.add(profile);
        //return the success signal
        return true;

        // database way to do it
//        try {
//            String query = "SELECT 'username' FROM profiles WHERE username = ?";
//            st = con.prepareStatement(query);
//            st.setString(1, profile.getUsername());
//            rs = st.executeQuery();
//            if(!(rs.next())) {
//                addProfileToDatabase(profile);
//                profiles.add(profile);
//                return true;
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return false;

    }

    private void addProfileToDatabase(Profile profile) {
        try {
            String query = "INSERT INTO profiles (id, username, password, accountType) values (null, ?, ?, ?)";
            st = con.prepareStatement(query);
            st.setString(1, profile.getUsername());
            st.setString(2, profile.getPassword());
            st.setString(3, profile.getAccountType().toString());
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * adds the requested water source report
     * @param waterSourceReport the water source report to add to the database
     * @return true if success, false if water source report already in the database
     */
    public boolean addWaterSourceReport(String date, String time, String nameOfReporter, Double latitude, Double longitude, TypeOfWater typeOfWater, ConditionOfWater conditionOfWater) {
        for (WaterSourceReport p : waterSourceReports) {
            if (p.getLatitude().equals(latitude)
                    && p.getLongitude().equals(longitude)) {
                // found duplicate water source report
                return false;
            }
        }
        //never found a duplicate water source report so safe to add it
        WaterSourceReport waterSourceReport = new WaterSourceReport(date, time, nameOfReporter, latitude, longitude, typeOfWater, conditionOfWater);
        waterSourceReports.add(waterSourceReport);
        //return the success signal
        return true;

        // database way to do it
//        try {
//            String query = "SELECT 'latitude', 'longitude' FROM watersourcereports WHERE latitude = ? AND longitude = ?";
//            st = con.prepareStatement(query);
//            st.setDouble(1, waterSourceReport.getLatitude());
//            st.setDouble(2, waterSourceReport.getLongitude());
//            rs = st.executeQuery();
//            if(!(rs.next())) {
//                addWaterSourceReportToDatabase(waterSourceReport);
//                waterSourceReports.add(waterSourceReport);
//                return true;
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return false;
    }

    private void addWaterSourceReportToDatabase(WaterSourceReport waterSourceReport) {
        try {
            String query = "INSERT INTO watersourcereports (id, date, time, nameOfReporter, latitude, longitude, typeOfWater, conditionOfWater) " +
                    "values (null, ?, ?, ?, ?, ?, ? ,?)";
            st = con.prepareStatement(query);
            st.setString(1, waterSourceReport.getDate());
            st.setString(2 ,waterSourceReport.getTime());
            st.setString(3, waterSourceReport.getNameOfReporter());
            st.setDouble(4, waterSourceReport.getLatitude());
            st.setDouble(5, waterSourceReport.getLongitude());
            st.setString(6, waterSourceReport.getTypeOfWater().toString());
            st.setString(7, waterSourceReport.getConditionOfWater().toString());
            st.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * adds the requested water purity report
     * @param waterPurityReport the water purity report to add to the database
     * @return true if success, false if profile already in the database
     */
    public boolean addWaterPurityReport(WaterPurityReport waterPurityReport) {
//        for (WaterPurityReport p : waterPurityReports) {
//            if (p.getLatitude().equals(waterPurityReport.getLatitude()) && p.getLongitude().equals(waterPurityReport.getLongitude())) {
//                // found duplicate water purity report
//                return false;
//            }
//        }
        //never found duplicate water purity report so safe to add it
        for (WaterSourceReport p : waterSourceReports) {
            if (p.getLatitude().equals(waterPurityReport.getLatitude()) && p.getLongitude().equals(waterPurityReport.getLongitude())) {
                p.getWaterPurityReports().add(waterPurityReport);
            }
        }
        //waterPurityReports.add(waterPurityReport);
        //return the success signal
        return true;

        // database way to do it
//        try {
//            String query = "SELECT 'latitude', 'longitude' FROM waterpurityreports WHERE latitude = ? AND longitude = ?";
//            st = con.prepareStatement(query);
//            st.setDouble(1, waterPurityReport.getLatitude());
//            st.setDouble(2, waterPurityReport.getLongitude());
//            rs = st.executeQuery();
//            if(!(rs.next())) {
//                addWaterPurityReportToDatabase(waterPurityReport);
//                waterPurityReports.add(waterPurityReport);
//                return true;
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return false;
    }

    private void addWaterPurityReportToDatabase(WaterPurityReport waterPurityReport) {
        try {
            String query = "INSERT INTO waterpurityreports (id, date, time, nameOfReporter, latitude, longitude, overallCondition, virusPPM, contaminantPPM) values " +
                    "(null, ?, ?, ?, ?, ?, ?, ?, ?)";
            st = con.prepareStatement(query);
            st.setString(1, waterPurityReport.getDate());
            st.setString(2 ,waterPurityReport.getTime());
            st.setString(3, waterPurityReport.getNameOfReporter());
            st.setDouble(4, waterPurityReport.getLatitude());
            st.setDouble(5, waterPurityReport.getLongitude());
            st.setString(6, waterPurityReport.getOverallCondition().toString());
            st.setDouble(7, waterPurityReport.getVirusPPM());
            st.setDouble(8, waterPurityReport.getContaminantPPM());
            st.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * looks for the requested profile with the matching username and password
     * used for login
     * @param username username of profile
     * @param password password of profile
     * @return true if profile is in server, false otherwise
     */
    public Profile searchForProfile(String username, String password) {
        for (Profile p : profiles) {
            if (p.getUsername().equals(username) && p.getPassword().equals(password)) {
                // found duplicate name
                return p;
            }
        }
        //never found the profile in the server
        return null;
    }

    /**
     * looks for the requested profile with the matching username and password
     * used for login
     * @param username username of profile
     * @return true if profile is in server, false otherwise
     */
    public boolean removeProfile(String username) {
        for (Profile p : profiles) {
            if (p.getUsername().equals(username)) {
                // found duplicate name
                profiles.remove(p);
                return true;
            }
        }
        //never found the profile in the server
        return false;
    }

    /**
     * list of profiles in the database
     * @return list of profiles in the database
     */
    public ObservableList<Profile> getProfiles() {
        return profiles;
    }

    public ObservableList<WaterSourceReport> getWaterSourceReports() {
        return waterSourceReports;
    }

    public ObservableList<WaterPurityReport> getWaterPurityReports() {
        return waterPurityReports;
    }

    public void loadWaterSourceReports() {
        waterSourceReports.clear();
        String query = "SELECT * FROM watersourcereports";

        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while(rs.next()) {
                String date = rs.getString("date");
                String time = rs.getString("time");
                String nameOfReporter = rs.getString("nameOfReporter");
                Double latitude = rs.getDouble("latitude");
                Double longitude = rs.getDouble("longitude");
                TypeOfWater typeOfWater = TypeOfWater.valueOf(rs.getString("typeOfWater"));
                ConditionOfWater conditionOfWater;
                if (rs.getString("conditionOfWater").equals("Treatable-Clear")) {
                    conditionOfWater = ConditionOfWater.Treatable_Clear;
                } else if (rs.getString("conditionOfWater").equals("Treatable-Muddy")) {
                    conditionOfWater = ConditionOfWater.Treatable_Muddy;
                } else {
                    conditionOfWater = ConditionOfWater.valueOf(rs.getString("conditionOfWater"));
                }
                WaterSourceReport waterSourceReport
                        = new WaterSourceReport(date, time, nameOfReporter, latitude, longitude, typeOfWater,conditionOfWater);
                int id = rs.getInt("id");
                waterSourceReport.setThisInstanceReportNumber(id);
                waterSourceReports.add(waterSourceReport);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadWaterPurityReports() {
        waterPurityReports.clear();
        String query = "SELECT * FROM waterpurityreports";

        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while(rs.next()) {
                String date = rs.getString("date");
                String time = rs.getString("time");
                String nameOfReporter = rs.getString("nameOfReporter");
                Double latitude = rs.getDouble("latitude");
                Double longitude = rs.getDouble("longitude");
                OverallCondition overallCondition = OverallCondition.valueOf(rs.getString("overallCondition"));
                Double virusPPM = rs.getDouble("virusPPM");
                Double contaminantPPM = rs.getDouble("contaminantPPM");
                WaterPurityReport waterPurityReport
                        = new WaterPurityReport(date, time, nameOfReporter, latitude, longitude, overallCondition, virusPPM, contaminantPPM);
                int id = rs.getInt("id");
                waterPurityReport.setThisInstanceReportNumber(id);
                waterPurityReports.add(waterPurityReport);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadWaterPurity() {

    }

}
