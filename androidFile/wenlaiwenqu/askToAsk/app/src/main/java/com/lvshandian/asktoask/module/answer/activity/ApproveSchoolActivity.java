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
 * 答咖认证学校界面
 */
public class ApproveSchoolActivity extends BaseActivity {
    @Bind(R.id.tv_select_school_save)
    TextView tvSelectSchoolSave;
    public static EditText etAnswerApproveSchool;
    @Bind(R.id.back)
    ImageView back;
    public static boolean issave=false;

    @Override
    protected void initListener() {
        tvSelectSchoolSave.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        etAnswerApproveSchool=(EditText)findViewById(R.id.et_answer_approve_school);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_approve_school;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_select_school_save:
                if(TextUtils.isEmpty(etAnswerApproveSchool.getText().toString())){
                    ToastUtils.showSnackBar(snackView,"输入不能为空");
                }else{
                    issave=true;
                    finish();
                }

                break;
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
