package com.lvshandian.menshen.analysesms;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.lvshandian.menshen.R;
import com.lvshandian.menshen.analysesms.AnayseSmsActivity;
import com.lvshandian.menshen.analysesms.AnayseSmsDetails;
import com.lvshandian.menshen.analysesms.AnayseSmsUploadActivity;
import com.lvshandian.menshen.analysesms.adapter.LazyFragment;
import com.lvshandian.menshen.analysesms.adapter.SmsAdapter;
import com.lvshandian.menshen.bean.SmsInfo;
import com.lvshandian.menshen.utils.LogUtils;
import com.lvshandian.menshen.utils.Sp;
import com.lvshandian.menshen.utils.TextUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by on 2015/10/30.
 */
public class FragmentSmsApp extends LazyFragment {

    ListView lv;
    private List<SmsInfo> list;
    private int tagPosion = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this); //第1步: 注册


    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null)
            this.getView()
                    .setVisibility(menuVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onVisible() {

        if (null != AnayseSmsActivity.tvAll && AnayseSmsActivity.tvSingle != null && getActivity() != null) {

            AnayseSmsActivity.tvSingle
                    .setTextColor(getActivity().getResources().getColor(
                            R.color.texthui_color));
            AnayseSmsActivity.tvAll
                    .setTextColor(getActivity().getResources().getColor(
                            R.color.back347));
        }


        if (adapter != null) {
            //刷新当前页面刷新
            if (getContext() != null) {
                String strDelete = (String) Sp.getParam(getContext(), "reflashsmsdelete", "");
                if (!TextUtils.isEmpty(strDelete)) {
                    String[] sts = TextUtils.out(strDelete);
                    if (!TextUtils.isEmpty(strDelete)) {
                        for (int i = list.size() - 1; i >= 0; i--) {

                            for (int j = 0; j < sts.length; j++) {
                                if (list.get(i).get_id().equals(sts[j])) {
                                    list.remove(i);
                                    break;
                                }
                            }

                        }

                    }

                }
                String strRead = (String) Sp.getParam(getContext(), "read", "");
                if (!TextUtils.isEmpty(strRead)) {
                    String[] sts = TextUtils.out(strRead);
                    if (!TextUtils.isEmpty(strRead)) {
                        for (int i = list.size() - 1; i >= 0; i--) {

                            for (int j = 0; j < sts.length; j++) {
                                if (list.get(i).get_id().equals(sts[j])) {
                                    list.get(i).setRead(1);
                                }
                            }

                        }

                    }
                }
            }
            adapter.indata();
            adapter.notifyDataSetChanged();
        }


    }

    SmsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        View mView = inflater.inflate(R.layout.fragment_app, container, false);
        ((TextView) mView.findViewById(R.id.title)).setText(title);
        lv = (ListView) mView.findViewById(R.id.lv);
        list = new ArrayList<>();
        list.clear();
        list = (List<SmsInfo>) getArguments().getSerializable("list");
        adapter = new SmsAdapter(getContext(), list, R.layout.anaysesms_item_framentapp, getArguments().getBoolean("isShow"));
        lv.setAdapter(adapter);
        ButterKnife.bind(this, mView);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if (AnayseSmsActivity.isShow) {
                    LogUtils.d("不能点击");
                    return;
                }
                tagPosion = position;
                Intent intent = new Intent(getActivity(), AnayseSmsDetails.class);
                intent.putExtra("smsinfo", list.get(position));
                startActivity(intent);

            }
        });
        return mView;
    }

    //接受方：
    @Subscribe //第2步:注册一个在后台线程执行的方法,用于接收事件
    public void onEventMainThread(String event) {
        if (event.equals("reflashread")) {
            LogUtils.e("1111----------1" + tagPosion);
            if (adapter != null && tagPosion != -1) {
                list.get(tagPosion).setRead(1);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册
    }

}
