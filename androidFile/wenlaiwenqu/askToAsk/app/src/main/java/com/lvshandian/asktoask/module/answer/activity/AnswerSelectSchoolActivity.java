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
 * on 2016/9/24.
 * 答咖选择学校
 */
public class AnswerSelectSchoolActivity extends BaseActivity {

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
    public  static boolean isissave=false;
    public static String school;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
        return R.layout.activity_answer_select_school_layout;
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

        getHeadViewAndOnclick();//添加头比布局并且加上监听
        sortListView.addHeaderView(headview);
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                //得到返回去的选中的学校
                if(position!=0){
                    school=((SortModel) adapter.getItem(position-1)).getName();
                    isissave=true;
                    finish();
                }

            }
        });

        SourceDateList = filledData(getResources().getStringArray(R.array.school));
        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SortAdapter(AnswerSelectSchoolActivity.this, SourceDateList);
        sortListView.setAdapter(adapter);


    }

    View headview;
    public void getHeadViewAndOnclick(){
        headview=getLayoutInflater().inflate(R.layout.gridview_select_school_header,null);
        headview.findViewById(R.id.tv_school_buxian).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                school="不限";
                isissave=true;
                finish();

            }
        });

        headview.findViewById(R.id.tv_school_beijing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                school="北京大学";
                isissave=true;
                finish();

            }
        });
        headview.findViewById(R.id.tv_school_qinghua).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                school="清华大学";
                isissave=true;
                finish();

            }
        });
        headview.findViewById(R.id.tv_school_fudan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                school="复旦大学";
                isissave=true;
                finish();

            }
        });
        headview.findViewById(R.id.tv_school_tongji).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                school="同济大学";
                isissave=true;
                finish();

            }
        });
        headview.findViewById(R.id.tv_school_xiamen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                school="厦门大学";
                isissave=true;
                finish();

            }
        });
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
