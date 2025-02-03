package exception;

/**
 * Represents an exception that is thrown when there is an error in user input.
 * This exception is used to handle invalid or unexpected input from the user.
 */
public class UserInputException extends Exception {
    private String message;

    /**
     * Constructs a UserInputException with the specified error message.
     *
     * @param message The error message describing the cause of the exception.
     */
    public UserInputException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * Returns the error message associated with this exception.
     *
     * @return The error message describing the cause of the exception.
     */
    @Override
    public String getMessage() {
        return this.message;
    }
}