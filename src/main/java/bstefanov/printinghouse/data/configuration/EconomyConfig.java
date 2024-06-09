package bstefanov.printinghouse.data.configuration;

import bstefanov.printinghouse.data.paper.PaperPrice;

import java.math.BigDecimal;

// Struct
public class EconomyConfig {
    public double bonusPercentage;
    public double discountPercentage;
    public PaperPrice paperPrice;
    public BigDecimal baseSalary;

    public EconomyConfig(double bonusPercentage, double discountPercentage, PaperPrice paperPrice, BigDecimal baseSalary) {
        this.bonusPercentage = bonusPercentage;
        this.discountPercentage = discountPercentage;
        this.paperPrice = paperPrice;
        this.baseSalary = baseSalary;
    }

    public EconomyConfig() {
        this.bonusPercentage = 1.0;
        this.discountPercentage = 1.0;
        this.paperPrice = new PaperPrice();
        this.baseSalary = BigDecimal.ONE;
    }
}
