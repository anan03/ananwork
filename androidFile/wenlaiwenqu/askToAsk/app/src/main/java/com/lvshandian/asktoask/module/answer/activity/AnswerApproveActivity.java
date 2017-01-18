package com.lvshandian.asktoask.module.answer.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.utils.BitmpTools;
import com.lvshandian.asktoask.utils.DialogView;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.PermisionUtils;
import com.lvshandian.asktoask.utils.TextUtils;
import com.lvshandian.asktoask.view.LoadingDialog;
import com.lvshandian.asktoask.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.io.File;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 答咖认证界面
 */


public class AnswerApproveActivity extends BaseActivity {
    @Bind(R.id.iv_answer_activity_back)
    ImageView ivAnswerActivityBack;
    @Bind(R.id.ll_answer_approve_name)
    LinearLayout llAnswerApproveName;
    @Bind(R.id.ll_answer_approve_address)
    LinearLayout llAnswerApproveAddress;
    @Bind(R.id.btn_answer_submit)
    Button btnAnswerSubmit;
    @Bind(R.id.et_approve_name)
    EditText etApproveName;
    @Bind(R.id.ll_root_popuwindws)
    LinearLayout llRootPopuwindws;
    @Bind(R.id.et_approve_address)
    EditText etApproveAddress;
    @Bind(R.id.et_approve_school)
    EditText etApproveSchool;
    @Bind(R.id.et_approve_class)
    EditText etApproveClass;
    @Bind(R.id.et_approve_job)
    EditText etApproveJob;
    @Bind(R.id.iv_id_card_zheng)
    ImageView ivIdCardZheng;
    @Bind(R.id.iv_id_card_fan)
    ImageView ivIdCardFan;
    @Bind(R.id.iv_student_zheng)
    ImageView ivStudentZheng;
    @Bind(R.id.iv_life_zhao)
    ImageView ivLifeZhao;
    private String imagelifePath = "";
    private String imagestudentzhengpath = "";
    private String imagepathidzheng = "";
    private String imagepathidfan = "";
    final int TAKE_PICTURESTUDENTZHENG = 1;
    final int CHOOSE_PICTURESTUDENTZHENG = 0;
    final int TAKE_PICTURELIFEZHAO = 11;
    final int CHOOSE_PICTURELIFEZHAO = 21;
    final int TAKE_PICTUREIDCARDZHENGZHENG = 111;
    final int CHOOSE_PICTURESHENGZHENGZHENG = 011;
    final int TAKE_PICTUREIDCARDFAN = 1111;
    final int CHOOSE_PICTUREIDCARDFAN = 0111;
    @Override
    protected void initListener() {
        ivAnswerActivityBack.setOnClickListener(this);
        ivLifeZhao.setOnClickListener(this);//生活照
        ivIdCardFan.setOnClickListener(this);//身份证反面
        ivIdCardZheng.setOnClickListener(this);//身份证正面
        btnAnswerSubmit.setOnClickListener(this);//提交
        ivStudentZheng.setOnClickListener(this);//学生证
    }
    BitmapFactory.Options options;

