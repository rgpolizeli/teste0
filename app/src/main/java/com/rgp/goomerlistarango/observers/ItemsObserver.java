package com.rgp.goomerlistarango.observers;

import com.rgp.goomerlistarango.interfaces.I_DispatcherItemsToRespectiveAdapter;
import com.rgp.goomerlistarango.models.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.observers.DisposableObserver;

public class ItemsObserver extends DisposableObserver<Item[]> {

    private I_DispatcherItemsToRespectiveAdapter dispatcherItemsToRespectiveAdapter;


    public ItemsObserver(I_DispatcherItemsToRespectiveAdapter dispatcherItemsToRespectiveAdapter) {
        this.dispatcherItemsToRespectiveAdapter = dispatcherItemsToRespectiveAdapter;
    }


    @Override
    public void onNext(Item[] items) {
        if (items != null) {
            List<Item> itemsList = new ArrayList<>();
            Collections.addAll(itemsList, items);
            Map<String, List<Item>> itemsPerCategoryMap = splitPerCategoty(itemsList);
            dispatcherItemsToRespectiveAdapter.dispatchItemsToRespectiveAdapter(itemsPerCategoryMap);
        }
    }

    private Map<String, List<Item>> splitPerCategoty(List<Item> itemsList) {

        Map<String, List<Item>> itemsPerCategoryMap = new HashMap<>();
        String groupUpperCase;
        for (Item item : itemsList) {

            groupUpperCase = item.getGroup().toUpperCase();
            List<Item> itemsOfCategory;
            if (!itemsPerCategoryMap.containsKey(groupUpperCase)) {
                itemsOfCategory = new ArrayList<>();
                itemsPerCategoryMap.put(groupUpperCase, itemsOfCategory);
            } else {
                itemsOfCategory = itemsPerCategoryMap.get(groupUpperCase);
            }
            itemsOfCategory.add(item);
        }

        return itemsPerCategoryMap;
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onComplete() {
    }
}
