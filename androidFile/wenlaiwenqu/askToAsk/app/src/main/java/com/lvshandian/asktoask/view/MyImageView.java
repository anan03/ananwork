package com.lvshandian.asktoask.view;

        import android.content.Context;
        import android.graphics.Canvas;
        import android.util.AttributeSet;
        import android.widget.ImageView;

/**
 * 重写ImageView，避免引用已回收的bitmap异常
 *
 * @author zwn
 *
 */
public class MyImageView extends ImageView {

    public MyImageView (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            super.onDraw(canvas);
        } catch (Exception e) {
            System.out
                    .println("MyImageView  -> onDraw() Canvas: trying to use a recycled bitmap");
        }
    }

}

