package bstefanov.printinghouse.ui.structs;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ReportTableStruct {
    private StringProperty details;
    private StringProperty moneyGainedLost;

    public ReportTableStruct() {
        this.details = new SimpleStringProperty();
        this.moneyGainedLost = new SimpleStringProperty();
    }

    public String getDetails() {
        return details.get();
    }

    public StringProperty detailsProperty() {
        return details;
    }

    public void setDetails(String details) {
        this.details.set(details);
    }

    public String getMoneyGainedLost() {
        return moneyGainedLost.get();
    }

    public StringProperty moneyGainedLostProperty() {
        return moneyGainedLost;
    }

    public void setMoneyGainedLost(String moneyGainedLost) {
        this.moneyGainedLost.set(moneyGainedLost);
    }
}