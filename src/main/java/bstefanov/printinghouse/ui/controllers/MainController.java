package bstefanov.printinghouse.ui.controllers;

import bstefanov.printinghouse.data.audit.FinalReport;
import bstefanov.printinghouse.service.SerializingService;
import bstefanov.printinghouse.ui.utils.SceneAndDataManagerSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainController {

    @FXML
    protected void onClickMenuSelectPrintingHouse(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.switchPane(event, "select-printing-house-view.fxml");
    }

    @FXML
    protected void onClickMenuLoadReport(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Printing House Report", "*.phr"));
        fileChooser.setTitle("Load Report File");

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            SerializingService serializingService = new SerializingService();

            FinalReport report = serializingService.loadDayReport(selectedFile.getPath());
            SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
            sceneAndDataMng.setFinalReport(report);
            sceneAndDataMng.setReportOpenedFromFile(true);
            sceneAndDataMng.switchPane(event, "report-printing-house-view.fxml");
        }
    }
}