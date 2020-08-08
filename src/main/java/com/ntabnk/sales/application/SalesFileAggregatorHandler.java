package com.ntabnk.sales.application;

import com.ntabnk.sales.domain.Sale;
import com.ntabnk.sales.domain.SalesFileRaw;
import com.ntabnk.sales.domain.SalesFileResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Handler;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class SalesFileAggregatorHandler {

    @Handler
    public SalesFileResult handle(SalesFileRaw salesFileRaw) {
        return SalesFileResult.builder()
                .customersSize(salesFileRaw.getCustomers().size())
                .salesPeopleSize(salesFileRaw.getSalespeople().size())
                .worstSalesperson(getWorstSalesperson(salesFileRaw.getSales()))
                .bestSaleId(getBestSaleId(salesFileRaw.getSales()))
                .build();
    }


    String getBestSaleId(List<Sale> sales) {
        if (CollectionUtils.isEmpty(sales)) {
            log.warn("No sales found.");
            return null;
        }
        sales.sort(Comparator.comparing(Sale::total).reversed());
        return sales.get(0).getId();
    }

    String getWorstSalesperson(List<Sale> sales) {
        if (CollectionUtils.isEmpty(sales)) {
            log.warn("No sales found.");
            return null;
        }
        Map<String, BigDecimal> salesPersonSaleAmountMap = new HashMap<>();
        sales.forEach(sale -> {
            salesPersonSaleAmountMap.merge(sale.getSalespersonName().toUpperCase(), sale.total(), BigDecimal::add);
        });

        final Map<String, BigDecimal> sortedByTotal = salesPersonSaleAmountMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return sortedByTotal.entrySet().iterator().next().getKey();

    }

}
