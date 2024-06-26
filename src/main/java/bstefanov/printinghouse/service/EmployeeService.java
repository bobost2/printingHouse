package bstefanov.printinghouse.service;

import bstefanov.printinghouse.data.employee.Employee;
import bstefanov.printinghouse.data.employee.Manager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;

public class EmployeeService {
    private HashSet<Employee> employees;
    private BigDecimal baseSalary;

    public EmployeeService() {
        employees = new HashSet<>();
        setSalaries(BigDecimal.ONE);
    }

    public EmployeeService(BigDecimal baseSalary) {
        employees = new HashSet<>();
        setSalaries(baseSalary);
    }

    public EmployeeService(HashSet<Employee> employees) {
        this.employees = employees;
        setSalaries(BigDecimal.ONE);
    }

    public EmployeeService(HashSet<Employee> employees, BigDecimal baseSalary) {
        this.employees = employees;
        setSalaries(baseSalary);
    }

    public void hireEmployee(Employee employee) {
        employees.add(employee);
        employee.setSalary(baseSalary);
    }

    public HashSet<Employee> getEmployees() {
        return employees;
    }

    public void fireEmployee(Employee employee) {
        employees.remove(employee);
    }

    public BigDecimal getAverageSalary() {
        BigDecimal sum = BigDecimal.ZERO;
        for (Employee employee : employees) {
            sum = sum.add(employee.getSalary());
        }
        if (employees.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return sum.divide(BigDecimal.valueOf(employees.size()), 5, RoundingMode.HALF_UP);
    }

    public BigDecimal getBaseSalary() {
        return baseSalary;
    }

    public void setSalaries(BigDecimal baseSalary) {
        this.baseSalary = baseSalary;
        for (Employee employee : employees) {
            employee.setSalary(baseSalary);
        }
    }

    public void setBonuses(double bonusPercentage) {
        for (Employee employee : employees) {
            if (employee instanceof Manager)
            {
                ((Manager) employee).setBonus(bonusPercentage);
            }
        }
    }
}
