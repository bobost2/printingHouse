package bstefanov.printinghouse.ui.controllers;

import bstefanov.printinghouse.data.employee.Employee;
import bstefanov.printinghouse.data.employee.Manager;
import bstefanov.printinghouse.data.employee.Operator;
import bstefanov.printinghouse.service.PrintingHouseService;
import bstefanov.printinghouse.ui.structs.EmployeeTableStruct;
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
import java.util.Date;
import java.util.HashSet;
import java.util.ResourceBundle;

public class SelectEmployeeController implements Initializable {

    @FXML
    private Label actionsMenuLabel;

    @FXML
    private TableView<EmployeeTableStruct> employeeViewTable;

    @FXML
    private TableColumn<EmployeeTableStruct, String> nameColumn;

    @FXML
    private TableColumn<EmployeeTableStruct, String> managerNameColumn;

    @FXML
    private TableColumn<EmployeeTableStruct, Date> hireDateColumn;

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

    private void onTableRowClick(ActionEvent event, EmployeeTableStruct tableStruct) throws IOException {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        sceneAndDataMng.setSelectedEmployee(tableStruct.getEmployee());
        sceneAndDataMng.switchPane(event, "manage-employee-view.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SceneAndDataManagerSingleton sceneAndDataMng = SceneAndDataManagerSingleton.getInstance();
        PrintingHouseService selectedPrintingHouse = sceneAndDataMng.getSelectedPrintingHouse();

        if (selectedPrintingHouse != null) {
            actionsMenuLabel.setText(selectedPrintingHouse.getName() + " - Manage Employees:");

            nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            managerNameColumn.setCellValueFactory(cellData -> cellData.getValue().managerProperty());
            hireDateColumn.setCellValueFactory(cellData -> cellData.getValue().hireDateProperty());

            employeeViewTable.getItems().clear();
            HashSet<Employee> employees = selectedPrintingHouse.getEmployees();
            for (Employee employee : employees) {
                EmployeeTableStruct tableStruct = new EmployeeTableStruct();
                tableStruct.setEmployee(employee);
                tableStruct.setName(employee.getName());

                if (employee instanceof Manager) {
                    tableStruct.setManager("-");
                } else {
                    tableStruct.setManager(((Operator) employee).getManager().getName());
                }

                tableStruct.setHireDate(employee.getHireDate());
                employeeViewTable.getItems().add(tableStruct);
            }

            employeeViewTable.setRowFactory( rf -> {
                TableRow<EmployeeTableStruct> row = new TableRow<>();
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
