package bstefanov.printinghouse.ui.controllers;

import bstefanov.printinghouse.service.PrintingHouseService;
import bstefanov.printinghouse.ui.utils.SceneAndDataManagerSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrintingHouseActionsController implements Initializable {

    @FXML
    private Label actionsMenuLabel;

    @FXML
    private Button startDayButton;

    @FXML
    private Button endDayButton;

    @FXML
    private Button sellBooksButton;

    @FXML
    protected void onClickManageEmployeesButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.switchPane(event, "select-employee-view.fxml");
    }

    @FXML
    protected void onClickManagePrintersButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.switchPane(event, "select-printer-view.fxml");
    }

    @FXML
    protected void onClickSettingsButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.switchPane(event, "printing-house-settings-view.fxml");
    }

    @FXML
    protected void onClickExitButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.switchPane(event, "select-printing-house-view.fxml");
    }

    private void refreshButtons() {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        PrintingHouseService selectedPrintingHouse = sceneAndDataMng.getSelectedPrintingHouse();

        if (selectedPrintingHouse != null) {
            startDayButton.setDisable(selectedPrintingHouse.isDayStarted());
            endDayButton.setDisable(!selectedPrintingHouse.isDayStarted());
            sellBooksButton.setDisable(!selectedPrintingHouse.isDayStarted());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        PrintingHouseService selectedPrintingHouse = sceneAndDataMng.getSelectedPrintingHouse();

        if (selectedPrintingHouse != null) {
            actionsMenuLabel.setText(selectedPrintingHouse.getName() + " - Select Operation:");
            refreshButtons();
        }
    }
}
