package kg.attractor.exam_7.exception.handler;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ValidationException;
import kg.attractor.exam_7.exception.FailedToCreateException;
import kg.attractor.exam_7.service.ErrorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@Hidden
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {
    private final ErrorService errorService;

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomErrorResponse notSuchElementHandler(NoSuchElementException e) {
        log.error("Ошибка запроса: ", e);
        return errorService.makeErrorResponse(e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomErrorResponse validationHandler(MethodArgumentNotValidException e) {
        log.error("Ошибка запроса: ", e);
        return errorService.makeErrorResponse(e.getBindingResult());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomErrorResponse handleValidation(ValidationException e) {
        log.error("Ошибка запроса: ", e);
        return errorService.makeErrorResponse(e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomErrorResponse handleException(Exception e) {
        log.error("Ошибка запроса: ", e);
        return errorService.makeErrorResponse(e);
    }

    @ExceptionHandler(FailedToCreateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomErrorResponse handlerFailedToCreateException(Exception e) {
        log.error("Ошибка запроса: ", e);
        return errorService.makeErrorResponse(e);
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomErrorResponse handlerDataAccessException(Exception e) {
        log.error("Ошибка запроса: ", e);
        return errorService.makeErrorResponse(e);
    }
}
