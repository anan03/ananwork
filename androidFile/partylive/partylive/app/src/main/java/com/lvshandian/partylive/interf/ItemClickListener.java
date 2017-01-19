package com.lvshandian.partylive.interf;

import android.view.View;
import android.widget.AdapterView;

import com.netease.nimlib.sdk.msg.model.IMMessage;

/**
 * Created by sll on 2016/11/24.
 */

public interface ItemClickListener {
    void onClickItem(AdapterView<?> adapterView, View view, IMMessage iMMessage, long l);
}
