package com.ntabnk.sales.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@EqualsAndHashCode(of = "id")
public class Sale {
    private String id;
    private List<SaleItem> saleItems;
    private String salespersonName;

    public BigDecimal total() {
        if (CollectionUtils.isEmpty(saleItems)) {
            return new BigDecimal(0);
        }
        return saleItems.stream()
                .map(saleItem -> saleItem.getPrice().multiply(new BigDecimal(saleItem.getQuantity())))
                .reduce(BigDecimal::add)
                .get();
    }
}
