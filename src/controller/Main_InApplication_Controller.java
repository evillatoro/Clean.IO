package controller;

import fxapp.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.AccountType;
import model.Profile;

/**
 * controller for the main in application screen
 */
public class Main_InApplication_Controller {

    /** a link back to the main application class */
    private Main mainApplication;

    /** the profile who is logged in*/
    private Profile profile;

    @FXML
    private Label welcomeUser;

    @FXML
    private Button viewWaterPurityReports;

    /**
     * setup the main application link so we can call methods there
     *
     * @param mainFXApplication  a reference (link) to our main class
     */
    public void setMainApp(Main mainFXApplication) {
        mainApplication = mainFXApplication;
    }

    /**
     * sets the profile that is logged in
     * @param profile profile that is logged in
     */
    public void setProfile(Profile profile) {
        this.profile = profile;
        welcomeUser.setText("Welcome " + profile.getUsername());
        if (profile.getAccountType().equals(AccountType.MANAGER)) {
            viewWaterPurityReports.setVisible(true);
        } else {
            viewWaterPurityReports.setVisible(false);
        }
    }

    /**
     * called when the user clicks edit profile
     */
    @FXML
    private void handleEditProfilePressed() {
        mainApplication.getEditProfileController().setProfile(profile);
        mainApplication.displayEditProfileScene();
    }

    /**
     * called when the user clicks logout
     */
    @FXML
    private void handleLogoutPressed() {
        mainApplication.displayWelcomeScene();
    }

    /**
     * called when user clicks view water availability
     */
    @FXML
    private void handleViewWaterAvailabilityPressed() {
        mainApplication.getWaterAvailabilityController().setProfile(profile);
        mainApplication.displayWaterAvailabilityScene();
    }

    /**
     * called when user clicks view water source reports
     */
    @FXML
    private void handleViewWaterSourceReportsPressed(){
        mainApplication.displayWaterSourceReportOverviewScene();
    }

    /**
     * called when user clicks view water purity reports
     */
    @FXML
    private void handleViewWaterPurityReportsPressed() {
        mainApplication.displayWaterPurityReportOverviewScene();
    }

}
