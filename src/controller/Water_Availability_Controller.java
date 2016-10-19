package controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import fxapp.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import model.*;
import netscape.javascript.JSObject;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * controller for the water availability scene
 */
public class Water_Availability_Controller implements Initializable, MapComponentInitializedListener {

    @FXML
    private GoogleMapView mapView;

    private GoogleMap map;

    private Profile profile;

    @FXML
    private TitledPane submitWaterReportPane;
    @FXML
    private TextField latitudeField;
    @FXML
    private TextField longitudeField;
    @FXML
    private ComboBox<TypeOfWater> typeOfWaterComboBox;
    @FXML
    private ComboBox<ConditionOfWater> conditionOfWaterComboBox;

    @FXML
    private TitledPane submitWaterPurityReportPane;
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

    /** a link back to the main application class */
    private Main mainApplication;

    private Marker lol;

    /**
     * setup the main application link so we can call methods there
     *
     * @param mainFXApplication  a reference (link) to our main class
     */
    public void setMainApp(Main mainFXApplication) {
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
        LatLong center = new LatLong(33.7756,-84.3963);

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
                latitudeField.setText(hi.getLatitude() + "");
                longitudeField.setText(hi.getLongitude() + "");
                int currentZoom = map.getZoom();
                map.setZoom(currentZoom - 1);
                map.setZoom(currentZoom);
            } else if (submitWaterPurityReportPane.isExpanded()) {
                LatLong hi = new LatLong((JSObject) obj.getMember("latLng"));
                lol.setPosition(hi);
                map.addMarker(lol);
                latitudeField1.setText(hi.getLatitude() + "");
                longitudeField1.setText(hi.getLongitude() + "");
                int currentZoom = map.getZoom();
                map.setZoom(currentZoom - 1);
                map.setZoom(currentZoom);
            }
        });

        Model facade = Model.getInstance();
        List<WaterSourceReport> waterSourceReports = facade.getDatabase().getWaterSourceReports();

        for (WaterSourceReport waterSourceReport : waterSourceReports) {
            MarkerOptions markerOptions = new MarkerOptions();
            LatLong loc = new LatLong(waterSourceReport.getLatitude(), waterSourceReport.getLongitude());
            markerOptions.position(loc)
                    .visible(Boolean.TRUE);

            Marker marker = new Marker(markerOptions);

            map.addUIEventHandler(marker,
                    UIEventType.click,
                    (JSObject obj) -> {
                        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
                        infoWindowOptions.content(waterSourceReport.getTypeOfWater() + "<br>" + waterSourceReport.getConditionOfWater());

                        InfoWindow window = new InfoWindow(infoWindowOptions);
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
            String nameOfReporter = Model.getInstance().getLoggedInProfile().getUsername();
            Double latitude = Double.parseDouble(latitudeField.getText());
            Double longitude = Double.parseDouble(longitudeField.getText());
            TypeOfWater typeOfWater = typeOfWaterComboBox.getSelectionModel().getSelectedItem();
            ConditionOfWater conditionOfWater = conditionOfWaterComboBox.getSelectionModel().getSelectedItem();
            WaterSourceReport waterSourceReport
                    = new WaterSourceReport(date, time, nameOfReporter, latitude, longitude, typeOfWater, conditionOfWater);
            Model.getInstance().addWaterSourceReport(waterSourceReport);

            Marker marker = new Marker(new MarkerOptions());
            marker.setPosition(new LatLong(latitude, longitude));
            map.addUIEventHandler(marker,
                    UIEventType.click,
                    (JSObject obj) -> {
                        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
                        infoWindowOptions.content(waterSourceReport.getTypeOfWater().toString() + "<br>" +
                                waterSourceReport.getConditionOfWater().toString());

                        InfoWindow window = new InfoWindow(infoWindowOptions);
                        window.open(map, marker);
                    });

            map.addMarker(marker);
        }
    }

    /**
     * called when the user clicks submit in the sibmit water purity pane
     */
    @FXML
    private void handleSubmitWaterPurityButtonPressed() {
        if (isInputValid()) {
            Date yeah = new Date();
            SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
            String date = dateFormatter.format(yeah);
            String time = timeFormatter.format(yeah);
            String nameOfReporter = Model.getInstance().getLoggedInProfile().getUsername();
            Double latitude = Double.parseDouble(latitudeField1.getText());
            Double longitude = Double.parseDouble(longitudeField1.getText());
            OverallCondition overallCondition = overallConditionComboBox.getSelectionModel().getSelectedItem();
            Double virusPPM = Double.parseDouble(virusPPMField.getText());
            Double contaminantPPM = Double.parseDouble(contaminantPPMField.getText());
            WaterPurityReport waterPurityReport
                    = new WaterPurityReport(date, time, nameOfReporter, latitude, longitude, overallCondition, virusPPM, contaminantPPM);
            Model.getInstance().addWaterPurityReport(waterPurityReport);

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
                if (longitudeField.getText() == null || longitudeField.getText().length() == 0) {
                    errorMessage += "No valid longitude!\n";
                }
                if (latitudeField.getText() == null || latitudeField.getText().length() == 0) {
                    errorMessage += "No valid latitude!\n";
                }
            } else if (submitWaterPurityReportPane.isExpanded()) {
                //for now just check they actually typed something
                if (longitudeField1.getText() == null || longitudeField1.getText().length() == 0) {
                    errorMessage += "No valid longitude!\n";
                }
                if (latitudeField1.getText() == null || latitudeField1.getText().length() == 0) {
                    errorMessage += "No valid latitude!\n";
                }

                //for now just check they actually typed something
                if (virusPPMField.getText() == null || virusPPMField.getText().length() == 0) {
                    errorMessage += "No valid virus PPM!\n";
                }
                if (contaminantPPMField.getText() == null || contaminantPPMField.getText().length() == 0) {
                    errorMessage += "No valid contaminant PPM!\n";
                }
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

    private void clearAll() {
        latitudeField.setText("");
        longitudeField.setText("");
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
