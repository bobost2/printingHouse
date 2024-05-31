package bstefanov.printinghouse.exceptions.printinghouse;

public class DayNotEndedYetException extends RuntimeException{
    static final String message = "The day has not ended yet";

    public DayNotEndedYetException() {
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
