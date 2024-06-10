package bstefanov.printinghouse.ui.controllers;

import bstefanov.printinghouse.data.employee.Employee;
import bstefanov.printinghouse.data.employee.Manager;
import bstefanov.printinghouse.data.employee.Operator;
import bstefanov.printinghouse.service.PrintingHouseService;
import bstefanov.printinghouse.ui.utils.SceneAndDataManagerSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageEmployeeController implements Initializable {

    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<Manager> managers;

    @FXML
    private Label actionsMenuLabel;

    @FXML
    private TextField nameTextBox;

    @FXML
    private Label managerLabel;

    @FXML
    private ChoiceBox<String> managerChoiceBox;

    @FXML
    private Button editButton;

    @FXML
    private Button fireEmployeeButton;

    public ManageEmployeeController() {
        managers = new ArrayList<>();
    }

    @FXML
    protected void onClickGoBackButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.switchPane(event, "select-employee-view.fxml");
    }

    @FXML
    protected void onClickEditButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        PrintingHouseService selectedPrintingHouse = sceneAndDataMng.getSelectedPrintingHouse();

        if (selectedPrintingHouse != null) {
            Employee selectedEmployee = sceneAndDataMng.getSelectedEmployee();
            selectedEmployee.setName(nameTextBox.getText());

            if (selectedEmployee instanceof Operator operator) {
                operator.setManager(managers.get(managerChoiceBox.getSelectionModel().getSelectedIndex()));
            }

            sceneAndDataMng.switchPane(event, "select-employee-view.fxml");
        }
    }

    @FXML
    protected void onClickFireEmployeeButton(ActionEvent event) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        PrintingHouseService selectedPrintingHouse = sceneAndDataMng.getSelectedPrintingHouse();

        if (selectedPrintingHouse != null) {
            Employee selectedEmployee = sceneAndDataMng.getSelectedEmployee();

            if (selectedEmployee instanceof Manager) {
                boolean hasOperators = selectedPrintingHouse.getEmployees().stream()
                        .filter(employee -> employee instanceof Operator)
                        .anyMatch(employee -> ((Operator) employee).getManager().equals(selectedEmployee));

                if (!hasOperators) {
                    selectedPrintingHouse.fireEmployee(selectedEmployee);
                    sceneAndDataMng.switchPane(event, "select-employee-view.fxml");
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Cannot fire manager");
                    alert.setContentText("Manager has operators assigned to him.");
                    alert.showAndWait();
                }
            }
            else {
                selectedPrintingHouse.fireEmployee(selectedEmployee);
                sceneAndDataMng.switchPane(event, "select-employee-view.fxml");
            }
        }
    }

    @FXML
    protected void onChangedTextField() {
        editButton.setDisable(nameTextBox.getText().isBlank());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        PrintingHouseService selectedPrintingHouse = sceneAndDataMng.getSelectedPrintingHouse();

        if (selectedPrintingHouse != null) {
            actionsMenuLabel.setText(selectedPrintingHouse.getName() + " - "
                    + sceneAndDataMng.getSelectedEmployee().getName() + ":");

            nameTextBox.setText(sceneAndDataMng.getSelectedEmployee().getName());

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

            Employee selectedEmployee = sceneAndDataMng.getSelectedEmployee();
            if (selectedEmployee instanceof Manager) {
                managerLabel.setDisable(true);
                managerChoiceBox.setDisable(true);
            }
        }
    }
}
