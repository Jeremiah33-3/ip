public class UserInputException extends Exception{
    String message;
    public UserInputException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
