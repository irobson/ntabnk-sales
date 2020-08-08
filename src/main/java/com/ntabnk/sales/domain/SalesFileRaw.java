package com.ntabnk.sales.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Slf4j
public class SalesFileRaw {
    private final List<Customer> customers = new ArrayList<>();
    private final List<Sale> sales = new ArrayList<>();
    private final List<Salesperson> salespeople = new ArrayList<>();

    public void add(Object object) {
        if (object instanceof Customer) {
            customers.add((Customer) object);
        } else if (object instanceof Sale) {
            sales.add((Sale) object);
        } else if (object instanceof Salesperson) {
            salespeople.add((Salesperson) object);
        } else {
            log.warn("Not supported object: {}.", object);
        }
    }
}
