package bstefanov.printinghouse.service;

import bstefanov.printinghouse.data.printer.Printer;

import java.util.HashSet;

public class PrintingService {
    HashSet<Printer> printers;

    public PrintingService() {
        printers = new HashSet<>();
    }

    public void addPrinter(Printer printer) {
        printers.add(printer);
    }

    public void removePrinter(Printer printer) {
        printers.remove(printer);
    }

    public HashSet<Printer> getPrinters() {
        return printers;
    }
}
