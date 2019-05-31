package com.rgp.goomerlistarango.models;

import java.util.List;

public class Items {
    private List<Item> items;

    public void add(Item item){
        this.items.add(item);
    }

    public void remove(Item item){
        this.items.remove(item);
    }

    public int size(){
       return this.items.size();
    }
}
