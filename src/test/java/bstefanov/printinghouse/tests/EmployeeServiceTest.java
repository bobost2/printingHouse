package bstefanov.printinghouse.tests;

import bstefanov.printinghouse.data.employee.Employee;
import bstefanov.printinghouse.data.employee.Manager;
import bstefanov.printinghouse.data.employee.Operator;
import bstefanov.printinghouse.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {
    EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeService();
    }

    @Test
    @DisplayName("Hire and fire employee test")
    void hireAndFireEmployeeTest() {
        Manager manager = new Manager("Toshko");
        Operator employee1 = new Operator("Pesho", manager);
        Operator employee2 = new Operator("Gosho", manager);

        employeeService.hireEmployee(manager);
        employeeService.hireEmployee(employee1);
        employeeService.hireEmployee(employee2);

        HashSet<Employee> employees = employeeService.getEmployees();

        assertEquals(3, employees.size());
        assertTrue(employees.contains(manager));
        assertTrue(employees.contains(employee1));
        assertTrue(employees.contains(employee2));

        employeeService.fireEmployee(employee1);
        employeeService.fireEmployee(employee2);

        employees = employeeService.getEmployees();

        assertEquals(1, employees.size());
        assertTrue(employees.contains(manager));
        assertFalse(employees.contains(employee1));
        assertFalse(employees.contains(employee2));

        employeeService.fireEmployee(manager);

        employees = employeeService.getEmployees();

        assertTrue(employees.isEmpty());
    }

    @Test
    @DisplayName("Set salaries and bonuses test")
    void setSalariesAndBonusesTest() {
        Manager manager = new Manager("Toshko");
        Operator employee1 = new Operator("Pesho", manager);
        Operator employee2 = new Operator("Gosho", manager);

        employeeService.hireEmployee(manager);
        employeeService.hireEmployee(employee1);
        employeeService.hireEmployee(employee2);

        employeeService.setSalaries(new BigDecimal(1000));
        employeeService.setBonuses(10);

        HashSet<Employee> employees = employeeService.getEmployees();

        for (Employee employee : employees) {
            if (employee.getClass() == Manager.class)
            {
                assertEquals(new BigDecimal(1100).setScale(5, RoundingMode.HALF_UP),
                        employee.getSalary().setScale(5, RoundingMode.HALF_UP));
            }
            else
            {
                assertEquals(new BigDecimal(1000).setScale(5, RoundingMode.HALF_UP),
                        employee.getSalary().setScale(5, RoundingMode.HALF_UP));
            }
        }
    }
}