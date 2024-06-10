package bstefanov.printinghouse.ui.controllers;

import bstefanov.printinghouse.data.employee.Employee;
import bstefanov.printinghouse.data.employee.Manager;
import bstefanov.printinghouse.data.employee.Operator;
import bstefanov.printinghouse.service.PrintingHouseService;
import bstefanov.printinghouse.ui.utils.SceneAndDataManagerSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HireEmployeeController implements Initializable {

    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<Manager> managers;

    @FXML
    private Label actionsMenuLabel;

    @FXML
    private ChoiceBox<String> positionChoiceBox;

    @FXML
    private TextField nameTextBox;

    @FXML
    private Label managerLabel;

    @FXML
    private ChoiceBox<String> managerChoiceBox;

    @FXML
    private Button hireEmployeeButton;

    public HireEmployeeController() {
        managers = new ArrayList<>();
    }

    @FXML
    protected void onClickGoBackButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.switchPane(event, "select-employee-view.fxml");
    }

    @FXML
    protected void onClickHireEmployeeButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        PrintingHouseService selectedPrintingHouse = sceneAndDataMng.getSelectedPrintingHouse();

        if (selectedPrintingHouse != null) {
            String name = nameTextBox.getText();
            String position = positionChoiceBox.getValue();

            if (position.equals("Manager")) {
                Employee manager = new Manager(name);
                selectedPrintingHouse.hireEmployee(manager);

            } else {
                int selectedIndex = positionChoiceBox.getSelectionModel().getSelectedIndex() - 1;
                Operator operator = new Operator(name, managers.get(selectedIndex));
                selectedPrintingHouse.hireEmployee(operator);
            }
        }

        sceneAndDataMng.switchPane(event, "select-employee-view.fxml");
    }

    @FXML
    protected void onPositionChoiceBoxChange() {
        boolean isManager = positionChoiceBox.getValue().equals("Manager");
        managerLabel.setDisable(isManager);
        managerChoiceBox.setDisable(isManager);

        onChangedTextField();
    }

    @FXML
    protected void onManagerChoiceBoxChange() {
        onChangedTextField();
    }

    @FXML
    protected void onChangedTextField() {
        boolean nameEmpty = nameTextBox.getText().isBlank();
        boolean managerEmpty = managerChoiceBox.getValue() == null;

        if (!managerEmpty)
        {
            managerEmpty = managerChoiceBox.getValue().isBlank();
        }

        hireEmployeeButton.setDisable( nameEmpty || (positionChoiceBox.getValue().equals("Operator") && managerEmpty));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        PrintingHouseService selectedPrintingHouse = sceneAndDataMng.getSelectedPrintingHouse();

        if (selectedPrintingHouse != null) {
            actionsMenuLabel.setText(selectedPrintingHouse.getName() + " - Hire Employee:");
            positionChoiceBox.getItems().addAll("Manager", "Operator");
            positionChoiceBox.setValue("Operator");

            managerChoiceBox.getItems().clear();
            managers.clear();

            selectedPrintingHouse.getEmployees().forEach(employee -> {
                if (employee instanceof Manager) {
                    managers.add((Manager) employee);
                }
            });

            for (Manager manager : managers) {
                managerChoiceBox.getItems().add(manager.getName());
            }

            if (!managers.isEmpty()) {
                managerChoiceBox.setValue(managers.get(0).getName());
            }
        }
    }
}
