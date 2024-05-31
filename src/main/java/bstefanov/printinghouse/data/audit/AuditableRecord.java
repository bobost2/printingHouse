package bstefanov.printinghouse.data.audit;

import java.io.Serializable;
import java.math.BigDecimal;

public interface AuditableRecord extends Serializable {
    String recordDetails();
    BigDecimal moneyGainedOrLost();
}
