package bstefanov.printinghouse.data.audit;

import java.math.BigDecimal;

public interface AuditableRecord {
    String recordDetails();
    BigDecimal moneyGainedOrLost();
}
