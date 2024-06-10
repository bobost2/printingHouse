package bstefanov.printinghouse.ui.structs;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PrintingHousesTableStruct {
    private int id;
    private StringProperty name;
    private StringProperty address;

    public PrintingHousesTableStruct() {
        this.name = new SimpleStringProperty();
        this.address = new SimpleStringProperty();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }
}