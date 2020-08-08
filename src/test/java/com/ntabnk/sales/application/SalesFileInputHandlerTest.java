package com.ntabnk.sales.application;

import com.ntabnk.sales.application.converters.CustomerLineConverter;
import com.ntabnk.sales.application.converters.SaleLineConverter;
import com.ntabnk.sales.application.converters.SalespersonLineConverter;
import com.ntabnk.sales.domain.SalesFileRaw;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SalesFileInputHandlerTest {

    private final SaleLineConverter saleLineConverter = new SaleLineConverter();
    private final CustomerLineConverter customerLineConverter = new CustomerLineConverter();
    private final SalespersonLineConverter salespersonLineConverter = new SalespersonLineConverter();

    @Test
    public void testHandler() {
        SalesFileInputHandler salesFileInputHandler = new SalesFileInputHandler(salespersonLineConverter,
                customerLineConverter, saleLineConverter);
        salesFileInputHandler.init();

        SalesFileRaw salesFileRaw = salesFileInputHandler.handle("001ç1234567891234çPedroç50000\n" +
                "001ç3245678865434çPauloç40000.99\n" +
                "002ç2345675434544345çJose da SilvaçRural\n" +
                "002ç2345675433444345çEduardo PereiraçRural\n" +
                "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro\n" +
                "003ç10ç[1-1-0.1]çRobson");

        assertThat(salesFileRaw).isNotNull();
        assertThat(salesFileRaw.getCustomers()).hasSize(2);
        assertThat(salesFileRaw.getSales()).hasSize(2);
        assertThat(salesFileRaw.getSalespeople()).hasSize(2);

    }
}
