package com.lvshandian.asktoask.module.answer.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.utils.ToastUtils;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * on 2016/9/22.
 * <p/>
 * 答咖认证生活照
 */
public class ApproveLifePhotoActivity extends BaseActivity {
    public static final int RESULT_FROM_ALBUM = 1;
    public static final int RESULT_FROM_CAMERA = 2;
    @Bind(R.id.iv_takephoto)
    ImageView ivTakephoto;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_save)
    TextView tvSave;
    public static final String PHOTOCODE="PHOTO";
    public static final int TAKEPHOTO=0;
    public static final int SELECTPHOTOS=1;


    @Override
    protected void initialized() {
        int code=getIntent().getIntExtra(PHOTOCODE,4);
        if(code==TAKEPHOTO){
            selectPicFromCamera();
        }if(code==SELECTPHOTOS){
            selectPicFromAlbum();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_save:
                ToastUtils.showSnackBar(snackView,"保存");
                break;

            default:
                break;

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_answer_approve_life_photo;
    }

    @Override
    protected void initListener() {
        tvSave.setOnClickListener(this);
        ivBack.setOnClickListener(this);

    }


    /**
     * 从相册中获取，返回结果会在onActivityResult()中
     */
    private void selectPicFromAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, RESULT_FROM_ALBUM);
    }

    /**
     * 从摄像头中获取，返回结果会在onActivityResult()中
     */
    private void selectPicFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, RESULT_FROM_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (null == data) return;
        switch (requestCode) {
            case RESULT_FROM_ALBUM:
                Uri imageUri = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    ivTakephoto.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            case RESULT_FROM_CAMERA:
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                ivTakephoto.setImageBitmap(bitmap);
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

