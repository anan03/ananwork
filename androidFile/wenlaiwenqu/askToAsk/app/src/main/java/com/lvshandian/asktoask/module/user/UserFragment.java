package com.lvshandian.asktoask.module.user;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.lvshandian.asktoask.BaseFragment;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.entry.User;
import com.lvshandian.asktoask.module.answer.activity.AnswerSelectAddressActivity;
import com.lvshandian.asktoask.module.setting.PersonalInfoActivity;
import com.lvshandian.asktoask.module.setting.SettingActivity;
import com.lvshandian.asktoask.module.setting.InviteFridndsActivity;
import com.lvshandian.asktoask.module.setting.OpinionBackActivity;
import com.lvshandian.asktoask.module.setting.UserAgreementActivity;
import com.lvshandian.asktoask.module.setting.CouponActivity;
import com.lvshandian.asktoask.module.user.activity.UserAnswerListActivity;
import com.lvshandian.asktoask.module.user.activity.UserAttentionListActivity;
import com.lvshandian.asktoask.module.user.activity.UserCollectActivity;
import com.lvshandian.asktoask.module.user.activity.UserFansListActivity;
import com.lvshandian.asktoask.module.user.activity.UserQuizListActivity;
import com.lvshandian.asktoask.module.user.activity.MineRewardActivity;
import com.lvshandian.asktoask.utils.DialogView;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.L;
import com.lvshandian.asktoask.utils.MethodUtils;
import com.lvshandian.asktoask.utils.PermisionUtils;
import com.lvshandian.asktoask.utils.TextUtils;
import com.lvshandian.asktoask.utils.ToastUtils;
import com.lvshandian.asktoask.utils.UpdateImagerUtils;
import com.lvshandian.asktoask.utils.UrlBuilder;
import com.lvshandian.asktoask.widgets.CircleImageView;
import com.lvshandian.asktoask.widgets.PullToZoomScrollView;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import butterknife.Bind;
/**
 * author: ldb on 2016/8/30 17:08.
 * email：lvdabing@lvshandian.com
 * company:北京绿闪电科技有限公司
 * details：用户
 */
