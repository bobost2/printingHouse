package bstefanov.printinghouse.exceptions.printer;

public class PrinterTooMuchPaperException extends RuntimeException{
    static final String message = "The printer cannot hold that much paper";

    public PrinterTooMuchPaperException() {
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
