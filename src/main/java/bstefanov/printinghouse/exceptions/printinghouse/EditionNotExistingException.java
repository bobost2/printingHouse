package bstefanov.printinghouse.exceptions.printinghouse;

public class EditionNotExistingException extends RuntimeException{
    static final String message = "The edition is not in the list of editions to print";

    public EditionNotExistingException() {
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
