package com.tonyyang.baserecyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
/**
 * https://github.com/alicx/LoadMoreRecyclerView的基础上进行修改，感谢原作者
 * 支持添加多个头部和尾部view，以及网格，瀑布流中全宽度view，支持列表，网格，瀑布流等布局
 * Create By Tony.Yang
 * 2016.5.20
 */
public class BaseRecycleView extends RecyclerView {
    /**
     * 布局类型
     */
    enum ITEM_TYPE {
        TYPE_NORMAL,//普通
        TYPE_GRID,//网格
        TYPE_STAGGERED//瀑布流
    }

    private ITEM_TYPE type = ITEM_TYPE.TYPE_NORMAL;//默认

    /**
     * item 类型
     */
    public final static int TYPE_NORMAL = 0;//普通item
    public final static int TYPE_GRID = 1;//网格item
    public final static int TYPE_STAGGERED = 2;//瀑布流item
    public final static int TYPE_HEADER = 3;//头部
    public final static int TYPE_FOOTER = 4;//尾部
    public final static int TYPE_SPECIAL = 5;//特殊item，在网格和瀑布流等布局需要全宽度item时调用

    private boolean mIsHeaderEnable = true;//是否显示头部
    private boolean mIsFooterEnable = true;//是否允许加载更多

    private int mHeaderCount = 0;//头部view数量
    private int mFooterCount = 0;//尾部view数量

    private List<View> mHeaderList = new ArrayList<>();//头部views
    private List<View> mFooterList = new ArrayList<>();//尾部views
    private List<Integer> mSpecialPositins = new ArrayList<>();//特殊view的positions

    private LinearLayout headerLayout;//尾部view
    private LinearLayout footerLayout;//头部view

    /**
     * 自定义实现了头部和底部的adapter
     */
    private AutoLoadAdapter mAutoLoadAdapter;
    private Adapter mItemAdapter;

    /**
     * 标记是否正在加载更多，防止再次调用加载更多接口
     */
    private boolean mIsLoadingMore;

    /**
     * 标记加载更多的position
     */
    private int mLoadMorePosition;

    /**
     * 加载更多的监听-业务需要实现加载数据
     */
    private LoadMoreListener mListener;

    public BaseRecycleView(Context context) {
        super(context);
        init();
    }

