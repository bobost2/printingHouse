package bstefanov.printinghouse.ui.controllers;

import bstefanov.printinghouse.data.edition.Edition;
import bstefanov.printinghouse.service.PrintingHouseService;
import bstefanov.printinghouse.ui.structs.EditionToPrintTableStruct;
import bstefanov.printinghouse.ui.utils.SceneAndDataManagerSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SellEditionsController implements Initializable {

    @FXML
    private Label actionsMenuLabel;

    @FXML
    private TableView<EditionToPrintTableStruct> editionViewTable;

    @FXML
    private TableColumn<EditionToPrintTableStruct, String> titleColumn;

    @FXML
    private TableColumn<EditionToPrintTableStruct, String> typeColumn;

    @FXML
    private TableColumn<EditionToPrintTableStruct, Integer> pagesColumn;

    @FXML
    private TableColumn<EditionToPrintTableStruct, Double> priceColumn;


    @FXML
    protected void onClickGoBackButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.switchPane(event, "printing-house-actions-view.fxml");
    }

    private void onTableRowClick(ActionEvent event, EditionToPrintTableStruct tableStruct) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        PrintingHouseService selectedPrintingHouse = sceneAndDataMng.getSelectedPrintingHouse();
        selectedPrintingHouse.sellEdition(tableStruct.getEdition(), 1);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        alert.setTitle("Edition Sold");
        alert.setHeaderText("Edition Sold Successfully");
        alert.setContentText("Edition " + tableStruct.getTitle() + " has been sold for " + tableStruct.getPrice() + "$.");
        alert.showAndWait();

        sceneAndDataMng.switchPane(event, "sell-edition-view.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        PrintingHouseService selectedPrintingHouse = sceneAndDataMng.getSelectedPrintingHouse();

        if (selectedPrintingHouse != null) {
            actionsMenuLabel.setText(selectedPrintingHouse.getName() + " - Sell Edition:");

            titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
            typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
            pagesColumn.setCellValueFactory(cellData -> cellData.getValue().amountOfPagesProperty().asObject());
            priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

            editionViewTable.getItems().clear();
            ArrayList<Edition> editions = selectedPrintingHouse.getPrintedEditions();
            for (Edition edition : editions) {
                EditionToPrintTableStruct tableStruct = new EditionToPrintTableStruct();
                tableStruct.setEdition(edition);
                tableStruct.setTitle(edition.getTitle());
                tableStruct.setType(edition.editionType());
                tableStruct.setAmountOfPages(edition.getAmountOfPages());
                tableStruct.setPrice(edition.getPrice().doubleValue());
                editionViewTable.getItems().add(tableStruct);
            }

            //noinspection DuplicatedCode
            editionViewTable.setRowFactory( rf -> {
                TableRow<EditionToPrintTableStruct> row = new TableRow<>();
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
