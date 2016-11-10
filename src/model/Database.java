package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class Database {

    /** the list of all registered profiles for this database */
    private final ObservableList<Profile> profiles = FXCollections.observableArrayList();

    /** a list of all the water source reports*/
    private final ObservableList<WaterSourceReport> waterSourceReports = FXCollections.observableArrayList();

    /** a list of all the water purity reports*/
    private final ObservableList<WaterPurityReport> waterPurityReports = FXCollections.observableArrayList();

    /**
     * * connects to a database
     */
    public Database() {

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
    }


    /**
     * adds the requested water source report
     * @param waterSourceReport the water source report to add to the database
     * @return true if success, false if water source report already in the database
     */
    public boolean addWaterSourceReport(WaterSourceReport waterSourceReport) {
        for (WaterSourceReport p : waterSourceReports) {
            if (p.getLatitude().equals(waterSourceReport.getLatitude())
                    && p.getLongitude().equals(waterSourceReport.getLongitude())) {
                // found duplicate water source report
                return false;
            }
        }
        //never found a duplicate water source report so safe to add it
        waterSourceReports.add(waterSourceReport);
        //return the success signal
        return true;
    }

    /**
     * adds the requested water purity report
     * @param waterPurityReport the water purity report to add to the database
     * @return true if water source report with same location found and is added
     */
    public boolean addWaterPurityReport(WaterPurityReport waterPurityReport) {
        //looking for matching water source report location to insert into
        for (WaterSourceReport p : waterSourceReports) {
            if (p.getLatitude().equals(waterPurityReport.getLatitude()) && p.getLongitude().equals(waterPurityReport.getLongitude())) {
                p.getWaterPurityReports().add(waterPurityReport);
                return true;
            }
        }

        return false;
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
        //never found the profile in the database
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

}
