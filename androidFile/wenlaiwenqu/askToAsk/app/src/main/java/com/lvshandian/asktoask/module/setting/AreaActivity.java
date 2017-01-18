package com.lvshandian.asktoask.module.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.adapter.SortAdapter;
import com.lvshandian.asktoask.entry.SortModel;
import com.lvshandian.asktoask.utils.CharacterParser;
import com.lvshandian.asktoask.utils.PinyinComparator;
import com.lvshandian.asktoask.widgets.SideBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by newlq on 2016/9/8.
 * 城市选择
 */
public class AreaActivity extends BaseActivity {


    //    @Bind(R.id.gv_selectorcity_remenchengshi)
//    GridView gvSelectorcityRemenchengshi;
    @Bind(R.id.tv_titlebar_centertext)
    TextView tvTitlebarCentertext;
    @Bind(R.id.ll_titlebar_zuojiantou)
    LinearLayout llTitlebarZuojiantou;
    @Bind(R.id.tv_titlebar_righttext)
    TextView tvTitlebarRighttext;
    @Bind(R.id.lv_selectorcity_chengshiliebiao)
    ListView lvSelectorcityChengshiliebiao;
    private String[] remenchengshi;
    private List<SortModel> listViewData;
    private PinyinComparator pinyinComparator;
    private CharacterParser characterParser;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter mSortAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_areaselector;
    }

    @Override
    protected void initListener() {
        llTitlebarZuojiantou.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        tvTitlebarCentertext.setText("城市选择");

        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();

        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                int position = mSortAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    lvSelectorcityChengshiliebiao.setSelection(position);
                }
            }
        });

        lvSelectorcityChengshiliebiao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("city", ((SortModel) mSortAdapter.getItem(position)).getName());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

//        remenchengshi = getApplicationContext().getResources().getStringArray(R.array.remenchengshi);
//        ArrayAdapter<String> hotCityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1);
//        hotCityAdapter.addAll(remenchengshi);
//        gvSelectorcityRemenchengshi.setAdapter(hotCityAdapter);

        listViewData = new ArrayList<>();
        listViewData = filledData(getResources().getStringArray(R.array.guoneichengshi));

        mSortAdapter = new SortAdapter(this, listViewData);
        lvSelectorcityChengshiliebiao.setAdapter(mSortAdapter);

    }

    private List<SortModel> filledData(String[] date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < date.length; i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i]);
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_titlebar_zuojiantou:
                finish();
                break;
            default:
                break;
        }

    }

}
