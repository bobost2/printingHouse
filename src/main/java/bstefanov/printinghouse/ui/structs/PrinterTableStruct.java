package bstefanov.printinghouse.ui.structs;

import bstefanov.printinghouse.data.printer.CartridgeType;
import bstefanov.printinghouse.data.printer.Printer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PrinterTableStruct {
    private Printer printer;
    private StringProperty model;
    private StringProperty cartridgeType;
    private IntegerProperty currentAmountOfPaper;
    private IntegerProperty maxAmountOfPaper;

    public PrinterTableStruct() {
        this.model = new SimpleStringProperty();
        this.cartridgeType = new SimpleStringProperty();
        this.currentAmountOfPaper = new SimpleIntegerProperty();
        this.maxAmountOfPaper = new SimpleIntegerProperty();
    }

    public Printer getPrinter() {
        return printer;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

    public String getModel() {
        return model.get();
    }

    public StringProperty modelProperty() {
        return model;
    }

    public void setModel(String model) {
        this.model.set(model);
    }

    public String getCartridgeType() {
        return cartridgeType.get();
    }

    public StringProperty cartridgeTypeProperty() {
        return cartridgeType;
    }

    public void setCartridgeType(CartridgeType cartridgeType) {
        if (cartridgeType == CartridgeType.COLOR) {
            this.cartridgeType.set("Color");
        } else {
            this.cartridgeType.set("Black and white");
        }
    }

    public int getCurrentAmountOfPaper() {
        return currentAmountOfPaper.get();
    }

    public IntegerProperty currentAmountOfPaperProperty() {
        return currentAmountOfPaper;
    }

    public void setCurrentAmountOfPaper(int currentAmountOfPaper) {
        this.currentAmountOfPaper.set(currentAmountOfPaper);
    }

    public int getMaxAmountOfPaper() {
        return maxAmountOfPaper.get();
    }

    public IntegerProperty maxAmountOfPaperProperty() {
        return maxAmountOfPaper;
    }

    public void setMaxAmountOfPaper(int maxAmountOfPaper) {
        this.maxAmountOfPaper.set(maxAmountOfPaper);
    }
}