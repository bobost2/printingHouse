package bstefanov.printinghouse.ui.controllers;

import bstefanov.printinghouse.service.PrintingHouseService;
import bstefanov.printinghouse.ui.utils.SceneAndDataManagerSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrintingHouseGeneralSettingsController implements Initializable {

    @FXML
    private Label actionsMenuLabel;

    @FXML
    private TextField nameTextBox;

    @FXML
    private TextField addressTextBox;

    @FXML
    private Button editButton;

    @FXML
    protected void onClickGoBackButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.switchPane(event, "printing-house-settings-view.fxml");
    }

    @FXML
    protected void onClickEditButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        PrintingHouseService selectedPrintingHouse = sceneAndDataMng.getSelectedPrintingHouse();

        selectedPrintingHouse.setName(nameTextBox.getText());
        selectedPrintingHouse.setAddress(addressTextBox.getText());
        sceneAndDataMng.switchPane(event, "printing-house-settings-view.fxml");
    }

    @FXML
    protected void onChangedTextField() {
        editButton.setDisable(nameTextBox.getText().isBlank() || addressTextBox.getText().isBlank());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        PrintingHouseService selectedPrintingHouse = sceneAndDataMng.getSelectedPrintingHouse();

        if (selectedPrintingHouse != null) {
            actionsMenuLabel.setText(selectedPrintingHouse.getName() + " - General Settings:");
            nameTextBox.setText(selectedPrintingHouse.getName());
            addressTextBox.setText(selectedPrintingHouse.getAddress());
        }
    }
}
