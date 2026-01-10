package kg.attractor.exam_7.dto.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAccountDto {
    @NotBlank(message = "Код валюты обязателен")
    @Size(min = 3, max = 3, message = "Код валюты должен состоять из 3 символов")
    @Pattern(regexp = "^(USD|EUR|KGS)$", message = "Допустимые коды валют - USD, EUR, KGS")
    private String currencyCode;
}

