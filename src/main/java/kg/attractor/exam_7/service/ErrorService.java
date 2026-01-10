package kg.attractor.exam_7.service;

import kg.attractor.exam_7.exception.handler.CustomErrorResponse;
import org.springframework.validation.BindingResult;

public interface ErrorService {
    CustomErrorResponse makeErrorResponse(Exception e);

    CustomErrorResponse makeErrorResponse(BindingResult bindingResult);
}