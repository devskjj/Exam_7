package kg.attractor.exam_7.service.impl;

import kg.attractor.exam_7.service.ExchangeRateService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private static final Map<String, BigDecimal> rates = new HashMap<>();

    static {
        rates.put("USD", new BigDecimal("1.0"));
        rates.put("EUR", new BigDecimal("1.1"));
        rates.put("KGS", new BigDecimal("87.5"));
    }

    @Override
    public BigDecimal getExchangeRate(String fromCurrency, String toCurrency) {
        BigDecimal from = rates.get(fromCurrency);
        BigDecimal to = rates.get(toCurrency);

        if (from == null || to == null) {
            throw new IllegalArgumentException(
                    "Неизвестная валюта: " + fromCurrency + " или " + toCurrency
            );
        }

        return to.divide(from, 6, RoundingMode.HALF_UP);
    }
}
