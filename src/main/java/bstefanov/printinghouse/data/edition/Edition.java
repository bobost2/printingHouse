package bstefanov.printinghouse.data.edition;

import bstefanov.printinghouse.data.paper.PaperSize;
import bstefanov.printinghouse.data.paper.PaperType;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public abstract class Edition {
    private final UUID id;
    private String title;
    private int amountOfPages;
    private PaperSize paperSize;
    private PaperType paperType;
    private BigDecimal price;

    public Edition(String title, int amountOfPages, PaperSize paperSize, PaperType paperType, BigDecimal price) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.amountOfPages = amountOfPages;
        this.paperSize = paperSize;
        this.paperType = paperType;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAmountOfPages() {
        return amountOfPages;
    }

    public void setAmountOfPages(int amountOfPages) {
        this.amountOfPages = amountOfPages;
    }

    public PaperSize getPaperSize() {
        return paperSize;
    }

    public void setPaperSize(PaperSize paperSize) {
        this.paperSize = paperSize;
    }

    public PaperType getPaperType() {
        return paperType;
    }

    public void setPaperType(PaperType paperType) {
        this.paperType = paperType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    abstract public String editionType();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edition edition = (Edition) o;
        return Objects.equals(id, edition.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Edition{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", amountOfPages=" + amountOfPages +
                ", paperSize=" + paperSize +
                ", paperType=" + paperType +
                ", price=" + price +
                '}';
    }
}
