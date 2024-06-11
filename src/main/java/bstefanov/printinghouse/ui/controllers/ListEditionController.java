package bstefanov.printinghouse.ui.controllers;

import bstefanov.printinghouse.data.edition.Edition;
import bstefanov.printinghouse.service.PrintingHouseService;
import bstefanov.printinghouse.ui.structs.EditionToPrintTableStruct;
import bstefanov.printinghouse.ui.utils.SceneAndDataManagerSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

public class ListEditionController implements Initializable {

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
    private TableColumn<EditionToPrintTableStruct, Integer> expectedEditionsColumn;

    @FXML
    protected void onClickGoBackButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.switchPane(event, "printing-house-actions-view.fxml");
    }

    @FXML
    protected void onClickAddEditionButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.switchPane(event, "create-edition-view.fxml");
    }

    private void onTableRowClick(ActionEvent event, EditionToPrintTableStruct tableStruct) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.setSelectedEdition(tableStruct.getEdition());
        sceneAndDataMng.switchPane(event, "modify-edition-view.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.setSelectedEdition(null);
        PrintingHouseService selectedPrintingHouse = sceneAndDataMng.getSelectedPrintingHouse();

        if (selectedPrintingHouse != null) {
            actionsMenuLabel.setText(selectedPrintingHouse.getName() + " - List of Editions to Print:");

            titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
            typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
            pagesColumn.setCellValueFactory(cellData -> cellData.getValue().amountOfPagesProperty().asObject());
            priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
            expectedEditionsColumn.setCellValueFactory(cellData -> cellData.getValue().expectedEditionsProperty().asObject());

            editionViewTable.getItems().clear();
            HashSet<Edition> editions = selectedPrintingHouse.getEditionsToPrint();
            for (Edition edition : editions) {
                EditionToPrintTableStruct tableStruct = new EditionToPrintTableStruct();
                tableStruct.setEdition(edition);
                tableStruct.setTitle(edition.getTitle());
                tableStruct.setType(edition.editionType());
                tableStruct.setAmountOfPages(edition.getAmountOfPages());
                tableStruct.setPrice(edition.getPrice().doubleValue());
                int expectedEditions = selectedPrintingHouse.getExpectedEditions().getOrDefault(edition, 0);
                tableStruct.setExpectedEditions(expectedEditions);
                editionViewTable.getItems().add(tableStruct);
            }

            editionViewTable.setRowFactory( rf -> {
                TableRow<EditionToPrintTableStruct> row = new TableRow<>();
                //noinspection DuplicatedCode
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
