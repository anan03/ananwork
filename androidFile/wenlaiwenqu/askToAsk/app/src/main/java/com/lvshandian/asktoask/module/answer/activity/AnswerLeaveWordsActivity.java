package com.lvshandian.asktoask.module.answer.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.utils.TextUtils;
import com.lvshandian.asktoask.utils.ToastUtils;
import butterknife.Bind;

/**
 * 答咖详情留言 on 2016/9/28.
 */
public class AnswerLeaveWordsActivity extends BaseActivity {
    @Bind(R.id.iv_hudong_detail_back)
    ImageView ivHudongDetailBack;
    @Bind(R.id.tv_answer_name_leave)
    TextView tvAnswerNameLeave;
    @Bind(R.id.tv_confirm)
    TextView tvConfirm;
    public static boolean isconfirm = false;
    public static final String LEAVENAME = "LEAVENAME";//留言的答师的名字
    public static EditText editText;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_hudong_detail_back:
                isconfirm = false;
                finish();
                break;
            case R.id.tv_confirm:
                if(TextUtils.isEmpty(editText.getText().toString())){
                    ToastUtils.showSnackBar(snackView,"输入不能为空");
                }else{
                    isconfirm = true;
                    finish();
                }

                break;
        }

    }


    @Override
    protected void initialized() {
        tvAnswerNameLeave.setText(getIntent().getStringExtra(LEAVENAME));
        editText=(EditText)findViewById(R.id.et_leave_words);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.answer_leave_words_layout;
    }

    @Override
    protected void initListener() {
        ivHudongDetailBack.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);


    }


}
