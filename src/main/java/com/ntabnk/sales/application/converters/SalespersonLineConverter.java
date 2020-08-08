package com.ntabnk.sales.application.converters;

import com.ntabnk.sales.domain.Salesperson;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SalespersonLineConverter implements LineConverter<Salesperson> {

    @Override
    public Salesperson convert(String[] data) {
        return Salesperson.builder()
                .cpf(data[0])
                .name(data[1])
                .salary(new BigDecimal(data[2]))
                .build();
    }
}
