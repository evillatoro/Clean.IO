package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;

/**
 * controller for the welcome scene
 */
public class Welcome_Scene_Controller {

    /** a link back to the main application class */
    private MainFXApplication mainApplication;

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
    private void handleLoginPressed() {
        mainApplication.displayLoginScene();
    }

    /**
     * called when the user clicks register
     */
    @FXML
    private void handleRegisterPressed() {
        mainApplication.displayRegisterScene();
    }

}
