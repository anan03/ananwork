package com.lvshandian.menshen.analysesms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lvshandian.menshen.R;
import com.lvshandian.menshen.analysesms.adapter.SmsSettingAdapter;
import com.lvshandian.menshen.bean.Keyworkinfo;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by on 2015/10/30.
 */
public class FragmentSeeting extends Fragment {

    @Bind(R.id.lv)
    ListView lv;
    private String title;
    public static int tag;
    LinearLayout ll_part;


    public SmsSettingAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString("title");

    }

    public ArrayList<Keyworkinfo> getList() {
        return list;
    }

    public void setList(ArrayList<Keyworkinfo> list) {
        this.list = list;
    }

    public ArrayList<Keyworkinfo> list = new ArrayList<Keyworkinfo>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String title = getArguments().getString("title");
        tag = getArguments().getInt("tag");
        View mView = inflater.inflate(R.layout.fragment_app, container, false);
        ((TextView) mView.findViewById(R.id.title)).setText(title);

        lv = (ListView) mView.findViewById(R.id.lv);
        ll_part = (LinearLayout) mView.findViewById(R.id.ll_part);
        list = (ArrayList<Keyworkinfo>) getArguments().getSerializable("list");
        adapter = new SmsSettingAdapter(ll_part, getContext(), list, R.layout.anaysesms_item_framentsetting);
        lv.setAdapter(adapter);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
