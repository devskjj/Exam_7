package kg.attractor.exam_7.exception;

import java.util.NoSuchElementException;

public class AccessDeniedException extends NoSuchElementException {
    public AccessDeniedException(String message) {
        super(message);
    }
}
