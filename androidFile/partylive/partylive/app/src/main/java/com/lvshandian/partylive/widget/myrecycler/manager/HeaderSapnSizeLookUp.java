package com.lvshandian.partylive.widget.myrecycler.manager;

import android.support.v7.widget.GridLayoutManager;

import com.lvshandian.partylive.widget.myrecycler.adapter.RefreshRecyclerViewAdapter;

/**
 * Created by Syehunter on 2015/11/22.
 */
public class HeaderSapnSizeLookUp extends GridLayoutManager.SpanSizeLookup {

    private RefreshRecyclerViewAdapter mAdapter;
    private int mSpanSize;

    public HeaderSapnSizeLookUp(RefreshRecyclerViewAdapter adapter, int spanSize){
        this.mAdapter = adapter;
        this.mSpanSize = spanSize;
    }

    @Override
    public int getSpanSize(int position) {
        boolean isHeaderOrFooter = mAdapter.isHeader(position) || mAdapter.isFooter(position);
        return isHeaderOrFooter ? mSpanSize : 1;
    }
}
