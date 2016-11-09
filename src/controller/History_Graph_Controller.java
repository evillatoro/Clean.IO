package controller;

import fxapp.MainFXApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.WaterPurityReport;

import java.util.Arrays;
import java.util.List;

/**
 * Controller for the history graph
 */
public class History_Graph_Controller {

    /** a link back to the main application class */
    private MainFXApplication mainFXApplication;

    @FXML
    private CategoryAxis x = new CategoryAxis();

    @FXML
    private NumberAxis y = new NumberAxis();

    @FXML
    private LineChart<String, Double> chart;

    private ObservableList<String> monthNames = FXCollections.observableArrayList();

    private List<WaterPurityReport> waterPurityReportList;

    private int virusPPMClick = 1;

    private int contaminantPPMClick = 1;

    private XYChart.Series<String, Double> virusSeries;

    private XYChart.Series<String, Double> contaminantSeries;

    private int numOfMonths;

    @FXML
    private TextField yearField;


    /**
     * setup the main application link so we can call methods there
     *
     * @param mainFXApplication  a reference (link) to our main class
     */
    public void setMainApp(MainFXApplication mainFXApplication) {
        this.mainFXApplication = mainFXApplication;
    }

    @FXML
    private void initialize() {
        x.setLabel("Month");
        y.setLabel("PPM");
        //String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        String[] months = new String[numOfMonths];
        for(int i = 0; i < months.length; i++) {
            months[i] = (i + 1) + "";
        }
        monthNames.addAll(Arrays.asList(months));
        x.setCategories(monthNames);
    }

    @FXML
    private void handleBackPressed() {
        chart.getData().clear();
        mainFXApplication.displayWaterSourceReportOverviewScene();
    }

    /**
     * loads the water purity reports of a water source report
     * @param waterPurityReportList list of water purity reports of a water source report
     */
    public void setWaterPurityData(List<WaterPurityReport> waterPurityReportList) {
        this.waterPurityReportList = waterPurityReportList;
//        double[] monthCounter = new double[12];
//        int[] eachMonthTotalReports = new int[12];
//        for (WaterPurityReport p : waterPurityReportList) {
//            int month = p.getMonth() - 1;
//            monthCounter[month] += p.getVirusPPM();
//            eachMonthTotalReports[month] ++;
//        }
//
//        double[] averages = new double[12];
//        for (int i = 0; i < 12; i++) {
//            if (eachMonthTotalReports[i] != 0) {
//                averages[i] = monthCounter[i] / eachMonthTotalReports[i];
//            }
//        }
//
//        XYChart.Series<String, Double> series = createMonthDataSeries(averages);
//        chart.getData().add(series);
    }

    /**
     * makes the lines of the graph
     * @param monthCounter arrary that contains data that will be displayed
     * @return series that will be displayed on a graph
     */
    private XYChart.Series<String, Double> createMonthDataSeries(double[] monthCounter) {
        XYChart.Series<String,Double> series = new XYChart.Series<String,Double>();

        for (int i = 0; i < monthCounter.length; i++) {
            XYChart.Data<String, Double> monthData = new XYChart.Data<String,Double>(monthNames.get(i), monthCounter[i]);
            series.getData().add(monthData);
        }

        return series;
    }

    /**
     * loads the virusPPM averages and puts them in the graph
     */
    @FXML
    private void virusPPM() {
        if (isInputValid()) {
            if ((virusPPMClick % 2) != 0) {
                double[] monthCounter = new double[numOfMonths];
                int[] eachMonthTotalReports = new int[numOfMonths];
                for (WaterPurityReport p : waterPurityReportList) {
                    if (yearField.getText().equals(p.getYear() + "")) {
                        int month = p.getMonth() - 1;
                        monthCounter[month] += p.getVirusPPM();
                        eachMonthTotalReports[month]++;
                    }
                }

                double[] averages = new double[numOfMonths];
                for (int i = 0; i < averages.length; i++) {
                    if (eachMonthTotalReports[i] != 0) {
                        averages[i] = monthCounter[i] / eachMonthTotalReports[i];
                    }
                }

                virusSeries = createMonthDataSeries(averages);
                virusSeries.setName("VirusPPM");
                chart.getData().add(virusSeries);
            } else {
                chart.getData().remove(virusSeries);
            }
            virusPPMClick++;
        }
    }

    /**
     * loads the contaminantPPM averages and puts them into the graph
     */
    @FXML
    private void contaminantPPM() {
        if (isInputValid()) {
            if ((contaminantPPMClick % 2) != 0) {
                double[] monthCounter = new double[numOfMonths];
                int[] eachMonthTotalReports = new int[numOfMonths];
                for (WaterPurityReport p : waterPurityReportList) {
                    if (yearField.getText().equals(p.getYear() + "")) {
                        int month = p.getMonth() - 1;
                        monthCounter[month] += p.getContaminantPPM();
                        eachMonthTotalReports[month]++;
                    }
                }

                double[] averages = new double[numOfMonths];
                for (int i = 0; i < averages.length; i++) {
                    if (eachMonthTotalReports[i] != 0) {
                        averages[i] = monthCounter[i] / eachMonthTotalReports[i];
                    }
                }

                contaminantSeries = createMonthDataSeries(averages);
                contaminantSeries.setName("ContaminantPPM");

                chart.getData().add(contaminantSeries);
            } else {
                chart.getData().remove(contaminantSeries);
            }
            contaminantPPMClick++;
        }
    }

    /**
     * validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        //for now just check they actually typed something
        if ((yearField.getText() == null) || (yearField.getText().length() == 0)) {
            errorMessage += "No valid year!";
        }

        //no error message means success / good input
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message if bad data
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainFXApplication.getWindow());
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

}
