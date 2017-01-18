package com.lvshandian.asktoask.module.interaction;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lvshandian.asktoask.BaseFragment;
import com.lvshandian.asktoask.MainActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.entry.HuDongImg;
import com.lvshandian.asktoask.entry.HuDongItem;
import com.lvshandian.asktoask.module.interaction.activity.HuDongQuestTypeActivity;
import com.lvshandian.asktoask.module.interaction.activity.HuDongSearchActivity;
import com.lvshandian.asktoask.module.interaction.adapter.HuDongAdapter;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.JsonUtil;
import com.lvshandian.asktoask.utils.TextUtils;
import com.lvshandian.asktoask.utils.ToastUtils;
import com.lvshandian.asktoask.utils.UiUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tandong.sa.zUImageLoader.utils.L;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author: ldb on 2016/8/30 17:08.
 * email：lvdabing@lvshandian.com
 * company:北京绿闪电科技有限公司
 * details：互动
 */
public class InteractionFragment extends BaseFragment {
    @Bind(R.id.search)
    LinearLayout search;
    @Bind(R.id.pull_lv)
    PullToRefreshListView pullLv;
    @Bind(R.id.ll_root)
    LinearLayout llRoot;
    @Bind(R.id.xiding)
    LinearLayout xiding2;
    @Bind(R.id.tuijian2)
    TextView tuijian2;
    @Bind(R.id.tuijin_line2)
    View tuijinLine2;
    @Bind(R.id.jinri2)
    TextView jinri2;
    @Bind(R.id.jinri_line2)
    View jinriLine2;
    @Bind(R.id.hotdooor2)
    TextView hotdooor2;
    @Bind(R.id.hotdooor_line2)
    View hotdooorLine2;
    @Bind(R.id.xuanshang2)
    TextView xuanshang2;
    @Bind(R.id.xuanshang_line2)
    View xuanshangLine2;
    @Bind(R.id.unsolve2)
    TextView unsolve2;
    @Bind(R.id.unsolve_line2)
    View unsolveLine2;
    public static boolean isfrush = true;
    private boolean scrollFlag = false;// 标记是否滑动
    private int lastVisibleItemPosition = 0;// 标记上次滑动位置
    private ViewPager viewPager;  //图片轮播
    private LinearLayout llPoints; //图片里面的小圆点
    private LinearLayout xiding;
    private List<HuDongItem.DataBean.PageBean.DataBean2> list = new ArrayList<>();//互动里面的内容
    private HuDongAdapter adapter;  //互动adapter
    private int NUM = 4; // 每行显示个数
    private int LIEWIDTH;//每列宽度
    private int LIE = 12;//列数
    private DisplayMetrics dm;
    private String type = "推荐"; //互动类型
    private String realpageNum = "1";
    public List<HuDongImg.DataBean.QuestionTypesBean> listQuestionTypesBeans = new ArrayList<>();//互动里面的问题类型
    private String[] img = {
            "http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383291_6518.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383291_8239.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383290_9329.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383290_1042.jpg"};
    private TextView tuijian;
    private TextView jinri;
    private TextView hotdooor;
    private TextView xuanshang;
    private TextView unsolve;
    private GridView gvHudong;
    private boolean ismore = false; //是否加载更多
    private View tuijinLine, jinriLine, hotdooorLine, xuanshangLine, unsolveLine;
    public static List<String> listtypes = new ArrayList<>();//问题类型
    ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    private List<HuDongItem.DataBean.PageBean.DataBean2> reallist = new ArrayList<>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            HuDongItem.DataBean bean = null;
            switch (msg.what) {
                case RequestCode.HUDONGTUIJIAN_RECODE:  //互动推荐数据
                    bean = JsonUtil.json2Bean(json, HuDongItem.DataBean.class);
                    reallist = bean.pageBean.data;
                    dealDataBeab(bean);
                    break;
                case RequestCode.HUDONGTODAY_RECODE:  //互动今日数据
                    bean = JsonUtil.json2Bean(json, HuDongItem.DataBean.class);
                    reallist = bean.pageBean.data;
                    dealDataBeab(bean);
                    break;
                case RequestCode.HUDONGHOTDOOR:  //互动热门
                    bean = JsonUtil.json2Bean(json, HuDongItem.DataBean.class);
                    reallist = bean.pageBean.data;
                    dealDataBeab(bean);
                    break;
                case RequestCode.HUDONGGREAT_RECODE:  //互动高悬赏
                    bean = JsonUtil.json2Bean(json, HuDongItem.DataBean.class);
                    reallist = bean.pageBean.data;
                    dealDataBeab(bean);
                    break;
                case RequestCode.HUDONGUNSOLVED_RECODE:  //互动未解决
                    bean = JsonUtil.json2Bean(json, HuDongItem.DataBean.class);
                    reallist = bean.pageBean.data;
                    dealDataBeab(bean);
                    break;
                case RequestCode.HUDONGBANNER_RECODE://banner图地址
                    HuDongImg.DataBean beanImg = JsonUtil.json2Bean(json, HuDongImg.DataBean.class);
                    //公网上的话用这个
                    img = getImgsFromUrl(beanImg.getCarouselPictures());
                    dealLunBo();//处理轮播图
                    listQuestionTypesBeans = beanImg.getQuestionTypes();
                    for (int i = 0; i < listQuestionTypesBeans.size(); i++) {
                        listtypes.add(listQuestionTypesBeans.get(i).getQuestionTypeName());
                    }
                    dealGridView();//处理图片类型
                    break;
                default:
                    break;
            }


        }
    };

    /**
     * 处理答咖数据
     *
     * @param bean
     */

    private void dealDataBeab(HuDongItem.DataBean bean) {
        if (ismore) {
            list.addAll(reallist);
            adapter.setmDatas(list);
            adapter.notifyDataSetChanged();
        } else {
            list.clear();
            list = reallist;
            adapter = new HuDongAdapter(getContext(), list, R.layout.hudong_item_layout, bean, httpDatas, llRoot, ((MainActivity) getActivity()).pop);
            adapter.setmDatas(list);
            pullLv.setAdapter(adapter);
        }
    }


    /**
     * 得到从网络什获取到的图片地址
     *
     * @param list
     * @return
     */
    private String[] getImgsFromUrl(List<HuDongImg.DataBean.CarouselPicturesBean> list) {
        String[] img = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            img[i] = list.get(i).getCarouselUrl();
        }
        return img;
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                int currentItem = viewPager.getCurrentItem();
                currentItem++;
                viewPager.setCurrentItem(currentItem);
                startRool();
            }
        }

    };

    private void startRool() {
        handler.sendEmptyMessageDelayed(1, 3000);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.interaction_fragment_layout;
    }

    @Override
    protected void initListener() {
    }


    /**
     * 给gradview加监听
     */
    private void dealGridView() {
        getScreenDen();
        LIEWIDTH = dm.widthPixels / NUM;
        setValue();
        //互动问题类型请求图片
        gvHudong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String type = listQuestionTypesBeans.get(position).getQuestionTypeName();
                goToQuestType(type);
            }
        });

    }

    /**
     * 跳转到互动问题类型界面
     *
     * @param type
     */
    public void goToQuestType(String type) {
        Intent intent = new Intent(getActivity(), HuDongQuestTypeActivity.class);
        intent.putExtra(HuDongQuestTypeActivity.TYPE, type);
        startActivity(intent);
    }


    private void getScreenDen() {
        dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
    }

    private void setValue() {
        LIE = listQuestionTypesBeans.size();
        MyGridViewAdapter adapter = new MyGridViewAdapter(getActivity(), LIE);
        gvHudong.setAdapter(adapter);
        LayoutParams params = new LayoutParams(adapter.getCount() * LIEWIDTH,
                LayoutParams.WRAP_CONTENT);
        gvHudong.setLayoutParams(params);
        gvHudong.setColumnWidth(dm.widthPixels / NUM);
        gvHudong.setStretchMode(GridView.NO_STRETCH);
        int count = adapter.getCount();
        gvHudong.setNumColumns(count);
    }


    class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tuijian:
                    tuijian.setTextColor(Color.parseColor("#ea5514"));
                    jinri.setTextColor(Color.parseColor("#333333"));
                    hotdooor.setTextColor(Color.parseColor("#333333"));
                    xuanshang.setTextColor(Color.parseColor("#333333"));
                    unsolve.setTextColor(Color.parseColor("#333333"));

                    tuijian2.setTextColor(Color.parseColor("#ea5514"));
                    jinri2.setTextColor(Color.parseColor("#333333"));
                    hotdooor2.setTextColor(Color.parseColor("#333333"));
                    xuanshang2.setTextColor(Color.parseColor("#333333"));
                    unsolve2.setTextColor(Color.parseColor("#333333"));


                    jinriLine.setVisibility(View.INVISIBLE);
                    hotdooorLine.setVisibility(View.INVISIBLE);
                    xuanshangLine.setVisibility(View.INVISIBLE);
                    unsolveLine.setVisibility(View.INVISIBLE);
                    tuijinLine.setVisibility(View.VISIBLE);

                    jinriLine2.setVisibility(View.INVISIBLE);
                    hotdooorLine2.setVisibility(View.INVISIBLE);
                    xuanshangLine2.setVisibility(View.INVISIBLE);
                    unsolveLine2.setVisibility(View.INVISIBLE);
                    tuijinLine2.setVisibility(View.VISIBLE);

                    list.clear();
                    realpageNum = "1";

                    requesHttp();

                    break;

                case R.id.tuijian2:
                    tuijian.setTextColor(Color.parseColor("#ea5514"));
                    jinri.setTextColor(Color.parseColor("#333333"));
                    hotdooor.setTextColor(Color.parseColor("#333333"));
                    xuanshang.setTextColor(Color.parseColor("#333333"));
                    unsolve.setTextColor(Color.parseColor("#333333"));

                    tuijian2.setTextColor(Color.parseColor("#ea5514"));
                    jinri2.setTextColor(Color.parseColor("#333333"));
                    hotdooor2.setTextColor(Color.parseColor("#333333"));
                    xuanshang2.setTextColor(Color.parseColor("#333333"));
                    unsolve2.setTextColor(Color.parseColor("#333333"));


                    jinriLine.setVisibility(View.INVISIBLE);
                    hotdooorLine.setVisibility(View.INVISIBLE);
                    xuanshangLine.setVisibility(View.INVISIBLE);
                    unsolveLine.setVisibility(View.INVISIBLE);
                    tuijinLine.setVisibility(View.VISIBLE);

                    jinriLine2.setVisibility(View.INVISIBLE);
                    hotdooorLine2.setVisibility(View.INVISIBLE);
                    xuanshangLine2.setVisibility(View.INVISIBLE);
                    unsolveLine2.setVisibility(View.INVISIBLE);
                    tuijinLine2.setVisibility(View.VISIBLE);
                    xiding2.setVisibility(View.GONE);
                    list.clear();
                    realpageNum = "1";

                    requesHttp();

                    break;


                case R.id.jinri:
                    tuijian.setTextColor(Color.parseColor("#333333"));
                    jinri.setTextColor(Color.parseColor("#ea5514"));
                    hotdooor.setTextColor(Color.parseColor("#333333"));
                    xuanshang.setTextColor(Color.parseColor("#333333"));
                    unsolve.setTextColor(Color.parseColor("#333333"));

                    tuijinLine.setVisibility(View.INVISIBLE);
                    hotdooorLine.setVisibility(View.INVISIBLE);
                    xuanshangLine.setVisibility(View.INVISIBLE);
                    unsolveLine.setVisibility(View.INVISIBLE);
                    jinriLine.setVisibility(View.VISIBLE);


                    tuijian.setTextColor(Color.parseColor("#333333"));
                    jinri.setTextColor(Color.parseColor("#ea5514"));
                    hotdooor.setTextColor(Color.parseColor("#333333"));
                    xuanshang.setTextColor(Color.parseColor("#333333"));
                    unsolve.setTextColor(Color.parseColor("#333333"));

                    tuijinLine.setVisibility(View.INVISIBLE);
                    hotdooorLine.setVisibility(View.INVISIBLE);
                    xuanshangLine.setVisibility(View.INVISIBLE);
                    unsolveLine.setVisibility(View.INVISIBLE);
                    jinriLine.setVisibility(View.VISIBLE);


                    list.clear();
                    realpageNum = "1";
                    requesToday();


                    break;


                case R.id.jinri2:

                    tuijian.setTextColor(Color.parseColor("#333333"));
                    jinri.setTextColor(Color.parseColor("#ea5514"));
                    hotdooor.setTextColor(Color.parseColor("#333333"));
                    xuanshang.setTextColor(Color.parseColor("#333333"));
                    unsolve.setTextColor(Color.parseColor("#333333"));

                    tuijinLine.setVisibility(View.INVISIBLE);
                    hotdooorLine.setVisibility(View.INVISIBLE);
                    xuanshangLine.setVisibility(View.INVISIBLE);
                    unsolveLine.setVisibility(View.INVISIBLE);
                    jinriLine.setVisibility(View.VISIBLE);


                    tuijian2.setTextColor(Color.parseColor("#333333"));
                    jinri2.setTextColor(Color.parseColor("#ea5514"));
                    hotdooor2.setTextColor(Color.parseColor("#333333"));
                    xuanshang2.setTextColor(Color.parseColor("#333333"));
                    unsolve2.setTextColor(Color.parseColor("#333333"));

                    tuijinLine2.setVisibility(View.INVISIBLE);
                    hotdooorLine2.setVisibility(View.INVISIBLE);
                    xuanshangLine2.setVisibility(View.INVISIBLE);
                    unsolveLine2.setVisibility(View.INVISIBLE);
                    jinriLine2.setVisibility(View.VISIBLE);

                    xiding2.setVisibility(View.GONE);
                    list.clear();
                    realpageNum = "1";
                    requesToday();


                    break;


                case R.id.hotdooor:
                    tuijian.setTextColor(Color.parseColor("#333333"));
                    jinri.setTextColor(Color.parseColor("#333333"));
                    hotdooor.setTextColor(Color.parseColor("#ea5514"));
                    xuanshang.setTextColor(Color.parseColor("#333333"));
                    unsolve.setTextColor(Color.parseColor("#333333"));

                    tuijinLine.setVisibility(View.INVISIBLE);
                    jinriLine.setVisibility(View.INVISIBLE);
                    xuanshangLine.setVisibility(View.INVISIBLE);
                    unsolveLine.setVisibility(View.INVISIBLE);
                    hotdooorLine.setVisibility(View.VISIBLE);

                    tuijian2.setTextColor(Color.parseColor("#333333"));
                    jinri2.setTextColor(Color.parseColor("#333333"));
                    hotdooor2.setTextColor(Color.parseColor("#ea5514"));
                    xuanshang2.setTextColor(Color.parseColor("#333333"));
                    unsolve2.setTextColor(Color.parseColor("#333333"));

                    tuijinLine2.setVisibility(View.INVISIBLE);
                    jinriLine2.setVisibility(View.INVISIBLE);
                    xuanshangLine2.setVisibility(View.INVISIBLE);
                    unsolveLine2.setVisibility(View.INVISIBLE);
                    hotdooorLine2.setVisibility(View.VISIBLE);

                    xiding2.setVisibility(View.GONE);

                    list.clear();
                    realpageNum = "1";
                    requesHotDoor();

                    break;


                case R.id.hotdooor2:

                    tuijian.setTextColor(Color.parseColor("#333333"));
                    jinri.setTextColor(Color.parseColor("#333333"));
                    hotdooor.setTextColor(Color.parseColor("#ea5514"));
                    xuanshang.setTextColor(Color.parseColor("#333333"));
                    unsolve.setTextColor(Color.parseColor("#333333"));

                    tuijinLine.setVisibility(View.INVISIBLE);
                    jinriLine.setVisibility(View.INVISIBLE);
                    xuanshangLine.setVisibility(View.INVISIBLE);
                    unsolveLine.setVisibility(View.INVISIBLE);
                    hotdooorLine.setVisibility(View.VISIBLE);

                    tuijian2.setTextColor(Color.parseColor("#333333"));
                    jinri2.setTextColor(Color.parseColor("#333333"));
                    hotdooor2.setTextColor(Color.parseColor("#ea5514"));
                    xuanshang2.setTextColor(Color.parseColor("#333333"));
                    unsolve2.setTextColor(Color.parseColor("#333333"));

                    tuijinLine2.setVisibility(View.INVISIBLE);
                    jinriLine2.setVisibility(View.INVISIBLE);
                    xuanshangLine2.setVisibility(View.INVISIBLE);
                    unsolveLine2.setVisibility(View.INVISIBLE);
                    hotdooorLine2.setVisibility(View.VISIBLE);

                    xiding2.setVisibility(View.GONE);
                    list.clear();
                    realpageNum = "1";
                    requesHotDoor();

                    break;
                case R.id.xuanshang:
                    tuijian.setTextColor(Color.parseColor("#333333"));
                    jinri.setTextColor(Color.parseColor("#333333"));
                    hotdooor.setTextColor(Color.parseColor("#333333"));
                    xuanshang.setTextColor(Color.parseColor("#ea5514"));
                    unsolve.setTextColor(Color.parseColor("#333333"));

                    tuijinLine.setVisibility(View.INVISIBLE);
                    jinriLine.setVisibility(View.INVISIBLE);
                    hotdooorLine.setVisibility(View.INVISIBLE);
                    unsolveLine.setVisibility(View.INVISIBLE);
                    xuanshangLine.setVisibility(View.VISIBLE);


                    tuijian2.setTextColor(Color.parseColor("#333333"));
                    jinri2.setTextColor(Color.parseColor("#333333"));
                    hotdooor2.setTextColor(Color.parseColor("#333333"));
                    xuanshang2.setTextColor(Color.parseColor("#ea5514"));
                    unsolve2.setTextColor(Color.parseColor("#333333"));

                    tuijinLine2.setVisibility(View.INVISIBLE);
                    jinriLine2.setVisibility(View.INVISIBLE);
                    hotdooorLine2.setVisibility(View.INVISIBLE);
                    unsolveLine2.setVisibility(View.INVISIBLE);
                    xuanshangLine2.setVisibility(View.VISIBLE);

                    xiding2.setVisibility(View.GONE);
                    list.clear();
                    realpageNum = "1";
                    requestGreatNumber();

                    break;

                case R.id.xuanshang2:
                    tuijian.setTextColor(Color.parseColor("#333333"));
                    jinri.setTextColor(Color.parseColor("#333333"));
                    hotdooor.setTextColor(Color.parseColor("#333333"));
                    xuanshang.setTextColor(Color.parseColor("#ea5514"));
                    unsolve.setTextColor(Color.parseColor("#333333"));

                    tuijinLine.setVisibility(View.INVISIBLE);
                    jinriLine.setVisibility(View.INVISIBLE);
                    hotdooorLine.setVisibility(View.INVISIBLE);
                    unsolveLine.setVisibility(View.INVISIBLE);
                    xuanshangLine.setVisibility(View.VISIBLE);


                    tuijian2.setTextColor(Color.parseColor("#333333"));
                    jinri2.setTextColor(Color.parseColor("#333333"));
                    hotdooor2.setTextColor(Color.parseColor("#333333"));
                    xuanshang2.setTextColor(Color.parseColor("#ea5514"));
                    unsolve2.setTextColor(Color.parseColor("#333333"));

                    tuijinLine2.setVisibility(View.INVISIBLE);
                    jinriLine2.setVisibility(View.INVISIBLE);
                    hotdooorLine2.setVisibility(View.INVISIBLE);
                    unsolveLine2.setVisibility(View.INVISIBLE);
                    xuanshangLine2.setVisibility(View.VISIBLE);

                    xiding2.setVisibility(View.GONE);
                    list.clear();
                    realpageNum = "1";
                    requestGreatNumber();

                    break;


                case R.id.unsolve:
                    tuijian.setTextColor(Color.parseColor("#333333"));
                    jinri.setTextColor(Color.parseColor("#333333"));
                    hotdooor.setTextColor(Color.parseColor("#333333"));
                    xuanshang.setTextColor(Color.parseColor("#333333"));
                    unsolve.setTextColor(Color.parseColor("#ea5514"));

                    tuijinLine.setVisibility(View.INVISIBLE);
                    jinriLine.setVisibility(View.INVISIBLE);
                    hotdooorLine.setVisibility(View.INVISIBLE);
                    xuanshangLine.setVisibility(View.INVISIBLE);
                    unsolveLine.setVisibility(View.VISIBLE);


                    tuijian2.setTextColor(Color.parseColor("#333333"));
                    jinri2.setTextColor(Color.parseColor("#333333"));
                    hotdooor2.setTextColor(Color.parseColor("#333333"));
                    xuanshang2.setTextColor(Color.parseColor("#333333"));
                    unsolve2.setTextColor(Color.parseColor("#ea5514"));

                    tuijinLine2.setVisibility(View.INVISIBLE);
                    jinriLine2.setVisibility(View.INVISIBLE);
                    hotdooorLine2.setVisibility(View.INVISIBLE);
                    xuanshangLine2.setVisibility(View.INVISIBLE);
                    unsolveLine2.setVisibility(View.VISIBLE);


                    list.clear();
                    realpageNum = "1";
                    requestUnsolved();

                    break;
                case R.id.unsolve2:

                    tuijian.setTextColor(Color.parseColor("#333333"));
                    jinri.setTextColor(Color.parseColor("#333333"));
                    hotdooor.setTextColor(Color.parseColor("#333333"));
                    xuanshang.setTextColor(Color.parseColor("#333333"));
                    unsolve.setTextColor(Color.parseColor("#ea5514"));

                    tuijinLine.setVisibility(View.INVISIBLE);
                    jinriLine.setVisibility(View.INVISIBLE);
                    hotdooorLine.setVisibility(View.INVISIBLE);
                    xuanshangLine.setVisibility(View.INVISIBLE);
                    unsolveLine.setVisibility(View.VISIBLE);


                    tuijian2.setTextColor(Color.parseColor("#333333"));
                    jinri2.setTextColor(Color.parseColor("#333333"));
                    hotdooor2.setTextColor(Color.parseColor("#333333"));
                    xuanshang2.setTextColor(Color.parseColor("#333333"));
                    unsolve2.setTextColor(Color.parseColor("#ea5514"));

                    tuijinLine2.setVisibility(View.INVISIBLE);
                    jinriLine2.setVisibility(View.INVISIBLE);
                    hotdooorLine2.setVisibility(View.INVISIBLE);
                    xuanshangLine2.setVisibility(View.INVISIBLE);
                    unsolveLine2.setVisibility(View.VISIBLE);
                    xiding2.setVisibility(View.GONE);
                    list.clear();
                    realpageNum = "1";
                    requestUnsolved();

                    break;
                default:
                    break;
            }
        }
    }

    private int reqType = 1;
    ListView listview;


    @Subscribe
    public void onEventMainThread(String frush) {
        tuijian.setTextColor(Color.parseColor("#ea5514"));
        jinri.setTextColor(Color.parseColor("#333333"));
        hotdooor.setTextColor(Color.parseColor("#333333"));
        xuanshang.setTextColor(Color.parseColor("#333333"));
        unsolve.setTextColor(Color.parseColor("#333333"));

        tuijian2.setTextColor(Color.parseColor("#ea5514"));
        jinri2.setTextColor(Color.parseColor("#333333"));
        hotdooor2.setTextColor(Color.parseColor("#333333"));
        xuanshang2.setTextColor(Color.parseColor("#333333"));
        unsolve2.setTextColor(Color.parseColor("#333333"));


        jinriLine.setVisibility(View.INVISIBLE);
        hotdooorLine.setVisibility(View.INVISIBLE);
        xuanshangLine.setVisibility(View.INVISIBLE);
        unsolveLine.setVisibility(View.INVISIBLE);
        tuijinLine.setVisibility(View.VISIBLE);

        jinriLine2.setVisibility(View.INVISIBLE);
        hotdooorLine2.setVisibility(View.INVISIBLE);
        xuanshangLine2.setVisibility(View.INVISIBLE);
        unsolveLine2.setVisibility(View.INVISIBLE);
        tuijinLine2.setVisibility(View.VISIBLE);

        list.clear();
        realpageNum = "1";

        requesHttp();
        isfrush = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initialized() {
        EventBus.getDefault().register(this);//注册eventbus
        xiding2.setVisibility(View.GONE);
        listview = pullLv.getRefreshableView();
        View headerView = View.inflate(getContext(), R.layout.hudong_header,
                null);
        xiding = (LinearLayout) headerView.findViewById(R.id.xiding);
        viewPager = (ViewPager) headerView.findViewById(R.id.viewPager);
        llPoints = (LinearLayout) headerView.findViewById(R.id.ll_points);
        jinri = (TextView) headerView.findViewById(R.id.jinri);
        tuijian = (TextView) headerView.findViewById(R.id.tuijian);
        hotdooor = (TextView) headerView.findViewById(R.id.hotdooor);
        xuanshang = (TextView) headerView.findViewById(R.id.xuanshang);
        unsolve = (TextView) headerView.findViewById(R.id.unsolve);
        jinriLine = headerView.findViewById(R.id.jinri_line);
        tuijinLine = headerView.findViewById(R.id.tuijin_line);
        hotdooorLine = headerView.findViewById(R.id.hotdooor_line);
        xuanshangLine = headerView.findViewById(R.id.xuanshang_line);
        unsolveLine = headerView.findViewById(R.id.unsolve_line);
        gvHudong = (GridView) headerView.findViewById(R.id.gv_hudong);
        addListener();
        listview.addHeaderView(headerView);
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                switch (scrollState) {
                    // 当不滚动时
                    case SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
                        scrollFlag = false;
                        // 判断滚动到底部    (listview.getCount() - 5)
                        if (listview.getLastVisiblePosition() >= 5) {
//                            toTopBtn.setVisibility(View.VISIBLE);
                            xiding2.setVisibility(View.VISIBLE);
                        }
                        // 判断滚动到顶部
                        if (listview.getFirstVisiblePosition() == 1) {
                            xiding2.setVisibility(View.GONE);
                        }

                        break;
                    case SCROLL_STATE_TOUCH_SCROLL:// 滚动时

                        if (listview.getLastVisiblePosition() >= 5) {
//                            toTopBtn.setVisibility(View.VISIBLE);
                            xiding2.setVisibility(View.VISIBLE);
                        }
                        // 判断滚动到顶部
                        if (listview.getFirstVisiblePosition() == 1) {
                            xiding2.setVisibility(View.GONE);
                        }

                        scrollFlag = true;
                        break;
                    case SCROLL_STATE_FLING:// 是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
                        scrollFlag = false;
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // 当开始滑动且ListView底部的Y轴点超出屏幕最大范围时，显示或隐藏顶部按钮

                if (listview.getLastVisiblePosition() >= 5) {
//                            toTopBtn.setVisibility(View.VISIBLE);
                    xiding2.setVisibility(View.VISIBLE);
                }
                // 判断滚动到顶部
                if (listview.getFirstVisiblePosition() == 1) {
                    xiding2.setVisibility(View.GONE);
                }
//                if (scrollFlag) {
//                    if (firstVisibleItem > lastVisibleItemPosition) {// 上滑
//                        xiding2.setVisibility(View.VISIBLE);
//                    } else if (firstVisibleItem < lastVisibleItemPosition) {// 下滑
//                        xiding2.setVisibility(View.GONE);
//                    } else {
//                        return;
//                    }
//                    lastVisibleItemPosition = firstVisibleItem;
//                }
//                L.i("第一个item位置。。。" + firstVisibleItem);
//                L.i("可见的Item的数量 。。。"+visibleItemCount);
//                L.i("item的总数 。。。"+totalItemCount);

            }
        });

        pullLv.setMode(PullToRefreshBase.Mode.BOTH);

        pullLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {//下拉刷新
                String label = DateUtils.formatDateTime(
                        getContext(),
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                // 显示最后更新的时间
                refreshView.getLoadingLayoutProxy()
                        .setLastUpdatedLabel(label);
                requesImg();
                ismore = false;
                list.clear();
                realpageNum = 1 + "";
                switch (reqType) {
                    case 1:
                        requesHttp();
                        break;
                    case 2:
                        requesToday();
                        break;
                    case 3:
                        requesHotDoor();
                        break;
                    case 4:
                        requestGreatNumber();
                        break;
                    case 5:
                        requestUnsolved();
                        break;

                }

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {//上拉加载更多
                ILoadingLayout endLabels = pullLv.getLoadingLayoutProxy(false, true);
                endLabels.setPullLabel("加载更多");// 刚开始上拉时显示的提示
                ismore = true;
                int p = Integer.parseInt(realpageNum) + 1;
                realpageNum = "" + p;
                switch (reqType) {
                    case 1:
                        requesHttp();
                        break;
                    case 2:
                        requesToday();
                        break;
                    case 3:
                        requesHotDoor();
                        break;
                    case 4:
                        requestGreatNumber();
                        break;
                    case 5:
                        requestUnsolved();
                        break;

                }
            }
        });

        //请求问题类型图片
        requesImg();
        //请求互动推荐数据
        requesHttp();

    }

    private void addListener() {


        tuijian.setTextColor(Color.parseColor("#ea5514"));
        tuijian.setOnClickListener(new MyListener());
        jinri.setOnClickListener(new MyListener());
        hotdooor.setOnClickListener(new MyListener());
        xuanshang.setOnClickListener(new MyListener());
        unsolve.setOnClickListener(new MyListener());


        tuijian2.setOnClickListener(new MyListener());
        jinri2.setOnClickListener(new MyListener());
        hotdooor2.setOnClickListener(new MyListener());
        xuanshang2.setOnClickListener(new MyListener());
        unsolve2.setOnClickListener(new MyListener());


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHuDongSearch();
            }
        });

    }


    /**
     * 跳转到互动搜索界面
     */
    private void goToHuDongSearch() {
        Intent intent = new Intent(getActivity(), HuDongSearchActivity.class);
        startActivity(intent);
    }

    /**
     * 处理轮播图
     */

    private void dealLunBo() {
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter();
        viewPager.setAdapter(myPagerAdapter);
        viewPager.setCurrentItem(img.length * 10000);
        startRool();
        //初始化ViewPager轮播小圆圈
        llPoints.removeAllViews();
        for (int i = 0; i < img.length; i++) {
            ImageView point = new ImageView(getActivity());
            if (i == 0) {
                point.setImageResource(R.drawable.shape_xiao_yuan_quan_selected);
            } else {
                point.setImageResource(R.drawable.shape_xiao_yuan_quan_nomal);
            }
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                params.leftMargin = UiUtils.dp2px(getContext(), 8);
            }
            llPoints.addView(point, params);
        }
        //ViewPager的轮播效果(小圆圈改变)
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                position = position % img.length;
                for (int i = 0; i < llPoints.getChildCount(); i++) {
                    ImageView image = (ImageView) llPoints.getChildAt(i);
                    if (i == position) {
                        image.setImageResource(R.drawable.shape_xiao_yuan_quan_selected);
                    } else {
                        image.setImageResource(R.drawable.shape_xiao_yuan_quan_nomal);
                    }
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 请求轮播图和问题类型
     */
    private void requesImg() {
        map.clear();
        httpDatas.getData("互动轮播图和问题类型", Request.Method.POST, urlBuilder.HUDONG_IMAGE_URL, map, mHandler, RequestCode.HUDONGBANNER_RECODE);
    }

    /**
     * 请求互动推荐列表
     */
    private void requesHttp() {
        map.clear();
        map.put("pageNum", realpageNum);
        map.put("userId", Global.getUserId(getContext()));
        httpDatas.getData("互动推荐列表", Request.Method.POST, urlBuilder.HUDONG_TUIJIAN_URL, map, mHandler, RequestCode.HUDONGTUIJIAN_RECODE);
        reqType = 1;
        if (pullLv != null) {
            pullLv.post(new Runnable() {
                @Override
                public void run() {
                    pullLv.onRefreshComplete();
                }
            });
        }


    }

    /**
     * 请求互动今日数据
     */

    private void requesToday() {
        map.clear();
        map.put("pageNum", realpageNum);
        map.put("userId", Global.getUserId(getContext()));
        httpDatas.getData("互动今日数据", Request.Method.POST, urlBuilder.HUDONG_JINRI_URL, map, mHandler, RequestCode.HUDONGTODAY_RECODE);
        reqType = 2;
        pullLv.post(new Runnable() {
            @Override
            public void run() {
                pullLv.onRefreshComplete();
            }
        });
    }


    /**
     * 请求互动热门数据
     */

    private void requesHotDoor() {
        map.clear();
        map.put("pageNum", realpageNum);
        map.put("userId", Global.getUserId(getContext()));
        httpDatas.getData("互动热门数据", Request.Method.POST, urlBuilder.HUDONG_HOOT_DOOR, map, mHandler, RequestCode.HUDONGHOTDOOR);
        reqType = 3;
        pullLv.post(new Runnable() {
            @Override
            public void run() {
                pullLv.onRefreshComplete();
            }
        });
    }

    /**
     * 互动高悬赏
     */
    private void requestGreatNumber() {
        map.clear();
        map.put("pageNum", realpageNum);
        map.put("userId", Global.getUserId(getContext()));
        httpDatas.getData("互动高悬赏", Request.Method.POST, urlBuilder.HUDONG_GREAT_NUMBER, map, mHandler, RequestCode.HUDONGGREAT_RECODE);
        reqType = 4;
        pullLv.post(new Runnable() {
            @Override
            public void run() {
                pullLv.onRefreshComplete();
            }
        });
    }

    /**
     * 互动未解决
     */
    private void requestUnsolved() {
        map.clear();
        map.put("pageNum", realpageNum);
        map.put("userId", Global.getUserId(getContext()));
        httpDatas.getData("互动未解决", Request.Method.POST, urlBuilder.HUDONG_UNSOLVED, map, mHandler, RequestCode.HUDONGUNSOLVED_RECODE);
        reqType = 5;
        pullLv.post(new Runnable() {
            @Override
            public void run() {
                pullLv.onRefreshComplete();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        requesHttp();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        // 清除所有的callbacks和message
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * 互动问题类型Adapter
     */
    class MyGridViewAdapter extends BaseAdapter {
        int count;
        Context context;

        public MyGridViewAdapter(Context context, int count) {
            // TODO Auto-generated constructor stub
            this.context = context;
            this.count = count;

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return count;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stud
            ViewHolder holder;
            HuDongImg.DataBean.QuestionTypesBean bean = listQuestionTypesBeans.get(position);
            if (null == convertView) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.griditem, null);
                holder.tv = (TextView) convertView.findViewById(R.id.textView1);
                holder.iv = (ImageView) convertView.findViewById(R.id.imageView1);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv.setText(bean.getQuestionTypeName());
            String urlImg = bean.getExtend1();
            if (!TextUtils.isEmpty(urlImg)) {
                ImageLoader.getInstance().displayImage(urlImg, holder.iv);
            } else {
                if (position == 0) {
                    holder.iv.setImageResource(R.drawable.yikao);
                }
                if (position == 1) {
                    holder.iv.setImageResource(R.drawable.ganhuo);
                }
                if (position == 2) {
                    holder.iv.setImageResource(R.drawable.xueshu);
                }
                if (position == 3) {
                    holder.iv.setImageResource(R.drawable.jiaoyou);
                }
            }


            return convertView;
        }

        class ViewHolder {
            ImageView iv;
            TextView tv;
        }

    }

    /**
     * 轮播图的adapter
     */

    class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            ImageLoader.getInstance().displayImage(img[position % img.length], imageView);
            imageView.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            handler.removeCallbacksAndMessages(null);
                            break;
                        case MotionEvent.ACTION_UP:
                            handler.sendEmptyMessageDelayed(1, 2000);
                            break;
                        /**
                         * 当保持按下操作，并从此控件移到外层控件时，就会触发ACTION_CANCEL事件时，
                         * 当前的手势被中断，不会在接收它的记录
                         * 将它当做ACTION_UP事件进行处理比较好
                         */
                        case MotionEvent.ACTION_CANCEL:
                            handler.sendEmptyMessageDelayed(1, 2000);
                            break;

                        default:
                            break;
                    }
                    // true处理这个事件
                    return true;
                }
            });

            container.addView(imageView);
            return imageView;
        }
    }
}
