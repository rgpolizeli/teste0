package com.rgp.goomerlistarango.models;

import java.util.ArrayList;
import java.util.List;

public class BusinessHours {
    private List<BusinessHour> businessHours;

    public BusinessHours(){
        businessHours = new ArrayList<>();
    }

    private void add(BusinessHour businessHour){
        this.businessHours.add(businessHour);
    }

    private void remove(BusinessHour businessHour){
    }

    private int size(){
        return this.businessHours.size();
    }
}