    @Override
    protected void initialized() {
        options= new BitmapFactory.Options();
        options.inSampleSize = 2;
        options.inJustDecodeBounds = true;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_answer_activity_back:
                finish();
                break;
            case R.id.iv_student_zheng:
                showAlertstudentzhegn();//学生证照片
                break;
            case R.id.iv_life_zhao:
                showAlertlifezhao();//生活照
                break;
            case R.id.iv_id_card_zheng:
                showAlertidcardzheng();//身份证正面
                break;
            case R.id.iv_id_card_fan:
                showAlertidcardfan();//身份证反面
                break;
            case R.id.btn_answer_submit:
                GoToApprove();//提交认证信息
                break;
            default:
                break;

        }
    }

    private Uri tempUri;


    //弹出对话框选择图片 学生证

    private void showAlertstudentzhegn() {
        new DialogView(getContext(), llRootPopuwindws, "", "相机", "相册", "取消",
                new DialogView.MyCameraCallback() {
                    @Override
                    public void refreshCallback(int tag) {

                        switch (tag) {
                            //调用相机
                            case 1:
                                PermisionUtils.newInstance().checkCameraPermission(getWindow().getDecorView().findViewById(android.R.id.content), new PermisionUtils.OnPermissionGrantedLintener() {
                                    @Override
                                    public void permissionGranted() {
                                        imagestudentzhengpath = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/"+  System.nanoTime() +".jpg";
                                        File temp = new File(imagestudentzhengpath);
                                        Uri imageFileUri = Uri.fromFile(temp);//获取文件的Uri
                                        Intent openCameraIntent = new Intent(
                                                MediaStore.ACTION_IMAGE_CAPTURE);
                                // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);





//                                        openCameraIntent.addCategory("android.intent.category.DEFAULT");

//                                        File file = new File(Environment.getExternalStorageDirectory() + "/000.jpg");
//                                        Uri uri = Uri.fromFile(file);
//                                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                        startActivityForResult(openCameraIntent, TAKE_PICTURESTUDENTZHENG);
                                    }
                                });




                                break;
                            //调用相册
                            case 2:

                                PermisionUtils.newInstance().checkWriteStoragePermission(getWindow().getDecorView().findViewById(android.R.id.content), new PermisionUtils.OnPermissionGrantedLintener() {
                                    @Override
                                    public void permissionGranted() {


                                        Intent openAlbumIntent;
                                        if (Build.VERSION.SDK_INT < 19) {
                                            openAlbumIntent = new Intent(
                                                    Intent.ACTION_GET_CONTENT);
                                            openAlbumIntent.setType("image/*");

                                        } else {
                                            openAlbumIntent = new Intent(Intent.ACTION_PICK,
                                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                            openAlbumIntent.setType("image/*");

                                        }
                                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURESTUDENTZHENG);


                                    }
                                });

                                break;
                            //取消
                            case 3:
                                break;
                        }
                    }
                });


    }


    /**
     * 生活照
     */
    private void showAlertlifezhao() {
        new DialogView(getContext(), llRootPopuwindws, "", "相机", "相册", "取消",
                new DialogView.MyCameraCallback() {
                    @Override
                    public void refreshCallback(int tag) {

                        switch (tag) {
                            //调用相机
                            case 1:

                                PermisionUtils.newInstance().checkCameraPermission(getWindow().getDecorView().findViewById(android.R.id.content), new PermisionUtils.OnPermissionGrantedLintener() {
                                    @Override
                                    public void permissionGranted() {
                                        Intent openCameraIntent = new Intent(
                                                MediaStore.ACTION_IMAGE_CAPTURE);
                                        imagelifePath =  Environment.getExternalStorageDirectory().getAbsolutePath()+ "/"+  System.nanoTime() +".jpg";
                                        File temp = new File(imagelifePath);
                                        Uri imageFileUri = Uri.fromFile(temp);//获取文件的Uri
//                                        Intent life = new Intent(
//                                                MediaStore.ACTION_IMAGE_CAPTURE);
//                                        openCameraIntent.addCategory("android.intent.category.DEFAULT");
//
//                                        File file = new File(Environment.getExternalStorageDirectory() + "/001.jpg");
//                                        Uri uri = Uri.fromFile(file);
                                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
                                        startActivityForResult(openCameraIntent, TAKE_PICTURELIFEZHAO);
                                    }
                                });

                                break;
                            //调用相册
                            case 2:

                                PermisionUtils.newInstance().checkWriteStoragePermission(getWindow().getDecorView().findViewById(android.R.id.content), new PermisionUtils.OnPermissionGrantedLintener() {
                                    @Override
                                    public void permissionGranted() {
                                        Intent openAlbumIntent;
                                        if (Build.VERSION.SDK_INT < 19) {
                                            openAlbumIntent = new Intent(
                                                    Intent.ACTION_GET_CONTENT);
                                            openAlbumIntent.setType("image/*");

                                        } else {
                                            openAlbumIntent = new Intent(Intent.ACTION_PICK,
                                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                            openAlbumIntent.setType("image/*");

                                        }
                                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURELIFEZHAO);

                                    }
                                });

                                break;
                            //取消
                            case 3:
                                break;
                        }
                    }
                });


    }

    /**
     * 身份证正面
     */

    private void showAlertidcardzheng() {
        new DialogView(getContext(), llRootPopuwindws, "", "相机", "相册", "取消",
                new DialogView.MyCameraCallback() {
                    @Override
                    public void refreshCallback(int tag) {

                        switch (tag) {
                            //调用相机
                            case 1:
                                PermisionUtils.newInstance().checkCameraPermission(getWindow().getDecorView().findViewById(android.R.id.content), new PermisionUtils.OnPermissionGrantedLintener() {
                                    @Override
                                    public void permissionGranted() {
                                        Intent openCameraIntent = new Intent(
                                                MediaStore.ACTION_IMAGE_CAPTURE);
//                                tempUri = Uri.fromFile(new File(Environment
//                                        .getExternalStorageDirectory(), "image.jpg"));
//                                // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
//                                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);


                                        imagepathidzheng= Environment.getExternalStorageDirectory().getAbsolutePath()+ "/"+  System.nanoTime() +".jpg";
                                        File temp = new File(imagepathidzheng);
                                        Uri imageFileUri = Uri.fromFile(temp);//获取文件的Uri

//                                        openCameraIntent.addCategory("android.intent.category.DEFAULT");
//
//                                        File file = new File(Environment.getExternalStorageDirectory() + "/002.jpg");
//                                        Uri uri = Uri.fromFile(file);
                                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
                                        startActivityForResult(openCameraIntent, TAKE_PICTUREIDCARDZHENGZHENG);
                                    }
                                });



                                break;
                            //调用相册
                            case 2:

                                PermisionUtils.newInstance().checkWriteStoragePermission(getWindow().getDecorView().findViewById(android.R.id.content), new PermisionUtils.OnPermissionGrantedLintener() {
                                    @Override
                                    public void permissionGranted() {


                                        Intent openAlbumIntent;
                                        if (Build.VERSION.SDK_INT < 19) {
                                            openAlbumIntent = new Intent(
                                                    Intent.ACTION_GET_CONTENT);
                                            openAlbumIntent.setType("image/*");

                                        } else {
                                            openAlbumIntent = new Intent(Intent.ACTION_PICK,
                                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                            openAlbumIntent.setType("image/*");

                                        }

                                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURESHENGZHENGZHENG);
                                    }
                                });


                                break;
                            //取消
                            case 3:
                                break;
                        }
                    }
                });


    }

    /**
     * 身份证反面
     */


    private void showAlertidcardfan() {
        new DialogView(getContext(), llRootPopuwindws, "", "相机", "相册", "取消",
                new DialogView.MyCameraCallback() {
                    @Override
                    public void refreshCallback(int tag) {

                        switch (tag) {
                            //调用相机
                            case 1:
                                PermisionUtils.newInstance().checkCameraPermission(getWindow().getDecorView().findViewById(android.R.id.content), new PermisionUtils.OnPermissionGrantedLintener() {
                                    @Override
                                    public void permissionGranted() {
                                        Intent openCameraIntent = new Intent(
                                                MediaStore.ACTION_IMAGE_CAPTURE);
//                                tempUri = Uri.fromFile(new File(Environment
//                                        .getExternalStorageDirectory(), "image.jpg"));
//                                // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
//                                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
//
//                                        openCameraIntent.addCategory("android.intent.category.DEFAULT");
//
//                                        File file = new File(Environment.getExternalStorageDirectory() + "/003.jpg");
//                                        Uri uri = Uri.fromFile(file);


                                        imagepathidfan = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/"+  System.nanoTime() +".jpg";
                                        File temp = new File(imagepathidfan);
                                        Uri imageFileUri = Uri.fromFile(temp);//获取文件的Uri

//                                        openCameraIntent.addCategory("android.intent.category.DEFAULT");
//
//                                        File file = new File(Environment.getExternalStorageDirectory() + "/002.jpg");
//                                        Uri uri = Uri.fromFile(file);




                                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
                                        startActivityForResult(openCameraIntent, TAKE_PICTUREIDCARDFAN);
                                    }
                                });


                                break;
                            //调用相册
                            case 2:
                                PermisionUtils.newInstance().checkWriteStoragePermission(getWindow().getDecorView().findViewById(android.R.id.content), new PermisionUtils.OnPermissionGrantedLintener() {
                                    @Override
                                    public void permissionGranted() {

                                        Intent openAlbumIntent;
                                        if (Build.VERSION.SDK_INT < 19) {
                                            openAlbumIntent = new Intent(
                                                    Intent.ACTION_GET_CONTENT);
                                            openAlbumIntent.setType("image/*");

                                        } else {
                                            openAlbumIntent = new Intent(Intent.ACTION_PICK,
                                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                            openAlbumIntent.setType("image/*");

                                        }
                                        startActivityForResult(openAlbumIntent, CHOOSE_PICTUREIDCARDFAN);



                                    }
                                });

                                break;
                            //取消
                            case 3:
                                break;
                        }
                    }
                });


    }







    /**
     * 去认证身份证
     */
    private void goToIdCard() {
        Intent intent = new Intent(this, AnswerIdCardActivity.class);
        startActivity(intent);
    }

    /**
     * 去认证学生证界面
     */
    private void goToStudentZheng() {
        Intent intent = new Intent(this, ApproveStudentZhengActivity.class);
        startActivity(intent);
    }


    /**
     * 去输入专业
     *
     * @return
     */
    private void goToInputJob() {
        Intent intent = new Intent(getContext(), AnswerApproveJobActivity.class);
//        startActivityForResult(intent, EDIJOB);
    }

    /**
     * 去输入班级
     *
     * @return
     */
    private void goToInputClass() {
        Intent intent = new Intent(getContext(), ApproveClassActivity.class);
//        startActivityForResult(intent, EDITCLASS);
    }

    /**
     * 去输入学校
     *
     * @return
     */
    private void goToInputSchool() {
        Intent intent = new Intent(getContext(), ApproveSchoolActivity.class);
//        startActivityForResult(intent, EDISCHOOL);
    }

    Bitmap bmp;



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
//            case EDITCLASS:
//                if (ApproveClassActivity.issave) {
//                    ToastUtils.showSnackBar(snackView, "班级填写成功" + ApproveClassActivity.etAnswerClass.getText().toString());
//                }
//                break;
//            case EDISCHOOL:
//                if (ApproveSchoolActivity.issave) {
//                    ToastUtils.showSnackBar(snackView, "学校填写成功" + ApproveSchoolActivity.etAnswerApproveSchool.getText().toString());
//                }
//                break;
//            case EDIJOB:
//                if (AnswerApproveJobActivity.issave) {
//                    ToastUtils.showSnackBar(snackView, "专业填写成功" + AnswerApproveJobActivity.etAnswerJob.getText().toString());
//                }
//                break;
            case TAKE_PICTURESTUDENTZHENG://拍照获取照片
//                if(data!=null){
//                    Bundle extras = data.getExtras();
//                    if (extras != null) {
////                        photo = extras.getParcelable("data");
//
//
//                    }
//                }
            {

//                imagestudentzhengpath = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/000.jpg";
                Log.d("aaa", "显示学生证图片路径" + imagestudentzhengpath);
                file:///mnt/sdcard/image.png

                ImageLoader.getInstance().displayImage("file:///"+imagestudentzhengpath,ivStudentZheng);





            }

//
//                if (resultCode == Activity.RESULT_OK) {
//                    bmp= BitmapFactory.decodeFile(imagestudentzhengpath);
//                    ivStudentZheng.setImageBitmap(BitmpTools.compressSmallImage(bmp));
//                    bmp.recycle();
//                    bmp=null;
//                }


            break;
            case CHOOSE_PICTURESTUDENTZHENG://从相册获取照片  学生证
                if (data != null) {

                        Uri originalUri = data.getData();
                        imagestudentzhengpath = getRealFilePath(originalUri);
                        //获得图片的uri

                        ImageLoader.getInstance().displayImage("file:///"+imagestudentzhengpath,ivStudentZheng);

                }

                break;
            case TAKE_PICTURELIFEZHAO://拍照获取照片  生活照

//
//                if (resultCode == Activity.RESULT_OK) {
//
//                    bmp = BitmapFactory.decodeFile(imagelifePath,options);
//                    ivLifeZhao.setImageBitmap(BitmpTools.compressSmallImage(bmp));
//
//
////
//                }
            {

//                imagelifePath = Environment.getExternalStorageDirectory().toString()+ "/001.jpg";
                ImageLoader.getInstance().displayImage("file:///"+imagelifePath,ivLifeZhao);




            }


            break;
            case CHOOSE_PICTURELIFEZHAO://从相册获取照片  生活照
                if (data != null) {
                        Uri originalUri = data.getData();
                        imagelifePath = getRealFilePath(originalUri);
                        //获得图片的uri
                        ImageLoader.getInstance().displayImage("file:///"+imagelifePath,ivLifeZhao);


                }

                break;
            case TAKE_PICTUREIDCARDZHENGZHENG://拍照获取照片   身份证正面
//
//                if (resultCode == Activity.RESULT_OK) {
//                    bmp = BitmapFactory.decodeFile(imagepathidzheng);
//                    ivIdCardZheng.setImageBitmap(BitmpTools.compressImage(bmp));
//
//
//                }

            {


//                imagepathidzheng = Environment.getExternalStorageDirectory() + "/002.jpg";
                ImageLoader.getInstance().displayImage("file:///"+imagepathidzheng,ivIdCardZheng);



            }


            break;
            case CHOOSE_PICTURESHENGZHENGZHENG://从相册获取照片  身份证正面
                if (data != null) {
                        Uri originalUri = data.getData();
                        imagepathidzheng = getRealFilePath(originalUri);
                        ImageLoader.getInstance().displayImage("file:///"+imagepathidzheng,ivIdCardZheng);
                        //获得图片的uri



                }

                break;
            case TAKE_PICTUREIDCARDFAN://拍照获取照片  身份证反面

//                if (resultCode == Activity.RESULT_OK) {
//                     bmp = BitmapFactory.decodeFile(imagepathidfan);
//                    ivIdCardFan.setImageBitmap(BitmpTools.compressImage(bmp));
//                    bmp.recycle();
//                    bmp=null;
//
//                }



            {

//                imagepathidfan = Environment.getExternalStorageDirectory() + "/003.jpg";
                ImageLoader.getInstance().displayImage("file:///"+imagepathidfan,ivIdCardFan);


            }


            break;
            case CHOOSE_PICTUREIDCARDFAN://从相册获取照片
                if (data != null) {
                        Uri originalUri = data.getData();
                        imagepathidfan = getRealFilePath(originalUri);
                        ImageLoader.getInstance().displayImage("file:///"+imagepathidfan,ivIdCardFan);
                        //获得图片的uri

                }

                break;

            default:
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


    /**
     * 答咖去认证答师
     */

    private void GoToApprove() {
//        //答咖认证
        picShangChuan();
    }


    /**
     * 答咖认证
     */
    private void picShangChuan() {
        if (tongGuo()) {
            final LoadingDialog mDialog  = new LoadingDialog(AnswerApproveActivity.this);
            RequestParams params = new RequestParams();
            params.addBodyParameter("userId", Global.getUserId(getContext()));
            params.addBodyParameter("userNickName", etApproveName.getText().toString());
            params.addBodyParameter("area", etApproveAddress.getText().toString());
            params.addBodyParameter("userGrade", etApproveClass.getText().toString());
            params.addBodyParameter("userSchool", etApproveSchool.getText().toString());
            params.addBodyParameter("userMajor",etApproveJob.getText().toString());
            params.addBodyParameter("area", etApproveAddress.getText().toString());
            params.addBodyParameter("extend2", etApproveAddress.getText().toString());//答咖认证时候的地址
            params.addBodyParameter("file1", new File(imagelifePath));
            params.addBodyParameter("file2", new File(imagepathidfan));
            params.addBodyParameter("file3", new File(imagepathidzheng));
            params.addBodyParameter("file4", new File(imagestudentzhengpath));

            HttpUtils http = new HttpUtils();
            http.send(HttpRequest.HttpMethod.POST,
                    com.lvshandian.asktoask.utils.UrlBuilder.serverUrl + UrlBuilder.ANSWERMASTERAPPROVE_URL,
                    params,
                    new RequestCallBack<String>() {

                        @Override
                        public void onStart() {
                            if (!mDialog.isShowing()) {
                                mDialog.show();
                            }
                        }

                        @Override
                        public void onLoading(long total, long current, boolean isUploading) {
                        }

                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            String result=responseInfo.result;
                            Log.d("aaa", "返回的数据" + result);
                            ToastUtils.showSnackBar(snackView,"提交成功");
                            finish();
                            mDialog.dismiss();


                        }

                        @Override
                        public void onFailure(HttpException error, String msg) {


                        }
                    });

        }

    }

    /**
     * 判断有些字段用户是不是没有输入
     *
     * @return
     */
    private boolean tongGuo() {
        if (TextUtils.isEmpty(etApproveName.getText().toString())) {
            ToastUtils.showSnackBar(snackView, "请输入姓名");
            return false;
        }
        if (TextUtils.isEmpty(etApproveAddress.getText().toString())) {
            ToastUtils.showSnackBar(snackView, "请输入地址");
            return false;
        }
        if (TextUtils.isEmpty(etApproveSchool.getText().toString())) {
            ToastUtils.showSnackBar(snackView, "请输入学校");
            return false;
        }
        if (TextUtils.isEmpty(etApproveClass.getText().toString())) {
            ToastUtils.showSnackBar(snackView, "请输入年级");
            return false;
        }
        if (TextUtils.isEmpty(etApproveJob.getText().toString())) {
            ToastUtils.showSnackBar(snackView, "请输入专业");
            return false;
        }
        if(TextUtils.isEmpty(imagestudentzhengpath)){
            ToastUtils.showSnackBar(snackView, "请选择学生证照片");
            return false;
        }
        if(TextUtils.isEmpty(imagelifePath)){
            ToastUtils.showSnackBar(snackView, "请选择生活照照片");
            return false;
        }
        if(TextUtils.isEmpty(imagepathidzheng)){
            ToastUtils.showSnackBar(snackView, "请选择身份证正面照片");
            return false;
        }
        if(TextUtils.isEmpty(imagepathidfan)){
            ToastUtils.showSnackBar(snackView, "请选择身份证背面照片");
            return false;
        }


        return true;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_answer_approve;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
