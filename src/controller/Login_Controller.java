package controller;

import fxapp.Main;
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
    private Main mainApplication;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    /**
     * setup the main application link so we can call methods there
     *
     * @param mainFXApplication  a reference (link) to our main class
     */
    public void setMainApp(Main mainFXApplication) {
        mainApplication = mainFXApplication;
    }

    /**
     * called when the user clicks cancel
     */
    @FXML
    public void handleCancelPressed() {
        mainApplication.displayWelcomeScene();
    }

    /**
     * called when the user clicks login
     */
    @FXML
    public void handleLoginCleanIOPressed() {
        if (isInputValid()) {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (Model.getInstance().getDatabase().searchProfile(username, password)) {
                Profile profile = Model.getInstance().getDatabase().getProfile(username, password);
                usernameField.clear();
                passwordField.clear();
                if (profile.getAccountType().equals(AccountType.ADMIN)) {
                    mainApplication.displayAdminScene();
                } else {
                    mainApplication.getMainInApplicationController().setProfile(profile);
                    Model.getInstance().setLoggedInProfile(profile);
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

}
