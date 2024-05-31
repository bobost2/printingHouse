package bstefanov.printinghouse.data.paper;

import bstefanov.printinghouse.exceptions.paper.InvalidPaperSizeException;
import bstefanov.printinghouse.exceptions.paper.InvalidPaperTypeException;

import java.math.BigDecimal;
import java.util.EnumMap;

@SuppressWarnings("UnpredictableBigDecimalConstructorCall")
public class PaperPrice {
    @SuppressWarnings("FieldMayBeFinal")
    private EnumMap<PaperType, BigDecimal> paperPrice;
    private double sizePercentageMultiplier;

    // Constructors
    public PaperPrice() {
        paperPrice = new EnumMap<>(PaperType.class);
        paperPrice.put(PaperType.GLOSSY, new BigDecimal(0.50));
        paperPrice.put(PaperType.NORMAL, new BigDecimal(0.15));
        paperPrice.put(PaperType.NEWSPRINT, new BigDecimal(0.10));
        sizePercentageMultiplier = 15.0;
    }

    public PaperPrice(EnumMap<PaperType, BigDecimal> paperPrice, double sizePercentageMultiplier) {
        this.paperPrice = paperPrice;
        this.sizePercentageMultiplier = sizePercentageMultiplier;
    }

    public PaperPrice(BigDecimal glossyPrice, BigDecimal normalPrice, BigDecimal newsprintPrice, double sizePercentageMultiplier) {
        paperPrice = new EnumMap<>(PaperType.class);
        paperPrice.put(PaperType.GLOSSY, glossyPrice);
        paperPrice.put(PaperType.NORMAL, normalPrice);
        paperPrice.put(PaperType.NEWSPRINT, newsprintPrice);
        this.sizePercentageMultiplier = sizePercentageMultiplier;
    }

    // Methods
    public double getSizePercentageMultiplier() {
        return sizePercentageMultiplier;
    }

    public void setSizePercentageMultiplier(double sizePercentageMultiplier) {
        this.sizePercentageMultiplier = sizePercentageMultiplier;
    }

    public BigDecimal getPrice(PaperType paperType, PaperSize size) {
        if (paperPrice.get(paperType) == null) {
            throw new InvalidPaperTypeException();
        }
        else if (size == null) {
            throw new InvalidPaperSizeException();
        }
        else if (size == PaperSize.A5) {
            return paperPrice.get(paperType);
        }

        return paperPrice.get(paperType).multiply(new BigDecimal(size.getValue() * sizePercentageMultiplier / 100));
    }

    public void setPrice(PaperType paperType, BigDecimal price) {
        if (paperPrice.get(paperType) == null) {
            throw new InvalidPaperTypeException();
        }
        paperPrice.put(paperType, price);
    }
}
