package com.ntabnk.sales.application.converters;

import com.ntabnk.sales.domain.Sale;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class SaleLineConverterTest {
    @Test
    public void shouldConvertValidLine() {
        String[] data = "10ç[1-2-0.1]çRobson".split("ç");
        Sale sale = new SaleLineConverter().convert(data);
        assertThat(sale).isNotNull();
        assertThat(sale.getId()).isEqualTo("10");
        assertThat(sale.getSalespersonName()).isEqualTo("Robson");
        assertThat(sale.total()).isCloseTo(new BigDecimal(0.2), Percentage.withPercentage(0.1));

        assertThat(sale.getSaleItems()).isNotNull();
        assertThat(sale.getSaleItems()).hasSize(1);
        assertThat(sale.getSaleItems().get(0).getId()).isEqualTo(1L);
        assertThat(sale.getSaleItems().get(0).getPrice()).isCloseTo(new BigDecimal(0.1),
                Percentage.withPercentage(0.1));
        assertThat(sale.getSaleItems().get(0).getQuantity()).isEqualTo(2);
    }
}
