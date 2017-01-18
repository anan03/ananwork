package com.lvshandian.asktoask.module.setting;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.volley.Request;
import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.entry.User;
import com.lvshandian.asktoask.module.answer.activity.AnswerSelectAddressActivity;
import com.lvshandian.asktoask.utils.Constant;
import com.lvshandian.asktoask.utils.DialogView;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.L;
import com.lvshandian.asktoask.utils.MethodUtils;
import com.lvshandian.asktoask.utils.PermisionUtils;
import com.lvshandian.asktoask.utils.TextUtils;
import com.lvshandian.asktoask.utils.UpdateImagerUtils;
import com.lvshandian.asktoask.utils.ToastUtils;
import com.lvshandian.asktoask.widgets.CircleImageView;
import com.lvshandian.asktoask.widgets.GenderPopupWindow;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import butterknife.Bind;

/**
 * 个人信息 修改界面
 */
public class PersonalInfoActivity extends BaseActivity {

    @Bind(R.id.tv_titlebar_centertext)
    TextView tvTitlebarCentertext;
    @Bind(R.id.ll_titlebar_zuojiantou)
    LinearLayout llTitlebarZuojiantou;
    @Bind(R.id.tv_titlebar_righttext)//右键确定按钮
    TextView tvTitlebarRighttext;
    @Bind(R.id.iv_gerenxinxi_youjiantou)
    ImageView ivGerenxinxiYoujiantou;
    @Bind(R.id.iv_gerenxinxi_touxiang)
    CircleImageView ivGerenxinxiTouxiang;
    @Bind(R.id.rel_gerenxinxi_qianmingitem)
    RelativeLayout relGerenxinxiQianmingitem;
    @Bind(R.id.rel_gerenxinxi_xingbieitem)
    RelativeLayout relGerenxinxiXingbieitem;
    @Bind(R.id.rel_gerenxinxi_xingmingitem)
    RelativeLayout relGerenxinxiXingmingitem;
    @Bind(R.id.rel_gerenxinxi_xuexiaoitem)
    RelativeLayout relGerenxinxiXuexiaoitem;
    @Bind(R.id.rel_gerenxinxi_nianjiitem)
    RelativeLayout relGerenxinxiNianjiitem;
    @Bind(R.id.rel_gerenxinxi_zhuanyeitem)
    RelativeLayout relGerenxinxiZhuanyeitem;
    @Bind(R.id.rel_gerenxinxi_diquitem)
    RelativeLayout relGerenxinxiDiquitem;
    @Bind(R.id.tv_gerenxinxi_qianmingcontent)
    TextView tvGerenxinxiQianmingcontent;
    @Bind(R.id.tv_gerenxinxi_xingbiecontent)
    TextView tvGerenxinxiXingbiecontent;
    @Bind(R.id.tv_gerenxinxi_xingmingcontent)
    TextView tvGerenxinxiXingmingcontent;
    @Bind(R.id.tv_gerenxinxi_xuexiaocontent)
    TextView tvGerenxinxiXuexiaocontent;
    @Bind(R.id.tv_gerenxinxi_nianjicontent)
    TextView tvGerenxinxiNianjicontent;
    @Bind(R.id.tv_gerenxinxi_fenkezhuanyecontent)
    TextView tvGerenxinxiFenkezhuanyecontent;
    @Bind(R.id.iv_gerenxinxi_diquyoujiantou)
    ImageView ivGerenxinxiDiquyoujiantou;
    @Bind(R.id.tv_gerenxinxi_diqucontent)
    TextView tvGerenxinxiDiqucontent;
    @Bind(R.id.iv_gerenxinxi_qianmingyoujiantou)
    ImageView ivGerenxinxiQianmingyoujiantou;
    @Bind(R.id.iv_gerenxinxi_xingbieyoujiantou)
    ImageView ivGerenxinxiXingbieyoujiantou;
    @Bind(R.id.iv_gerenxinxi_xingmingyoujiantou)
    ImageView ivGerenxinxiXingmingyoujiantou;
    @Bind(R.id.iv_gerenxinxi_xuexiaoyoujiantou)
    ImageView ivGerenxinxiXuexiaoyoujiantou;
    @Bind(R.id.iv_gerenxinxi_nianjiyoujiantou)
    ImageView ivGerenxinxiNianjiyoujiantou;
    @Bind(R.id.iv_gerenxinxi_fenkezhuanyeyoujiantou)
    ImageView ivGerenxinxiFenkezhuanyeyoujiantou;
    @Bind(R.id.ll_root_popuwindws)
    LinearLayout llRootPopuwindws;
    private final int REQUESTCODE_PICK = 100;
    private final int REQUESTCODE_CUTTING = 200;
    private final int REQUESTCODE_ADDRESS = 300;
    private final int RESULT_CODE = 400;
    protected static final int CHOOSE_PICTURE = 0;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    protected static final int TAKE_PICTURE = 1;
    private Bitmap photo = null;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RequestCode.XIUGAIGERENXINXI_COED:
                    User.getUser().setUser_sign(MethodUtils.getTextContent(tvGerenxinxiQianmingcontent));
                    User.getUser().setUser_real_name(MethodUtils.getTextContent(tvGerenxinxiXingmingcontent));
                    User.getUser().setUser_school(MethodUtils.getTextContent(tvGerenxinxiXuexiaocontent));
                    User.getUser().setUser_grade(MethodUtils.getTextContent(tvGerenxinxiNianjicontent));
                    User.getUser().setUser_major(MethodUtils.getTextContent(tvGerenxinxiFenkezhuanyecontent));
                    User.getUser().setArea(MethodUtils.getTextContent(tvGerenxinxiDiqucontent));
                    AnswerSelectAddressActivity.isissave=true;
                    L.d("aaa","地区修改成功了");
                    finish();
                    break;
                case 400:
                    break;
                /**
                 * 图片上传成功
                 */
                case 200:
                    if (photo != null) {
                        //将图片路径保存至sp
                        Global.setParam(getContext(), Constant.IMAGER_HEAD_PATH, UpdateImagerUtils.savePhoto(photo, Environment
                                .getExternalStorageDirectory().getAbsolutePath(), String
                                .valueOf(System.currentTimeMillis())));
                        ivGerenxinxiTouxiang.setImageBitmap(photo);
                        User.getUser().setUser_head_img((String) msg.obj);
                    }
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personalinfo;
    }

    @Override
    protected void initListener() {
        tvTitlebarRighttext.setOnClickListener(this);
        llTitlebarZuojiantou.setOnClickListener(this);
        relGerenxinxiDiquitem.setOnClickListener(this);
        relGerenxinxiNianjiitem.setOnClickListener(this);
        relGerenxinxiXingbieitem.setOnClickListener(this);
        relGerenxinxiZhuanyeitem.setOnClickListener(this);
        relGerenxinxiXuexiaoitem.setOnClickListener(this);
        relGerenxinxiXingmingitem.setOnClickListener(this);
        relGerenxinxiQianmingitem.setOnClickListener(this);
        ivGerenxinxiTouxiang.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        tvTitlebarRighttext.setText("确定");
        tvTitlebarCentertext.setText(R.string.setting_gerenxinxi_titletext);
        tvGerenxinxiDiqucontent.setText(User.getUser().getArea());
        tvGerenxinxiXingbiecontent.setText(User.getUser().getUser_sex());
        tvGerenxinxiNianjicontent.setText(User.getUser().getUser_grade());
        tvGerenxinxiQianmingcontent.setText(User.getUser().getUser_sign());
        tvGerenxinxiXuexiaocontent.setText(User.getUser().getUser_school());
        tvGerenxinxiXingmingcontent.setText(User.getUser().getUser_real_name());
        tvGerenxinxiFenkezhuanyecontent.setText(User.getUser().getUser_major());

        if (!TextUtils.isEmpty((String) Global.getParam(getContext(), Constant.IMAGER_HEAD_PATH, ""))) {
            File file = new File((String) Global.getParam(getContext(), Constant.IMAGER_HEAD_PATH, ""));
            if (file.exists()) {
                ImageLoader.getInstance().displayImage("file:///" + (String) Global.getParam(getContext(), Constant.IMAGER_HEAD_PATH, ""), ivGerenxinxiTouxiang);

            } else {

                ImageLoader.getInstance().displayImage(User.getUser().getUser_head_img(), ivGerenxinxiTouxiang);
            }


        } else {
            ImageLoader.getInstance().displayImage(User.getUser().getUser_head_img(), ivGerenxinxiTouxiang);
        }

    }
    public static  boolean isname=false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_titlebar_zuojiantou:
                new DialogView(mContext, llRootPopuwindws, 0, "确定要退出编辑吗", "确定", "取消",
                        new DialogView.MyCallback() {


                            @Override
                            public void refreshActivity() {
                                finish();

                            }
                        });


                break;
            case R.id.tv_titlebar_righttext://确认修改
                changeAllInfo();//去修改个人信息
                break;
            case R.id.rel_gerenxinxi_xingbieitem:       //性别
                GenderPopupWindow popupWindow = new GenderPopupWindow(this, new String[]{"男", "女"},1,"请选择性别");
                popupWindow.setGetGenderListener(new GenderPopupWindow.GenderListener() {
                    @Override
                    public void getGender(String gender) {
                        tvGerenxinxiXingbiecontent.setText(gender);
                        User.getUser().setUser_sex(gender);
                    }
                });
                popupWindow.showPopupWindow(findViewById(R.id.ll_root_popuwindws));
                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.5f;
                getWindow().setAttributes(lp);
                break;
            case R.id.rel_gerenxinxi_qianmingitem:      //签名
                goChangeInfo("签名", User.getUser().getUser_sign(), MethodUtils.getTextContent(tvGerenxinxiQianmingcontent));
                break;
            case R.id.rel_gerenxinxi_xingmingitem:      //姓名
                isname=true;
                goChangeInfo("姓名", User.getUser().getUser_real_name(), MethodUtils.getTextContent(tvGerenxinxiXingmingcontent));
                break;
            case R.id.rel_gerenxinxi_xuexiaoitem:       //学校
                goChangeInfo("学校", User.getUser().getUser_school(), MethodUtils.getTextContent(tvGerenxinxiXuexiaocontent));
                break;
            case R.id.rel_gerenxinxi_nianjiitem:        //年级
                goChangeInfo("年级", User.getUser().getUser_grade(), MethodUtils.getTextContent(tvGerenxinxiNianjicontent));
                break;
            case R.id.rel_gerenxinxi_zhuanyeitem:       //专业
                goChangeInfo("分科专业", User.getUser().getUser_major(), MethodUtils.getTextContent(tvGerenxinxiFenkezhuanyecontent));
                break;
            case R.id.rel_gerenxinxi_diquitem:          //地区
                Intent intent = new Intent(new Intent(getContext(), AnswerSelectAddressActivity.class));
                startActivityForResult(intent, REQUESTCODE_ADDRESS);
                break;
            case R.id.iv_gerenxinxi_touxiang:          //头像


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
                                                tempUri = Uri.fromFile(new File(Environment
                                                        .getExternalStorageDirectory(), "image.jpg"));
                                                // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                                                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                                                startActivityForResult(openCameraIntent, TAKE_PICTURE);
                                            }
                                        });



                                        break;
                                    //调用相册
                                    case 2:
                                        PermisionUtils.newInstance().checkWriteStoragePermission(getWindow().getDecorView().findViewById(android.R.id.content), new PermisionUtils.OnPermissionGrantedLintener() {
                                            @Override
                                            public void permissionGranted() {
                                                Intent openAlbumIntent = new Intent(
                                                        Intent.ACTION_GET_CONTENT);
                                                openAlbumIntent.setType("image/*");
                                                startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                                            }
                                        });
                                        break;
                                    //取消
                                    case 3:
                                        break;
                                }
                            }
                        });

                break;


        }
    }


    /**
     * 修改个人信息接口数据
     */
    private void changeAllInfo() {
              if (!Global.isLogin(getContext())) {
                  ToastUtils.showSnackBar(snackView, "请登录");
                    return;}
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("userId", Global.getUserId(getContext()));
        map.put("area", MethodUtils.getTextContent(tvGerenxinxiDiqucontent));
        map.put("userSex", MethodUtils.getTextContent(tvGerenxinxiXingbiecontent));
        map.put("userGrade", MethodUtils.getTextContent(tvGerenxinxiNianjicontent));
        map.put("userSign", MethodUtils.getTextContent(tvGerenxinxiQianmingcontent));
        map.put("userSchool", MethodUtils.getTextContent(tvGerenxinxiXuexiaocontent));
        map.put("userRealName", MethodUtils.getTextContent(tvGerenxinxiXingmingcontent));
        map.put("userMajor", MethodUtils.getTextContent(tvGerenxinxiFenkezhuanyecontent));
        httpDatas.getData("修改个人信息", Request.Method.POST, UrlBuilder.MINE_XIUGAIGERENXINXI_URL, map, mHandler, RequestCode.XIUGAIGERENXINXI_COED);
    }


    /**
     * 打开信息修改详情页
     *
     *
     * 签名
     * @param str1 打开的是属于那个条目的页面
     * @param str2 打开页的hintPerson
     * @param str3 可能需要回显的EditText数据
     */
    private void goChangeInfo(String str1, String str2, String str3) {
        Intent intent = new Intent(this, ChangePersonalInfoActivity.class);
        intent.putExtra(Constant.Change_personal_WhichInfo, str1);
        intent.putExtra(Constant.Change_personal_WhichInfoHint, str2);
        intent.putExtra(Constant.Change_personal_InfoContent, str3);
        startActivityForResult(intent, 12);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == RESULT_OK && requestCode == 12) {
            Bundle bundle = data.getExtras();
            String textcontent = bundle.getString("textcontent");
            switch (bundle.getString(Constant.Change_personal_WhichInfo)) {
                case "签名":
                    tvGerenxinxiQianmingcontent.setText(textcontent);
                    break;
                case "姓名":
                    tvGerenxinxiXingmingcontent.setText(textcontent);
                    break;
                case "学校":
                    tvGerenxinxiXuexiaocontent.setText(textcontent);
                    break;
                case "年级":
                    tvGerenxinxiNianjicontent.setText(textcontent);
                    break;
                case "分科专业":
                    tvGerenxinxiFenkezhuanyecontent.setText(textcontent);
                    break;
                default:
                    break;
            }
        }


        switch (requestCode) {
            case TAKE_PICTURE:
                startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                break;
            case CHOOSE_PICTURE:
                if(data!=null){
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                }
                break;
            case CROP_SMALL_PICTURE://裁剪后的图片
                if (data != null) {
                    setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                }
                break;
            //地区修改
            case REQUESTCODE_ADDRESS:
                if (AnswerSelectAddressActivity.isissave) {
                    tvGerenxinxiDiqucontent.setText(AnswerSelectAddressActivity.place);
                    User.getUser().setArea(AnswerSelectAddressActivity.place);
                }
                break;


            default:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     * @param
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            photo = extras.getParcelable("data");
            uploadPic(photo);//保存和上传图片
        }
    }

    private void uploadPic(Bitmap bitmap) {
        // 上传至服务器
        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了
        String imagePath = UpdateImagerUtils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));
        if (imagePath != null) {
            try {
                UpdateImagerUtils.uploadFile(getContext(), imagePath, UrlBuilder.serverUrl + UrlBuilder.COLLECT_HEAD, mHandler);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

}
