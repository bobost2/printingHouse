package bstefanov.printinghouse.ui.controllers;

import bstefanov.printinghouse.data.printer.CartridgeType;
import bstefanov.printinghouse.data.printer.Printer;
import bstefanov.printinghouse.service.PrintingHouseService;
import bstefanov.printinghouse.ui.utils.SceneAndDataManagerSingleton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreatePrinterController implements Initializable {

    @FXML
    private Label actionsMenuLabel;

    @FXML
    private TextField modelTextBox;

    @FXML
    private ChoiceBox<String> cartridgeTypeChoiceBox;

    @FXML
    private Spinner<Integer> paperCapacitySpinner;

    @FXML
    private Button addPrinterButton;

    @FXML
    protected void onClickGoBackButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.switchPane(event, "select-printer-view.fxml");
    }

    @FXML
    protected void onClickAddPrinterButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();

        PrintingHouseService selectedPrintingHouse = sceneAndDataMng.getSelectedPrintingHouse();
        CartridgeType cartridgeType =
                cartridgeTypeChoiceBox.getValue().equals("Color") ? CartridgeType.COLOR : CartridgeType.BLACK_AND_WHITE;
        Printer printer = new Printer(modelTextBox.getText(), cartridgeType, paperCapacitySpinner.getValue());
        selectedPrintingHouse.addPrinter(printer);

        sceneAndDataMng.switchPane(event, "select-printer-view.fxml");
    }

    @FXML
    protected void onChangedTextField() {
        addPrinterButton.setDisable(modelTextBox.getText().isBlank());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        PrintingHouseService selectedPrintingHouse = sceneAndDataMng.getSelectedPrintingHouse();

        if (selectedPrintingHouse != null) {
            actionsMenuLabel.setText(selectedPrintingHouse.getName() + " - Add Printer:");

            cartridgeTypeChoiceBox.setItems(FXCollections.observableArrayList("Black and White", "Color"));
            cartridgeTypeChoiceBox.setValue("Black and White");

            paperCapacitySpinner.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 20));
        }
    }
}