public class UserFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.iv_user_icon)
    CircleImageView ivUserIcon;//圆形头像
    @Bind(R.id.tv_user_username)
    TextView tvUserUsername;
    @Bind(R.id.tv_user_tag_xuexiao)
    TextView tvUserTagXuexiao;
    @Bind(R.id.tv_user_tag_zhuanye)
    TextView tvUserTagZhuanye;
    @Bind(R.id.tv_user_tag_nianji)
    TextView tvUserTagNianji;
    @Bind(R.id.tv_user_gexingqianming)
    TextView tvUserGexingqianming;
    @Bind(R.id.ll_mine_huidabtn)
    LinearLayout llMineHuidabtn;
    @Bind(R.id.ll_mine_tiwenbtn)
    LinearLayout llMineTiwenbtn;
    @Bind(R.id.ll_mine_shoucangbtn)
    LinearLayout llMineShoucangbtn;
    @Bind(R.id.ll_mine_guanzhubtn)
    LinearLayout llMineGuanzhubtn;
    @Bind(R.id.ll_mine_fensibtn)
    LinearLayout llMineFensibtn;
    @Bind(R.id.rel_user_yonghuxieyi)
    RelativeLayout reluseryonghuxieyi;
    @Bind(R.id.rel_user_youhuiquan)
    RelativeLayout reluseryouhuiquan;
    @Bind(R.id.rel_mine_setting)
    RelativeLayout relminesetting;
    @Bind(R.id.rel_user_tuijianhaoyou)
    RelativeLayout relusertuijianhaoyou;
    @Bind(R.id.rel_user_wodexuanshang)
    RelativeLayout reluserwodexuanshang;
    @Bind(R.id.rel_user_yijianfankui)
    RelativeLayout reluseryijianfankui;
    @Bind(R.id.tv_user_answernum)
    TextView tvUserAnswernum;
    @Bind(R.id.tv_user_asknum)
    TextView tvUserAsknum;
    @Bind(R.id.tv_user_collect)
    TextView tvUserCollect;
    @Bind(R.id.tv_user_praise)
    TextView tvUserPraise;
    @Bind(R.id.tv_user_fans)
    TextView tvUserFans;
    @Bind(R.id.scrollview)
    PullToZoomScrollView scrollview;
    private final int PESON_COED = 400;
    @Bind(R.id.ll_bg_convert_pic)
    LinearLayout llBgConvertPic;
    protected static final int CHOOSE_PICTURE = 0;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    protected static final int TAKE_PICTURE = 1;
    private Bitmap photo = null;
    ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RequestCode.MINE_UPHAFLSCRRENDATA_REQUESTCODE:
                    Bundle data = msg.getData();
                    String json = data.getString(HttpDatas.info);
                    if (!MethodUtils.isEmpty(json)) {
                        User user = JSON.parseObject(json, User.class);
                        User.getUser().cloneUser(user);
                        tvUserUsername.setText(User.getUser().getUser_real_name());
                        tvUserGexingqianming.setText(User.getUser().getUser_sign());
                        tvUserFans.setText(User.getUser().getUser_fans());
                        tvUserAsknum.setText(User.getUser().getUser_ask());
                        tvUserPraise.setText(User.getUser().getUser_attentions());
                        tvUserCollect.setText(User.getUser().getUser_collect());
                        tvUserAnswernum.setText(User.getUser().getUser_answer());
                        ImageLoader.getInstance().displayImage(User.getUser().getUser_head_img(), ivUserIcon);//直接从网络上获取图片
                        //后台的的图片地址为空的话 展示默认图片
                        if(!TextUtils.isEmpty(User.getmUser().getExtend1())){
                            Glide.with(mContext).load(User.getmUser().getExtend1()).asBitmap().into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    Drawable drawable = new BitmapDrawable(resource);
                                    llBgConvertPic.setBackground(drawable);
                                }
                            });
                        }else{
                            Resources resources = mContext.getResources();
                            Drawable drawable = resources.getDrawable(R.drawable.mine_icon_bg);
                            llBgConvertPic.setBackground(drawable);
                        }
                        //学校
                        if (TextUtils.isEmpty(User.getUser().getUser_school())) {
                            tvUserTagXuexiao.setVisibility(View.GONE);
                        } else {
                            tvUserTagXuexiao.setVisibility(View.VISIBLE);
                            tvUserTagXuexiao.setText(User.getUser().getUser_school());
                        }
                        //专业
                        if (TextUtils.isEmpty(User.getUser().getUser_major())) {
                            tvUserTagZhuanye.setVisibility(View.GONE);
                        } else {
                            tvUserTagZhuanye.setVisibility(View.VISIBLE);
                            tvUserTagZhuanye.setText(User.getUser().getUser_major());
                        }//年级
                        if (TextUtils.isEmpty(User.getUser().getUser_grade())) {
                            tvUserTagNianji.setVisibility(View.GONE);
                        } else {
                            tvUserTagNianji.setVisibility(View.VISIBLE);
                            tvUserTagNianji.setText(User.getUser().getUser_grade());
                        }
                        if ("男".equals(User.getUser().getUser_sex())) {
                            Drawable sexicon = getResources().getDrawable(R.drawable.sex_men);
                            sexicon.setBounds(0, 0, sexicon.getMinimumWidth(), sexicon.getMinimumHeight());
                            tvUserUsername.setCompoundDrawables(null, null, sexicon, null);
                        } else if ("女".equals(User.getUser().getUser_sex())) {
                            Drawable sexicon = getResources().getDrawable(R.drawable.sex_women);
                            sexicon.setBounds(0, 0, sexicon.getMinimumWidth(), sexicon.getMinimumHeight());
                            tvUserUsername.setCompoundDrawables(null, null, sexicon, null);
                        }

                    }
                    break;
                /**
                 * 图片上传失败
                 */

                case 400:

                    break;

                 /**
                 * 图片上传成功
                 */

                case 200:
                    httpData();//重新进行网络请求
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.user_fragment_layout;
    }

    @Override
    protected void initListener() {
        ivUserIcon.setOnClickListener(this);
        relminesetting.setOnClickListener(this);
        llMineHuidabtn.setOnClickListener(this);
        llMineTiwenbtn.setOnClickListener(this);
        llMineFensibtn.setOnClickListener(this);
        llMineGuanzhubtn.setOnClickListener(this);
        llMineShoucangbtn.setOnClickListener(this);
        reluseryouhuiquan.setOnClickListener(this);
        reluseryonghuxieyi.setOnClickListener(this);
        reluseryijianfankui.setOnClickListener(this);
        relusertuijianhaoyou.setOnClickListener(this);
        reluserwodexuanshang.setOnClickListener(this);
        llBgConvertPic.setOnClickListener(this);//更换图片
    }



    @Override
    protected void initialized() {
    }

    /**
     * 请求网络数据
     */
    private void httpData(){
        map.put("userId", Global.getUserId(getContext()));
        httpDatas.getData("上半屏数据", Request.Method.POST, urlBuilder.MINE_HALFSCREENDATA, map, mHandler, RequestCode.MINE_UPHAFLSCRRENDATA_REQUESTCODE,false);
    }

    @Override
    public void onResume() {
        super.onResume();
        httpData();
        if(AnswerSelectAddressActivity.isissave){
            ToastUtils.showSnackBar(view,"修改成功");
            AnswerSelectAddressActivity.isissave=false;
        }
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_mine_huidabtn:            //回答
                if (Global.isLogin(getContext())) {
                    gotoActivity(UserAnswerListActivity.class, false);
                    return;
                }
                break;
            case R.id.iv_user_icon:            //头像，点击修改个人资料
                if (Global.isLogin(getContext())) {
                    Intent intent = new Intent(getContext(), PersonalInfoActivity.class);
                    getActivity().startActivityForResult(intent, PESON_COED);
                    return;
                }
                break;
            case R.id.ll_mine_tiwenbtn:            //提问
                if (Global.isLogin(getContext())) {
                    gotoActivity(UserQuizListActivity.class, false);
                    return;
                }
                break;
            case R.id.ll_mine_shoucangbtn:         //收藏
                if (Global.isLogin(getContext())) {
                    gotoActivity(UserCollectActivity.class, false);
                    return;
                }
                break;
            case R.id.ll_mine_guanzhubtn:           //关注
                if (Global.isLogin(getContext())) {
                    gotoActivity(UserAttentionListActivity.class, false);
                    return;
                }
                break;
            case R.id.ll_mine_fensibtn:             //粉丝
                if (Global.isLogin(getContext())) {
                    gotoActivity(UserFansListActivity.class, false);
                    return;
                }
                break;
            case R.id.rel_mine_setting:             //设置
                if (Global.isLogin(getContext())) {
                    gotoActivity(SettingActivity.class, false);
                    return;
                }


                break;
            case R.id.rel_user_youhuiquan:           //优惠券
                if (Global.isLogin(getContext())) {
                    gotoActivity(CouponActivity.class, false);
                    return;
                }


                break;
            case R.id.rel_user_wodexuanshang:           //我的悬赏
                if (Global.isLogin(getContext())) {
                    gotoActivity(MineRewardActivity.class, false);
                    return;
                }


                break;
            case R.id.rel_user_yijianfankui:             //意见反馈
                if (Global.isLogin(getContext())) {
                    gotoActivity(OpinionBackActivity.class, false);
                    return;
                }


                break;
            case R.id.rel_user_tuijianhaoyou:             //推荐好友
                if (Global.isLogin(getContext())) {
                    gotoActivity(InviteFridndsActivity.class, false);
                    return;
                }
                break;
            case R.id.rel_user_yonghuxieyi:             //用户协议
                if (Global.isLogin(getContext())) {
                    gotoActivity(UserAgreementActivity.class, false);
                    return;
                }
                break;

            case R.id.ll_bg_convert_pic://换图片
                new DialogView(getContext(), scrollview, "", "相机", "相册", "取消",
                        new DialogView.MyCameraCallback() {
                            @Override
                            public void refreshCallback(int tag) {

                                switch (tag) {
                                    //调用相机
                                    case 1:
                                        PermisionUtils.newInstance().checkCameraPermission(getActivity().getWindow().getDecorView().findViewById(android.R.id.content), new PermisionUtils.OnPermissionGrantedLintener() {
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
                                        PermisionUtils.newInstance().checkWriteStoragePermission(getActivity().getWindow().getDecorView().findViewById(android.R.id.content), new PermisionUtils.OnPermissionGrantedLintener() {
                                            @Override
                                            public void permissionGranted() {


                                                Intent intent;
                                                if (Build.VERSION.SDK_INT < 19) {
                                                    intent = new Intent();
                                                    intent.setAction(Intent.ACTION_GET_CONTENT);
                                                    intent.setType("image/*");
                                                } else {
                                                    intent = new Intent(Intent.ACTION_PICK,
                                                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                                    intent.setType("image/*");
                                                }


                                                startActivityForResult(intent, CHOOSE_PICTURE);
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

            default:
                break;
        }
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case TAKE_PICTURE:
                startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                break;
            case CHOOSE_PICTURE:
                if(data!=null){
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                }
                break;
            case CROP_SMALL_PICTURE:
                if (data != null) {
                    setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                }
                break;
            case   PESON_COED:
                ToastUtils.showSnackBar(view, "修改成功");
                break;

        }


    }


    /**
     * 图片上传
     * @param bitmap
     */
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
                UpdateImagerUtils.uploadFile(getContext(), imagePath, UrlBuilder.serverUrl +"/wlwq/appapp/background.do" , mHandler);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{

        }
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
//            photo = UpdateImagerUtils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
            Drawable drawable = new BitmapDrawable(photo);
            llBgConvertPic.setBackground(drawable);
            uploadPic(photo);//上传图片 我的背景图
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
