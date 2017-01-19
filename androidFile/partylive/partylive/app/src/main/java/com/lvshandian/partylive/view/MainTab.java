package com.lvshandian.partylive.view;


import com.lvshandian.partylive.R;
import com.lvshandian.partylive.moudles.group.GroupFragment;
import com.lvshandian.partylive.moudles.index.IndexPagerFragment;
import com.lvshandian.partylive.moudles.mine.MyInformationFragment;
import com.lvshandian.partylive.moudles.nearby.NearbyFragment;


/**
 * Created by Administrator on 2016/3/9.
 */
public enum  MainTab {
    INDEX(0, R.drawable.btn_tab_hot_background, 0, IndexPagerFragment.class),
    NEARBY(1, R.drawable.btn_tab_nearby_background, 1, NearbyFragment.class),
    LIVE(2, R.drawable.btn_tab_live_background, 2, MyInformationFragment.class),
    GROUP(3, R.drawable.btn_tab_group_background, 3, GroupFragment.class),
    HOME(4, R.drawable.btn_tab_home_background, 4, MyInformationFragment.class);
    private int idx;
    private int resName;
    private int resIcon;
    private Class<?> clz;

    private MainTab(int idx, int resIcon, int resName, Class<?> clz) {
        this.idx = idx;
        this.resIcon = resIcon;
        this.resName = resName;
        this.clz = clz;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getResName() {
        return resName;
    }

    public void setResName(int resName) {
        this.resName = resName;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }
}