    public BaseRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseRecycleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * 初始化-添加滚动监听
     * <p/>
     * 回调加载更多方法，前提是
     * <pre>
     *    1、有监听并且支持加载更多：null != mListener && mIsFooterEnable
     *    2、目前没有在加载，正在上拉（dy>0），当前最后一条可见的view是否是当前数据列表的最好一条--及加载更多
     * </pre>
     */
    private void init() {
        super.addOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (null != mListener && mIsFooterEnable && !mIsLoadingMore && dy > 0) {
                    int lastVisiblePosition = getLastVisiblePosition();
                    if (lastVisiblePosition + 1 == mAutoLoadAdapter.getItemCount()) {
                        setLoadingMore(true);
                        mLoadMorePosition = lastVisiblePosition;
                        mListener.onLoadMore();
                    }
                }
            }
        });
    }

    /**
     * 设置布局类型
     */
    private void setItemType(ITEM_TYPE type) {
        this.type = type;
    }

    /**
     * 设置特殊item positions
     */
    public void setSpecialItem(List<Integer> specialItems) {
        this.mSpecialPositins = specialItems;
    }

    /**
     * 添加头部view
     */
    public void addHeaderView(View view) {
        mHeaderCount++;
        mHeaderList.add(view);
    }

    /**
     * 添加尾部view
     */
    public void addFooterView(View view) {
        mFooterCount++;
        mFooterList.add(view);
    }

    /**
     * 判断是否头部view
     */
    public boolean isHeaderPostion(int position) {
        if (position < mHeaderCount)
            return true;
        return false;
    }

    /**
     * 判断是否尾部view
     */
    public boolean isFooterPosition(int position) {
        if (position >= mHeaderCount + mItemAdapter.getItemCount())
            return true;
        return false;
    }

    /**
     * 判断是否特殊item
     */
    public boolean isSpecialItem(int position) {
        for (int p : mSpecialPositins) {
            if (p == position)
                return true;
        }
        return false;
    }

    /**
     * 获取header容器
     */
    private LinearLayout getHeaderLayout(ITEM_TYPE type) {
        if (headerLayout == null) {
            headerLayout = new LinearLayout(getContext());
            headerLayout.setLayoutParams(new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            headerLayout.setOrientation(LinearLayout.VERTICAL);
        } else {
            //避免设置了网格布局后layoutparams变化
            if (type == ITEM_TYPE.TYPE_STAGGERED && !(headerLayout.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams))
                headerLayout.setLayoutParams(new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        return headerLayout;
    }

    /**
     * 获取footer容器
     */
    private LinearLayout getFooterLayout(ITEM_TYPE type) {
        if (footerLayout == null) {
            footerLayout = new LinearLayout(getContext());
            footerLayout.setLayoutParams(new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            footerLayout.setOrientation(LinearLayout.VERTICAL);
        } else {
            if (type == ITEM_TYPE.TYPE_STAGGERED && !(footerLayout.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams))
                footerLayout.setLayoutParams(new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        return footerLayout;
    }

    /**
     * 设置加载更多的监听
     */
    public void setLoadMoreListener(LoadMoreListener listener) {
        mListener = listener;
    }

    @Override
    public void setLayoutManager(LayoutManager manager) {
        if (manager instanceof GridLayoutManager) {
            //头部，尾部，特殊item设置全宽度
            ((GridLayoutManager) manager).setSpanSizeLookup(new BaseSpanSizeLookup(((GridLayoutManager) manager).getSpanCount()));
            setItemType(ITEM_TYPE.TYPE_GRID);
        } else if (manager instanceof LinearLayoutManager) {
            setItemType(ITEM_TYPE.TYPE_NORMAL);
        } else if (manager instanceof StaggeredGridLayoutManager) {
            setItemType(ITEM_TYPE.TYPE_STAGGERED);
        }
        super.setLayoutManager(manager);
    }

    /**
     * 设置正在加载更多
     */
    public void setLoadingMore(boolean loadingMore) {
        this.mIsLoadingMore = loadingMore;
    }

    /**
     * 加载更多监听
     */
    public interface LoadMoreListener {
        void onLoadMore();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter != null) {
            mAutoLoadAdapter = new AutoLoadAdapter(adapter);
        }
        mItemAdapter = adapter;
        super.swapAdapter(mAutoLoadAdapter, true);
    }

    /**
     * 切换layoutManager
     */
    public void switchLayoutManager(LayoutManager layoutManager) {
        int firstVisiblePosition = getFirstVisiblePosition();
        setLayoutManager(layoutManager);
        //瀑布流不滚动到上一可见位置，避免滑动到头部后item重新排列
        if (!(layoutManager instanceof StaggeredGridLayoutManager))
            getLayoutManager().scrollToPosition(firstVisiblePosition);
    }

    /**
     * 获取第一条展示的位置
     */
    private int getFirstVisiblePosition() {
        int position;
        if (getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        } else if (getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        } else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) getLayoutManager();
            int[] lastPositions = layoutManager.findFirstVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMinPositions(lastPositions);
        } else {
            position = 0;
        }
        return position;
    }

    /**
     * 获得当前展示最小的position
     */
    private int getMinPositions(int[] positions) {
        int size = positions.length;
        int minPosition = Integer.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            minPosition = Math.min(minPosition, positions[i]);
        }
        return minPosition;
    }

    /**
     * 获取最后一条展示的位置
     */
    private int getLastVisiblePosition() {
        int position;
        if (getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
        } else if (getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
        } else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) getLayoutManager();
            int[] lastPositions = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMaxPosition(lastPositions);
        } else {
            position = getLayoutManager().getItemCount() - 1;
        }
        return position;
    }

    /**
     * 获得最大的位置
     */
    private int getMaxPosition(int[] positions) {
        int size = positions.length;
        int maxPosition = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            maxPosition = Math.max(maxPosition, positions[i]);
        }
        return maxPosition;
    }

    /**
     * 设置头部view是否展示
     */
    public void setHeaderEnable(boolean enable) {
        mAutoLoadAdapter.setHeaderEnable(enable);
    }

    /**
     * 设置是否支持自动加载更多
     */
    public void setAutoLoadMoreEnable(boolean autoLoadMore) {
        mIsFooterEnable = autoLoadMore;
    }

    /**
     * 通知更多的数据已经加载
     */
    public void notifyMoreFinish(boolean hasMore) {
        setAutoLoadMoreEnable(hasMore);
        getAdapter().notifyItemRemoved(mLoadMorePosition);
        mIsLoadingMore = false;
    }

    /**
     * 网格布局头部，尾部，特殊item设置全宽度
     */
    private class BaseSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

        private int mSpanSize;

        public BaseSpanSizeLookup(int size) {
            mSpanSize = size;
        }

        @Override
        public int getSpanSize(int position) {
            return (isHeaderPostion(position) || isFooterPosition(position) || isSpecialItem(position - mHeaderCount)) ? mSpanSize : 1;
        }
    }

    /**
     * 基础adapter
     */
    public class AutoLoadAdapter extends Adapter<ViewHolder> {

        /**
         * 数据adapter
         */
        private Adapter mInternalAdapter;

        /**
         * 自定义头部，尾部holder
         */
        private HeaderViewHolder headerHolder;
        private FooterViewHolder footerHolder;

        public AutoLoadAdapter(Adapter adapter) {
            mInternalAdapter = adapter;
        }

        public void setHeaderEnable(boolean enable) {
            mIsHeaderEnable = enable;
        }

        /**
         * 需要计算上加载更多和添加的头部俩个
         */
        @Override
        public int getItemCount() {
            int count = mInternalAdapter.getItemCount();
            if (mIsFooterEnable) count += mHeaderCount;
            if (mIsHeaderEnable) count += mFooterCount;
            return count;
        }

        @Override
        public int getItemViewType(int position) {
            if (mHeaderCount > 0 && position < mHeaderCount && mIsHeaderEnable) {
                return TYPE_HEADER;
            }
            if (position >= mHeaderCount && position < mHeaderCount + mInternalAdapter.getItemCount() && mIsFooterEnable) {
                if (isSpecialItem(position - mHeaderCount))
                    return TYPE_SPECIAL;
                else {
                    if (type == ITEM_TYPE.TYPE_GRID)
                        return TYPE_GRID;
                    else if (type == ITEM_TYPE.TYPE_STAGGERED)
                        return TYPE_STAGGERED;
                    else
                        return TYPE_NORMAL;
                }
            } else {
                return TYPE_FOOTER;
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_HEADER)
                return new HeaderViewHolder(getHeaderLayout(type));
            else if (viewType == TYPE_FOOTER)
                return new FooterViewHolder(getFooterLayout(type));
            else
                return mInternalAdapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (holder instanceof HeaderViewHolder) {
                headerHolder = (HeaderViewHolder) holder;
                if (type == ITEM_TYPE.TYPE_STAGGERED) {
                    if (!(headerHolder.contentLayout.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams))
                        headerHolder.contentLayout.setLayoutParams(new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    ((StaggeredGridLayoutManager.LayoutParams) headerHolder.itemView.getLayoutParams()).setFullSpan(true);
                }
                if (headerHolder.contentLayout.findViewWithTag(position) == null) {
                    if (mHeaderList.size() > 0 && position >= 0 && position < mHeaderList.size()) {
                        mHeaderList.get(position).setTag(position);
                        headerHolder.contentLayout.addView(mHeaderList.get(position));
                    }
                }
            } else if (holder instanceof FooterViewHolder) {
                footerHolder = (FooterViewHolder) holder;
                if (type == ITEM_TYPE.TYPE_STAGGERED) {
                    if (!(footerHolder.contentLayout.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams))
                        footerHolder.contentLayout.setLayoutParams(new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    ((StaggeredGridLayoutManager.LayoutParams) footerHolder.itemView.getLayoutParams()).setFullSpan(true);
                }
                int footPosition = position - mInternalAdapter.getItemCount() - mHeaderCount;
                if (footerHolder.contentLayout.findViewWithTag(footPosition) == null) {
                    if (mFooterList.size() > 0 && footPosition >= 0 && footPosition < mFooterList.size()) {
                        mFooterList.get(footPosition).setTag(footPosition);
                        footerHolder.contentLayout.addView(mFooterList.get(footPosition));
                    }
                }
            } else {
                if (type == ITEM_TYPE.TYPE_STAGGERED)
                    if (isSpecialItem(position - mHeaderCount)) {
                        if (!(holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams))
                            holder.itemView.setLayoutParams(new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, holder.itemView.getHeight()));
                        ((StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams()).setFullSpan(true);
                    }
                mInternalAdapter.onBindViewHolder(holder, position - mHeaderCount);
            }
        }

        public class FooterViewHolder extends ViewHolder {

            private LinearLayout contentLayout;

            public FooterViewHolder(View itemView) {
                super(itemView);
                contentLayout = (LinearLayout) itemView;
            }
        }

        public class HeaderViewHolder extends ViewHolder {

            private LinearLayout contentLayout;

            public HeaderViewHolder(View itemView) {
                super(itemView);
                contentLayout = (LinearLayout) itemView;
            }
        }

    }

    /**
     * item点击监听
     */
    public interface OnItemTouchListener {
        void onClickListener(int position);

        void onLongClickListener(int position);
    }

    /**
     * 基础viewholder，监听点击事件
     */
    public static class BaseViewHolder extends ViewHolder implements OnClickListener, OnLongClickListener {

        public int position;
        private OnItemTouchListener listener;

        public BaseViewHolder(View itemView, OnItemTouchListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null)
                listener.onClickListener(position);
        }

        @Override
        public boolean onLongClick(View v) {
            if (listener != null)
                listener.onLongClickListener(position);
            return true;
        }
    }
}