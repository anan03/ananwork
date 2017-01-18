package com.lvshandian.asktoask.module.answer.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.module.answer.activity.character.CharacterParser;
import com.lvshandian.asktoask.module.answer.activity.character.PinyinComparator;
import com.lvshandian.asktoask.module.answer.activity.character.SideBar;
import com.lvshandian.asktoask.module.answer.activity.character.SortAdapter;
import com.lvshandian.asktoask.module.answer.activity.character.SortModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;

/**
 * 答咖去选择地区 on 2016/10/5.
 */
public class AnswerSelectAddressActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.country_lvcountry)
    ListView countryLvcountry;
    @Bind(R.id.sidrbar)
    SideBar sidrbar;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private PinyinComparator pinyinComparator;
    private SideBar sideBar;
    private TextView dialog;
    private ListView sortListView;
    private SortAdapter adapter;
    private List<SortModel> SourceDateList;
    public static boolean isissave = false;
    public static String place;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }

    }

    @Override
    protected void initialized() {
        initViews();
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_answer_select_place_layout;
    }

    /**
     * 处理所有的地区和学校
     */


    private void initViews() {
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });

        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        headerview = getLayoutInflater().inflate(R.layout.gridview_select_school_header, null);
        getHeadViewAndOnclick();//添加头比布局并且加上监听
        sortListView.addHeaderView(headerview);
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                //得到返回去的选中的学校
                if (position != 0) {
                    place = ((SortModel) adapter.getItem(position - 1)).getName();
                    isissave = true;
                    finish();
                }

            }
        });

        SourceDateList = filledData(getResources().getStringArray(R.array.citys));
        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SortAdapter(AnswerSelectAddressActivity.this, SourceDateList);
        sortListView.setAdapter(adapter);
    }

    private View headerview;

    public void getHeadViewAndOnclick() {
        headerview = getLayoutInflater().inflate(R.layout.gridview_select_area_header, null);
        headerview.findViewById(R.id.tv_area_buxian).setOnClickListener(new MyListener());
        headerview.findViewById(R.id.tv_area_beijing).setOnClickListener(new MyListener());
        headerview.findViewById(R.id.tv_area_chengdu).setOnClickListener(new MyListener());
        headerview.findViewById(R.id.tv_area_guangzhou).setOnClickListener(new MyListener());
        headerview.findViewById(R.id.tv_area_hangzhou).setOnClickListener(new MyListener());
        headerview.findViewById(R.id.tv_area_nanjing).setOnClickListener(new MyListener());
        headerview.findViewById(R.id.tv_area_shagnhai).setOnClickListener(new MyListener());
        headerview.findViewById(R.id.tv_area_shenzhen).setOnClickListener(new MyListener());
        headerview.findViewById(R.id.tv_area_tianji).setOnClickListener(new MyListener());
        headerview.findViewById(R.id.tv_area_wuhan).setOnClickListener(new MyListener());
        headerview.findViewById(R.id.tv_area_xian).setOnClickListener(new MyListener());
        headerview.findViewById(R.id.tv_area_chongqing).setOnClickListener(new MyListener());
    }

    class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_area_buxian:
                    place = "不限";
                    isissave = true;
                    finish();
                    break;
                case R.id.tv_area_beijing:
                    place = "北京";
                    isissave = true;
                    finish();
                    break;
                case R.id.tv_area_chengdu:
                    place = "成都";
                    isissave = true;
                    finish();

                    break;
                case R.id.tv_area_guangzhou:
                    place = "广州";
                    isissave = true;
                    finish();

                    break;
                case R.id.tv_area_hangzhou:
                    place = "杭州";
                    isissave = true;
                    finish();

                    break;
                case R.id.tv_area_nanjing:
                    place = "南京";
                    isissave = true;
                    finish();

                    break;
                case R.id.tv_area_shagnhai:
                    place = "上海";
                    isissave = true;
                    finish();

                    break;
                case R.id.tv_area_shenzhen:
                    place = "深圳";
                    isissave = true;
                    finish();

                    break;
                case R.id.tv_area_tianji:
                    place = "天津";
                    isissave = true;
                    finish();

                    break;
                case R.id.tv_area_wuhan:
                    place = "武汉";
                    isissave = true;
                    finish();

                    break;
                case R.id.tv_area_xian:
                    place = "西安";
                    isissave = true;
                    finish();

                    break;
                case R.id.tv_area_chongqing:
                    place = "重庆";
                    isissave = true;
                    finish();
                    break;

            }
        }
    }


    /**
     * 为ListView填充(设置)数据
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData(String[] date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < date.length; i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i]);
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

}
