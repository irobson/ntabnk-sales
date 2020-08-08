package com.ntabnk.sales.domain;

import lombok.Builder;

import java.util.List;

@Builder
public class Sale {
    private String id;
    private List<Item> items;
    private String salespersonName;
}
