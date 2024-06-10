package bstefanov.printinghouse.ui.controllers;

import bstefanov.printinghouse.service.PrintingHouseService;
import bstefanov.printinghouse.ui.utils.SceneAndDataManagerSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class SelectEmployeeController implements Initializable {

    @FXML
    private Label actionsMenuLabel;

    @FXML
    private TableView<?> employeeViewTable;

    @FXML
    private TableColumn<?, String> nameColumn;

    @FXML
    private TableColumn<?, String> managerNameColumn;

    @FXML
    private TableColumn<?, Date> hireDateColumn;

    @FXML
    protected void onClickGoBackButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.switchPane(event, "printing-house-actions-view.fxml");
    }

    @FXML
    protected void onClickHireEmployeeButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.switchPane(event, "hire-employee-view.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        PrintingHouseService selectedPrintingHouse = sceneAndDataMng.getSelectedPrintingHouse();

        if (selectedPrintingHouse != null) {
            actionsMenuLabel.setText(selectedPrintingHouse.getName() + " - Manage Employees:");
        }
    }
}
