package bstefanov.printinghouse.data.edition;

import bstefanov.printinghouse.data.paper.PaperSize;
import bstefanov.printinghouse.data.paper.PaperType;

import java.math.BigDecimal;

public class Book extends Edition {
    private String author;
    private String genre;
    private String isbn;
    private String publisher;

    public Book(String title, int amountOfPages, PaperSize paperSize, PaperType paperType, BigDecimal price) {
        super(title, amountOfPages, paperSize, paperType, price);
    }

    public Book(String title, int amountOfPages, PaperSize paperSize, PaperType paperType, BigDecimal price,
                String author, String genre, String isbn, String publisher) {
        super(title, amountOfPages, paperSize, paperType, price);
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
        this.publisher = publisher;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String editionType() {
        return "Book";
    }
}
