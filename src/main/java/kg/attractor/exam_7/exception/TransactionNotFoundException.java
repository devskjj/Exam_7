package kg.attractor.exam_7.exception;

import java.util.NoSuchElementException;

public class TransactionNotFoundException extends NoSuchElementException {
    public TransactionNotFoundException(String message) {
        super(message);
    }

    public TransactionNotFoundException(Long id) {
        super("Транзакция с ID " + id + " не найдена");
    }
}
