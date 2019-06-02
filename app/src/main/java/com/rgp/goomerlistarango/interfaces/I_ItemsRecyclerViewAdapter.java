package com.rgp.goomerlistarango.interfaces;

import androidx.annotation.NonNull;

import com.rgp.goomerlistarango.models.Item;

import java.util.List;

public interface I_ItemsRecyclerViewAdapter {
    void updateItems(@NonNull final List<Item> items);
}

