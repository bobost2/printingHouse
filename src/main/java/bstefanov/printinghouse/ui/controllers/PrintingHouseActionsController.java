package bstefanov.printinghouse.ui.controllers;

import bstefanov.printinghouse.data.audit.FinalReport;
import bstefanov.printinghouse.service.PrintingHouseService;
import bstefanov.printinghouse.ui.utils.DoubleStringConverter;
import bstefanov.printinghouse.ui.utils.SceneAndDataManagerSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class PrintingHouseActionsController implements Initializable {

    @FXML
    private Label actionsMenuLabel;

    @FXML
    private Button startDayButton;

    @FXML
    private Button endDayButton;

    @FXML
    private Button sellEditionsButton;

    @FXML
    protected void onClickStartDayButton() {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        PrintingHouseService selectedPrintingHouse = sceneAndDataMng.getSelectedPrintingHouse();
        if (selectedPrintingHouse != null) {

            Dialog<Double> dialog = new Dialog<>();
            dialog.setTitle("Set target revenue");
            dialog.setHeaderText("Please enter the target revenue for the day before beginning the day");

            Spinner<Double> spinner = new Spinner<>();
            spinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 50000, 500, 100));
            spinner.getValueFactory().setConverter(DoubleStringConverter.getConverter());

            VBox vbox = new VBox(spinner);
            dialog.getDialogPane().setContent(vbox);

            ButtonType okButtonType = new ButtonType("Begin Day", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(okButtonType, cancelButtonType);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == okButtonType) {
                    return spinner.getValue();
                }
                return null;
            });

            Optional<Double> result = dialog.showAndWait();
            result.ifPresent(value -> {
                selectedPrintingHouse.setExpectedRevenue(BigDecimal.valueOf(value));
                selectedPrintingHouse.startDay();
                refreshButtons();
            });
        }
    }

    @FXML
    protected void onClickEndDayButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        PrintingHouseService selectedPrintingHouse = sceneAndDataMng.getSelectedPrintingHouse();
        if (selectedPrintingHouse != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.OK, ButtonType.CANCEL);
            alert.setTitle("End Day");
            alert.setHeaderText("Are you sure you want to end the day?");
            alert.setContentText("This action cannot be undone.");
            Optional<ButtonType> alertResult = alert.showAndWait();
            if (alertResult.isPresent() && alertResult.get() == ButtonType.OK) {
                FinalReport finalReport = selectedPrintingHouse.endDayAndGetReport();
                sceneAndDataMng.setFinalReport(finalReport);
                sceneAndDataMng.setReportOpenedFromFile(false);

                refreshButtons();
                sceneAndDataMng.switchPane(event, "report-printing-house-view.fxml");
            }
        }
    }

    @FXML
    protected void onClickListEditionsButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.switchPane(event, "list-edition-view.fxml");
    }

    @FXML
    protected void onClickSellEditionsButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.switchPane(event, "sell-edition-view.fxml");
    }

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
            sellEditionsButton.setDisable(!selectedPrintingHouse.isDayStarted());
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
