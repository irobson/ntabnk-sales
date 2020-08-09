package com.ntabnk.sales.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class SalesFileRaw {
    private final List<Customer> customers = new ArrayList<>();
    private final List<Sale> sales = new ArrayList<>();
    private final List<Salesperson> salespeople = new ArrayList<>();

    public boolean add(Customer customer) {
        return customers.add(customer);
    }

    public boolean add(Sale sale) {
        return sales.add(sale);
    }

    public boolean add(Salesperson salesperson) {
        return salespeople.add(salesperson);
    }
}
