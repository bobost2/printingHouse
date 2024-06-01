package bstefanov.printinghouse.tests;

import bstefanov.printinghouse.data.audit.FinalReport;
import bstefanov.printinghouse.data.configuration.EconomyConfig;
import bstefanov.printinghouse.data.edition.Book;
import bstefanov.printinghouse.data.edition.Comics;
import bstefanov.printinghouse.data.edition.Edition;
import bstefanov.printinghouse.data.edition.Newspaper;
import bstefanov.printinghouse.data.employee.Manager;
import bstefanov.printinghouse.data.employee.Operator;
import bstefanov.printinghouse.data.paper.PaperPrice;
import bstefanov.printinghouse.data.paper.PaperSize;
import bstefanov.printinghouse.data.paper.PaperType;
import bstefanov.printinghouse.data.printer.CartridgeType;
import bstefanov.printinghouse.data.printer.Printer;
import bstefanov.printinghouse.service.PrintingHouseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class PrintingHouseServiceTest {
    private PrintingHouseService printingHouseService;
    private Edition comicsEdition;
    private Edition bookEdition;
    private Edition newspaperEdition;
    private Printer printerBlack;
    private Printer printerColor;

    @BeforeEach
    void setUp() {
        printingHouseService = new PrintingHouseService("TestPrintingHouse", "TestAddress");

        EconomyConfig economyConfig = new EconomyConfig(
                20,
                15,
                new PaperPrice(),
                new BigDecimal("1350")
        );

        printingHouseService.setExpectedRevenue(new BigDecimal("50")); // low standards
        printingHouseService.applyEconomyConfig(economyConfig);

        Manager manager = new Manager("Toshko");
        Operator employee1 = new Operator("Pesho", manager);
        Operator employee2 = new Operator("Gosho", manager);

        printingHouseService.hireEmployee(manager);
        printingHouseService.hireEmployee(employee1);
        printingHouseService.hireEmployee(employee2);

        printerBlack = new Printer("BlackPrinter", CartridgeType.BLACK_AND_WHITE, 150);
        printerColor = new Printer("ColorPrinter", CartridgeType.COLOR, 100);

        printerBlack.addPaper(150);
        printerColor.addPaper(100);

        printingHouseService.addPrinter(printerBlack);
        printingHouseService.addPrinter(printerColor);

        comicsEdition = new Comics("Comics1", 20, PaperSize.A5, PaperType.NORMAL, new BigDecimal("5.20"));
        bookEdition = new Book("Book1", 50, PaperSize.A4, PaperType.GLOSSY, new BigDecimal("30.50"));
        newspaperEdition = new Newspaper("Newspaper1", 20, PaperSize.A3, PaperType.NEWSPRINT, new BigDecimal("5.50"));

        printingHouseService.addEditionToPrint(comicsEdition);
        printingHouseService.addEditionToPrint(bookEdition);
        printingHouseService.addEditionToPrint(newspaperEdition);

        printingHouseService.addExpectedEdition(comicsEdition, 3);
        printingHouseService.addExpectedEdition(bookEdition, 10);
        printingHouseService.addExpectedEdition(newspaperEdition, 20);

        printingHouseService.startDay();
    }

    @Test
    @DisplayName("Normal day test")
    void normalDayTest(){
        assertTrue(printingHouseService.printEdition(printerColor, comicsEdition, CartridgeType.COLOR, 3));
        assertTrue(printingHouseService.printEdition(printerBlack, bookEdition, CartridgeType.BLACK_AND_WHITE, 2));
        assertTrue(printingHouseService.printEdition(printerBlack, newspaperEdition, CartridgeType.BLACK_AND_WHITE, 2));

        printingHouseService.sellEdition(comicsEdition, 2);
        printingHouseService.sellEdition(bookEdition, 1);

        FinalReport report = printingHouseService.endDayAndGetReport();
        assertEquals(new BigDecimal("40.90").setScale(2, RoundingMode.HALF_UP),
                report.getProfit().setScale(2, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("202.35").setScale(2, RoundingMode.HALF_UP),
                report.getLosses().setScale(2, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("-161.45").setScale(2, RoundingMode.HALF_UP),
                report.getTotal().setScale(2, RoundingMode.HALF_UP));

        // System.out.println(report);
    }

    @Test
    @DisplayName("Manager Bonus test")
    void managerBonusTest(){
        assertTrue(printingHouseService.printEdition(printerBlack, bookEdition, CartridgeType.BLACK_AND_WHITE, 3));
        assertTrue(printingHouseService.printEdition(printerColor, comicsEdition, CartridgeType.COLOR, 3));

        printingHouseService.sellEdition(bookEdition, 3);
        printingHouseService.sellEdition(comicsEdition, 3);

        FinalReport report = printingHouseService.endDayAndGetReport();
        assertEquals(new BigDecimal("107.10").setScale(2, RoundingMode.HALF_UP),
                report.getProfit().setScale(2, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("234.60").setScale(2, RoundingMode.HALF_UP),
                report.getLosses().setScale(2, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("-127.50").setScale(2, RoundingMode.HALF_UP),
                report.getTotal().setScale(2, RoundingMode.HALF_UP));

        // System.out.println(report);
    }

    @Test
    @DisplayName("Prints beyond expectations test")
    void printsBeyondExpectationsTest(){
        assertTrue(printingHouseService.printEdition(printerColor, comicsEdition, CartridgeType.COLOR, 5));
        printingHouseService.sellEdition(comicsEdition, 5);

        FinalReport report = printingHouseService.endDayAndGetReport();
        assertEquals(new BigDecimal("22.10").setScale(2, RoundingMode.HALF_UP),
                report.getProfit().setScale(2, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("145.65").setScale(2, RoundingMode.HALF_UP),
                report.getLosses().setScale(2, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("-123.55").setScale(2, RoundingMode.HALF_UP),
                report.getTotal().setScale(2, RoundingMode.HALF_UP));

        // System.out.println(report);
    }
}