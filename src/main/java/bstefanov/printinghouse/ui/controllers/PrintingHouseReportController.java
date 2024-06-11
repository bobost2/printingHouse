package bstefanov.printinghouse.ui.controllers;

import bstefanov.printinghouse.data.audit.AuditableRecord;
import bstefanov.printinghouse.data.audit.FinalReport;
import bstefanov.printinghouse.service.PrintingHouseService;
import bstefanov.printinghouse.service.SerializingService;
import bstefanov.printinghouse.ui.structs.ReportTableStruct;
import bstefanov.printinghouse.ui.utils.SceneAndDataManagerSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;

public class PrintingHouseReportController implements Initializable {

    @FXML
    private TableView<ReportTableStruct> reportViewTable;

    @FXML
    private TableColumn<ReportTableStruct, String> detailsColumn;

    @FXML
    private TableColumn<ReportTableStruct, String> moneyGainedLostColumn;

    @FXML
    private Label profitLabel;

    @FXML
    private Label expectedProfitLabel;

    @FXML
    private Label lossesLabel;

    @FXML
    private Label totalLabel;

    @FXML
    protected void onClickGoBackButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        if (!sceneAndDataMng.isReportOpenedFromFile()) {
            sceneAndDataMng.switchPane(event, "printing-house-actions-view.fxml");
        }
        else {
            sceneAndDataMng.switchPane(event, "main-view.fxml");
        }
    }

    @FXML
    protected void onClickExportButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        FinalReport report = sceneAndDataMng.getFinalReport();
        if (report == null) return;
        SerializingService serializingService = new SerializingService();
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Printing House Report", "*.phr"),
                    new FileChooser.ExtensionFilter("Text File", "*.txt")
            );
            fileChooser.setTitle("Save Report File");

            String fileName = "report.phr";
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            fileChooser.setInitialFileName(fileName);
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                fileName = file.getName();
                String extension = "";

                int i = fileName.lastIndexOf('.');
                if (i > 0) {
                    extension = fileName.substring(i + 1);
                }

                if (extension.equals("txt")) {
                    serializingService.saveDayReportTxt(file.getPath(), report);
                }
                else {
                    serializingService.saveDayReport(file.getPath(), report);
                }
            }
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
            alert.setTitle("Error");
            alert.setHeaderText("Error exporting report");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        FinalReport report = sceneAndDataMng.getFinalReport();
        if (report == null) return;

        profitLabel.setText(report.getProfit().setScale(2, RoundingMode.HALF_UP) + "$");
        expectedProfitLabel.setText("Expected profit: " +
                report.getExpectedProfit().setScale(2, RoundingMode.HALF_UP) + "$");
        lossesLabel.setText(report.getLosses().setScale(2, RoundingMode.HALF_UP) + "$");
        totalLabel.setText(report.getTotal().setScale(2, RoundingMode.HALF_UP) + "$");

        detailsColumn.setCellValueFactory(new PropertyValueFactory<>("details"));
        moneyGainedLostColumn.setCellValueFactory(new PropertyValueFactory<>("moneyGainedLost"));

        reportViewTable.getItems().clear();
        for (AuditableRecord entry : report.getAudit()) {
            ReportTableStruct tableStruct = new ReportTableStruct();
            tableStruct.setDetails(entry.recordDetails());
            tableStruct.setMoneyGainedLost(entry.moneyGainedOrLost().setScale(2, RoundingMode.HALF_UP) + "$");
            reportViewTable.getItems().add(tableStruct);
        }
    }
}
