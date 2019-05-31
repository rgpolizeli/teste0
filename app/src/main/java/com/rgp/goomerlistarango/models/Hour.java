package com.rgp.goomerlistarango.models;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the opening hours of the restaurant on a certain weekday. The days of the week are represented from 1 to 7.
 */

public class Hour {
    private String from;
    private String to;
    private List<Integer> days;

    public Hour() {
        days = new ArrayList<>();
    }

    public List<Integer> getDays() {
        return this.days;
    }

    public void setWeekday(List<Integer> weekdays) {
        this.days = weekdays;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void addWeekday(int weekday) {
        if (weekday >= 1 && weekday <= 7) {
            this.days.add(weekday);
        }
    }
}
