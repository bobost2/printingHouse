package bstefanov.printinghouse.exceptions.paper;

public class InvalidPaperSizeException extends RuntimeException{
    static final String message = "Invalid paper size";

    public InvalidPaperSizeException() {
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
