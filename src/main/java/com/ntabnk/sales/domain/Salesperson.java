package com.ntabnk.sales.domain;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class Salesperson {
    private String cpf;
    private String name;
    private BigDecimal salary;
}
