package bstefanov.printinghouse.data.audit;

import bstefanov.printinghouse.data.edition.Edition;
import bstefanov.printinghouse.data.printer.Printer;

import java.math.BigDecimal;

public class EditionPrinted implements AuditableRecord{

    private final Edition edition;
    private final Printer printer;
    private final BigDecimal totalPrice;

    public EditionPrinted(Edition edition, Printer printer, BigDecimal totalPrice) {
        this.edition = edition;
        this.printer = printer;
        this.totalPrice = totalPrice;
    }

    @Override
    public String recordDetails() {
        return "[-] Edition '" + edition.getTitle() +"' printed on printer '" + printer.getModel();
    }

    @Override
    public BigDecimal moneyGainedOrLost() {
        return totalPrice.multiply(BigDecimal.valueOf(-1));
    }

    @Override
    public String toString() {
        return "EditionPrinted{" +
                ", recordDetails=" + recordDetails() +
                ", totalPrice=" + moneyGainedOrLost() +
                '}';
    }
}
