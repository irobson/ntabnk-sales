package com.ntabnk.sales.application;

import com.ntabnk.sales.application.converters.CustomerLineConverter;
import com.ntabnk.sales.application.converters.LineConverter;
import com.ntabnk.sales.application.converters.SaleLineConverter;
import com.ntabnk.sales.application.converters.SalespersonLineConverter;
import com.ntabnk.sales.domain.Customer;
import com.ntabnk.sales.domain.Sale;
import com.ntabnk.sales.domain.SalesFileRaw;
import com.ntabnk.sales.domain.Salesperson;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Handler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

@Component
@Slf4j
public class SalesFileInputHandler {
    public static final int EXPECTED_LENGTH = 3;
    public static final String LINE_BREAKER = "\n";
    private static final String COLUMN_BREAKER = "ç";

    private BiFunction<SalesFileRaw, Customer, Boolean> addCustomer =
            (salesFileRaw, customer) -> salesFileRaw.add(customer);

    private BiFunction<SalesFileRaw, Sale, Boolean> addSale =
            (salesFileRaw, sale) -> salesFileRaw.add(sale);

    private BiFunction<SalesFileRaw, Salesperson, Boolean> addSalesperson =
            (salesFileRaw, salesperson) -> salesFileRaw.add(salesperson);

    private Map<String, LineConverter> lineConverterMap = new HashMap<>();
    private Map<Class, BiFunction> salesFileRawMap = new HashMap<>();


    private SalespersonLineConverter salespersonLineConverter;
    private CustomerLineConverter customerLineConverter;
    private SaleLineConverter saleLineConverter;

    public SalesFileInputHandler(SalespersonLineConverter salespersonLineConverter, CustomerLineConverter customerLineConverter, SaleLineConverter saleLineConverter) {
        this.salespersonLineConverter = salespersonLineConverter;
        this.customerLineConverter = customerLineConverter;
        this.saleLineConverter = saleLineConverter;
    }

    @PostConstruct
    void init() {
        lineConverterMap.put("001", salespersonLineConverter);
        lineConverterMap.put("002", customerLineConverter);
        lineConverterMap.put("003", saleLineConverter);

        salesFileRawMap.put(Customer.class, addCustomer);
        salesFileRawMap.put(Sale.class, addSale);
        salesFileRawMap.put(Salesperson.class, addSalesperson);
    }

    @Handler
    public SalesFileRaw handle(String body) {
        String[] lines = body.split(LINE_BREAKER);
        SalesFileRaw salesFileRaw = new SalesFileRaw();
        Arrays.stream(lines)
                .map(line -> line.split(COLUMN_BREAKER))
                .filter(array -> validate(array))
                .map(array -> convert(array))
                .forEach(element -> this.addToSalesFileRaw(element, salesFileRaw));
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

    private void addToSalesFileRaw(Object object, SalesFileRaw salesFileRaw) {
        if (this.salesFileRawMap.containsKey(object.getClass())) {
            this.salesFileRawMap.get(object.getClass()).apply(salesFileRaw, object);
        }
    }

}
