package bstefanov.printinghouse.service;

import bstefanov.printinghouse.data.audit.AuditableRecord;
import bstefanov.printinghouse.data.audit.EditionPrinted;
import bstefanov.printinghouse.data.audit.EmployeePay;
import bstefanov.printinghouse.data.edition.Edition;
import bstefanov.printinghouse.data.employee.Employee;
import bstefanov.printinghouse.data.paper.PaperPrice;
import bstefanov.printinghouse.data.printer.Printer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;

public class AuditingService {
    private ArrayList<AuditableRecord> audit;
    private BigDecimal expectedRevenue;
    private PaperPrice paperPrice;
    private double bonusPercentage;
    private double discountPercentage;

    public AuditingService() {
        audit = new ArrayList<>();
    }

    public BigDecimal getExpectedRevenue() {
        return expectedRevenue;
    }

    public void setExpectedRevenue(BigDecimal expectedRevenue) {
        this.expectedRevenue = expectedRevenue;
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
        // TODO: Implement this method
    }

    public void recordPrinted(Printer printer, Edition edition) {
        BigDecimal spending = paperPrice.getPrice(edition.getPaperType(), edition.getPaperSize())
                .multiply(BigDecimal.valueOf(edition.getAmountOfPages()));

        EditionPrinted printedLog = new EditionPrinted(edition, printer, spending);
        audit.add(printedLog);
    }

    public void recordEmployeePay(HashSet<Employee> employees) {
        for (Employee employee : employees) {
            EmployeePay pay = new EmployeePay(employee);
            audit.add(pay);
        }
    }

    // We need the employees to calculate their pay and bonus
    // TODO: This should return a serializable object
    public void getDayReport() {
        BigDecimal revenue = new BigDecimal(0);

        for (AuditableRecord record : audit) {
            revenue = revenue.add(record.moneyGainedOrLost());
        }

        System.out.println("Total revenue: " + revenue);
    }
    public void saveDayReport() {
        // TODO: Implement this method
    }

    // TODO: This should return a serializable object
    public void loadDayReport() {
        // TODO: Implement this method
    }
}
