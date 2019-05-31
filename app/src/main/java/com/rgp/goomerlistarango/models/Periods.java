package com.rgp.goomerlistarango.models;

import java.util.ArrayList;
import java.util.List;

public class Periods {
    private List<Period> periods;

    public Periods(){
        this.periods = new ArrayList<>();
    }

    public void add(Period period){
        this.periods.add(period);
    }

    public void remove(Period period){
        this.periods.remove(period);
    }

    public int size(){
        return this.periods.size();
    }
}
