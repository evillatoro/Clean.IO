package controller;

import fxapp.MainFXApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import model.WaterPurityReport;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by edwinvillatoro on 10/31/16.
 */
public class History_Graph_Controller {
    /** a link back to the main application class */
    private MainFXApplication mainFXApplicationApplication;

    @FXML
    private CategoryAxis x = new CategoryAxis();

    @FXML
    private NumberAxis y = new NumberAxis();

    @FXML
    private LineChart<String, Double> chart;

    private ObservableList<String> monthNames = FXCollections.observableArrayList();

    private List<WaterPurityReport> waterPurityReportList;

    private int virusPPMclick = 1;

    private int contaminantPPMclick = 1;

    private XYChart.Series<String, Double> virusSeries;

    private XYChart.Series<String, Double> contaminantSeries;

    /**
     * setup the main application link so we can call methods there
     *
     * @param mainFXApplicationFXApplication  a reference (link) to our main class
     */
    public void setMainApp(MainFXApplication mainFXApplicationFXApplication) {
        mainFXApplicationApplication = mainFXApplicationFXApplication;
    }

    @FXML
    private void initialize() {
        x.setLabel("Month");
        y.setLabel("PPM");
        //String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        String[] months = new String[12];
        for(int i = 0; i < 12; i++) {
            months[i] = (i + 1) + "";
        }
        monthNames.addAll(Arrays.asList(months));
        x.setCategories(monthNames);
    }

    @FXML
    private void handleBackPressed() {
        chart.getData().clear();
        mainFXApplicationApplication.displayMainInApplicationScene();
    }

    @FXML
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

    private XYChart.Series<String, Double> createMonthDataSeries(double[] monthCounter) {
        XYChart.Series<String,Double> series = new XYChart.Series<String,Double>();

        for (int i = 0; i < monthCounter.length; i++) {
            XYChart.Data<String, Double> monthData = new XYChart.Data<String,Double>(monthNames.get(i), monthCounter[i]);
            series.getData().add(monthData);
        }

        return series;
    }

    @FXML
    private void virusPPM() {
        if (virusPPMclick % 2 != 0) {
            double[] monthCounter = new double[12];
            int[] eachMonthTotalReports = new int[12];
            for (WaterPurityReport p : waterPurityReportList) {
                int month = p.getMonth() - 1;
                monthCounter[month] += p.getVirusPPM();
                eachMonthTotalReports[month]++;
            }

            double[] averages = new double[12];
            for (int i = 0; i < 12; i++) {
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
        virusPPMclick++;
    }

    @FXML
    private void contaminantPPM() {
        if (contaminantPPMclick % 2 != 0) {
            double[] monthCounter = new double[12];
            int[] eachMonthTotalReports = new int[12];
            for (WaterPurityReport p : waterPurityReportList) {
                int month = p.getMonth() - 1;
                monthCounter[month] += p.getContaminantPPM();
                eachMonthTotalReports[month]++;
            }

            double[] averages = new double[12];
            for (int i = 0; i < 12; i++) {
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
        contaminantPPMclick++;
    }

}
