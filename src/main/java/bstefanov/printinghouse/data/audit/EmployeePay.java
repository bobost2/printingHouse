package bstefanov.printinghouse.data.audit;

import bstefanov.printinghouse.data.employee.Employee;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
        return employee.getSalary().multiply(BigDecimal.valueOf(-1)).divide(BigDecimal.valueOf(31), 5, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        return "EmployeePay{" +
                ", recordDetails=" + recordDetails() +
                ", totalPrice=" + moneyGainedOrLost() +
                '}';
    }
}
