package bstefanov.printinghouse.ui.controllers;

import bstefanov.printinghouse.data.printer.Printer;
import bstefanov.printinghouse.service.PrintingHouseService;
import bstefanov.printinghouse.ui.structs.PrinterTableStruct;
import bstefanov.printinghouse.ui.utils.SceneAndDataManagerSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

public class SelectPrinterController implements Initializable {

    @FXML
    private Label actionsMenuLabel;

    @FXML
    private TableView<PrinterTableStruct> printerViewTable;

    @FXML
    private TableColumn<PrinterTableStruct, String> modelColumn;

    @FXML
    private TableColumn<PrinterTableStruct, String> cartridgeTypeColumn;

    @FXML
    private TableColumn<PrinterTableStruct, Integer> paperColumn;

    @FXML
    private TableColumn<PrinterTableStruct, Integer> maxPaperColumn;

    @FXML
    protected void onClickGoBackButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.switchPane(event, "printing-house-actions-view.fxml");
    }

    @FXML
    protected void onClickAddPrinterButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.switchPane(event, "create-printer-view.fxml");
    }

    private void onTableRowClick(ActionEvent event, PrinterTableStruct tableStruct) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.setSelectedPrinter(tableStruct.getPrinter());
        System.out.println("Selected printer: " + tableStruct.getPrinter().getModel());
        System.out.println("Not implemented yet.");
//        sceneAndDataMng.switchPane(event, "select-printer-view.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        PrintingHouseService selectedPrintingHouse = sceneAndDataMng.getSelectedPrintingHouse();

        if (selectedPrintingHouse != null) {
            actionsMenuLabel.setText(selectedPrintingHouse.getName() + " - Manage Printers:");

            modelColumn.setCellValueFactory(cellData -> cellData.getValue().modelProperty());
            cartridgeTypeColumn.setCellValueFactory(cellData -> cellData.getValue().cartridgeTypeProperty());
            paperColumn.setCellValueFactory(cellData -> cellData.getValue().currentAmountOfPaperProperty().asObject());
            maxPaperColumn.setCellValueFactory(cellData -> cellData.getValue().maxAmountOfPaperProperty().asObject());

            printerViewTable.getItems().clear();
            HashSet<Printer> printers = selectedPrintingHouse.getPrinters();
            for (Printer printer : printers) {
                PrinterTableStruct tableStruct = new PrinterTableStruct();
                tableStruct.setPrinter(printer);
                tableStruct.setModel(printer.getModel());
                tableStruct.setCartridgeType(printer.getCartridgeType());
                tableStruct.setCurrentAmountOfPaper(printer.getCurrentAmountOfPaper());
                tableStruct.setMaxAmountOfPaper(printer.getMaxAmountOfPaper());
                printerViewTable.getItems().add(tableStruct);
            }

            printerViewTable.setRowFactory( rf -> {
                TableRow<PrinterTableStruct> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                        try {
                            onTableRowClick(new ActionEvent(row, null), row.getItem());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                return row;
            });
        }
    }
}
