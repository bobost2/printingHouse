package bstefanov.printinghouse.tests;

import bstefanov.printinghouse.data.paper.PaperPrice;
import bstefanov.printinghouse.data.paper.PaperSize;
import bstefanov.printinghouse.data.paper.PaperType;
import bstefanov.printinghouse.exceptions.paper.InvalidPaperSizeException;
import bstefanov.printinghouse.exceptions.paper.InvalidPaperTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class PaperPriceTest {

    private PaperPrice paperPrice;
    private PaperPrice modifiedPaperPrices;

    @BeforeEach
    void setUp() {
        // Default prices:
        // Glossy: 0.50
        // Normal: 0.15
        // Newsprint: 0.10
        // Size percentage multiplier: 15.0
        paperPrice = new PaperPrice();

        // Modified prices:
        // Glossy: 0.75
        // Normal: 0.25
        // Newsprint: 0.20
        // Size percentage multiplier: 20.0
        modifiedPaperPrices = new PaperPrice();
        modifiedPaperPrices.setPrice(PaperType.GLOSSY, new BigDecimal("0.75"));
        modifiedPaperPrices.setPrice(PaperType.NORMAL, new BigDecimal("0.25"));
        modifiedPaperPrices.setPrice(PaperType.NEWSPRINT, new BigDecimal("0.20"));
        modifiedPaperPrices.setSizePercentageMultiplier(20.0);
    }

    @Test
    @DisplayName("Test getPrice() with A5 paper size")
    void testGetPriceA5() {
        assertEquals(new BigDecimal("0.50").setScale(5, RoundingMode.HALF_UP),
                paperPrice.getPrice(PaperType.GLOSSY, PaperSize.A5).setScale(5, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("0.15").setScale(5, RoundingMode.HALF_UP),
                paperPrice.getPrice(PaperType.NORMAL, PaperSize.A5).setScale(5, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("0.10").setScale(5, RoundingMode.HALF_UP),
                paperPrice.getPrice(PaperType.NEWSPRINT, PaperSize.A5).setScale(5, RoundingMode.HALF_UP));

        assertEquals(new BigDecimal("0.75").setScale(5, RoundingMode.HALF_UP),
                modifiedPaperPrices.getPrice(PaperType.GLOSSY, PaperSize.A5).setScale(5, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("0.25").setScale(5, RoundingMode.HALF_UP),
                modifiedPaperPrices.getPrice(PaperType.NORMAL, PaperSize.A5).setScale(5, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("0.20").setScale(5, RoundingMode.HALF_UP),
                modifiedPaperPrices.getPrice(PaperType.NEWSPRINT, PaperSize.A5).setScale(5, RoundingMode.HALF_UP));
    }

    @Test
    @DisplayName("Test getPrice() with A4 paper size")
    void testGetPriceA4() {
        assertEquals(new BigDecimal("0.575").setScale(5, RoundingMode.HALF_UP),
                paperPrice.getPrice(PaperType.GLOSSY, PaperSize.A4).setScale(5, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("0.1725").setScale(5, RoundingMode.HALF_UP),
                paperPrice.getPrice(PaperType.NORMAL, PaperSize.A4).setScale(5, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("0.115").setScale(5, RoundingMode.HALF_UP),
                paperPrice.getPrice(PaperType.NEWSPRINT, PaperSize.A4).setScale(5, RoundingMode.HALF_UP));

        assertEquals(new BigDecimal("0.90").setScale(5, RoundingMode.HALF_UP),
                modifiedPaperPrices.getPrice(PaperType.GLOSSY, PaperSize.A4).setScale(5, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("0.30").setScale(5, RoundingMode.HALF_UP),
                modifiedPaperPrices.getPrice(PaperType.NORMAL, PaperSize.A4).setScale(5, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("0.24").setScale(5, RoundingMode.HALF_UP),
                modifiedPaperPrices.getPrice(PaperType.NEWSPRINT, PaperSize.A4).setScale(5, RoundingMode.HALF_UP));
    }

    @Test
    @DisplayName("Test getPrice() with A3 paper size")
    void testGetPriceA3() {
        assertEquals(new BigDecimal("0.65").setScale(5, RoundingMode.HALF_UP),
                paperPrice.getPrice(PaperType.GLOSSY, PaperSize.A3).setScale(5, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("0.195").setScale(5, RoundingMode.HALF_UP),
                paperPrice.getPrice(PaperType.NORMAL, PaperSize.A3).setScale(5, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("0.13").setScale(5, RoundingMode.HALF_UP),
                paperPrice.getPrice(PaperType.NEWSPRINT, PaperSize.A3).setScale(5, RoundingMode.HALF_UP));

        assertEquals(new BigDecimal("1.05").setScale(5, RoundingMode.HALF_UP),
                modifiedPaperPrices.getPrice(PaperType.GLOSSY, PaperSize.A3).setScale(5, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("0.35").setScale(5, RoundingMode.HALF_UP),
                modifiedPaperPrices.getPrice(PaperType.NORMAL, PaperSize.A3).setScale(5, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("0.28").setScale(5, RoundingMode.HALF_UP),
                modifiedPaperPrices.getPrice(PaperType.NEWSPRINT, PaperSize.A3).setScale(5, RoundingMode.HALF_UP));
    }

    @Test
    @DisplayName("Test getPrice() with A2 paper size")
    void testGetPriceA2() {
        assertEquals(new BigDecimal("0.725").setScale(5, RoundingMode.HALF_UP),
                paperPrice.getPrice(PaperType.GLOSSY, PaperSize.A2).setScale(5, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("0.2175").setScale(5, RoundingMode.HALF_UP),
                paperPrice.getPrice(PaperType.NORMAL, PaperSize.A2).setScale(5, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("0.145").setScale(5, RoundingMode.HALF_UP),
                paperPrice.getPrice(PaperType.NEWSPRINT, PaperSize.A2).setScale(5, RoundingMode.HALF_UP));

        assertEquals(new BigDecimal("1.20").setScale(5, RoundingMode.HALF_UP),
                modifiedPaperPrices.getPrice(PaperType.GLOSSY, PaperSize.A2).setScale(5, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("0.40").setScale(5, RoundingMode.HALF_UP),
                modifiedPaperPrices.getPrice(PaperType.NORMAL, PaperSize.A2).setScale(5, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("0.32").setScale(5, RoundingMode.HALF_UP),
                modifiedPaperPrices.getPrice(PaperType.NEWSPRINT, PaperSize.A2).setScale(5, RoundingMode.HALF_UP));
    }

    @Test
    @DisplayName("Test getPrice() with A1 paper size")
    void testGetPriceA1() {
        assertEquals(new BigDecimal("0.8").setScale(5, RoundingMode.HALF_UP),
                paperPrice.getPrice(PaperType.GLOSSY, PaperSize.A1).setScale(5, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("0.24").setScale(5, RoundingMode.HALF_UP),
                paperPrice.getPrice(PaperType.NORMAL, PaperSize.A1).setScale(5, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("0.16").setScale(5, RoundingMode.HALF_UP),
                paperPrice.getPrice(PaperType.NEWSPRINT, PaperSize.A1).setScale(5, RoundingMode.HALF_UP));

        assertEquals(new BigDecimal("1.35").setScale(5, RoundingMode.HALF_UP),
                modifiedPaperPrices.getPrice(PaperType.GLOSSY, PaperSize.A1).setScale(5, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("0.45").setScale(5, RoundingMode.HALF_UP),
                modifiedPaperPrices.getPrice(PaperType.NORMAL, PaperSize.A1).setScale(5, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("0.36").setScale(5, RoundingMode.HALF_UP),
                modifiedPaperPrices.getPrice(PaperType.NEWSPRINT, PaperSize.A1).setScale(5, RoundingMode.HALF_UP));
    }

    @Test
    @DisplayName("Test getPrice() exceptions")
    void testGetPriceExceptions() {
        assertThrows(InvalidPaperTypeException.class, () -> paperPrice.getPrice(null, null));
        assertThrows(InvalidPaperTypeException.class, () -> paperPrice.getPrice(null, PaperSize.A5));
        assertThrows(InvalidPaperSizeException.class, () -> paperPrice.getPrice(PaperType.GLOSSY, null));

        assertThrows(InvalidPaperTypeException.class, () -> modifiedPaperPrices.setPrice(null, null));
    }
}