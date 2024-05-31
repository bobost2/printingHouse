package bstefanov.printinghouse.exceptions.printinghouse;

public class EditionNotEnoughCopiesException extends RuntimeException{
    static final String message = "There are not enough copies of this edition";

    public EditionNotEnoughCopiesException() {
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
