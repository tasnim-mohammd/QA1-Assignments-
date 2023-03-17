package edu.najah.cap.ex;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NoSuchFileException;
import java.util.Objects;

public class EditorFileException extends IOException {

    public static final long serialVersionUID = 1L;
    private final int errorCode;

    public EditorFileException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public EditorFileException(String message, Throwable cause, int errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public static EditorFileException fileAlreadyExistsException(String message) {
        Objects.requireNonNull(message, "message must not be null");
        return new EditorFileException(message, new FileAlreadyExistsException(message), 409);
    }

    public static EditorFileException noSuchFileException(String message) {
        Objects.requireNonNull(message, "message must not be null");
        return new EditorFileException(message, new NoSuchFileException(message), 404);
    }
}
