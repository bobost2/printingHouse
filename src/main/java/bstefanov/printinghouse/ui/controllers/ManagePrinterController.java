package bstefanov.printinghouse.ui.controllers;

import bstefanov.printinghouse.data.edition.Book;
import bstefanov.printinghouse.data.edition.Edition;
import bstefanov.printinghouse.data.printer.Printer;
import bstefanov.printinghouse.exceptions.printer.PrinterTooMuchPaperException;
import bstefanov.printinghouse.service.PrintingHouseService;
import bstefanov.printinghouse.ui.utils.SceneAndDataManagerSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManagePrinterController implements Initializable {

    private ArrayList<Edition> editions;

    @FXML
    private Label actionsMenuLabel;

    @FXML
    private Label paperLabel;

    @FXML
    private Spinner<Integer> fillPaperSpinner;

    @FXML
    private ChoiceBox<String> editionsToPrintChoiceBox;

    @FXML
    private Spinner<Integer> amountOfCopiesSpinner;

    private void refreshPaper() {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        Printer selectedPrinter = sceneAndDataMng.getSelectedPrinter();
        if (selectedPrinter != null) {
            paperLabel.setText(selectedPrinter.getCurrentAmountOfPaper() + "/" + selectedPrinter.getMaxAmountOfPaper());
        }
    }

    @FXML
    protected void onClickGoBackButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.switchPane(event, "select-printer-view.fxml");
    }

    @FXML
    protected void onClickRemovePrinterButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        PrintingHouseService selectedPrintingHouse = sceneAndDataMng.getSelectedPrintingHouse();
        if (selectedPrintingHouse == null) return;

        Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle("Delete Printer");
        alert.setHeaderText("Are you sure you want to delete this printer?");
        alert.setContentText("This action cannot be undone.");
        Optional<ButtonType> alertResult = alert.showAndWait();
        if (alertResult.isPresent() && alertResult.get() == ButtonType.OK) {
            selectedPrintingHouse.removePrinter(sceneAndDataMng.getSelectedPrinter());
            sceneAndDataMng.switchPane(event, "select-printer-view.fxml");
        }
    }

    @FXML
    protected void onClickFillPaperButton() {
        Printer selectedPrinter = SceneAndDataManagerSingleton.getInstance().getSelectedPrinter();
        if (selectedPrinter != null) {
            try {
                selectedPrinter.addPaper(fillPaperSpinner.getValue());
            } catch (PrinterTooMuchPaperException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
            refreshPaper();
        }
    }

    @FXML
    protected void onClickPrintButton(ActionEvent event) {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        PrintingHouseService selectedPrintingHouse = sceneAndDataMng.getSelectedPrintingHouse();
        if (selectedPrintingHouse == null) return;

        Printer selectedPrinter = SceneAndDataManagerSingleton.getInstance().getSelectedPrinter();
        if (selectedPrinter != null) {
            try {
                int selectedIndex = editionsToPrintChoiceBox.getSelectionModel().getSelectedIndex();
                selectedPrintingHouse.printEdition(selectedPrinter, editions.get(selectedIndex),
                        selectedPrinter.getCartridgeType(), amountOfCopiesSpinner.getValue());
            } catch (RuntimeException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
            refreshPaper();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        PrintingHouseService selectedPrintingHouse = sceneAndDataMng.getSelectedPrintingHouse();

        if (selectedPrintingHouse != null) {
            Printer selectedPrinter = sceneAndDataMng.getSelectedPrinter();
            if (selectedPrinter != null) {
                actionsMenuLabel.setText(selectedPrintingHouse.getName() + " - " + selectedPrinter.getModel() + ":");
                fillPaperSpinner.setValueFactory(
                        new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                                selectedPrinter.getMaxAmountOfPaper(), 1));
                refreshPaper();

                amountOfCopiesSpinner.setValueFactory(
                        new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1));

                editionsToPrintChoiceBox.getItems().clear();
                editions = new ArrayList<>(selectedPrintingHouse.getEditionsToPrint());
                for (Edition edition : editions) {
                    editionsToPrintChoiceBox.getItems().add(edition.getTitle());
                }

                if (!editionsToPrintChoiceBox.getItems().isEmpty()) {
                    editionsToPrintChoiceBox.setValue(editionsToPrintChoiceBox.getItems().get(0));
                }
            }
        }
    }
}
