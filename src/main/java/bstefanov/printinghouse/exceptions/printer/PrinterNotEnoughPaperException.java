package bstefanov.printinghouse.exceptions.printer;

public class PrinterNotEnoughPaperException extends RuntimeException{
    static final String message = "The printer does not have enough paper for this operation";

    public PrinterNotEnoughPaperException() {
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
