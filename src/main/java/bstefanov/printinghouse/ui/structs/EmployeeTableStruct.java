package bstefanov.printinghouse.ui.structs;

import bstefanov.printinghouse.data.employee.Employee;
import bstefanov.printinghouse.data.printer.CartridgeType;
import bstefanov.printinghouse.data.printer.Printer;
import javafx.beans.property.*;

import java.util.Date;

public class EmployeeTableStruct {
    private Employee employee;
    private StringProperty name;
    private StringProperty manager;
    private ObjectProperty<Date> hireDate;

    public EmployeeTableStruct() {
        this.name = new SimpleStringProperty();
        this.manager = new SimpleStringProperty();
        this.hireDate = new SimpleObjectProperty<>();
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getManager() {
        return manager.get();
    }

    public StringProperty managerProperty() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager.set(manager);
    }

    public Date getHireDate() {
        return hireDate.get();
    }

    public ObjectProperty<Date> hireDateProperty() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate.set(hireDate);
    }
}