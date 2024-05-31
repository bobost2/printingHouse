package bstefanov.printinghouse.service;

import bstefanov.printinghouse.data.audit.*;
import bstefanov.printinghouse.data.edition.Edition;
import bstefanov.printinghouse.data.employee.Employee;
import bstefanov.printinghouse.data.paper.PaperPrice;
import bstefanov.printinghouse.data.printer.Printer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;

public class AuditingService {
    private ArrayList<AuditableRecord> audit;
    private BigDecimal expectedProfit;
    private PaperPrice paperPrice;
    private double bonusPercentage;
    private double discountPercentage;
    private BigDecimal profit;

    public AuditingService() {
        audit = new ArrayList<>();
    }

    public BigDecimal getExpectedProfit() {
        return expectedProfit;
    }

    public void setExpectedProfit(BigDecimal expectedProfit) {
        this.expectedProfit = expectedProfit;
    }

    public PaperPrice getPaperPrice() {
        return paperPrice;
    }

    public void setPaperPrice(PaperPrice paperPrice) {
        this.paperPrice = paperPrice;
    }

    public double getBonusPercentage() {
        return bonusPercentage;
    }

    public void setBonusPercentage(double bonusPercentage) {
        this.bonusPercentage = bonusPercentage;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void recordSell(Edition edition, int copies, boolean applyDiscount) {
        BigDecimal price = edition.getPrice().multiply(BigDecimal.valueOf(copies));
        if (applyDiscount) {
            price = price.min(price.multiply(BigDecimal.valueOf(discountPercentage/100)));
        }

        EditionSold soldLog = new EditionSold(edition, copies, price);
        audit.add(soldLog);
    }

    public void recordPrinted(Printer printer, Edition edition) {
        BigDecimal spending = paperPrice.getPrice(edition.getPaperType(), edition.getPaperSize())
                .multiply(BigDecimal.valueOf(edition.getAmountOfPages()));

        EditionPrinted printedLog = new EditionPrinted(edition, printer, spending);
        audit.add(printedLog);
    }

    public boolean calculateProfitAndCheckIfExpectationsAreMet() {
        profit = new BigDecimal(0);
        for (AuditableRecord record : audit) {
            if (record.moneyGainedOrLost().compareTo(BigDecimal.ZERO) > 0)
            {
                profit = profit.add(record.moneyGainedOrLost());
            }
        }

        return profit.compareTo(expectedProfit) > 0;
    }

    public void recordEmployeePay(HashSet<Employee> employees) {
        for (Employee employee : employees) {
            EmployeePay pay = new EmployeePay(employee);
            audit.add(pay);
        }
    }

    // We need the employees to calculate their pay and bonus
    public FinalReport getDayReport() {
        return new FinalReport(audit, expectedProfit);
    }
}
