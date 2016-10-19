package controller;

import fxapp.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Model;
import model.Profile;

/**
 * controller for admin screen
 */
public class Admin_Controller {

    /** a link back to the main application class */
    private Main mainApplication;

    @FXML
    private TableView<Profile> profileTable;

    @FXML
    private TableColumn<Profile, Integer> accountNumberColumn;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    /**
     * setup the main application link so we can call methods there
     *
     * @param mainFXApplication  a reference (link) to our main class
     */
    public void setMainApp(Main mainFXApplication) {
        mainApplication = mainFXApplication;

        profileTable.setItems(Model.getInstance().getDatabase().getProfiles());
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the source table with the one column
        accountNumberColumn.setCellValueFactory(
                cellData -> cellData.getValue().thisInstanceAccountNumberProperty().asObject());

        // Clear water source details.
        showProfileDetails(null);

        // Listen for selection changes and show the profile details when changed.
        profileTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showProfileDetails(newValue));
        accountNumberColumn.setStyle("-fx-alignment: CENTER;");
    }

    /**
     * Fills all text fields to show details about the profile.
     * If the specified profile is null, all text fields are cleared.
     *
     * @param profile the profile or null
     */
    private void showProfileDetails(Profile profile) {
        if (profile != null) {
            // Fill the labels with info from the profile object.
            usernameLabel.setText(profile.getUsername());
            passwordLabel.setText(profile.getPassword());
        } else {
            // profile is null, remove all the text.
            usernameLabel.setText("");
            passwordLabel.setText("");
        }
    }

    /**
     * called when the user clicks on the delete button
     */
    @FXML
    private void handleDeletePressed() {
        int selectedIndex = profileTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Model.getInstance().getDatabase().removeProfile(profileTable.getSelectionModel().getSelectedItem().getUsername(), profileTable.getSelectionModel().getSelectedItem().getPassword());
            profileTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApplication.getWindow());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Profile Selected");
            alert.setContentText("Please select a profile in the table.");

            alert.showAndWait();
        }
    }

    /**
     * called when the user clicks logout
     */
    @FXML
    private void handleLogoutPressed() {
        mainApplication.displayWelcomeScene();
    }
    
}
