package com.lvshandian.asktoask.module.setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.activeandroid.util.Log;
import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.utils.Constant;
import com.lvshandian.asktoask.utils.MethodUtils;
import com.lvshandian.asktoask.utils.TextUtils;
import butterknife.Bind;

/**
 * 个人信息页
 */
public class ChangePersonalInfoActivity extends BaseActivity {

    @Bind(R.id.tv_titlebar_centertext)
    TextView tvTitlebarCentertext;
    @Bind(R.id.ll_titlebar_zuojiantou)
    LinearLayout llTitlebarZuojiantou;
    @Bind(R.id.tv_titlebar_righttext)
    TextView tvTitlebarRighttext;
    @Bind(R.id.et_xiugaigerenxinxi_bianjikuang)
    EditText etXiugaigerenxinxiBianjikuang;
    private String textContent = "";
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RequestCode.XIUGAIGRENXINXIofONE_REQUESTCODE:


                    Bundle data = msg.getData();
                    String json = data.getString(HttpDatas.info);
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.Change_personal_WhichInfo, getIntent().getStringExtra(Constant.Change_personal_WhichInfo));
                    bundle.putString("textcontent", textContent);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_changepersonalinfo;
    }

    @Override
    protected void initListener() {
        tvTitlebarRighttext.setOnClickListener(this);
        llTitlebarZuojiantou.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        if(PersonalInfoActivity.isname){
//            etXiugaigerenxinxiBianjikuang.setInputExtras();
            etXiugaigerenxinxiBianjikuang.setFilters(new InputFilter[]{new InputFilter.LengthFilter(7)});
        }

        tvTitlebarRighttext.setText("确定");
        tvTitlebarCentertext.setText(getIntent().getStringExtra(Constant.Change_personal_WhichInfo));
//        etXiugaigerenxinxiBianjikuang.setHint(getIntent().getStringExtra(Constant.Change_personal_WhichInfoHint));
        etXiugaigerenxinxiBianjikuang.setText(getIntent().getStringExtra(Constant.Change_personal_WhichInfoHint));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_titlebar_zuojiantou:
                if (!MethodUtils.isEmpty(MethodUtils.getTextContent(etXiugaigerenxinxiBianjikuang))) {
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                    builder.setTitle("确定退出么")
                            .setMessage("放弃修改么?")
                            .setPositiveButton("放弃", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                } else {
                    finish();
                }
                break;
            case R.id.tv_titlebar_righttext:
                PersonalInfoActivity.isname=false;
                changeInfo();
                break;
            default:
                break;
        }

    }

    /**
     * 改变这个
     */
    private void changeInfo() {
//        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
//        map.put("userId", UrlBuilder.getUserId());

        textContent = MethodUtils.getTextContent(etXiugaigerenxinxiBianjikuang);
        if (TextUtils.isEmpty(textContent)) {

            if (TextUtils.isEmpty(MethodUtils.getTextContentHint(etXiugaigerenxinxiBianjikuang))) {
                textContent = getIntent().getStringExtra(Constant.Change_personal_WhichInfoHint);
            }

        }

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.Change_personal_WhichInfo, getIntent().getStringExtra(Constant.Change_personal_WhichInfo));
        bundle.putString("textcontent", textContent);

//       UserFragment.getInstance().EditextView(intent);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();


//        switch (getIntent().getStringExtra(Constant.Change_personal_WhichInfo)) {
//            case "年级":
//                map.put("userGrade", textContent);
//                map.put("userSign", User.getUser().getUser_sign());
//                map.put("userSchool", User.getUser().getUser_school());
//                map.put("userMajor", User.getUser().getUser_major());
//                map.put("userRealName", User.getUser().getUser_real_name());
//                break;
//            case "签名":
//                map.put("userGr000ade", textContent);
//                map.put("userSign", User.getUser().getUser_sign());
//                map.put("userSchool", User.getUser().getUser_school());
//                map.put("userMajor", User.getUser().getUser_major());
//                map.put("userRealName", User.getUser().getUser_real_name());
//                break;
//            case "学校":
//                map.put("userSchool", textContent);
//                map.put("userGrade", User.getUser().getUser_grade());
//                map.put("userSign", User.getUser().getUser_sign());
//                map.put("userMajor", User.getUser().getUser_major());
//                map.put("userRealName", User.getUser().getUser_real_name());
//                break;
//            case "分科专业":
//                map.put("userMajor", textContent);
//                map.put("userGrade", User.getUser().getUser_grade());
//                map.put("userSign", User.getUser().getUser_sign());
//                map.put("userSchool", User.getUser().getUser_school());
//                map.put("userRealName", User.getUser().getUser_real_name());
//                break;
//            case "姓名":
//                map.put("userRealName", textContent);
//                map.put("userSign", User.getUser().getUser_sign());
//                map.put("userMajor", User.getUser().getUser_major());
//                map.put("userGrade", User.getUser().getUser_grade());
//                map.put("userSchool", User.getUser().getUser_school());
//                break;
//            default:
//                break;
//        }
//
//        Intent intent = new Intent();
//        Bundle bundle = new Bundle();
//        bundle.putString(Constant.Change_personal_WhichInfo, getIntent().getStringExtra(Constant.Change_personal_WhichInfo));
//        bundle.putString("textcontent", textContent);
//        Log.d("text1", "修改前" + etXiugaigerenxinxiBianjikuang);
////                    UserFragment.getInstance().EditextView(intent);
//        intent.putExtras(bundle);
//        setResult(RESULT_OK, intent);
//        finish();


//        map.put("area", User.getUser().getArea());
//        map.put("userSex", User.getUser().getUser_sex());
//        httpDatas.getData("修改个人信息其中的一项" + getIntent().getStringExtra(Constant.Change_personal_WhichInfo),
//                Request.Method.POST, UrlBuilder.MINE_XIUGAIGERENXINXI_URL, map, mHandler, RequestCode.XIUGAIGRENXINXIofONE_REQUESTCODE);
    }

}
