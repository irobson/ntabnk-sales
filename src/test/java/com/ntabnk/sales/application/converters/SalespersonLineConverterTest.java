package com.ntabnk.sales.application.converters;

import com.ntabnk.sales.domain.Salesperson;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class SalespersonLineConverterTest {

    @Test
    public void testConvert() {
        String[] data = "1234567891234çPedroç50000".split("ç");
        Salesperson salesperson = new SalespersonLineConverter().convert(data);
        assertThat(salesperson).isNotNull();
        assertThat(salesperson.getName()).isEqualTo("Pedro");
        assertThat(salesperson.getCpf()).isEqualTo("1234567891234");
        assertThat(salesperson.getSalary()).isEqualTo(new BigDecimal("50000"));
    }
}
