package com.lvshandian.asktoask.module.setting;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.entry.DiscountCoupon;
import com.lvshandian.asktoask.module.user.adapter.UserYouHuiQuanAdapter;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.MethodUtils;
import com.lvshandian.asktoask.utils.ToastUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的模块中的优惠劵
 * 更改优惠券
 */
public class CouponActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @Bind(R.id.ll)
    View ll;
    @Bind(R.id.tv_titlebar_centertext)
    TextView tvTitlebarCentertext;
    @Bind(R.id.ll_titlebar_zuojiantou)
    LinearLayout llTitlebarZuojiantou;
    @Bind(R.id.tv_titlebar_righttext)
    TextView tvTitlebarRighttext;
    @Bind(R.id.et_youhuiquan_duihuanma)
    EditText etYouhuiquanDuihuanma;
    @Bind(R.id.tv_youhuiquan_goduihuan)
    TextView tvYouhuiquanGoduihuan;
    @Bind(R.id.lv_youhuiquan_youhuiquanlist)
    ListView lvYouhuiquanYouhuiquanlist;
    @Bind(R.id.tv_no_youhuijuan)
    TextView tvNoYouhuijuan;
    private UserYouHuiQuanAdapter adapter;
    private List<DiscountCoupon> mAdapterDatas = new ArrayList<>();//优惠劵

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {
                case RequestCode.YOUHUIQUAN_YOUHUIQUANLIEBIAO_REQUESTCODE:  //获取到优惠券列表
                    mAdapterDatas = JSON.parseArray(json, DiscountCoupon.class);
                    if (mAdapterDatas.size() == 0) {
                        tvNoYouhuijuan.setVisibility(View.VISIBLE);//暂无数据
                        lvYouhuiquanYouhuiquanlist.setVisibility(View.GONE);
                    } else {
                        tvNoYouhuijuan.setVisibility(View.GONE);//展示优惠劵
                        lvYouhuiquanYouhuiquanlist.setVisibility(View.VISIBLE);
                        adapter = new UserYouHuiQuanAdapter(getContext(), mAdapterDatas, R.layout.user_item_youhuiquan);//优惠劵适配器
                        adapter.setmDatas(mAdapterDatas);
                        lvYouhuiquanYouhuiquanlist.setAdapter(adapter);
                    }
                    break;
                case RequestCode.YOUHUIQUAN_DUIHUANYOUHUIQUAN_REQUESTCODE:  //兑换优惠券
                    getYouHuiQuanLieBiao();//优惠劵列表
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_youhuiquan_layout;
    }

    @Override
    protected void initListener() {
        llTitlebarZuojiantou.setOnClickListener(this);
        tvYouhuiquanGoduihuan.setOnClickListener(this);
        lvYouhuiquanYouhuiquanlist.setOnItemClickListener(this);
    }

    @Override
    protected void initialized() {
        tvTitlebarCentertext.setText(R.string.mine_itemtext_youhuiquan);//优惠劵标题

        getYouHuiQuanLieBiao();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_youhuiquan_goduihuan:
                duihuanduihuanma();//兑换码
                break;
            case R.id.ll_titlebar_zuojiantou:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 兑换码按钮
     */
    private void duihuanduihuanma() {
        String duihuanma = MethodUtils.getTextContent(etYouhuiquanDuihuanma);
        if (!MethodUtils.isEmpty(duihuanma)) {
            ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
            map.put("userId", Global.getUserId(getContext()));
            map.put("cdkeyCode", duihuanma);
            httpDatas.getData("兑换优惠券", Request.Method.POST, UrlBuilder.MINE_DUIHUANYOUHUIQUAN_URL, map, mHandler, RequestCode.YOUHUIQUAN_DUIHUANYOUHUIQUAN_REQUESTCODE);

        } else {
            ToastUtils.showSnackBar(snackView, "请输入兑换码");
        }
    }

    /**
     * 获取列表数据
     */
    private void getYouHuiQuanLieBiao() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("userId", Global.getUserId(getContext()));
        httpDatas.getData("优惠券列表", Request.Method.POST, UrlBuilder.MINE_YOUHUIQUAN_URL, map, mHandler, RequestCode.YOUHUIQUAN_YOUHUIQUANLIEBIAO_REQUESTCODE);
    }

    /**
     * 条目点击事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
