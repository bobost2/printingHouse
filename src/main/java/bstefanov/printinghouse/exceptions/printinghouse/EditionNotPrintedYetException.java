package bstefanov.printinghouse.exceptions.printinghouse;

public class EditionNotPrintedYetException extends RuntimeException{
    static final String message = "The edition has not been printed yet";

    public EditionNotPrintedYetException() {
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
