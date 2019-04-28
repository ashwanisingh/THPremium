package com.ns.activity;

import android.support.v7.widget.RecyclerView;

public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter {

    public static final int VT_NORMAL = 1;
    public static final int VT_DASHBOARD = 2;
    public static final int VT_BRIEFCASE = 3;
    public static final int VT_TRENDING = 4;
    public static final int VT_BOOKMARK = 5;
}
