package com.mocircle.devsuite.android.ui.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class ListBasedActionRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder, C>
        extends ActionRecyclerViewAdapter<T, VH, C> {

    protected List<T> data = new ArrayList<>();

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
