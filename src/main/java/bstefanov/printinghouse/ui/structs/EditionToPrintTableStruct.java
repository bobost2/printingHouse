package bstefanov.printinghouse.ui.structs;

import bstefanov.printinghouse.data.edition.Edition;
import bstefanov.printinghouse.data.printer.CartridgeType;
import bstefanov.printinghouse.data.printer.Printer;
import javafx.beans.property.*;

public class EditionToPrintTableStruct {
    private Edition edition;
    private StringProperty title;
    private StringProperty type;
    private IntegerProperty amountOfPages;
    private DoubleProperty price;
    private IntegerProperty expectedEditions;

    public EditionToPrintTableStruct() {
        this.title = new SimpleStringProperty();
        this.type = new SimpleStringProperty();
        this.amountOfPages = new SimpleIntegerProperty();
        this.price = new SimpleDoubleProperty();
        this.expectedEditions = new SimpleIntegerProperty();
    }

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public int getAmountOfPages() {
        return amountOfPages.get();
    }

    public IntegerProperty amountOfPagesProperty() {
        return amountOfPages;
    }

    public void setAmountOfPages(int amountOfPages) {
        this.amountOfPages.set(amountOfPages);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public int getExpectedEditions() {
        return expectedEditions.get();
    }

    public IntegerProperty expectedEditionsProperty() {
        return expectedEditions;
    }

    public void setExpectedEditions(int expectedEditions) {
        this.expectedEditions.set(expectedEditions);
    }
}