package SWD392_SOSS.exceptions;

public class NotFoundUserOrWrongException extends Exception{
    private static final long serialVersionUID = 1L;

    public NotFoundUserOrWrongException(String message) {
        super(message);
    }
}
