package bstefanov.printinghouse.exceptions.printinghouse;

public class DayNotStartedYetException extends RuntimeException{
    static final String message = "The day has not started yet";

    public DayNotStartedYetException() {
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
