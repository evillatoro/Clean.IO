package controller;

import fxapp.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Model;
import model.WaterSourceReport;

/**
 * controller for the water source report overview
 */
public class Water_Source_Report_Overview_Controller {

    /** a link back to the main application class */
    private Main mainApplication;

    @FXML
    private TableView<WaterSourceReport> waterSourceTable;

    @FXML
    private TableColumn<WaterSourceReport, Integer> reportNumberColumn;

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
    public void setMainApp(Main mainFXApplication) {
        mainApplication = mainFXApplication;

        waterSourceTable.setItems(Model.getInstance().getDatabase().getWaterSourceReports());
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
        mainApplication.displayMainInApplicationScene();
    }

}
