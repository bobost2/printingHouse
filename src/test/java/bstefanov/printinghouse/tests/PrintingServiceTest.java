package bstefanov.printinghouse.tests;

import bstefanov.printinghouse.data.edition.Book;
import bstefanov.printinghouse.data.paper.PaperSize;
import bstefanov.printinghouse.data.paper.PaperType;
import bstefanov.printinghouse.data.printer.CartridgeType;
import bstefanov.printinghouse.data.printer.Printer;
import bstefanov.printinghouse.exceptions.printer.PrinterColorsNotSupportedException;
import bstefanov.printinghouse.exceptions.printer.PrinterNotEnoughPaperException;
import bstefanov.printinghouse.exceptions.printer.PrinterTooMuchPaperException;
import bstefanov.printinghouse.service.PrintingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PrintingServiceTest {

    PrintingService printingService;

    @BeforeEach
    void setUp() {
        printingService = new PrintingService();
    }

    @Test
    @DisplayName("Add and remove printer test (Service Test)")
    void addAndRemovePrinterTest() {
        Printer printer1 = new Printer("Printer 1", CartridgeType.BLACK_AND_WHITE, 70);
        Printer printer2 = new Printer("Printer 2", CartridgeType.COLOR, 30);

        printingService.addPrinter(printer1);
        printingService.addPrinter(printer2);

        HashSet<Printer> printers = printingService.getPrinters();
        assertEquals(2, printers.size());
        assertTrue(printers.contains(printer1));
        assertTrue(printers.contains(printer2));

        printingService.removePrinter(printer1);
        printers = printingService.getPrinters();
        assertEquals(1, printers.size());
        assertFalse(printers.contains(printer1));
        assertTrue(printers.contains(printer2));

        printingService.removePrinter(printer2);
        printers = printingService.getPrinters();
        assertFalse(printers.contains(printer1));
        assertFalse(printers.contains(printer2));
        assertTrue(printers.isEmpty());
    }

    @Test
    @DisplayName("Add and remove paper test (Paper Class Test)")
    void addAndRemovePaperTest() {
        Printer printer = new Printer("Printer 1", CartridgeType.BLACK_AND_WHITE, 30);
        assertEquals(0, printer.getCurrentAmountOfPaper());

        printer.addPaper(20);
        assertEquals(20, printer.getCurrentAmountOfPaper());

        printer.addPaper(10);
        assertEquals(30, printer.getCurrentAmountOfPaper());
        assertEquals(printer.getCurrentAmountOfPaper(), printer.getMaxAmountOfPaper());

        assertThrows(PrinterTooMuchPaperException.class, () -> printer.addPaper(10));
    }

    @Test
    @DisplayName("Print edition test (Paper Class Test)")
    void printEditionTest() {
        Book edition = new Book("Book 1", 10, PaperSize.A4, PaperType.GLOSSY, new BigDecimal("20"));
        Printer printer = new Printer("Printer 1", CartridgeType.BLACK_AND_WHITE, 30);

        assertThrows(PrinterNotEnoughPaperException.class, () -> printer.print(edition, CartridgeType.BLACK_AND_WHITE, 2));

        printer.addPaper(25);
        assertEquals(25, printer.getCurrentAmountOfPaper());

        assertTrue(printer.print(edition, CartridgeType.BLACK_AND_WHITE, 2));

        assertEquals(5, printer.getCurrentAmountOfPaper());

        printer.addPaper(5);
        assertEquals(10, printer.getCurrentAmountOfPaper());

        assertThrows(PrinterColorsNotSupportedException.class, () -> printer.print(edition, CartridgeType.COLOR, 1));

        assertTrue(printer.print(edition, CartridgeType.BLACK_AND_WHITE, 1));
        assertEquals(0, printer.getCurrentAmountOfPaper());
    }
}