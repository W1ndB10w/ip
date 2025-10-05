package reverie.exception;

/**
 * Represents a custom exception for the Reverie chatbot.
 * A <code>ReverieException</code> is thrown when specific errors
 * occur during the execution of Reverie commands.
 */
public class ReverieException extends Exception {
    /**
     * Constructs a ReverieException with the specified error message.
     *
     * @param message The error message describing what went wrong.
     */
    public ReverieException(String message) {
        super(message);
    }
}
