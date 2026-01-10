package kg.attractor.exam_7.exception;

import java.util.NoSuchElementException;

public class UserNotFoundException extends NoSuchElementException {
    public UserNotFoundException() {
        super("Пользователь не найден");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
