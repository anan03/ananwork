package com.lvshandian.partylive.moudles.mine.my;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.method.NumberKeyListener;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.Request;
import com.lvshandian.partylive.R;
import com.lvshandian.partylive.base.BaseActivity;
import com.lvshandian.partylive.interf.ResultListener;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.httprequest.RequestCode;
import com.lvshandian.partylive.utils.AliYunImageUtils;
import com.lvshandian.partylive.utils.CardTest;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.utils.PermisionUtils;
import com.lvshandian.partylive.utils.TextUtils;
import com.lvshandian.partylive.utils.ToastUtil;
import com.zhy.autolayout.AutoFrameLayout;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;

/**
 * Created by gjj on 2016/11/21.
 */

public class AuthenticationActivity extends BaseActivity {

    @Bind(R.id.iv_image)
    ImageView ivImage;
    @Bind(R.id.afl_layout)
    AutoFrameLayout aflLayout;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_identy_num)
    EditText etIdentyNum;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_identy_name)
    EditText etIdentyName;
    @Bind(R.id.btn_submit)
    Button btnSubmit;


    private TextView tvCamera;
    private TextView tvCancel;
    private TextView tvPhonePicture;
    private PopupWindow popupWindow;
    private Uri tempUri;
    private String imagePath;
    private final int TAKE_PICTURE = 1;
    private final int CHOOSE_PICTURE = TAKE_PICTURE + 1;

    /**
     * 身份证号可以输入的字符
     */
    private char[] ET_IDENTY_NUM = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'X'
    };
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String info = msg.getData().getString(HttpDatas.info);
            switch (msg.what) {
                case RequestCode.REAL_NAME_VERTIFY:
                    LogUtils.e("实名认证: " + info);
                    gotoActivity(RealNameVertifyActivity.class, true);
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_authentication;
    }

    @Override
    protected void initListener() {
        aflLayout.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);


        //身份证号只能输入数字和X
        etIdentyNum.setKeyListener(new NumberKeyListener() {
            @Override
            protected char[] getAcceptedChars() {
                return ET_IDENTY_NUM;
            }

            @Override
            public int getInputType() {
                return InputType.TYPE_CLASS_TEXT;
            }
        });
    }

    @Override
    protected void initialized() {
        initTitle("", "实名认证", null);
        initPopView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_titlebar_left:
                defaultFinish();
                break;
            case R.id.afl_layout:
                //选取图片
                if (popupWindow != null && !popupWindow.isShowing()) {
                    popupWindow.showAtLocation(v, Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                }
                break;
            case R.id.tv_camera:
                //拍照上传
                takeCameraPic();
                popDismiss();
                break;
            case R.id.tv_phone_picture:
                //本地图片
                choseLocalImage();
                popDismiss();
                break;
            case R.id.tv_cancel:
                popDismiss();
                break;
            case R.id.btn_submit:
                if (checkInput()) {
                    submitInfo();
                }
                break;
        }
    }

    /**
     * 提交信息
     */
    private void submitInfo() {

        AliYunImageUtils.newInstance().uploadImage(this, imagePath, new ResultListener() {
            @Override
            public void onSucess(String data) {
                LogUtils.e("阿里云图片地址:　" + data);
                Map<String, String> params = new HashMap<>();
                params.put("userId", appUser.getId());
                params.put("name", etName.getText().toString().trim());
                params.put("phone", etName.getText().toString().trim());
                params.put("idcard", etIdentyNum.getText().toString().trim());
                params.put("veriInfo", etIdentyName.getText().toString().trim());
                params.put("picUrl", data);

                httpDatas.getDataForJson("实名认证", Request.Method.POST, urlBuilder.realInfoAuthen, params, handler, RequestCode.REAL_NAME_VERTIFY);
            }

            @Override
            public void onFaild() {

            }
        });

    }


    /**
     * 输入检验
     *
     * @return
     */
    private boolean checkInput() {
        if (TextUtils.isEmpty(imagePath)) {
            ToastUtil.makeToast("请选择上传图片");
            return false;
        }

        if (TextUtils.isEmpty(TextUtils.getTextContent(etName))) {
            ToastUtil.makeToast("请输入您的真实姓名");
            return false;
        }
        if (TextUtils.isEmpty(TextUtils.getTextContent(etIdentyNum))) {
            ToastUtil.makeToast("请输入您的身份证号");
            return false;
        }

        if (!CardTest.IDCardValidate(TextUtils.getTextContent(etIdentyNum)).endsWith(CardTest.SUCCESS)) {
            ToastUtil.makeToast("身份证号无效");
            return false;
        }

        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone) || !isPhoneNum(phone)) {
            ToastUtil.makeToast("请输入正确的手机号码");
            return false;
        }

        if (TextUtils.isEmpty(TextUtils.getTextContent(etIdentyName))) {
            ToastUtil.makeToast("请输入认证名称");
            return false;
        }
        return true;
    }

    /**
     * 选取本地图片
     */
    private void choseLocalImage() {
        PermisionUtils.newInstance().checkWriteStoragePermission(this, new PermisionUtils.OnPermissionGrantedLintener() {
            @Override
            public void permissionGranted() {
                Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                openAlbumIntent.setType("image/*");
                startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
            }
        });
    }

    /**
     * 取消popWindow
     */
    private void popDismiss() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    /**
     * 拍照上传
     */
    private void takeCameraPic() {
        PermisionUtils.newInstance().checkCallPhonePermission(this, new PermisionUtils.OnPermissionGrantedLintener() {
            @Override
            public void permissionGranted() {
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
                // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                startActivityForResult(openCameraIntent, TAKE_PICTURE);
            }
        });
    }

    /**
     * 头像来源选择popWindow
     */
    private void initPopView() {
        View popView = View.inflate(this, R.layout.pop_header_address, null);

        tvCamera = (TextView) popView.findViewById(R.id.tv_camera);
        tvCancel = (TextView) popView.findViewById(R.id.tv_cancel);
        tvPhonePicture = (TextView) popView.findViewById(R.id.tv_phone_picture);
        if (tvCamera != null) {
            tvCamera.setOnClickListener(this);
        }

        if (tvPhonePicture != null) {
            tvPhonePicture.setOnClickListener(this);
        }
        if (tvCancel != null) {
            tvCancel.setOnClickListener(this);
        }
        int width = FrameLayout.LayoutParams.MATCH_PARENT;
        int height = FrameLayout.LayoutParams.MATCH_PARENT;
        popupWindow = new PopupWindow(popView, width, height);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.alpha_half)));// 颜色设置为透明
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                backImage();
                break;
            case CHOOSE_PICTURE:
                if (data != null && data.getData() != null) {
                    tempUri = data.getData();
                    backImage();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 拍照或选取图片返回的图片
     */
    private void backImage() {
        String pathWithUri = getFilePathWithUri(tempUri, this);
        imagePath = pathWithUri;

        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeFile(pathWithUri, option);
        ivImage.setImageBitmap(bitmap);
    }


    /**
     * 通过URI获取文件的路径
     *
     * @param uri
     * @param activity
     * @return Author JPH
     * Date 2016/6/6 0006 20:01
     */
    public static String getFilePathWithUri(Uri uri, Activity activity) {
        if (uri == null) {
            return null;
        }
        String picturePath = null;
        String scheme = uri.getScheme();
        if ("content".equals(scheme)) {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = activity.getContentResolver().query(uri,
                    filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);  //获取照片路径
            cursor.close();
        } else if ("file".equals(scheme)) {
            picturePath = uri.getPath();
        }
        return picturePath;
    }

    public boolean isPhoneNum(String num) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(14[57])|(17[0])|(17[7])|(18[0,0-9]))\\d{8}$");
        Matcher m = p.matcher(num);
        return m.matches();
    }
}
