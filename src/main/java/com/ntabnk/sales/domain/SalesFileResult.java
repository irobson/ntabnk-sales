package com.ntabnk.sales.domain;

import lombok.Builder;

@Builder
public class SalesFileResult {

    private Integer customersSize;
    private Integer salesPeopleSize;
    private String bestSaleId;
    private String worstSalesperson;

    public String formattedResult() {
        return new StringBuilder()
                .append("Customers size: ")
                .append(customersSize)
                .append(", ")
                .append("Sales People size: ")
                .append(salesPeopleSize)
                .append(", ")
                .append("Best Sales Id: ")
                .append(bestSaleId)
                .append(", ")
                .append("Worst Salesperson name: ")
                .append(worstSalesperson)
                .toString();
    }
}
