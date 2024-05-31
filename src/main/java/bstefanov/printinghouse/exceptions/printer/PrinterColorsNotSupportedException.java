package bstefanov.printinghouse.exceptions.printer;

public class PrinterColorsNotSupportedException extends RuntimeException{
    static final String message = "The printer does not support printing in the specified colors";

    public PrinterColorsNotSupportedException() {
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
