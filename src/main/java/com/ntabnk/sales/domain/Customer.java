package com.ntabnk.sales.domain;

import lombok.Builder;

@Builder
public class Customer {
    private String cnpj;
    private String name;
    private String businessArea;
}
