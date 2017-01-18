package com.lvshandian.asktoask.module.answer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.android.volley.Request;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lvshandian.asktoask.BaseFragment;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.entry.Answer;
import com.lvshandian.asktoask.entry.AnswerSearchHome;
import com.lvshandian.asktoask.module.answer.activity.AnserSearchActivity;
import com.lvshandian.asktoask.module.answer.activity.AnswerApproveActivity;
import com.lvshandian.asktoask.module.answer.activity.AnswerSelectAddressActivity;
import com.lvshandian.asktoask.module.answer.activity.AnswerSelectSchoolActivity;
import com.lvshandian.asktoask.module.answer.adapter.AnswerAdapter;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.JsonUtil;
import com.lvshandian.asktoask.utils.UrlBuilder;
import com.lvshandian.asktoask.utils.ToastUtils;
import com.lvshandian.asktoask.utils.UiUtils;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import butterknife.Bind;

/**
 * author: ldb on 2016/8/30 17:08.
 * email：lvdabing@lvshandian.com
 * company:北京绿闪电科技有限公司
 * details：答咖首页
 */
public class AnswerFragment extends BaseFragment {
    @Bind(R.id.ll_search_answer)
    LinearLayout llSearchAnswer;
    @Bind(R.id.tv_answer_approve)
    TextView tvAnswerApprove;
    @Bind(R.id.vp_answer)
    ViewPager vpAnswer;
    @Bind(R.id.ll_points)
    LinearLayout llPoints;
    @Bind(R.id.tv_hot_master)
    TextView tvHotMaster;
    @Bind(R.id.ll_hot_master)
    LinearLayout llHotMaster;
    @Bind(R.id.tv_answer_university_place)
    TextView tvAnswerUniversityPlace;
    @Bind(R.id.place_linne)
    ImageView placeLinne;
    @Bind(R.id.ll_place)
    LinearLayout llPlace;
    @Bind(R.id.tv_answer_university)
    TextView tvAnswerUniversity;
    @Bind(R.id.university_line)
    ImageView universityLine;
    @Bind(R.id.ll_university)
    LinearLayout llUniversity;
    @Bind(R.id.answer_master_line)
    View answerMasterLine;
    @Bind(R.id.tv_show_nodata)
    TextView tvShowNodata;
    @Bind(R.id.lv_answer_chiefly)
    ListView lvAnswerChiefly;
    @Bind(R.id.pull_scroll)
    PullToRefreshScrollView pullScroll;
    private List<Answer.DataBean.ClientUsersBean> reallist;
    ConcurrentHashMap<String, String> map;//储存请求参数
    private final int recodeSchool = 101;
    private final int recodeAddress = 102;
    List<AnswerSearchHome.DataBean> listsearch;
    boolean isApprove = false;//是否可以去认证
    /**
     * 图片地址
     */
    private String[] img = {
            "http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383291_6518.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383291_8239.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383290_9329.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383290_1042.jpg"};
    /**
     * 图片轮播
     */
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (vpAnswer != null) {
                    int currentItem = vpAnswer.getCurrentItem();
                    currentItem++;
                    vpAnswer.setCurrentItem(currentItem);
                    startRool();
                }
            }
        }

    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {
                case RequestCode.ANSWER_CODE:  //答咖首页
                    Answer.DataBean bean = JsonUtil.json2Bean(json, Answer.DataBean.class);
                    reallist = bean.clientUsers;
                    //deal答咖界面的轮播图  这里是线上环境
                    img = getImgsFromUrl(bean.carouselPictures);
                    dealLunBo();
                    tvAnswerUniversity.setTextColor(getResources().getColor(R.color.black));
                    universityLine.setImageResource(R.drawable.right_boult);

                    tvAnswerUniversityPlace.setTextColor(getResources().getColor(R.color.black));
                    placeLinne.setImageResource(R.drawable.right_boult);

                    tvHotMaster.setTextColor(getResources().getColor(R.color.main));
                    answerMasterLine.setVisibility(View.VISIBLE);//显示最热答师线
                    if (reallist.size() == 0) {
                        lvAnswerChiefly.setVisibility(View.GONE);
                        tvShowNodata.setVisibility(View.VISIBLE);
                    } else {
                        tvShowNodata.setVisibility(View.GONE);
                        lvAnswerChiefly.setVisibility(View.VISIBLE);
                        AnswerAdapter adapter = new AnswerAdapter(getContext(), reallist, R.layout.answer_chiefly_item, httpDatas);
                        lvAnswerChiefly.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        Global.setListViewHeightBasedOnChildren(lvAnswerChiefly);
                    }
                    break;
                case RequestCode.ANSWER_SEARCH_PLACE://答咖搜索地区返回答师
                    //设置变色
                    tvAnswerUniversity.setTextColor(getResources().getColor(R.color.black));
                    universityLine.setImageResource(R.drawable.right_boult);
                    tvAnswerUniversityPlace.setTextColor(getResources().getColor(R.color.main));
                    placeLinne.setImageResource(R.drawable.select_right);
                    tvHotMaster.setTextColor(getResources().getColor(R.color.black));
                    answerMasterLine.setVisibility(View.INVISIBLE);//隐藏最热答师线
                    List<Answer.DataBean.ClientUsersBean> listsearchplace = JsonUtil.json2BeanList(json, Answer.DataBean.ClientUsersBean.class);
                    if (listsearchplace.size() == 0) {
                        lvAnswerChiefly.setVisibility(View.GONE);
                        tvShowNodata.setVisibility(View.VISIBLE);
                    } else {
                        lvAnswerChiefly.setVisibility(View.VISIBLE);
                        tvShowNodata.setVisibility(View.GONE);
                        AnswerAdapter adapter = new AnswerAdapter(getContext(), listsearchplace, R.layout.answer_chiefly_item, httpDatas);
                        lvAnswerChiefly.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        Global.setListViewHeightBasedOnChildren(lvAnswerChiefly);
                    }

                    break;

                case RequestCode.ANSWER_SEARCH_SCHOOL://答咖搜索学校返回答师
                    tvAnswerUniversity.setTextColor(getResources().getColor(R.color.main));
                    universityLine.setImageResource(R.drawable.select_right);
                    tvAnswerUniversityPlace.setTextColor(getResources().getColor(R.color.black));
                    placeLinne.setImageResource(R.drawable.right_boult);
                    tvHotMaster.setTextColor(getResources().getColor(R.color.black));
                    answerMasterLine.setVisibility(View.INVISIBLE);//隐藏最热答师线
                    List<Answer.DataBean.ClientUsersBean> listschool = JsonUtil.json2BeanList(json, Answer.DataBean.ClientUsersBean.class);
                    if (listschool.size() == 0) {
                        lvAnswerChiefly.setVisibility(View.GONE);
                        tvShowNodata.setVisibility(View.VISIBLE);
                    } else {
                        lvAnswerChiefly.setVisibility(View.VISIBLE);
                        tvShowNodata.setVisibility(View.GONE);
                        AnswerAdapter adapter = new AnswerAdapter(getContext(), listschool, R.layout.answer_chiefly_item, httpDatas);
                        lvAnswerChiefly.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        Global.setListViewHeightBasedOnChildren(lvAnswerChiefly);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.answer_fragment_layout;
    }

    /**
     * 给界面的控件加监听
     */
    @Override
    protected void initListener() {
        tvAnswerUniversity.setOnClickListener(new MyAnswerListener());//答咖去选择大学
        llSearchAnswer.setOnClickListener(new MyAnswerListener());//答咖搜素
        tvAnswerUniversityPlace.setOnClickListener(new MyAnswerListener());//答咖去地区
        tvAnswerApprove.setOnClickListener(new MyAnswerListener());//认证监听
        pullScroll.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//只支持下拉
        llPlace.setOnClickListener(new MyAnswerListener());//答咖界面的答师地方监听
        llUniversity.setOnClickListener(new MyAnswerListener());//答咖界面的答师学校监听
        pullScroll.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {//下拉刷新
                String label = DateUtils.formatDateTime(
                        getContext(),
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                // 显示最后更新的时间
                refreshView.getLoadingLayoutProxy()
                        .setLastUpdatedLabel(label);
                httpAnswerCHiefly();//请求答师数据
                pullScroll.onRefreshComplete();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {//上拉加载更多

            }
        });


        tvHotMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpAnswerCHiefly();//请求答咖首页数据
            }
        });


    }

    /**
     * 得到从网络什获取到的图片地址
     * 转换为数组
     *
     * @param list
     * @return
     */
    private String[] getImgsFromUrl(List<Answer.DataBean.CarouselPicturesBean> list) {

        String[] img = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            img[i] = list.get(i).carouselUrl;
        }

        return img;
    }

    /**
     * 处理轮播图
     */
    private void dealLunBo() {
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter();
        vpAnswer.setAdapter(myPagerAdapter);
        vpAnswer.setCurrentItem(img.length * 10000);
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
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                params.leftMargin = UiUtils.dp2px(getContext(), 8);
            }
            llPoints.addView(point, params);
        }
        //ViewPager的轮播效果(小圆圈改变)
        vpAnswer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
     * 开始轮播
     */
    private void startRool() {
        handler.sendEmptyMessageDelayed(1, 3000);
    }


    @Override
    protected void initialized() {
        map = new ConcurrentHashMap<>();
        lvAnswerChiefly.setFocusable(false);
        //请求答咖首页数据
        httpAnswerCHiefly();
    }


    /**
     * 答咖首页数据 答师列表
     */
    private void httpAnswerCHiefly() {
        map.clear();
        map.put("attentorId", Global.getUserId(getContext()));
        httpDatas.getData("答咖界面轮播图和列表数据", Request.Method.POST, urlBuilder.ANSWER_CHIEFLY_URL, map, mHandler, RequestCode.ANSWER_CODE);
    }

    /**
     * 答咖搜索地区
     * //    http://192.168.0.121:8080/wlwq/mennakagahapati/searchByAreaKey.do?area=天
     */
    private void httpAnswerSearchPlace(String strplace) {
        map.clear();
        map.put("attentorId", Global.getUserId(getContext()));
        map.put("area", strplace);
        httpDatas.getData("答咖搜索地区返回答师", Request.Method.POST, urlBuilder.ANSWER_PLACE_URL, map, mHandler, RequestCode.ANSWER_SEARCH_PLACE);
    }


    /**
     * 答咖搜索学校
     * //   //    http://192.168.0.121:8080/wlwq/mennakagahapati/searchBySchoolKey.do?userSchool=辉
     */
    private void httpAnswerSearchSchool(String strschool) {
        map.clear();
        map.put("attentorId", Global.getUserId(getContext()));
        map.put("userSchool", strschool);
        httpDatas.getData("答咖搜索学校返回答师", Request.Method.POST, urlBuilder.ANSWER_SCHOOL_URL, map, mHandler, RequestCode.ANSWER_SEARCH_SCHOOL);
    }
    class MyAnswerListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_search_answer:
                    goToAnswerSearchActivity();//答咖搜索
                    break;
                case R.id.ll_place:
                    //答咖认证去选择地区
                    goToAnswerSelectAddressActivity();
                    break;
                case R.id.tv_answer_university_place:
                    //答咖认证去选择地区
                    goToAnswerSelectAddressActivity();
                    break;
                case R.id.ll_university:
                    //答咖认证去选择学校
                    goToAnswerSelectSchoolActivity();
                    break;
                case R.id.tv_answer_university:
                    //答咖认证去选择学校
                    goToAnswerSelectSchoolActivity();
                    break;
                case R.id.tv_answer_approve:

                    String strapprove=(String)Global.getParam(getContext(),"isappprove","ww");

                    if(Global.isLogin(getContext())){
                        if("1".equals(strapprove)){
                            ToastUtils.showSnackBar(getActivity().getWindow().getDecorView().getRootView(),"您已经是答师，请勿重复认证");
                        }else{
                            if (goToApproveCurrent()) {//看看是否可以去认证
                                goToApprove();
                            }
                        }

                    } else {
                        ToastUtils.showSnackBar(getActivity().getWindow().getDecorView().getRootView(),"请先去登陆");
                    }

                default:
                    break;
            }
        }
    }

    /**
     * 答咖认证时
     */
    private boolean goToApproveCurrent() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("userId", Global.getUserId(getContext()));
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST,
                UrlBuilder.serverUrl + urlBuilder.ANSWER_APPROVE_CURRENT_URL,
                params,
                new RequestCallBack<String>() {

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        try {
                            JSONObject object = new JSONObject(result);
                            String str = object.getString("msg");
                            if ("可以认证!".equals(str)) {
                                isApprove = true;
                            } else if ("认证中".equals(str)) {
                                isApprove = false;
                                ToastUtils.showSnackBar(getActivity().getWindow().getDecorView().getRootView(), "认证中");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {


                    }
                });
        return isApprove;
    }


    /**
     * 答咖去选择学校
     */

    private void goToAnswerSelectSchoolActivity() {
        Intent intent = new Intent(new Intent(getContext(), AnswerSelectSchoolActivity.class));
        startActivityForResult(intent, recodeSchool);
    }

    /**
     * 答咖去选择地区
     */

    private void goToAnswerSelectAddressActivity() {
        Intent intent = new Intent(new Intent(getContext(), AnswerSelectAddressActivity.class));
        startActivityForResult(intent, recodeAddress);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case recodeSchool:
                if (AnswerSelectSchoolActivity.isissave) {
                    AnswerSelectSchoolActivity.isissave=false;
                    //得到选择学校后返回的学校值
                    tvAnswerUniversity.setText(AnswerSelectSchoolActivity.school);
                    httpAnswerSearchSchool(AnswerSelectSchoolActivity.school);
                }
                break;
            case recodeAddress:
                if (AnswerSelectAddressActivity.isissave) {
                    AnswerSelectAddressActivity.isissave=false;
                    //得到选择地区后返回的学校值
                    tvAnswerUniversityPlace.setText(AnswerSelectAddressActivity.place);
                    httpAnswerSearchPlace(AnswerSelectAddressActivity.place);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 去答咖搜索界面
     */

    private void goToAnswerSearchActivity() {
        startActivity(new Intent(getContext(), AnserSearchActivity.class));
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
            BitmapUtils bitmapUtils = new BitmapUtils(getActivity());
            bitmapUtils.display(imageView, img[position % img.length]);
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

    /**
     * 答咖去认证
     */
    private void goToApprove() {
        Intent intent = new Intent(getActivity(), AnswerApproveActivity.class);
        startActivity(intent);
    }


}
