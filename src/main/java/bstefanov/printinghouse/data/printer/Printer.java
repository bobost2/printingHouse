package bstefanov.printinghouse.data.printer;

import bstefanov.printinghouse.data.edition.Edition;
import bstefanov.printinghouse.exceptions.printer.PrinterColorsNotSupportedException;
import bstefanov.printinghouse.exceptions.printer.PrinterNotEnoughPaperException;
import bstefanov.printinghouse.exceptions.printer.PrinterTooMuchPaperException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Printer {
    private final UUID id;
    private final String model;
    private final int maxAmountOfPaper;
    private int currentAmountOfPaper;
    private final CartridgeType cartridgeType;
    private ArrayList<Edition> printedEditions;

    public Printer(String model, CartridgeType cartridgeType, int maxAmountOfPaper) {
        id = UUID.randomUUID();
        this.model = model;
        this.cartridgeType = cartridgeType;
        this.maxAmountOfPaper = maxAmountOfPaper;
    }

    public CartridgeType getCartridgeType() {
        return cartridgeType;
    }

    public void addPaper(int amount) {
        if (currentAmountOfPaper + amount > maxAmountOfPaper) {
            throw new PrinterTooMuchPaperException();
        }
        currentAmountOfPaper += amount;
    }

    public int getCurrentAmountOfPaper() {
        return currentAmountOfPaper;
    }

    public int getMaxAmountOfPaper() {
        return maxAmountOfPaper;
    }

    public String getModel() {
        return model;
    }

    public boolean print(Edition edition, CartridgeType cartridgeType, int copies) {
        if (edition.getAmountOfPages() * copies > currentAmountOfPaper) {
            throw new PrinterNotEnoughPaperException();
        }
        if (this.cartridgeType != cartridgeType) {
            throw new PrinterColorsNotSupportedException();
        }
        for (int i = 0; i < copies; i++) {
            currentAmountOfPaper -= edition.getAmountOfPages();
            printedEditions.add(edition);
        }
        return true;
    }

    public List<Edition> getPrintedEditions() {
        return printedEditions;
    }

    public void clearPrintedEditions() {
        printedEditions.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Printer printer = (Printer) o;
        return Objects.equals(id, printer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Printer{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", maxAmountOfPaper=" + maxAmountOfPaper +
                ", currentAmountOfPaper=" + currentAmountOfPaper +
                ", cartridgeType=" + cartridgeType +
                '}';
    }
}
