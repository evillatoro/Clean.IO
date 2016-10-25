package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;

/**
 * controller for the welcome scene
 */
public class Welcome_Controller {

    /** a link back to the main application class */
    private MainFXApplication mainFXApplicationApplication;

    /**
     * setup the main application link so we can call methods there
     *
     * @param mainFXApplicationFXApplication  a reference (link) to our main class
     */
    public void setMainApp(MainFXApplication mainFXApplicationFXApplication) {
        mainFXApplicationApplication = mainFXApplicationFXApplication;
    }

    /**
     * called when the user clicks login
     */
    @FXML
    private void handleLoginPressed() {
        mainFXApplicationApplication.displayLoginScene();
    }

    /**
     * called when the user clicks register
     */
    @FXML
    private void handleRegisterPressed() {
        mainFXApplicationApplication.displayRegisterScene();
    }

}
