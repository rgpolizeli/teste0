package com.rgp.goomerlistarango.models;

/**
 * This class represents a sale. Each sale has an business hours, being able to be in different days and different periods of the day.
 */
public class Sale {
    private String description;
    private double price;
    private BusinessHours businessHours;

    public Sale(String description, double price, BusinessHours businessHours) {
        this.description = description;
        this.price = price;
        this.businessHours = businessHours;
    }
}
