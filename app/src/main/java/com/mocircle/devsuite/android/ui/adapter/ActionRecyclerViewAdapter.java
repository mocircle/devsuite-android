package com.mocircle.devsuite.android.ui.adapter;

import android.support.v7.widget.RecyclerView;

public abstract class ActionRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder, C>
        extends RecyclerView.Adapter<VH> {

    protected C actionListener;

    public C getActionListener() {
        return actionListener;
    }

    public void setActionListener(C listener) {
        this.actionListener = listener;
    }

}
