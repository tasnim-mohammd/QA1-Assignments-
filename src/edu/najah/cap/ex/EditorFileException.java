package edu.najah.cap.ex;

import java.nio.file.NoSuchFileException;

public class EditorFileException extends EditorException{
    public EditorFileException(String message) {
        super(message);
    }

    public static class EditorException extends NoSuchFileException {

        public EditorException(String message) {
            super(message);
        }
    }
}
