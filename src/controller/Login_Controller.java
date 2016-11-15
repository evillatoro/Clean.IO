package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.AccountType;
import model.Model;
import model.Profile;

/**
 * controller for the login scene
 */
public class Login_Controller {

    /** a link back to the main application class */
    private MainFXApplication mainApplication;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    /**
     * setup the main application link so we can call methods there
     *
     * @param mainFXApplication  a reference (link) to our main class
     */
    public void setMainApp(MainFXApplication mainFXApplication) {
        mainApplication = mainFXApplication;
    }

    /**
     * called when the user clicks login
     */
    @FXML
    private void handleLoginCleanIOPressed() {
        if (isInputValid()) {
            String username = usernameField.getText();
            String password = passwordField.getText();
            Profile profileToLogin = Model.getInstance().searchForProfile(username, password);
            if (profileToLogin != null) {
                usernameField.clear();
                passwordField.clear();
                if (profileToLogin.getAccountType().equals(AccountType.ADMIN)) {
                    mainApplication.displayAdminScene();
                } else {
                    mainApplication.displayMainInApplicationScene();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                Stage stage = mainApplication.getWindow();
                alert.initOwner(stage);
                alert.setTitle("Error");
                alert.setHeaderText("wrong username or password");
                //alert.setContentText("wrong username or password");

                alert.showAndWait();
            }
        }
    }

    /**
     * called when the user clicks register
     */
    @FXML
    private void handleRegisterPressed() {
        mainApplication.displayRegisterScene();
    }

    /**
     * called when the user clicks cancel
     */
    @FXML
    private void handleCancelPressed() {
        mainApplication.displayWelcomeScene();
    }

    /**
     * validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        //for now just check they actually typed something
        if ((usernameField.getText() == null) || (usernameField.getText().isEmpty())) {
            errorMessage += "No valid username!\n";
        }
        if ((passwordField.getText() == null) || (passwordField.getText().isEmpty())) {
            errorMessage += "No valid password entered!\n";
        }


        //no error message means success / good input
        if (errorMessage.isEmpty()) {
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

}
