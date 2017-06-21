package com.jer.autotextview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class AutoTextView extends TextView {

	public static final String TAG = "AutoTextView";

	/** 字幕滚动的速度 快，普通，慢 */
	public static final int SCROLL_SLOW = 0;
	public static final int SCROLL_NORM = 1;
	public static final int SCROLL_FAST = 2;

	/** 字幕内容 */
	private String mText;

	/** 字幕字体颜色 */
	private int mTextColor;

	/** 字幕字体大小 */
	private float mTextSize;

	private float offX = 0f;

	private float mStep = 0.5f;

	private Rect mRect = new Rect();

	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);;

	public AutoTextView(Context context) {
		super(context);
		setSingleLine(true);
	}

	public AutoTextView(Context context, AttributeSet attr) {
		super(context, attr);
		setSingleLine(true);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mText = getText().toString();
		mTextColor = getCurrentTextColor();
		mTextSize = getTextSize();
		mPaint.setColor(mTextColor);
		mPaint.setTextSize(mTextSize);
		mPaint.getTextBounds(mText, 0, mText.length(), mRect);
	};

	@Override
	protected void onDraw(Canvas canvas) {
		float x, y;
		x = getMeasuredWidth() - offX;
		y = getMeasuredHeight() / 2 + (mPaint.descent() - mPaint.ascent()) / 2;
		canvas.drawText(mText, x, y, mPaint);
		offX += mStep;
		if (offX >= getMeasuredWidth() + mRect.width()) {
			offX = 0f;
		}
		invalidate();
	}

	/**
	 * 设置字幕滚动的速度
	 */
	public void setScrollMode(int scrollMod) {
		if (scrollMod == SCROLL_SLOW) {
			mStep = 0.5f;
		} else if (scrollMod == SCROLL_NORM) {
			mStep = 1f;
		} else {
			mStep = 1.5f;
		}
	}

}
