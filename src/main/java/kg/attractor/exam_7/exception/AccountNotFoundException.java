package kg.attractor.exam_7.exception;

import java.util.NoSuchElementException;

public class AccountNotFoundException extends NoSuchElementException {
    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException(Long accountId) {
        super("Счет с ID " + accountId + " не найден");
    }
}
