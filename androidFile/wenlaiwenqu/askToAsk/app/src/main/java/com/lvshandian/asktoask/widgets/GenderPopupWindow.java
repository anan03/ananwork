package com.lvshandian.asktoask.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.module.postquestion.PostQuestionActivity;
import com.lvshandian.asktoask.module.setting.PersonalInfoActivity;

import java.lang.reflect.Field;

/**
 * timerpicker on 2016/9/8.
 */
public class GenderPopupWindow {
    private Context context;
    private final TextView tvSexPicviewQueding;
    private final TextView tvCancle;
    private final NumberPicker sexpicview;
    private final PopupWindow popupWindow;
    private String[] strings = {"男", "女"};
    private int tag;
    String textcontenttype;

    public GenderPopupWindow(Context context,String[] strings,int tag,String textcontenttype) {
        View popupWindowView = View.inflate(context, R.layout.sex_pickview, null);
        this.context=context;
        this.strings=strings;
        this.textcontenttype=textcontenttype;
        this.tag=tag;
        popupWindow = new PopupWindow(popupWindowView, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow.setBackgroundDrawable(new ColorDrawable(0xffffff));
//        popupWindow.setAnimationStyle(R.style.AnimationPreview);

        tvCancle= (TextView)popupWindowView.findViewById(R.id.tv_cancle);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.update();

        tvSexPicviewQueding = (TextView) popupWindowView.findViewById(R.id.tv_sex_picview_queding);
        ((TextView) popupWindowView.findViewById(R.id.tv_type_content)).setText(textcontenttype);
        sexpicview = (NumberPicker) popupWindowView.findViewById(R.id.np_sexpicview);
        setNumberPickerDividerColor(sexpicview);//设置分割线颜色
        sexpicview.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);//禁止输入
        sexpicview.setDisplayedValues(strings);
        sexpicview.setMinValue(0);
        sexpicview.setMaxValue(strings.length - 1);

    }

    public void showPopupWindow(View parent) {
        if (!popupWindow.isShowing()) {
            popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        } else {
            popupWindow.dismiss();
        }
    }

    public interface GenderListener {
        void getGender(String gender);
    }

    private GenderListener mGetGenderListener;

    public void setGetGenderListener(GenderListener l) {
        mGetGenderListener = l;
        tvSexPicviewQueding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetGenderListener.getGender(strings[sexpicview.getValue()]);
                //1的话是个人信息页
                if(tag==1){
                    WindowManager.LayoutParams lp= ((PersonalInfoActivity)context).getWindow().getAttributes();
                    lp.alpha=1.0f;//设置透明度 完全透明
                    ((PersonalInfoActivity) context).getWindow().setAttributes(lp);
                }if(tag==2){
                    WindowManager.LayoutParams lp= ((PostQuestionActivity)context).getWindow().getAttributes();
                    lp.alpha=1.0f;//设置透明度 完全透明
                    ((PostQuestionActivity) context).getWindow().setAttributes(lp);
                }

                popupWindow.dismiss();
            }
        });
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tag==1){
                    WindowManager.LayoutParams lp = ((PersonalInfoActivity) context).getWindow().getAttributes();
                    lp.alpha = 1.0f;//设置透明度 完全透明
                    ((PersonalInfoActivity) context).getWindow().setAttributes(lp);
                }
                if(tag==2){
                    WindowManager.LayoutParams lp = ((PostQuestionActivity) context).getWindow().getAttributes();
                    lp.alpha = 1.0f;//设置透明度 完全透明
                    ((PostQuestionActivity) context).getWindow().setAttributes(lp);
                }
                popupWindow.dismiss();
            }
        });

    }


    private void setNumberPickerDividerColor(NumberPicker numberPicker) {
        NumberPicker picker = numberPicker;
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    //设置分割线的颜色值
                    pf.set(picker, new ColorDrawable(context.getResources().getColor(R.color.white)));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }


}
