package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.AccountType;
import model.Model;
import model.Profile;
import model.WaterSourceReport;

/**
 * controller for the water source report overview
 */
public class Water_Source_Report_Overview_Controller {

    /** a link back to the main application class */
    private MainFXApplication mainFXApplication;

    @FXML
    private TableView<WaterSourceReport> waterSourceTable;

    @FXML
    private TableColumn<WaterSourceReport, Integer> reportNumberColumn;

    @FXML
    private Button viewHistoryGraph;
    @FXML
    private Button viewWaterPurityReports;

    @FXML
    private Label dateLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label nameOfWorkerLabel;
    @FXML
    private Label latOfLocationOfWaterLabel;
    @FXML
    private Label longOfLocationOfWaterLabel;
    @FXML
    private Label typeOfWaterLabel;
    @FXML
    private Label conditionOfWaterLabel;

    /**
     * setup the main application link so we can call methods there
     *
     * @param mainFXApplication  a reference (link) to our main class
     */
    public void setMainApp(MainFXApplication mainFXApplication) {
        this.mainFXApplication = mainFXApplication;

        waterSourceTable.setItems(Model.getInstance().getWaterSourceReports());
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the source table with the one column
        reportNumberColumn.setCellValueFactory(
                cellData -> cellData.getValue().getThisReportNumberProperty().asObject());

        // Clear water source details.
        showWaterSourceReportDetails(null);

        // Listen for selection changes and show the water source report details when changed.
        waterSourceTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showWaterSourceReportDetails(newValue));
        reportNumberColumn.setStyle("-fx-alignment: CENTER;");
    }

    /**
     * sets up what the logged in user can see
     * @param profile profile with a certain authorization
     */
    public void setProfile(Profile profile) {
        if (profile.getAccountType().equals(AccountType.MANAGER)) {
            viewWaterPurityReports.setVisible(true);
            viewHistoryGraph.setVisible(true);
        } else {
            viewHistoryGraph.setVisible(false);
            viewWaterPurityReports.setVisible(false);
        }
    }

    /**
     * Fills all text fields to show details about the waterSourceReport.
     * If the specified waterSourceReport is null, all text fields are cleared.
     *
     * @param waterSourceReport the waterSourceReport or null
     */
    private void showWaterSourceReportDetails(WaterSourceReport waterSourceReport) {
        if (waterSourceReport != null) {
            // Fill the labels with info from the waterSourceReport object.
            dateLabel.setText(waterSourceReport.getDate());
            timeLabel.setText(waterSourceReport.getTime());
            nameOfWorkerLabel.setText(waterSourceReport.getNameOfReporter());
            latOfLocationOfWaterLabel.setText(waterSourceReport.getLatitude().toString());
            longOfLocationOfWaterLabel.setText(waterSourceReport.getLongitude().toString());
            typeOfWaterLabel.setText(waterSourceReport.getTypeOfWater().toString());
            conditionOfWaterLabel.setText(waterSourceReport.getConditionOfWater().toString());
        } else {
            // water source is null, remove all the text.
            dateLabel.setText("");
            timeLabel.setText("");
            nameOfWorkerLabel.setText("");
            latOfLocationOfWaterLabel.setText("");
            longOfLocationOfWaterLabel.setText("");
            typeOfWaterLabel.setText("");
            conditionOfWaterLabel.setText("");
        }
    }

    /**
     * called when the user clicks back to main menu
     */
    @FXML
    private void handleBackToMainMenuPressed() {
        mainFXApplication.displayMainInApplicationScene();
    }

    /**
     * called when the user clicks view water purity reports
     */
    @FXML
    private void handleViewWaterPurityReportsPressed() {
        if (waterSourceTable.getSelectionModel().getSelectedItem() != null) {
            mainFXApplication.displayWaterPurityReportOverviewScene(
                    waterSourceTable.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * called when the user clicks view history graph
     */
    @FXML
    private void handleViewHistoryGraphPressed() {
        if (waterSourceTable.getSelectionModel().getSelectedItem() != null) {
            mainFXApplication.displayHistoryGraphScene(waterSourceTable.getSelectionModel().getSelectedItem());
        }
    }

}
