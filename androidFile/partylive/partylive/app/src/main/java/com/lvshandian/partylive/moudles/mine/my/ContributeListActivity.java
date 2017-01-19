package com.lvshandian.partylive.moudles.mine.my;

import android.view.View;
import android.widget.ListView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by gjj on 2016/11/22.
 * 贡献榜
 */

public class ContributeListActivity extends BaseActivity {
    @Bind(R.id.lv_list)
    ListView lvList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contribute_list;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initialized() {
        initTitle("", "贡献榜", null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_titlebar_left:
                defaultFinish();
                break;
        }
    }
}
