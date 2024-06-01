package bstefanov.printinghouse.data.audit;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

public class FinalReport implements Serializable {
    private final ArrayList<AuditableRecord> audit;
    private final BigDecimal expectedProfit;
    private final BigDecimal profit;
    private final BigDecimal losses;
    private final BigDecimal total;

    public FinalReport(ArrayList<AuditableRecord> audit, BigDecimal expectedProfit) {
        this.audit = audit;
        this.expectedProfit = expectedProfit;

        BigDecimal tempProfit = BigDecimal.ZERO;
        BigDecimal tempLosses = BigDecimal.ZERO;

        for (AuditableRecord record : audit) {
            BigDecimal money = record.moneyGainedOrLost();
            if (money.compareTo(BigDecimal.ZERO) > 0) {
                tempProfit = tempProfit.add(money);
            } else {
                tempLosses = tempLosses.add(money);
            }
        }

        profit = tempProfit;
        losses = tempLosses.multiply(BigDecimal.valueOf(-1));

        total = profit.subtract(losses);
    }

    public ArrayList<AuditableRecord> getAudit() {
        return audit;
    }

    public BigDecimal getExpectedProfit() {
        return expectedProfit;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public BigDecimal getLosses() {
        return losses;
    }

    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "FinalReport{" +
                "audit=" + audit +
                ", expectedProfit=" + expectedProfit +
                ", profit=" + profit +
                ", losses=" + losses +
                ", total=" + total +
                '}';
    }
}
