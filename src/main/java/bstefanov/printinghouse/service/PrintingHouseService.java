package bstefanov.printinghouse.service;

import bstefanov.printinghouse.data.audit.FinalReport;
import bstefanov.printinghouse.data.configuration.EconomyConfig;
import bstefanov.printinghouse.data.edition.Edition;
import bstefanov.printinghouse.data.employee.Employee;
import bstefanov.printinghouse.data.printer.CartridgeType;
import bstefanov.printinghouse.data.printer.Printer;
import bstefanov.printinghouse.exceptions.printinghouse.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class PrintingHouseService {
    private String name;
    private String address;
    private boolean isDayStarted;

    // Data
    private HashSet<Edition> editionsToPrint;
    private ArrayList<Edition> printedEditions;
    private HashMap<Edition, Integer> expectedEditions;

    // Other services
    EmployeeService employeeService;
    PrintingService printingService;
    AuditingService auditingService;

    public PrintingHouseService(String name, String address) {
        this.name = name;
        this.address = address;

        isDayStarted = false;

        editionsToPrint = new HashSet<>();
        printedEditions = new ArrayList<>();
        expectedEditions = new HashMap<>();

        employeeService = new EmployeeService();
        printingService = new PrintingService();
        auditingService = new AuditingService();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addEditionToPrint(Edition edition) {
        editionsToPrint.add(edition);
    }

    public void removeEditionToPrint(Edition edition) {
        editionsToPrint.remove(edition);
    }

    public HashSet<Edition> getEditionsToPrint() {
        return editionsToPrint;
    }

    public ArrayList<Edition> getPrintedEditions() {
        return printedEditions;
    }

    public HashMap<Edition, Integer> getExpectedEditions() {
        return expectedEditions;
    }

    public void addExpectedEdition(Edition edition, int copies) {
        expectedEditions.put(edition, copies);
    }

    public void removeExpectedEdition(Edition edition) {
        expectedEditions.remove(edition);
    }

    public boolean isDayStarted() {
        return isDayStarted;
    }

    // Expose employeeService methods
    public void hireEmployee(Employee employee) {
        employeeService.hireEmployee(employee);
    }

    public HashSet<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    public void fireEmployee(Employee employee) {
        employeeService.fireEmployee(employee);
    }

    // Expose printingService methods
    public void addPrinter(Printer printer) {
        printingService.addPrinter(printer);
    }

    public HashSet<Printer> getPrinters() {
        return printingService.getPrinters();
    }

    public void removePrinter(Printer printer) {
        printingService.removePrinter(printer);
    }

    // Additional methods
    public boolean printEdition(Printer printer, Edition edition, CartridgeType cartridgeType, int copies) {
        if ( !isDayStarted ) {
            throw new DayNotStartedYetException();
        }

        if ( !editionsToPrint.contains(edition) ) {
            throw new EditionNotExistingException();
        }

        if ( printer.print(edition, cartridgeType, copies) )
        {
            for ( int i = 0; i < copies; i++ ) {
                printedEditions.add(edition);
            }
            return true;
        }

        return false;
    }

    public void sellEdition(Edition edition, int copies) {
        if ( !isDayStarted ) {
            throw new DayNotStartedYetException();
        }

        if ( !printedEditions.contains(edition) ) {
            throw new EditionNotPrintedYetException();
        }

        int totalCopies = 0;
        for ( Edition printedEdition : printedEditions ) {
            if ( printedEdition.equals(edition) ) {
                totalCopies++;
            }
        }

        if ( copies > totalCopies ) {
            throw new EditionNotEnoughCopiesException();
        }

        for ( int i = 0; i < copies; i++ ) {
            printedEditions.remove(edition);
        }

        boolean applyDiscount = totalCopies > expectedEditions.get(edition);

        auditingService.recordSell(edition, copies, applyDiscount);
    }

    public EconomyConfig getEconomyConfig() {
        EconomyConfig economyConfig = new EconomyConfig();
        economyConfig.bonusPercentage = auditingService.getBonusPercentage();
        economyConfig.discountPercentage = auditingService.getDiscountPercentage();
        economyConfig.paperPrice = auditingService.getPaperPrice();
        economyConfig.baseSalary = employeeService.getBaseSalary();
        return economyConfig;
    }

    public void applyEconomyConfig(EconomyConfig economyConfig) {
        auditingService.setBonusPercentage(economyConfig.bonusPercentage);
        auditingService.setDiscountPercentage(economyConfig.discountPercentage);
        auditingService.setPaperPrice(economyConfig.paperPrice);
        employeeService.setSalaries(economyConfig.baseSalary);
    }

    public void setExpectedRevenue(BigDecimal expectedRevenue) {
        auditingService.setExpectedProfit(expectedRevenue);
    }

    public void startDay() {
        if ( isDayStarted ) {
            throw new DayNotEndedYetException();
        }

        for ( Printer printer : printingService.getPrinters() ) {
            printer.clearPrintedEditions();
        }
        employeeService.setBonuses(0);
        isDayStarted = true;
    }

    public FinalReport endDayAndGetReport() {
        if ( !isDayStarted ) {
            throw new DayNotStartedYetException();
        }

        isDayStarted = false;

        for ( Printer printer : printingService.getPrinters() ) {
            for ( Edition printedEdition : printer.getPrintedEditions() ) {
                auditingService.recordPrinted(printer, printedEdition);
            }
        }

        if (auditingService.calculateProfitAndCheckIfExpectationsAreMet())
        {
            employeeService.setBonuses(auditingService.getBonusPercentage());
        }

        auditingService.recordEmployeePay(employeeService.getEmployees());

        return auditingService.getDayReport();

        // Should the expected editions be cleared?
        //expectedEditions.clear();
    }
}