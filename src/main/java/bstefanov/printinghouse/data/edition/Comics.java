package bstefanov.printinghouse.data.edition;

import bstefanov.printinghouse.data.paper.PaperSize;
import bstefanov.printinghouse.data.paper.PaperType;

import java.math.BigDecimal;

public class Comics extends Book {
    private String illustrator;

    public Comics(String title, int amountOfPages, PaperSize paperSize, PaperType paperType, BigDecimal price) {
        super(title, amountOfPages, paperSize, paperType, price);
    }

    public Comics(String title, int amountOfPages, PaperSize paperSize, PaperType paperType, BigDecimal price,
                  String author, String genre, String isbn, String publisher, String illustrator) {
        super(title, amountOfPages, paperSize, paperType, price, author, genre, isbn, publisher);
        this.illustrator = illustrator;
    }

    public String getIllustrator() {
        return illustrator;
    }

    public void setIllustrator(String illustrator) {
        this.illustrator = illustrator;
    }

    @Override
    public String editionType() {
        return "Comics";
    }
}
