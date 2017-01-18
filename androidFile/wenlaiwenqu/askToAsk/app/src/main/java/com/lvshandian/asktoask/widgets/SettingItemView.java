package com.lvshandian.asktoask.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvshandian.asktoask.R;

/**
 * author: newlq on 2016/9/1 17:08.
 * email： @lvshandian.com
 * company:北京绿闪电科技有限公司
 * details：我的页面 下部每个条目
 */
public class SettingItemView extends RelativeLayout {


    private int imgResId;
    private String strResId;

    private TextView tv_settingitem_text;
    private TextView tv_settingitem_righttext;

    private ImageView iv_settingitem_icon;
    private ImageView iv_settingitem_jiantouicon;

    public SettingItemView(Context context) {
        this(context, null, 0);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialise(context, attrs, defStyleAttr);
    }

    private void initialise(Context context, AttributeSet attrs, int defStyleAttr) {

        View rootView = View.inflate(context, R.layout.widgets_settingitemview, this);
        tv_settingitem_text = (TextView) rootView.findViewById(R.id.tv_settingitem_text);
        tv_settingitem_righttext = (TextView) rootView.findViewById(R.id.tv_settingitem_righttext);

        iv_settingitem_icon = (ImageView) rootView.findViewById(R.id.iv_settingitem_icon);
        iv_settingitem_jiantouicon = (ImageView) rootView.findViewById(R.id.iv_settingitem_jiantouicon);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SettingItemView, defStyleAttr, 0);

        imgResId = a.getResourceId(R.styleable.SettingItemView_itemicon, 0);
        strResId = a.getString(R.styleable.SettingItemView_itemtext);

        setItemText(strResId);
        setItemIcon(imgResId);

        a.recycle();
    }

    private void setItemText(String itemText){
        if (tv_settingitem_text != null)
            tv_settingitem_text.setText(itemText);
    }

    private void setItemIcon(int resId){
        if (iv_settingitem_icon != null && imgResId > 0)
            iv_settingitem_icon.setBackgroundResource(resId);
    }

    public void setRightText(String rightText){
        tv_settingitem_righttext.setText(rightText);
    }


}
