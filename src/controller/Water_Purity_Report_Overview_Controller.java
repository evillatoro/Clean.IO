package controller;

import fxapp.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Model;
import model.WaterPurityReport;

/**
 * controller for the water source report overview
 */
public class Water_Purity_Report_Overview_Controller {

    /** a link back to the main application class */
    private Main mainApplication;

    @FXML
    private TableView<WaterPurityReport> waterPurityTable;

    @FXML
    private TableColumn<WaterPurityReport, Integer> reportNumberColumn;

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
    private Label overallConditionLabel;
    @FXML
    private Label virusPPMLabel;
    @FXML
    private Label contaminantPPMLabel;

    /**
     * setup the main application link so we can call methods there
     *
     * @param mainFXApplication  a reference (link) to our main class
     */
    public void setMainApp(Main mainFXApplication) {
        mainApplication = mainFXApplication;

        waterPurityTable.setItems(Model.getInstance().getDatabase().getWaterPurityReports());
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the purity table with the one column
        reportNumberColumn.setCellValueFactory(
                cellData -> cellData.getValue().getThisReportNumberProperty().asObject());

        // Clear water purity details.
        showWaterPurityReportDetails(null);

        // Listen for selection changes and show the water purity report details when changed.
        waterPurityTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showWaterPurityReportDetails(newValue));
        reportNumberColumn.setStyle("-fx-alignment: CENTER;");
    }

    /**
     * Fills all text fields to show details about the waterPurityReport.
     * If the specified waterPurityReport is null, all text fields are cleared.
     *
     * @param waterPurityReport the waterPurityReport or null
     */
    private void showWaterPurityReportDetails(WaterPurityReport waterPurityReport) {
        if (waterPurityReport != null) {
            // Fill the labels with info from the waterSourceReport object.
            dateLabel.setText(waterPurityReport.getDate());
            timeLabel.setText(waterPurityReport.getTime());
            nameOfWorkerLabel.setText(waterPurityReport.getNameOfReporter());
            latOfLocationOfWaterLabel.setText(waterPurityReport.getLatitude().toString());
            longOfLocationOfWaterLabel.setText(waterPurityReport.getLongitude().toString());
            overallConditionLabel.setText(waterPurityReport.getOverallCondition().toString());
            virusPPMLabel.setText(waterPurityReport.getVirusPPM().toString());
            contaminantPPMLabel.setText(waterPurityReport.getContaminantPPM().toString());
        } else {
            // water source is null, remove all the text.
            dateLabel.setText("");
            timeLabel.setText("");
            nameOfWorkerLabel.setText("");
            latOfLocationOfWaterLabel.setText("");
            longOfLocationOfWaterLabel.setText("");
            overallConditionLabel.setText("");
            virusPPMLabel.setText("");
            contaminantPPMLabel.setText("");
        }
    }

    /**
     * called when the user clicks back to main menu
     */
    @FXML
    private void handleBackToMainMenuPressed() {
        mainApplication.displayMainInApplicationScene();
    }

}
