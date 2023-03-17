package edu.najah.cap.ex;

public class EditorException extends Exception {
    private static final long serialVersionUID = 1L;
    private final int errorCode;

    public EditorException(String message) {
        this(message, 0);
    }

    public EditorException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
