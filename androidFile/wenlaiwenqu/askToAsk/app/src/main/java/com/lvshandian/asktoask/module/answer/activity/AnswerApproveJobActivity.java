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
 * 答咖认证专业  2016/9/20.
 */
public class AnswerApproveJobActivity extends BaseActivity {
    @Bind(R.id.iv_approve_job_back)
    ImageView ivApproveJob;
    @Bind(R.id.tv_approve_job_save)
    TextView tvApproveJobSave;
   public static EditText etAnswerJob;
    public static boolean issave=false;

    @Override
    protected void initListener() {
        ivApproveJob.setOnClickListener(this);
        tvApproveJobSave.setOnClickListener(this);
        etAnswerJob=(EditText) findViewById(R.id.et_answer_job);

    }

    @Override
    protected void initialized() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_approve_job;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_approve_job_back:
                issave=false;
                finish();
                break;
            case R.id.tv_approve_job_save:
                if(TextUtils.isEmpty(etAnswerJob.getText().toString())){
                    ToastUtils.showSnackBar(snackView,"输入不能为空");
                }else{
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
