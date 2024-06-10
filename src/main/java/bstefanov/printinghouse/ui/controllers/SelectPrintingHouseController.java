package bstefanov.printinghouse.ui.controllers;

import bstefanov.printinghouse.ui.structs.PrintingHousesTableStruct;
import bstefanov.printinghouse.ui.utils.SceneAndDataManagerSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SelectPrintingHouseController implements Initializable {

    @FXML
    private TableView<PrintingHousesTableStruct> printingHouseSelectionTable;

    @FXML
    private TableColumn<PrintingHousesTableStruct, String> printingHouseNameColumn;

    @FXML
    private TableColumn<PrintingHousesTableStruct, String> printingHouseAddressColumn;

    private void onSelectPrintingHouse(ActionEvent actionEvent, PrintingHousesTableStruct tableStruct) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.setSelectedPrintingHouse(sceneAndDataMng.getPrintingHouses().get(tableStruct.getId()));
        onClickMenuPrintingHouseActions(actionEvent);
    }

    @FXML
    protected void onClickMenuPrintingHouseActions(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.switchPane(event, "printing-house-actions-view.fxml");
    }

    @FXML
    protected void onClickGoBack(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.switchPane(event, "main-view.fxml");
    }

    @FXML
    protected void onClickAddButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.switchPane(event, "create-printing-house-view.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        printingHouseNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        printingHouseAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        printingHouseSelectionTable.getItems().clear();
        for (int i = 0; i < sceneAndDataMng.getPrintingHouses().size(); i++) {
            PrintingHousesTableStruct tableStruct = new PrintingHousesTableStruct();
            tableStruct.setId(i);
            tableStruct.setName(sceneAndDataMng.getPrintingHouses().get(i).getName());
            tableStruct.setAddress(sceneAndDataMng.getPrintingHouses().get(i).getAddress());
            printingHouseSelectionTable.getItems().add(tableStruct);
        }

        printingHouseSelectionTable.setRowFactory( rf -> {
            TableRow<PrintingHousesTableStruct> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    try {
                        onSelectPrintingHouse(new ActionEvent(row, null), row.getItem());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return row;
        });
    }
}
