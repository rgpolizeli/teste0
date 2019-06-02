package com.rgp.goomerlistarango.interfaces;

import com.rgp.goomerlistarango.models.Item;

import java.util.List;
import java.util.Map;

public interface I_DispatcherItemsToRespectiveAdapter {
    void dispatchItemsToRespectiveAdapter(Map<String, List<Item>> itemsByGroup);
}
