package com.ntabnk.sales.application.converters;

import com.ntabnk.sales.domain.Customer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerLineConverterTest {

    @Test
    public void testConversion() {
        String[] data = "2345675434544345çJose da SilvaçRural".split("ç");
        Customer customer = new CustomerLineConverter().convert(data);
        assertThat(customer).isNotNull();
        assertThat(customer.getBusinessArea()).isEqualTo("Rural");
        assertThat(customer.getCnpj()).isEqualTo("2345675434544345");
        assertThat(customer.getName()).isEqualTo("Jose da Silva");
    }
}
