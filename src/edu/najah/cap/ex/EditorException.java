package edu.najah.cap.ex;

import java.io.Serial;

public class EditorException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;
    public EditorException(String message) {
        this(message, 0);
    }

    public EditorException(String message, int errorCode) {
        super(message);
    }

}
