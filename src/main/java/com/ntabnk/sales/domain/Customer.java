package com.ntabnk.sales.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Customer {
    private String cnpj;
    private String name;
    private String businessArea;
}
