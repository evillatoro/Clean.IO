package controller;

import fxapp.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Profile;

/**
 * controller for the edit profile scene
 */
public class Edit_Profile_Controller {

    /** a link back to the main application class */
    private Main mainApplication;

    /** the profile whose data is being manipulated*/
    private Profile profile;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField streetField;

    @FXML
    private TextField postalCodeField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField accountTypeField;

    /**
     * setup the main application link so we can call methods there
     *
     * @param mainFXApplication  a reference (link) to our main class
     */
    public void setMainApp(Main mainFXApplication) {
        mainApplication = mainFXApplication;
    }

    /**
     * sets the profile to be edited
     * @param profile profile to be edited
     */
    public void setProfile(Profile profile) {
        this.profile = profile;

        firstNameField.setText(profile.getFirstName());
        lastNameField.setText(profile.getLastName());
        passwordField.setText(profile.getPassword());
        accountTypeField.setText(profile.getAccountType().toString());
    }

    /**
     * called when the user clicks ok
     */
    @FXML
    private void handleOkPressed() {
            profile.setFirstName(firstNameField.getText());
            profile.setLastName(lastNameField.getText());
            profile.setPassword(passwordField.getText());
            mainApplication.displayMainInApplicationScene();
    }

    /**
     * called when the user clicks cancel
     */
    @FXML
    private void handleCancelPressed() {
        mainApplication.displayMainInApplicationScene();
    }

}
