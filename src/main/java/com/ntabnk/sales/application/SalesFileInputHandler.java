package com.ntabnk.sales.application;

import com.ntabnk.sales.application.converters.CustomerLineConverter;
import com.ntabnk.sales.application.converters.LineConverter;
import com.ntabnk.sales.application.converters.SaleLineConverter;
import com.ntabnk.sales.application.converters.SalespersonLineConverter;
import com.ntabnk.sales.domain.SalesFileRaw;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Handler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class SalesFileInputHandler {
    private static final String COLUMN_BREAKER = "ç";
    public static final int EXPECTED_LENGTH = 3;
    public static final String LINE_BREAKER = "\n";

    private Map<String, LineConverter> lineConverterMap = new HashMap<>();

    private SalespersonLineConverter salespersonLineConverter;
    private CustomerLineConverter customerLineConverter;
    private SaleLineConverter saleLineConverter;

    public SalesFileInputHandler(SalespersonLineConverter salespersonLineConverter, CustomerLineConverter customerLineConverter, SaleLineConverter saleLineConverter) {
        this.salespersonLineConverter = salespersonLineConverter;
        this.customerLineConverter = customerLineConverter;
        this.saleLineConverter = saleLineConverter;
    }

    @PostConstruct
    private void init() {
        lineConverterMap.put("001", salespersonLineConverter);
        lineConverterMap.put("002", customerLineConverter);
        lineConverterMap.put("003", saleLineConverter);
    }

    @Handler
    public SalesFileRaw handle(String body) {
        String[] lines = body.split(LINE_BREAKER);
        SalesFileRaw salesFileRaw = new SalesFileRaw();
        Arrays.stream(lines)
                .map(line -> line.split(COLUMN_BREAKER))
                .filter(array -> validate(array))
                .map(array -> convert(array))
                .forEach(salesFileRaw::add);
        return salesFileRaw;
    }

    private Object convert(String[] data) {
        return lineConverterMap.get(data[0]).convert(Arrays.copyOfRange(data, 1, data.length));
    }

    private boolean validate(String[] data) {
        if (data.length < EXPECTED_LENGTH) {
            log.warn("Unexpected length, at least {} elements should be present.", EXPECTED_LENGTH);
            return false;
        }
        if (!lineConverterMap.containsKey(data[0])) {
            log.warn("Unexpected id: {}.", data[0]);
            return false;
        }
        return true;
    }

}
