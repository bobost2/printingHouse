package bstefanov.printinghouse.data.employee;

import java.math.BigDecimal;

public class Manager extends Employee {
    private double bonusPercentage;

    public Manager(String name) {
        super(name);
        bonusPercentage = 0;
    }

    public double getBonus() {
        return bonusPercentage;
    }

    public void setBonus(double bonus) {
        this.bonusPercentage = bonus;
    }

    @Override
    public BigDecimal getSalary() {
        BigDecimal baseSalary = super.getSalary();
        return baseSalary.add(baseSalary.multiply(BigDecimal.valueOf(bonusPercentage/100)));
    }
}
