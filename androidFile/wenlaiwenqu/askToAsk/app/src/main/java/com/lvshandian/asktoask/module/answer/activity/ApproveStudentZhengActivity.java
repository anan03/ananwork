package com.lvshandian.asktoask.module.answer.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
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
import com.lvshandian.asktoask.utils.TextUtils;
import com.lvshandian.asktoask.view.MyImageView;
import com.lvshandian.asktoask.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * on 2016/9/24.
 * 答咖认证学生证界面
 */
public class ApproveStudentZhengActivity extends BaseActivity {
    @Bind(R.id.iv_answer_activity_back)
    ImageView ivAnswerActivityBack;
    @Bind(R.id.tv_add_student_zheng)
    TextView tvAddStudentZheng;
    @Bind(R.id.tv_next_step)
    TextView tvNextStep;
    @Bind(R.id.ll_root_popuwindws)
    LinearLayout llRootPopuwindws;
    final int TAKE_PICTURE = 1;
    final int CHOOSE_PICTURE = 0;
    @Bind(R.id.tv_takephpto_student_zheng)
    MyImageView tvTakephptoStudentZheng;
    public static String imagestudentPath = "";
    Bitmap bm;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_approve_student_zheng;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void initListener() {
        ivAnswerActivityBack.setOnClickListener(this);
        tvAddStudentZheng.setOnClickListener(this);
        tvNextStep.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_answer_activity_back:
                finish();
                break;
            case R.id.tv_add_student_zheng:
                showAlert();
                break;
            case R.id.tv_next_step:
                if(TextUtils.isEmpty(imagestudentPath)){
                    ToastUtils.showSnackBar(snackView, "请选择图片");
                }else{
                    ToastUtils.showSnackBar(snackView, "图片保存成功");
                    finish();
                }

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
                                //图片每次都保存在这里
                                File file = new File(Environment.getExternalStorageDirectory() + "/001.jpg");
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE://拍照获取照片
//                if(data!=null){
//                    Bundle extras = data.getExtras();
//                    if (extras != null) {
//                        Bitmap photo = extras.getParcelable("data");
//                        tvAddStudentZheng.setVisibility(View.GONE);
//                        tvTakephptoStudentZheng.setVisibility(View.VISIBLE);
//                        Log.d("aaa","photo"+photo);
//                        tvTakephptoStudentZheng.setImageBitmap(photo);
//
//
//                    }
//                    imagestudentPath=Environment.getExternalStorageDirectory()+"/001.jpg";//学生证图片
//
//                }else{
//
//                    File file = new File(imagestudentPath);
//                    if (file.exists()) {
//                        Bitmap bm = BitmapFactory.decodeFile(filepath);
//                        //将图片显示到ImageView中
//                        img.setImageBitmap(bm);
//                    }
//                }

                imagestudentPath = Environment.getExternalStorageDirectory() + "/001.jpg";//学生证图片
                tvAddStudentZheng.setVisibility(View.GONE);
                tvTakephptoStudentZheng.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage("file:///"+imagestudentPath,tvTakephptoStudentZheng);
//                File file = new File(imagestudentPath);
//                if (file.exists()) {
//                    bm = BitmapFactory.decodeFile(imagestudentPath);
//                    //将图片显示到ImageView中
//                    try {
//                        tvTakephptoStudentZheng.setImageBitmap(PicUtils.comPress(bm));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//
//                }


                break;
            case CHOOSE_PICTURE://从相册获取照片

//                    Bundle extras = data.getExtras();

                Uri originalUri = data.getData();        //获得图片的uri
                tvAddStudentZheng.setVisibility(View.GONE);
                tvTakephptoStudentZheng.setVisibility(View.VISIBLE);
                //从相册获取到的图片路径
                imagestudentPath = getRealFilePath(originalUri);
//                File filealbunm = new File(imagestudentPath);
//                if (filealbunm.exists()) {
//                    bm = BitmapFactory.decodeFile(imagestudentPath);
//                    //将图片显示到ImageView中
//                    try {
//                        tvTakephptoStudentZheng.setImageBitmap(PicUtils.comPress(bm));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                }
                ImageLoader.getInstance().displayImage("file:///"+imagestudentPath,tvTakephptoStudentZheng);

                break;


        }


    }


    /**
     * 拍照从手机相册获取到图片路径
     *
     * @param uri
     * @return
     */
    public String getRealFilePath(final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


    @Override
    protected void onDestroy() {
        if(bm!=null){
            bm.recycle();
        }

        super.onDestroy();
    }


}
