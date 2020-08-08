package com.ntabnk.sales.application.converters;

import com.ntabnk.sales.domain.Item;
import com.ntabnk.sales.domain.Sale;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class SaleLineConverter implements LineConverter<Sale> {

    public static final String LINE_BREAKER = ",";
    public static final String COLUMN_BREAKER = "-";

    @Override
    public Sale convert(String[] data) {
        return Sale.builder()
                .id(data[0])
                .items(Arrays.stream(cleanUp(data[1]).split(LINE_BREAKER))
                        .map(item -> item.split(COLUMN_BREAKER))
                        .map(SaleLineConverter::toItem)
                        .collect(Collectors.toList()))
                .salespersonName(data[2])
                .build();
    }

    private static Item toItem(String[] data) {
        return Item.builder()
                .id(Long.valueOf(data[0]))
                .quantity(Integer.valueOf(data[1]))
                .price(new BigDecimal(data[2]))
                .build();
    }

    private static String cleanUp(String value) {
        return value.replace("[", "").replace("]", "");
    }
}
