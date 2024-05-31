package bstefanov.printinghouse.data.edition;

import bstefanov.printinghouse.data.paper.PaperSize;
import bstefanov.printinghouse.data.paper.PaperType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Newspaper extends Edition {
    private Date publicationDate;
    private String publisher;
    private List<String> topics;

    public Newspaper(String title, int amountOfPages, PaperSize paperSize, PaperType paperType, BigDecimal price) {
        super(title, amountOfPages, paperSize, paperType, price);
    }

    public Newspaper(String title, int amountOfPages, PaperSize paperSize, PaperType paperType, BigDecimal price,
                      Date publicationDate, String publisher, List<String> topics) {
        super(title, amountOfPages, paperSize, paperType, price);
        this.publicationDate = publicationDate;
        this.publisher = publisher;
        this.topics = topics;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    @Override
    public String editionType() {
        return "Newspaper";
    }
}
