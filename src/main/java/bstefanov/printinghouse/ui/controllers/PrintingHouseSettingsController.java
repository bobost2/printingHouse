package bstefanov.printinghouse.ui.controllers;

import bstefanov.printinghouse.service.PrintingHouseService;
import bstefanov.printinghouse.ui.utils.SceneAndDataManagerSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class PrintingHouseSettingsController implements Initializable {

    @FXML
    private Label actionsMenuLabel;

    @FXML
    protected void onClickGeneralSettingsButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.switchPane(event, "printing-house-general-settings-view.fxml");
    }

    @FXML
    protected void onClickEconomySettingsButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.switchPane(event, "printing-house-economy-settings-view.fxml");
    }

    @FXML
    protected void onClickDestroyButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();

        Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle("Delete Printing House");
        alert.setHeaderText("Are you sure you want to delete this printing house?");
        alert.setContentText("This action cannot be undone.");
        Optional<ButtonType> alertResult = alert.showAndWait();
        if (alertResult.isPresent() && alertResult.get() == ButtonType.OK) {
            sceneAndDataMng.getPrintingHouses().remove(sceneAndDataMng.getSelectedPrintingHouse());
            sceneAndDataMng.switchPane(event, "select-printing-house-view.fxml");
        }
    }

    @FXML
    protected void onClickReturnToOperationsButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.switchPane(event, "printing-house-actions-view.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        PrintingHouseService selectedPrintingHouse = sceneAndDataMng.getSelectedPrintingHouse();

        if (selectedPrintingHouse != null) {
            actionsMenuLabel.setText(selectedPrintingHouse.getName() + " - Settings:");
        }
    }
}
