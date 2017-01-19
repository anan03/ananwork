package com.lvshandian.partylive.widget;

/**
 * Created by Administrator on 2016/8/26.
 */
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.GridView;
import android.widget.LinearLayout;

public class HeadGridView extends LinearLayout{
    private static final String TAG = HeadGridView.class.getSimpleName();
    private LinearLayout mHeadLayout;
    private GridView mGridView;
    private int mHeadHeight;
    private boolean mIsBeingDragged;
    private float mLastMotionY;
    private float mTouchSlop;
    private boolean mGridViewFocused = true;
    private State mState = State.getDefaultMode();

    public HeadGridView(Context context) {
        this(context, null);
    }

    public HeadGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initContentView(context);
    }

    private void initContentView(Context context) {
        ViewConfiguration config = ViewConfiguration.get(context);
        mTouchSlop = config.getScaledTouchSlop();
        setOrientation(VERTICAL);
        GridView gridView = new GridView(context);
        LayoutParams gridParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        gridView.setNumColumns(2);
        gridView.setLayoutParams(gridParams);
        mGridView = gridView;
        initGridView(gridView);
        addView(gridView);

        LinearLayout headLayout = new LinearLayout(context);
        LayoutParams headParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        headLayout.setLayoutParams(headParams);
        mHeadLayout = headLayout;
        initHead(headLayout);
        addView(headLayout,0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        int gridWidthSpec = MeasureSpec.makeMeasureSpec(parentWidth, MeasureSpec.EXACTLY);
        int gridHeightSpec = MeasureSpec.makeMeasureSpec(parentHeight, MeasureSpec.EXACTLY);
        mGridView.measure(gridWidthSpec, gridHeightSpec);
        setMeasuredDimension(parentWidth, parentHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mHeadHeight = getChildAt(0).getMeasuredHeight();
    }

    protected void initGridView(GridView gridView) {

    }

    protected void initHead(LinearLayout headLayout) {

    }

    @Override
    public final boolean onInterceptTouchEvent(MotionEvent event) {
        Log.d(TAG, "currentState = "+mState+" event = "+event.getAction()+" mIsBeingDragged = "+mIsBeingDragged);
        final int action = event.getAction();

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mIsBeingDragged = false;
            return false;
        }

        if (mIsBeingDragged) {
            return true;
        }

        switch (action) {
            case MotionEvent.ACTION_MOVE:
                final float y = event.getY();
                final float absDiff = Math.abs(y - mLastMotionY);;

                if (absDiff > mTouchSlop) {
                    mLastMotionY = y;
                    mIsBeingDragged = true;
                    if (mState != State.Head_Visible) {
                        mGridViewFocused = false;
                    }
                }
                break;
            case MotionEvent.ACTION_DOWN:
                mLastMotionY = event.getY();
                mIsBeingDragged = false;
                break;
            default:
                break;
        }
        return mIsBeingDragged;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN && event.getEdgeFlags() != 0) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                if (mIsBeingDragged) {
                    pullEvent(event);
                    return true;
                }
                break;
            }
            case MotionEvent.ACTION_DOWN:
                mLastMotionY = event.getY();
                return true;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mIsBeingDragged) {
                    mIsBeingDragged = false;
                }
                return true;
        }
        return false;
    }

    private void dispatchDownToGridView(MotionEvent e, float downY) {
        int action = e.getAction();
        MotionEvent de = MotionEvent.obtain(e);
        de.setLocation(de.getX(), downY);
        de.setAction(MotionEvent.ACTION_DOWN);
        mGridView.dispatchTouchEvent(de);
        e.setAction(action);
    }

    private void pullEvent(MotionEvent e) {
        if (!mGridViewFocused) {
            dispatchDownToGridView(e, mLastMotionY);
            mGridViewFocused = true;
        }

        float y = e.getY();
        int scrollValue = Math.round(mLastMotionY - y);
        if (mState == State.Bottom_Top) {
            if (scrollValue > 0){
                if (mState == State.Bottom_Top) {
                    dispatchDownToGridView(e, mLastMotionY);
                }
                mGridView.dispatchTouchEvent(e);
                mState = State.Bottom_Medium;
            } else {
                mState = State.Head_Visible;
            }
        }

        if (mState.canHeadViewScroll()) {
            int currentScrollY = getScrollY();
            int maxScroolY = mHeadHeight - currentScrollY;
            if (scrollValue > 0) { //向上滑动
                if (maxScroolY < scrollValue) {
                    scrollValue = maxScroolY;
                    mState = State.Bottom_Top;
                }
                scrollBy(0, scrollValue);
            } else { //向下滑动
                if (currentScrollY > 0) {
                    if (currentScrollY < - scrollValue) {
                        scrollValue = - currentScrollY;
                    }
                    scrollBy(0, scrollValue);
                }
            }
        } else {
            mGridView.dispatchTouchEvent(e);
            if (scrollValue < 0) {
                int top = mGridView.getChildAt(0).getTop();
                if (top == 0) {
                    mState = State.Bottom_Top;
                }
            }
        }
        mLastMotionY = y;
    }

    public static enum State{
        Head_Visible(0x01),
        Bottom_Top(0x02),
        Bottom_Bottom(0x03),
        Bottom_Medium(0x04);

        private int id;

        private State(int id) {
            this.id = id;
        }

        public int getId(){
            return id;
        }

        public static State getDefaultMode(){
            return State.Head_Visible;
        }

        static State mapState(final int id) {
            for (State value : State.values()) {
                if (id == value.getId()) {
                    return value;
                }
            }
            return getDefaultMode();
        }

        boolean canHeadViewScroll() {
            return this == Head_Visible || this == Bottom_Top;
        }
    }
}
