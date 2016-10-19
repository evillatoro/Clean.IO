package model;

public class Model {

    private static final Model instance = new Model();
    public static Model getInstance() {
        return instance;
    }

    /** database of project*/
    private Database database;

    /** remember the currently logged in profile*/
    private Profile loggedInProfile;

    private Model () {
        database = new Database();
        database.loadWaterPurityReports();
        database.loadWaterSourceReports();
        // default profiles to use to test
        database.getProfiles().add(new Profile("u", "p", AccountType.USER));
        database.getProfiles().add(new Profile("w", "p", AccountType.WORKER));
        database.getProfiles().add(new Profile("m", "p", AccountType.MANAGER));
        database.getProfiles().add(new Profile("a", "p", AccountType.ADMIN));
    }

    /**
     * add a profile to the database
     *
     * @param profile the profile to add to the database
     * @return true if profile added, false if not added
     */
    public boolean addProfile(Profile profile) {
        if (database != null && database.addProfile(profile)) {
            loggedInProfile = profile;
            return true;
        }
        return false;
    }

    /**
     * add a water source report to the database
     *
     * @param waterSourceReport the water source report to add to the database
     * @return true if water source report added, false if not added
     */
    public boolean addWaterSourceReport(WaterSourceReport waterSourceReport) {
        return database != null && database.addWaterSourceReport(waterSourceReport);
    }

    /**
     * add a water purity report to the database
     *
     * @param waterPurityReport the water purity report to add to the database
     * @return true if water purity report added, false if not added
     */
    public boolean addWaterPurityReport(WaterPurityReport waterPurityReport) {
        return database != null && database.addWaterPurityReport(waterPurityReport);
    }

    public Database getDatabase() {
        return database;
    }

    /**
     * sets the profile that is logged in
     * @param profile profile that is logged in
     */
    public void setLoggedInProfile(Profile profile) {
        loggedInProfile = profile;
    }

    public Profile getLoggedInProfile() {
        return loggedInProfile;
    }

    /**
     * loads the water source reports from database
     */
    public void loadWaterSourceReports() {
        database.loadWaterSourceReports();
    }

    /**
     * loads the water purity reports from database
     */
    public void loadWaterPurityReports() {
        database.loadWaterPurityReports();
    }

}
