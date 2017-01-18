package com.lvshandian.asktoask.module.answer.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.utils.DialogView;
import com.lvshandian.asktoask.utils.PicUtils;
import com.lvshandian.asktoask.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * on 2016/10/8.
 * 答咖认证身份证界面
 */
public class AnswerIdCardActivity extends BaseActivity {

    @Bind(R.id.iv_answer_activity_back)
    ImageView ivAnswerActivityBack;
    @Bind(R.id.tv_next_step)
    TextView tvNextStep;
    @Bind(R.id.tv_takephpto_id_card_zheng)
    ImageView tvTakephptoIdCardZheng;
    @Bind(R.id.tv_takephpto_id_card_bei)
    ImageView tvTakephptoIdCardBei;
    @Bind(R.id.ll_root_popuwindws)
    LinearLayout llRootPopuwindws;
    final int TAKE_PICTURE = 1;
    final int CHOOSE_PICTURE = 0;
    @Bind(R.id.tv_zheng)
    TextView tvZheng;
    @Bind(R.id.tv_bei)
    TextView tvBei;
    private int first = 0;

    public static String imageIdZhengtPath="";

    public static String imageIdBeitPath="";


    @Override
    protected void initialized() {

    }

    @Override
    protected void initListener() {
        ivAnswerActivityBack.setOnClickListener(this);
        tvZheng.setOnClickListener(this);
        tvBei.setOnClickListener(this);
        tvNextStep.setOnClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_approve_idcard_layout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_answer_activity_back:
                finish();
                break;
            case R.id.tv_zheng:
                first = 1;//正面
                showAlert();
                break;
            case R.id.tv_bei:
                first = 2;//背面
                showAlert();
                break;
            case R.id.tv_next_step:
                ToastUtils.showSnackBar(snackView, "图片保存成功");
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

    //弹出对话框选择图片

    private void showAlert() {
        new DialogView(getContext(), llRootPopuwindws, "", "相机", "相册", "取消",
                new DialogView.MyCameraCallback() {
                    @Override
                    public void refreshCallback(int tag) {

                        switch (tag) {
                            //调用相机
                            case 1:
                                Intent openCameraIntent = new Intent(
                                        MediaStore.ACTION_IMAGE_CAPTURE);
//                                tempUri = Uri.fromFile(new File(Environment
//                                        .getExternalStorageDirectory(), "image.jpg"));
//                                // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
//                                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                                openCameraIntent.addCategory("android.intent.category.DEFAULT");
                                File file;
                                if(first==1){
                                    //图片每次都保存在这里
                                    file = new File(Environment.getExternalStorageDirectory()+"/003.jpg");//身份证正面
                                }else{
                                    //图片每次都保存在这里
                                    file = new File(Environment.getExternalStorageDirectory()+"/004.jpg");//身份证背面
                                }
                                Uri uri = Uri.fromFile(file);
                                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                startActivityForResult(openCameraIntent, TAKE_PICTURE);
                                break;
                            //调用相册
                            case 2:
                                Intent openAlbumIntent = new Intent(
                                        Intent.ACTION_GET_CONTENT);
                                openAlbumIntent.setType("image/*");
                                startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                                break;
                            //取消
                            case 3:
                                break;
                        }
                    }
                });
    }

    private Bitmap bm=null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE://拍照获取照片
//                if (data != null) {
//                    Bundle extras = data.getExtras();
//                    if (extras != null) {
//
//                        if (first == 1) {
//                            Bitmap photo = extras.getParcelable("data");
//                            tvZheng.setVisibility(View.GONE);
//                            tvTakephptoIdCardZheng.setVisibility(View.VISIBLE);
////                            tvTakephptoIdCardZheng.setImageBitmap(photo);
////                            photo.recycle();
//                            imageIdZhengtPath=Environment.getExternalStorageDirectory()+"/003.jpg";//学生证图片
//                        }
//                        if (first == 2) {
//                            Bitmap photo1 = extras.getParcelable("data");
//                            tvBei.setVisibility(View.GONE);
//                            tvTakephptoIdCardBei.setVisibility(View.VISIBLE);
////                            tvTakephptoIdCardBei.setImageBitmap(photo1);
////                            photo1.recycle();
//                            imageIdBeitPath=Environment.getExternalStorageDirectory()+"/004.jpg";//学生证背面图片
//                            ImageLoader.getInstance().displayImage(imageIdBeitPath,tvTakephptoIdCardBei);
//
//                        }
//                    }
//                }


                if (first == 1) {

                            tvZheng.setVisibility(View.GONE);
                            tvTakephptoIdCardZheng.setVisibility(View.VISIBLE);
                            imageIdZhengtPath=Environment.getExternalStorageDirectory()+"/003.jpg";//学生证图片

//                    File file = new File(imageIdZhengtPath);
//                    if (file.exists()) {
//                        bm = BitmapFactory.decodeFile(imageIdZhengtPath);
//                        //将图片显示到ImageView中
//                        tvTakephptoIdCardZheng.setImageBitmap(bm);
//                    }
                    ImageLoader.getInstance().displayImage("file:///"+imageIdZhengtPath,tvTakephptoIdCardZheng);


                            }



                if (first == 2) {
                    tvBei.setVisibility(View.GONE);
                    tvTakephptoIdCardBei.setVisibility(View.VISIBLE);
                    imageIdBeitPath=Environment.getExternalStorageDirectory()+"/003.jpg";//学生证图片

//                    File file = new File(imageIdBeitPath);
//                    if (file.exists()) {
//                        bm = BitmapFactory.decodeFile(imageIdBeitPath);
//                        //将图片显示到ImageView中
//                        tvTakephptoIdCardBei.setImageBitmap(bm);
//                    }

                    ImageLoader.getInstance().displayImage("file:///"+imageIdZhengtPath,tvTakephptoIdCardBei);

                }




                break;
            case CHOOSE_PICTURE://从相册获取照片
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        ContentResolver resolver = getContentResolver();
                        Uri originalUri = data.getData();        //获得图片的uri
                        try {

                            if (first == 1) {
                                Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);       //显得到bitmap图片
                                tvZheng.setVisibility(View.GONE);
                                tvTakephptoIdCardZheng.setVisibility(View.VISIBLE);
                                //从相册获取到的图片路径
                                imageIdZhengtPath=PicUtils.getRealFilePath(originalUri,getContext());

//                                File filealbunm = new File(imageIdZhengtPath);
//                                if (filealbunm.exists()) {
//                                    bm = BitmapFactory.decodeFile(imageIdZhengtPath);
//                                    //将图片显示到ImageView中
//                                    tvTakephptoIdCardZheng.setImageBitmap(bm);
//
//                                }


                                ImageLoader.getInstance().displayImage("file:///"+imageIdZhengtPath,tvTakephptoIdCardZheng);



                            }
                            if (first == 2) {
//                                Bitmap photo1 = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                                tvBei.setVisibility(View.GONE);
                                tvTakephptoIdCardBei.setVisibility(View.VISIBLE);
                                imageIdBeitPath= PicUtils.getRealFilePath(originalUri, getContext());
//                                File filealbunm = new File(imageIdBeitPath);
//                                if (filealbunm.exists()) {
//                                    bm = BitmapFactory.decodeFile(imageIdBeitPath);
//                                    //将图片显示到ImageView中
//                                    tvTakephptoIdCardBei.setImageBitmap(bm);
//                                }

                                ImageLoader.getInstance().displayImage("file:///"+imageIdBeitPath,tvTakephptoIdCardBei);

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }

                break;


        }


    }




    /**
     * 销毁资源
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
