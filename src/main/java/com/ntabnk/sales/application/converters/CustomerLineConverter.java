package com.ntabnk.sales.application.converters;

import com.ntabnk.sales.domain.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerLineConverter implements LineConverter<Customer> {

    @Override
    public Customer convert(String[] data) {
        return Customer.builder()
                .cnpj(data[0])
                .name(data[1])
                .businessArea(data[2])
                .build();
    }

}
