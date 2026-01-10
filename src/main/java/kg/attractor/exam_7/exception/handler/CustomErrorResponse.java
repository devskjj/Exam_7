package kg.attractor.exam_7.exception.handler;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CustomErrorResponse {
    private String errorMessage;
    private Map<String, List<String>> reasons;
}
