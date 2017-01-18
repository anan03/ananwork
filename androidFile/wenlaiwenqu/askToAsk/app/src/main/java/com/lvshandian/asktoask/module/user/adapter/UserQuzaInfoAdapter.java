package com.lvshandian.asktoask.module.user.adapter;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.adapter.CommonAdapter;
import com.lvshandian.asktoask.common.adapter.ViewHolder;
import com.lvshandian.asktoask.entry.UserAnswerInfo;
import com.lvshandian.asktoask.module.user.activity.QuzaDetailsActivity;
import com.lvshandian.asktoask.utils.DateUtil;
import com.lvshandian.asktoask.utils.TextUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提问详情下的列表 on 2016/10/10.
 */
public class UserQuzaInfoAdapter extends CommonAdapter<UserAnswerInfo.DataBean> {
    Context context;
    public static int selectIndex = -1;
    // 用来控制CheckBox的选中状况
    private  static HashMap<Integer,Boolean> isSelected;
    public static List<UserAnswerInfo.DataBean> mDatas;
    private SparseArray<UserAnswerInfo.DataBean> mSelected = new SparseArray<>();//进行回调的集合



    public UserQuzaInfoAdapter(Context context, List<UserAnswerInfo.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        isSelected=new HashMap<>();
        this.context = context;
        this.mDatas=mDatas;
        // 初始化数据
        initDate();
    }
    // 初始化isSelected的数据
    private void initDate(){
        for(int i=0; i<mDatas.size();i++) {
                getIsSelected().put(i,false);
            }
        }


    public static HashMap<Integer,Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer,Boolean> isSelected) {
        UserQuzaInfoAdapter.isSelected = isSelected;
    }



    @Override
    public void convert(ViewHolder helper, final UserAnswerInfo.DataBean item, final int position) {
        ImageLoader.getInstance().displayImage(item.getUserHeadImg(), (ImageView) helper.getView(R.id.iv_hudong_type_detail));
        if (!TextUtils.isEmpty(item.getUserSex())) {
            if (item.getUserSex().equals("男")) {
                helper.setImageResource(R.id.iv_hudong_detail_sex, R.drawable.sex_men);
            } else if (item.getUserSex().equals("女")) {
                helper.setImageResource(R.id.iv_hudong_detail_sex, R.drawable.sex_women);
            }
        }
        final CheckBox ck=(CheckBox)helper.getView(R.id.ck_accept);
        if(QuzaDetailsActivity.isAccept){
            ck.setVisibility(View.GONE);
        }else {
            ck.setVisibility(View.VISIBLE);
            ck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (ck.isChecked()) {
                        mSelected.put(position, item);
                        item.ischeck=true;

                        Log.d("aaa","添加了"+position);
                    } else {
                        mSelected.remove(position);
                        item.ischeck=false;
                        Log.d("aaa","移除了"+position);
                    }

                    if (mListener != null) {
                        mListener.onDatasChange(mSelected);
                    }

                  /*  if (ck.isChecked()) {
                        getIsSelected().put(position, true);
                        item.ischeck = true;
                    } else {
                        getIsSelected().put(position, false);
                        item.ischeck = false;
                    }*/
                }
            });
            //ck.setChecked(getIsSelected().get(position));
        }


        //姓名
        helper.setText(R.id.tv_hudong_type_username, item.getUserRealName());
        //发表内容
        helper.setText(R.id.tv_title_hudong_detail, item.getAnswerData() + "");
        //时间
        helper.setText(R.id.tv_time_hudong_detail, DateUtil.timesOne(item.getAnswerDate()));

        if(!TextUtils.isEmpty(item.getUserSchool())){
            helper.getView(R.id.tv_hudong_detail_label_school).setVisibility(View.VISIBLE);
            //学校
            helper.setText(R.id.tv_hudong_detail_label_school, item.getUserSchool() + "");
        }else{
            helper.getView(R.id.tv_hudong_detail_label_school).setVisibility(View.INVISIBLE);
        }
        if(!TextUtils.isEmpty(item.getUserGrade())){
            helper.getView(R.id.tv_hudong_detail_label_grade).setVisibility(View.VISIBLE);
            //年级
            helper.setText(R.id.tv_hudong_detail_label_grade, item.getUserGrade() + "");
        }else{
            helper.getView(R.id.tv_hudong_detail_label_grade).setVisibility(View.INVISIBLE);
        }
        if(!TextUtils.isEmpty(item.getUserMajor())){
            helper.getView(R.id.tv_hudong_detail_label_major).setVisibility(View.VISIBLE);
            //专业
            helper.setText(R.id.tv_hudong_detail_label_major, item.getUserMajor() + "");
        }else{
            helper.getView(R.id.tv_hudong_detail_label_major).setVisibility(View.INVISIBLE);
        }

        if (item.getIsaccept().equals("1")) {
            helper.getView(R.id.tv_accept).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tv_accept).setVisibility(View.INVISIBLE);
        }
        //选中时改变颜色
        if (selectIndex == position) {
            helper.getView(R.id.ll_item_answer).setBackgroundColor(context.getResources().getColor(R.color.colorf2f2f2));
        } else {
            helper.getView(R.id.ll_item_answer).setBackgroundColor(context.getResources().getColor(R.color.white));
        }
    }


    /**
     * 接口回调来实现去获取是否选中的状态
     * @param litener
     */


    public void setOnDataChangeLitener(OnDatasChanLitener litener) {
        mListener = litener;
    }
    private OnDatasChanLitener mListener;
    public interface OnDatasChanLitener {
        void onDatasChange(SparseArray<UserAnswerInfo.DataBean> array);
    }

}