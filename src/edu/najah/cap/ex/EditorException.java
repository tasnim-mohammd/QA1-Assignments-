package edu.najah.cap.ex;

import java.io.Serial;

public class EditorException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public EditorException(String message) {
        super(message);
    }

}
