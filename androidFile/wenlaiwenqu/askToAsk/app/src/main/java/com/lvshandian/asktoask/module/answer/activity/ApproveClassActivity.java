package com.lvshandian.asktoask.module.answer.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.utils.TextUtils;
import com.lvshandian.asktoask.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 答咖认证年级 2016/9/20.
 */
public class ApproveClassActivity extends BaseActivity {
    @Bind(R.id.iv_approve_class_back)
    ImageView ivApproveClassBack;
    @Bind(R.id.tv_approve_class_save)
    TextView tvApproveClassSave;
    public static EditText etAnswerClass;
    public static boolean issave=false;

    @Override
    protected void initListener() {
            ivApproveClassBack.setOnClickListener(this);
            tvApproveClassSave.setOnClickListener(this);

    }

    @Override
    protected void initialized() {
       etAnswerClass=(EditText)findViewById(R.id.et_answer_class);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_approve_class;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_approve_class_back:
                issave=false;
            finish();
            break;
            case R.id.tv_approve_class_save:
                if(TextUtils.isEmpty(etAnswerClass.getText().toString())){
                ToastUtils.showSnackBar(snackView, "输入不能为空");
               }
               else{
                    issave=true;
                    finish();
                }

                break;
            default:break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
