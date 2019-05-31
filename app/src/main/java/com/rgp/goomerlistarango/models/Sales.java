package com.rgp.goomerlistarango.models;

import java.util.List;

public class Sales {

    private List<Sale> sales;

    public void add(Sale sale){
        this.sales.add(sale);
    }

    public void remove(Sale sale){
        this.sales.remove(sale);
    }

    public int size(){
        return this.sales.size();
    }

}
