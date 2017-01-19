package com.netease.nim.uikit.common.adapter;

import android.content.Context;

public interface TAdapterDelegate {

	public int getViewTypeCount();

	public Class<? extends TViewHolder> viewHolderAtPosition(int position, Context context);

	public boolean enabled(int position);
}