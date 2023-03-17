package edu.najah.cap.ex;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NoSuchFileException;
import java.util.Objects;

public class EditorFileException extends IOException {

    public static final long serialVersionUID = 1L;

    public EditorFileException(String message) {
        super(message);
    }

    public EditorFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public static EditorFileException fileAlreadyExistsException(String message) {
        Objects.requireNonNull(message, "message must not be null");
        return new EditorFileException(message, new FileAlreadyExistsException(message));
    }

    public static EditorFileException noSuchFileException(String message) {
        Objects.requireNonNull(message, "message must not be null");
        return new EditorFileException(message, new NoSuchFileException(message));
    }
}
