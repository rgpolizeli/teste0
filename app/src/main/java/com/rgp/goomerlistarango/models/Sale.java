package com.rgp.goomerlistarango.models;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a sale. Each sale contains the periods of its duration as hours.
 */
public class Sale {
    private String description;
    private double price;
    private List<Hour> hours;

    public Sale() {
        this.hours = new ArrayList<>();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void addHour(Hour hour) {
        this.hours.add(hour);
    }

    public List<Hour> getHours() {
        return this.hours;
    }
}
