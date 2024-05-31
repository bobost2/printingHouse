package bstefanov.printinghouse.exceptions.paper;

public class InvalidPaperTypeException extends RuntimeException{
    static final String message = "Invalid paper type";

    public InvalidPaperTypeException() {
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
