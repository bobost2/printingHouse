package bstefanov.printinghouse.data.audit;

import bstefanov.printinghouse.data.edition.Edition;

import java.math.BigDecimal;

public class EditionSold implements AuditableRecord {
    private final Edition edition;
    private final int copiesSold;
    private final BigDecimal totalPrice;

    public EditionSold(Edition edition, int copiesSold, BigDecimal totalPrice) {
        this.edition = edition;
        this.copiesSold = copiesSold;
        this.totalPrice = totalPrice;
    }

    @Override
    public String recordDetails() {
        if (copiesSold > 1) {
            return "[+] Edition '" + edition.getTitle() + "' sold x" + copiesSold + " times";
        } else {
            return "[+] Edition '" + edition.getTitle() + "' sold";
        }
    }

    @Override
    public BigDecimal moneyGainedOrLost() {
        return totalPrice;
    }
}
