package bstefanov.printinghouse.service;

import bstefanov.printinghouse.data.employee.Employee;
import bstefanov.printinghouse.data.employee.Manager;

import java.math.BigDecimal;
import java.util.HashSet;

public class EmployeeService {
    HashSet<Employee> employees;

    public EmployeeService() {
        employees = new HashSet<>();
    }

    public EmployeeService(BigDecimal baseSalary) {
        employees = new HashSet<>();
        setSalaries(baseSalary);
    }

    public EmployeeService(HashSet<Employee> employees) {
        this.employees = employees;
    }

    public EmployeeService(HashSet<Employee> employees, BigDecimal baseSalary) {
        this.employees = employees;
        setSalaries(baseSalary);
    }

    public void hireEmployee(Employee employee) {
        employees.add(employee);
    }

    public HashSet<Employee> getEmployees() {
        return employees;
    }

    public void fireEmployee(Employee employee) {
        employees.remove(employee);
    }

    public void setSalaries(BigDecimal baseSalary) {
        for (Employee employee : employees) {
            employee.setSalary(baseSalary);
        }
    }

    public void setBonuses(double bonusPercentage) {
        for (Employee employee : employees) {
            if (employee.getClass() == Manager.class)
            {
                ((Manager) employee).setBonus(bonusPercentage);
            }
        }
    }
}
