package bstefanov.printinghouse.data.audit;

import bstefanov.printinghouse.data.employee.Employee;

import java.math.BigDecimal;

public class EmployeePay implements AuditableRecord{
    private final Employee employee;

    public EmployeePay(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String recordDetails() {
        return "[-] Paid employee " + employee.getName() + " for the day";
    }

    @Override
    public BigDecimal moneyGainedOrLost() {
        return employee.getSalary().multiply(BigDecimal.valueOf(-1));
    }
}
