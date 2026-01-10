package kg.attractor.exam_7.exception;

public class FailedToCreateException extends IllegalStateException {
    public FailedToCreateException(String message) {
        super(message);
    }
}