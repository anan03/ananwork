package com.lvshandian.asktoask.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/10/6.
 */


    public class MyListview extends ListView {

        public MyListview(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        /**
         * 设置不滚动
         */
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
        {
            int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                    View.MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);

        }

    }

