package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.AccountType;
import model.Model;
import model.Profile;

/**
 * controller for the register scene
 */
public class Register_Controller {

    /** a link back to the main application class */
    private MainFXApplication mainApplication;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private ComboBox<AccountType> accountTypeComboBox;

    /**
     * setup the main application link so we can call methods there
     *
     * @param mainFXApplication  a reference (link) to our main class
     */
    public void setMainApp(MainFXApplication mainFXApplication) {
        mainApplication = mainFXApplication;
    }

    /**
     * sets the combo box with account types
     */
    @FXML
    private void initialize() {
        accountTypeComboBox.getItems().setAll(AccountType.values());
        accountTypeComboBox.setValue(AccountType.USER);
    }

    /**
     * called when the user clicks login
     */
    @FXML
    public void handleLoginPressed() {
        clearFields();
        mainApplication.displayLoginScene();
    }

    /**
     * called when the user clicks cancel
     */
    @FXML
    private void handleCancelPressed() {
        clearFields();
        mainApplication.displayWelcomeScene();
    }

    /**
     * called when the user clicks submit
     */
    @FXML
    public void handleSubmitPressed() {
        if (isInputValid()) {
            Profile profile = new Profile(
                    usernameField.getText(),
                    passwordField.getText(),
                    accountTypeComboBox.getSelectionModel().getSelectedItem(),
                    firstNameField.getText(),
                    lastNameField.getText());
            if (!Model.getInstance().addProfile(profile)) {
                //if the add fails, notify the user
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(mainApplication.getWindow());
                alert.setTitle("Profile Not Added");
                alert.setHeaderText("Bad Profile Add");
                alert.setContentText("Profile was not added, check that they are not already in server!");

                alert.showAndWait();
            } else {
                clearFields();
                System.out.println(profile + " added to server");
                if (profile.getAccountType().equals(AccountType.ADMIN)) {
                    mainApplication.displayAdminScene();
                } else {
                    mainApplication.displayMainInApplicationScene();
                }
            }
        }
    }

    /**
     * validates the user input in the text fields.
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        //for now just check they actually typed something
        if (usernameField.getText() == null || usernameField.getText().length() == 0) {
            errorMessage += "No valid username!\n";
        }
        if (passwordField.getText() == null || passwordField.getText().length() == 0) {
            errorMessage += "No valid password entered!\n";
        }

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "No first name entered!\n";
        }

        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "No last name entered!\n";
        }


        //no error message means success / good input
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message if bad data
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainApplication.getWindow());
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
        firstNameField.clear();
        lastNameField.clear();
    }

}
