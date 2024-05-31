package bstefanov.printinghouse.data.edition;

import bstefanov.printinghouse.data.paper.PaperSize;
import bstefanov.printinghouse.data.paper.PaperType;

import java.math.BigDecimal;

public class Poster extends Edition {
    private double width;
    private double height;
    private String event;

    public Poster(String title, int amountOfPages, PaperSize paperSize, PaperType paperType, BigDecimal price) {
        super(title, amountOfPages, paperSize, paperType, price);
    }

    public Poster(String title, int amountOfPages, PaperSize paperSize, PaperType paperType, BigDecimal price,
                  double width, double height, String event) {
        super(title, amountOfPages, paperSize, paperType, price);
        this.width = width;
        this.height = height;
        this.event = event;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public String editionType() {
        return "Poster";
    }
}
