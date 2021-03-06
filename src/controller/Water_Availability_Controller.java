package controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import model.AccountType;
import model.ConditionOfWater;
import model.Model;
import model.OverallCondition;
import model.Profile;
import model.TypeOfWater;
import model.WaterPurityReport;
import model.WaterSourceReport;
import netscape.javascript.JSObject;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * controller for the water availability scene
 */
public class Water_Availability_Controller implements Initializable,
        MapComponentInitializedListener {

    @FXML
    private GoogleMapView mapView;

    private GoogleMap map;

    /** logged in user **/
    private Profile profile;

    @FXML
    private TitledPane submitWaterReportPane;
    @FXML
    private TextField latitudeFieldWSR;
    @FXML
    private TextField longitudeFieldWSR;
    @FXML
    private ComboBox<TypeOfWater> typeOfWaterComboBox;
    @FXML
    private ComboBox<ConditionOfWater> conditionOfWaterComboBox;

    @FXML
    private TitledPane submitWaterPurityReportPane;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField latitudeField1;
    @FXML
    private TextField longitudeField1;
    @FXML
    private ComboBox<OverallCondition> overallConditionComboBox;
    @FXML
    private TextField virusPPMField;
    @FXML
    private TextField contaminantPPMField;

    private final Double LATCENTER = 33.7756;

    private final Double LONGCENTER = -84.3963;

    /** a link back to the main application class */
    private MainFXApplication mainApplication;

    private Marker lol;

    /**
     * setup the main application link so we can call methods there
     *
     * @param mainFXApplication  a reference (link) to our main class
     */
    public void setMainApp(MainFXApplication mainFXApplication) {
        mainApplication = mainFXApplication;
    }

    /**
     * sets the profile that is logged in
     * @param profile profile that is logged in
     */
    public void setProfile(Profile profile) {
        this.profile = profile;
        if (profile.getAccountType().equals(AccountType.USER)) {
            submitWaterPurityReportPane.setVisible(false);
        } else {
            submitWaterPurityReportPane.setVisible(true);
        }
    }

    @FXML
    private void handleBackToMainMenuPressed() {
        clearAll();
        submitWaterPurityReportPane.setExpanded(false);
        submitWaterReportPane.setExpanded(false);
        mainApplication.displayMainInApplicationScene();
    }

    @Override
    public void mapInitialized() {
        MapOptions options = new MapOptions();

        //set up the center location for the map
//        Double centerLat = 33.7756;
//        Double centerLong = -84.3963;
        LatLong center = new LatLong(LATCENTER, LONGCENTER);

        options.center(center)
                .zoom(9)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .mapType(MapTypeIdEnum.TERRAIN);

        map = mapView.createMap(options);
        lol = new Marker(new MarkerOptions());

        map.addUIEventHandler(UIEventType.click, (JSObject obj) -> {
            if (submitWaterReportPane.isExpanded()) {
                LatLong hi = new LatLong((JSObject) obj.getMember("latLng"));
                lol.setPosition(hi);
                map.addMarker(lol);
                latitudeFieldWSR.setText(hi.getLatitude() + "");
                longitudeFieldWSR.setText(hi.getLongitude() + "");
                int currentZoom = map.getZoom();
                map.setZoom(currentZoom - 1);
                map.setZoom(currentZoom);
//            } else if (submitWaterPurityReportPane.isExpanded()) {

//                LatLong hi = new LatLong((JSObject) obj.getMember("latLng"));
//                lol.setPosition(hi);
//                map.addMarker(lol);
//                latitudeField1.setText(hi.getLatitude() + "");
//                longitudeField1.setText(hi.getLongitude() + "");
//                int currentZoom = map.getZoom();
//                map.setZoom(currentZoom - 1);
//                map.setZoom(currentZoom);
            }
        });

        List<WaterSourceReport> waterSourceReports = Model.getInstance()
                .getWaterSourceReports();

        for (WaterSourceReport waterSourceReport : waterSourceReports) {
            MarkerOptions markerOptions = new MarkerOptions();
            LatLong loc = new LatLong(waterSourceReport.getLatitude(),
                    waterSourceReport.getLongitude());
            markerOptions.position(loc)
                    .visible(Boolean.TRUE);

            Marker marker = new Marker(markerOptions);
            InfoWindow window = new InfoWindow();
            map.addUIEventHandler(marker,
                    UIEventType.click,
                    (JSObject obj) -> {
                        if (submitWaterPurityReportPane.isExpanded()) {
                            LatLong location = new LatLong((JSObject) obj
                                    .getMember("latLng"));
                            latitudeField1.
                                    setText(location.getLatitude() + "");
                            longitudeField1.
                                    setText(location.getLongitude() + "");

                        }
                        InfoWindowOptions infoWindowOptions =
                                new InfoWindowOptions();
                        infoWindowOptions.content(waterSourceReport.
                                getTypeOfWater() + "<br>"
                                + waterSourceReport.getConditionOfWater());
                        window.setOptions(infoWindowOptions);
                        window.open(map, marker);
                    });

            map.addMarker(marker);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapView.addMapInializedListener(this);
        typeOfWaterComboBox.getItems().setAll(TypeOfWater.values());
        typeOfWaterComboBox.setValue(TypeOfWater.Bottled);

        conditionOfWaterComboBox.getItems().setAll(ConditionOfWater.values());
        conditionOfWaterComboBox.setValue(ConditionOfWater.Potable);

        overallConditionComboBox.getItems().setAll(OverallCondition.values());
        overallConditionComboBox.setValue(OverallCondition.Safe);
    }

    /**
     * called when the submit water report tab is clicked
     */
    @FXML
    private void handleWaterReportTabPressed() {
        if (!submitWaterReportPane.isExpanded()) {
            clearAll();
        } else if (!submitWaterPurityReportPane.isExpanded()) {
            clearAll();
        }
    }

    /**
     * called when the submit water purity report tab is clicked
     */
    @FXML
    private void handleWaterPurityTabPressed() {
        if (!submitWaterPurityReportPane.isExpanded()) {
            clearAll();
        } else if (!submitWaterReportPane.isExpanded()) {
            clearAll();
        }
    }

    /**
     * called when the user clicks submit in the submit water report pane
     */
    @FXML
    private void handleSubmitWaterReportButtonPressed() {
        if (isInputValid()) {
            Date yeah = new Date();
            SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
            String date = dateFormatter.format(yeah);
            String time = timeFormatter.format(yeah);
            String nameOfReporter = profile.getUsername();
            Double latitude = Double.parseDouble(latitudeFieldWSR.getText());
            Double longitude = Double.parseDouble(longitudeFieldWSR.getText());
            TypeOfWater typeOfWater =
                    typeOfWaterComboBox.getSelectionModel().getSelectedItem();
            ConditionOfWater conditionOfWater =
                    conditionOfWaterComboBox.
                            getSelectionModel().getSelectedItem();
            WaterSourceReport waterSourceReport
                 = new WaterSourceReport(date, time, nameOfReporter, latitude,
                    longitude, typeOfWater, conditionOfWater);

            if (Model.getInstance().addWaterSourceReport(waterSourceReport)) {

                Marker marker = new Marker(new MarkerOptions());
                marker.setPosition(new LatLong(latitude, longitude));
                InfoWindow window = new InfoWindow();
                map.addUIEventHandler(marker,
                        UIEventType.click,
                        (JSObject obj) -> {
                            if (submitWaterPurityReportPane.isExpanded()) {
                                LatLong location =new LatLong((JSObject) obj.
                                        getMember("latLng"));
                                latitudeField1.setText(location.
                                        getLatitude() + "");
                                longitudeField1.setText(location.
                                        getLongitude() + "");

                            }

                            InfoWindowOptions infoWindowOptions =
                                    new InfoWindowOptions();
                            infoWindowOptions.content(waterSourceReport.
                                    getTypeOfWater() + "<br>"
                                    + waterSourceReport.getConditionOfWater());
                            window.setOptions(infoWindowOptions);
                            window.open(map, marker);
                        });

                map.addMarker(marker);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setHeaderText("Water report successfully submitted!");
                //alert.setContentText("I have a great message for you!");

                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                Stage stage = mainApplication.getWindow();
                alert.initOwner(stage);
                alert.setTitle("Error");
                alert.setHeaderText("Someone has already submitted a" +
                        "report for this location.");
                //alert.setContentText("wrong username or password");

                alert.showAndWait();
            }
        }
    }

    /**
     * called when the user clicks submit in the submit water purity pane
     */
    @FXML
    private void handleSubmitWaterPurityButtonPressed() {
        if (isInputValid()) {
//            Date yeah = new Date();
//            SimpleDateFormat dateFormatter =
//              new SimpleDateFormat("MM/dd/yyyy");
//            SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
            String date = datePicker.getValue().getMonthValue() + "/" +
                    datePicker.getValue().getDayOfMonth() + "/"
                    + datePicker.getValue().getYear();
            String time = "default";
//            String date = dateFormatter.format(yeah);
//            String time = timeFormatter.format(yeah);
            String nameOfReporter = profile.getUsername();
            Double latitude = Double.parseDouble(latitudeField1.getText());
            Double longitude = Double.parseDouble(longitudeField1.getText());
            OverallCondition overallCondition = overallConditionComboBox.
                    getSelectionModel().getSelectedItem();
            Double virusPPM = Double.parseDouble(virusPPMField.getText());
            Double contaminantPPM = Double.parseDouble(contaminantPPMField.
                    getText());
            WaterPurityReport waterPurityReport
                    = new WaterPurityReport(date, time, nameOfReporter,
                    latitude, longitude,
                    overallCondition, virusPPM, contaminantPPM);
            waterPurityReport.setMonth(datePicker.getValue().getMonthValue());
            waterPurityReport.setYear(datePicker.getValue().getYear());

            if (!Model.getInstance().addWaterPurityReport(waterPurityReport)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                Stage stage = mainApplication.getWindow();
                alert.initOwner(stage);
                alert.setTitle("Error");
                alert.setHeaderText("Someone has already submitted a" +
                        "report for this location.");
                //alert.setContentText("wrong username or password");

                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setHeaderText("Water report successfully submitted!");
                //alert.setContentText("I have a great message for you!");

                alert.showAndWait();
            }

            map.removeMarker(lol);
            int currentZoom = map.getZoom();
            map.setZoom(currentZoom - 1);
            map.setZoom(currentZoom);
        }
    }

    /**
     * validates the user input in the text fields.
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";
            if (submitWaterReportPane.isExpanded()) {
                //for now just check they actually typed something
                if ((longitudeFieldWSR.getText() == null) ||
                        (longitudeFieldWSR.getText().isEmpty())) {
                    errorMessage += "No valid longitude!\n";
                }
                if ((latitudeFieldWSR.getText() == null) ||
                        (latitudeFieldWSR.getText().isEmpty())) {
                    errorMessage += "No valid latitude!\n";
                }
            } else if (submitWaterPurityReportPane.isExpanded()) {
                //for now just check they actually typed something
                if (datePicker.getValue() == null) {
                    errorMessage += "No valid date!\n";
                }
                if ((longitudeField1.getText() == null) ||
                        (longitudeField1.getText().isEmpty())) {
                    errorMessage += "No valid longitude!\n";
                }
                if ((latitudeField1.getText() == null) ||
                        (latitudeField1.getText().isEmpty())) {
                    errorMessage += "No valid latitude!\n";
                }

                //for now just check they actually typed something
                if ((virusPPMField.getText() == null) ||
                        (virusPPMField.getText().isEmpty())) {
                    errorMessage += "No valid virus PPM!\n";
                }
                if ((contaminantPPMField.getText() == null) ||
                        (contaminantPPMField.getText().isEmpty())) {
                    errorMessage += "No valid contaminant PPM!\n";
                }
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

    private void clearAll() {
        latitudeFieldWSR.setText("");
        longitudeFieldWSR.setText("");
        typeOfWaterComboBox.setValue(TypeOfWater.Bottled);
        conditionOfWaterComboBox.setValue(ConditionOfWater.Potable);

        latitudeField1.setText("");
        longitudeField1.setText("");
        overallConditionComboBox.setValue(OverallCondition.Safe);
        virusPPMField.setText("");
        contaminantPPMField.setText("");
        map.removeMarker(lol);
        int currentZoom = map.getZoom();
        map.setZoom(currentZoom - 1);
        map.setZoom(currentZoom);
    }

}
