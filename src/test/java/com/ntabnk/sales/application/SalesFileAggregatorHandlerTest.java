package com.ntabnk.sales.application;

import com.ntabnk.sales.application.converters.CustomerLineConverter;
import com.ntabnk.sales.application.converters.SaleLineConverter;
import com.ntabnk.sales.application.converters.SalespersonLineConverter;
import com.ntabnk.sales.domain.SalesFileRaw;
import com.ntabnk.sales.domain.SalesFileResult;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SalesFileAggregatorHandlerTest {

    private final SaleLineConverter saleLineConverter = new SaleLineConverter();
    private final CustomerLineConverter customerLineConverter = new CustomerLineConverter();
    private final SalespersonLineConverter salespersonLineConverter = new SalespersonLineConverter();

    @Test
    public void shouldHandleValidSalesFileRaw() {
        SalesFileRaw salesFileRaw = new SalesFileRaw();
        salesFileRaw.add(saleLineConverter.convert("10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro".split("ç")));
        salesFileRaw.add(saleLineConverter.convert("10ç[1-10-100,2-30-2.00,3-40-3.10]çRobson".split("ç")));
        salesFileRaw.add(saleLineConverter.convert("10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro".split("ç")));
        salesFileRaw.add(customerLineConverter.convert("2345675434544345çJose da SilvaçRural".split("ç")));
        salesFileRaw.add(salespersonLineConverter.convert("1234567891234çPedroç50000".split("ç")));

        SalesFileResult salesFileResult = new SalesFileAggregatorHandler().handle(salesFileRaw);
        assertThat(salesFileResult).isNotNull();
        assertThat(salesFileResult.formattedResult()).isEqualTo("Customers size: 1, Sales People size: 1, " +
                "Best Sales Id: 10, Worst Salesperson name: ROBSON");
    }
}
