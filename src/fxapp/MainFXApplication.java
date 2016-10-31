package fxapp;

import controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Model;
import model.WaterPurityReport;
import model.WaterSourceReport;

import java.io.IOException;

public class MainFXApplication extends Application {

    private Stage window;

    private Scene welcomeScene;

    private Scene loginScene;

    private Scene registerScene;

    private Scene mainInApplicationScene;

    private Scene editProfileScene;

    private Scene waterSourceReportOverviewScene;

    private Scene waterPurityReportOverviewScene;

    private Scene waterAvailabilityScene;

    private Scene adminScene;

    private Scene historyGraphScene;

    private Main_InApplication_Controller mainInApplicationController;

    private Edit_Profile_Controller editProfileController;

    private Water_Availability_Controller waterAvailabilityController;

    private Water_Purity_Report_Overview_Controller waterPurityOverviewController;

    private History_Graph_Controller historyGraphController;

    private Water_Source_Report_Overview_Controller waterSourceOverviewController;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * window of application
     *
     * @return main stage of application
     */
    public Stage getWindow() {
        return window;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        window = primaryStage;
        primaryStage.setTitle("Clean.IO");

        loadWelcomeScene();
        loadLoginScene();
        loadRegisterScene();
        loadMainInApplication();
        loadEditProfileScene();
        loadWaterSourceReportOverView();
        loadWaterPurityReportOverView();
        loadWaterAvailabilityScene();
        loadAdminScene();
        loadHistoryGraphScene();

        displayWelcomeScene();
    }

    private void loadWelcomeScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFXApplication.class.getResource("../view/Welcome_Screen.fxml"));
        BorderPane welcomeScreenLayout = loader.load();

        // Give the controller access to the main app.
        Welcome_Controller controller = loader.getController();
        controller.setMainApp(this);

        welcomeScene = new Scene(welcomeScreenLayout);
    }

    private void loadLoginScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFXApplication.class.getResource("../view/Login_Screen.fxml"));
        AnchorPane loginScreenLayout = loader.load();

        // Give the controller access to the main app.
        Login_Controller controller = loader.getController();
        controller.setMainApp(this);

        loginScene = new Scene(loginScreenLayout);
    }

    private void loadRegisterScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFXApplication.class.getResource("../view/Register_Screen.fxml"));
        AnchorPane registerScreenLayout = loader.load();

        // Give the controller access to the main app.
        Register_Controller controller = loader.getController();
        controller.setMainApp(this);

        registerScene = new Scene(registerScreenLayout);
    }

    private void loadMainInApplication() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFXApplication.class.getResource("../view/Main_InApplication_Screen.fxml"));
        AnchorPane mainInApplicationScreenLayout = loader.load();

        // Give the controller access to the main app.
        Main_InApplication_Controller controller = loader.getController();
        mainInApplicationController = controller;
        controller.setMainApp(this);

        mainInApplicationScene = new Scene(mainInApplicationScreenLayout);
    }

    private void loadEditProfileScene() throws IOException {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/Edit_Profile_Screen.fxml"));
            AnchorPane editProfileScreenLayout = loader.load();

            // Give the controller access to the main app.
            Edit_Profile_Controller controller = loader.getController();
            editProfileController = controller;
            controller.setMainApp(this);

            editProfileScene = new Scene(editProfileScreenLayout);
    }

    private void loadWaterSourceReportOverView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFXApplication.class.getResource("../view/Water_Source_Report_Overview_Screen.fxml"));
        AnchorPane waterSourceReportOverviewLayout = loader.load();

        // Give the controller access to the main app.
        Water_Source_Report_Overview_Controller controller = loader.getController();
        waterSourceOverviewController = controller;
        controller.setMainApp(this);

        waterSourceReportOverviewScene = new Scene(waterSourceReportOverviewLayout);
    }

    private void loadWaterPurityReportOverView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFXApplication.class.getResource("../view/Water_Purity_Report_Overview_Screen.fxml"));
        AnchorPane waterSourceReportOverviewLayout = loader.load();

        // Give the controller access to the main app.
        Water_Purity_Report_Overview_Controller controller = loader.getController();
        waterPurityOverviewController = controller;
        controller.setMainApp(this);

        waterPurityReportOverviewScene = new Scene(waterSourceReportOverviewLayout);
    }

    private void loadWaterAvailabilityScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFXApplication.class.getResource("../view/Water_Availability_Screen.fxml"));
        BorderPane waterSourceReportOverviewLayout = loader.load();

        // Give the controller access to the main app.
        Water_Availability_Controller controller = loader.getController();
        waterAvailabilityController = controller;
        controller.setMainApp(this);

        waterAvailabilityScene = new Scene(waterSourceReportOverviewLayout);
    }

    private void loadAdminScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFXApplication.class.getResource("../view/Admin_Screen.fxml"));
        BorderPane waterSourceReportOverviewLayout = loader.load();

        // Give the controller access to the main app.
        Admin_Controller controller = loader.getController();
        controller.setMainApp(this);

        adminScene = new Scene(waterSourceReportOverviewLayout);
    }

    private void loadHistoryGraphScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFXApplication.class.getResource("../view/History_Graph_Screen.fxml"));
        AnchorPane waterSourceReportOverviewLayout = loader.load();

        // Give the controller access to the main app.
        History_Graph_Controller controller = loader.getController();
        historyGraphController = controller;
        controller.setMainApp(this);

        historyGraphScene = new Scene(waterSourceReportOverviewLayout);
    }

    public void displayWelcomeScene() {
        window.setScene(welcomeScene);
        window.show();
    }

    public void displayLoginScene() {
        window.setScene(loginScene);
        window.show();
    }

    public void displayRegisterScene() {
        window.setScene(registerScene);
        window.show();
    }

    public void displayMainInApplicationScene() {
        mainInApplicationController.setProfile(Model.getInstance().getLoggedInProfile());
        window.setScene(mainInApplicationScene);
        window.show();
    }

    public void displayEditProfileScene() {
        editProfileController.setProfile(Model.getInstance().getLoggedInProfile());
        window.setScene(editProfileScene);
        window.show();
    }

    public void displayWaterSourceReportOverviewScene() {
        waterSourceOverviewController.setProfile(Model.getInstance().getLoggedInProfile());
        window.setScene(waterSourceReportOverviewScene);
        window.show();
    }

    public void displayWaterPurityReportOverviewScene(WaterSourceReport waterSourceReport) {
        window.setScene(waterPurityReportOverviewScene);
        waterPurityOverviewController.setData(waterSourceReport);
        window.show();
    }

    public void displayWaterAvailabilityScene() {
        waterAvailabilityController.setProfile(Model.getInstance().getLoggedInProfile());
        window.setScene(waterAvailabilityScene);
        window.show();
    }

    public void displayAdminScene() {
        window.setScene(adminScene);
        window.show();
    }

    public void displayHistoryGraphScene(WaterSourceReport waterSourceReport) {
        historyGraphController.setWaterPurityData(waterSourceReport.getWaterPurityReports());
        window.setScene(historyGraphScene);
        window.show();
    }

}
