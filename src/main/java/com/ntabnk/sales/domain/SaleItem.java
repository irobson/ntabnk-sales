package com.ntabnk.sales.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Builder
@Getter
public class SaleItem {
    private Long id;
    private Integer quantity;
    private BigDecimal price;
}
