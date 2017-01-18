package com.lvshandian.asktoask.module.postquestion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.king.photo.activity.AlbumActivity;
import com.king.photo.activity.GalleryActivity;
import com.king.photo.util.ImageItem;
import com.king.photo.util.PublicWay;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.entry.DiscountCoupon;
import com.lvshandian.asktoask.entry.User;
import com.lvshandian.asktoask.module.interaction.InteractionFragment;
import com.lvshandian.asktoask.module.postquestion.pic.Bimp;
import com.lvshandian.asktoask.module.postquestion.pic.FileUtils;
import com.lvshandian.asktoask.module.postquestion.pic.PhotoActivity;
import com.lvshandian.asktoask.module.postquestion.pic.TestPicActivity;
import com.lvshandian.asktoask.utils.DialogView;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.InputMethodUtils;
import com.lvshandian.asktoask.utils.L;
import com.lvshandian.asktoask.utils.MethodUtils;
import com.lvshandian.asktoask.utils.PermisionUtils;
import com.lvshandian.asktoask.utils.Res;
import com.lvshandian.asktoask.utils.TextUtils;
import com.lvshandian.asktoask.view.LoadingDialog;
import com.lvshandian.asktoask.view.MyGridView;
import com.lvshandian.asktoask.utils.ToastUtils;
import com.lvshandian.asktoask.widgets.GenderPopupWindow;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;

/**
 * 发布问题 on 2016/9/26.
 */
public class PostQuestionActivity extends BaseActivity {
    @Bind(R.id.tv_hudong_detail_type)
    TextView tvHudongDetailType;
    @Bind(R.id.ll_question_money)
    LinearLayout llQuestionMoney;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_reward)
    TextView tvReward;
    @Bind(R.id.tv_ask_question)
    TextView tvAskQuestion;
    @Bind(R.id.et_ask_question)
    EditText etAskQuestion;
    @Bind(R.id.et_ask_content)
    EditText etAskContent;
    @Bind(R.id.ll_compon)
    LinearLayout llCompon;
    @Bind(R.id.tv_reward_0)
    TextView tvReward0;
    @Bind(R.id.tv_reward_1)
    TextView tvReward1;
    @Bind(R.id.tv_reward_2)
    TextView tvReward2;
    @Bind(R.id.tv_reward_5)
    TextView tvReward5;
    @Bind(R.id.tv_reward_11)
    TextView tvReward11;
    @Bind(R.id.tv_reward_15)
    TextView tvReward15;
    @Bind(R.id.tv_reward_25)
    TextView tvReward25;
    @Bind(R.id.tv_reward_50)
    TextView tvReward50;
    @Bind(R.id.tv_reward_100)
    TextView tvReward100;
    @Bind(R.id.tv_reward_200)
    TextView tvReward200;
    @Bind(R.id.tv_classify)
    TextView tvClassify;
    @Bind(R.id.ll_classify)
    LinearLayout llClassify;
    @Bind(R.id.tv_price_total)
    TextView tvPriceTotal;
    @Bind(R.id.et_input_money)
    EditText etInputMoney;
    @Bind(R.id.tv_coupon)
    TextView tvCoupon;
    @Bind(R.id.ck_niming)
    CheckBox ckNiming;
    @Bind(R.id.rl_reward)
    RelativeLayout rlReward;
    @Bind(R.id.rl_reward_money)
    RelativeLayout rlRewardMoney;
    @Bind(R.id.noScrollgridview)
    MyGridView noScrollgridview;
    @Bind(R.id.tv_show_nums_post_question)
    TextView tvShowNumsPostQuestion;
    @Bind(R.id.ll_content)
    RelativeLayout llContent;
    @Bind(R.id.ll_root_popuwindws)
    LinearLayout llRootPopuwindws;
    private String rewardmoney = "0";
    //    "匿名传1" 不是匿名传0
    private String isniming = "0";
    DiscountCoupon discountCoupon;//发布问题优惠劵
    public static final int recodecoupon = 101;
    final int CHOOSE_PICTURE = 0;
    private Bitmap bimap;
    LoadingDialog mDialog;

    public static boolean paysuccess = false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RequestCode.FILE_DATA:
                    Bundle data = msg.getData();
                    String json = data.getString(HttpDatas.info);

                    Log.e("json", json.toString());


                    break;
            }

        }
    };

    @Override
    protected void initialized() {
        EventBus.getDefault().register(this);
        mDialog = new LoadingDialog(PostQuestionActivity.this);
        Res.init(this);
        bimap = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.add_pic_postquestion);
        //处理微信发表图片
        bimap = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.add_pic_postquestion);

        indate();

    }

    private MyGridView noScrollgridview_pic;
    private GridAdapter adapter;
    private TextView activity_selectimg_send;

    //图库
    private void indate() {

        noScrollgridview = (MyGridView) findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this);
        adapter.update();
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 == Bimp.bmp.size()) {
                    new PopupWindows(PostQuestionActivity.this,
                            noScrollgridview);
                } else {
                    Intent intent = new Intent(PostQuestionActivity.this,
                            PhotoActivity.class);
                    intent.putExtra("ID", arg2);
                    startActivity(intent);
                }
            }
        });
//        activity_selectimg_send = (TextView) findViewById(R.id.activity_selectimg_send);
//        activity_selectimg_send.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                List<String> list = new ArrayList<String>();
//                for (int i = 0; i < Bimp.drr.size(); i++) {
//                    String Str = Bimp.drr.get(i).substring(
//                            Bimp.drr.get(i).lastIndexOf("/") + 1,
//                            Bimp.drr.get(i).lastIndexOf("."));
//                    list.add(FileUtils.SDPATH + Str + ".JPEG");
//                }
//                // 高清的压缩图片全部就在 list 路径里面了
//                // 高清的压缩过的 bmp 对象 都在 Bimp.bmp里面
//                // 完成上传服务器后 .........
//                Log.e("list", list.toString());
//                FileUtils.deleteDir();
//            }
//        });
    }

    public class PopupWindows extends PopupWindow {

        public PopupWindows(final Context mContext, View parent) {

            View view = View.inflate(mContext, R.layout.item_popupwindowss,
                    null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view
                    .findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.push_bottom_in_2));

            setWidth(LayoutParams.FILL_PARENT);
            setHeight(LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();

            Button bt1 = (Button) view
                    .findViewById(R.id.item_popupwindows_camera);
            Button bt2 = (Button) view
                    .findViewById(R.id.item_popupwindows_Photo);
            Button bt3 = (Button) view
                    .findViewById(R.id.item_popupwindows_cancel);
            bt1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    PermisionUtils.newInstance().checkCameraPermission(getWindow().getDecorView().findViewById(android.R.id.content), new PermisionUtils.OnPermissionGrantedLintener() {
                        @Override
                        public void permissionGranted() {
                            photo();
                            dismiss();
                        }
                    });

                    dismiss();
                }
            });
            bt2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    PermisionUtils.newInstance().checkWriteStoragePermission(getWindow().getDecorView().findViewById(android.R.id.content), new PermisionUtils.OnPermissionGrantedLintener() {
                        @Override
                        public void permissionGranted() {

                            Intent intent = new Intent(PostQuestionActivity.this,
                                    TestPicActivity.class);
                            startActivity(intent);
                            dismiss();
                        }
                    });

                    dismiss();
                }
            });
            bt3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });

        }
    }

    public boolean isThread = false;

    @SuppressLint("HandlerLeak")
    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater; // 视图容器
        private int selectedPosition = -1;// 选中的位置
        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void update() {
            loading();
        }

        public int getCount() {
            return (Bimp.bmp.size() + 1);
        }

        public Object getItem(int arg0) {

            return null;
        }

        public long getItemId(int arg0) {

            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        /**
         * ListView Item设置
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            final int coord = position;
            ViewHolder holder = null;
            if (convertView == null) {

                convertView = inflater.inflate(R.layout.item_published_gridaa,
                        parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == Bimp.bmp.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.icon_addpic_unfocused));
                if (position == 9) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(Bimp.bmp.get(position));
            }

            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        adapter.notifyDataSetChanged();
                        break;
                }
                super.handleMessage(msg);
            }
        };


        public void loading() {
            isThread = true;

            new Thread(new Runnable() {
                public void run() {

                    if (Bimp.drr.size() == 0) {
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                        return;
                    }
                    for (int i = 0; i < Bimp.max; i++) {
                        String path = Bimp.drr.get(i);
                        Bitmap bm = Bimp.revitionImageSize(path);
                        FileUtils.saveBitmap(bm, i + 1 + "");
                    }
                    while (isThread) {

                        if (Bimp.max == Bimp.drr.size()) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            break;
                        } else {
//                            try {
                            if (Bimp.drr.size() == 0) {
                                return;
                            }

                            String path = Bimp.drr.get(Bimp.max);
                            System.out.println(path);
                            Bitmap bm = Bimp.revitionImageSize(path);
                            Bimp.bmp.add(bm);
                            Log.e("zyn1Bimp", Bimp.max + "");
                            String newStr = path.substring(
                                    path.lastIndexOf("/") + 1,
                                    path.lastIndexOf("."));
                            FileUtils.saveBitmap(bm, Bimp.max + 1 + "");
                            Bimp.max += 1;
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
//                            } catch (IOException e) {
//
//                                e.printStackTrace();
//                            }
                        }
                    }
                }
            }).start();
        }

    }

    public String getString(String s) {
        String path = null;
        if (s == null)
            return "";
        for (int i = s.length() - 1; i > 0; i++) {
            s.charAt(i);
        }
        return path;
    }

    protected void onRestart() {
        indate();
        super.onRestart();
    }


    private static final int TAKE_PICTURE = 0x000000;
    private String path = "";

    public void photo() {


        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        openCameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        File out = new File(getPhotopath());
        path = out.getPath();
        Uri uri = Uri.fromFile(out);
        // 获取拍照后未压缩的原图片，并保存在uri路径中
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);

    }

    /**
     * 获取原图片存储路径  拍照时就是这个地址
     *
     * @return
     */
//    private String getPhotopath() {
//        // 照片全路径
//        String fileName = "";
//        // 文件夹路径
//        String pathUrl = Environment.getExternalStorageDirectory() + "/mymy/";
//        String imageName = "imageTest.jpg";
//        File file = new File(pathUrl);
//        file.mkdirs();// 创建文件夹
//        fileName = pathUrl + imageName;
//        return fileName;
//    }
    private String getPhotopath() {
        // 照片全路径
        String fileName = "";
        // 文件夹路径
        String pathUrl = Environment.getExternalStorageDirectory()
                + "/formats/";

        String imageName = String.valueOf(System.currentTimeMillis()) + ".jpg";
//        String imageName = Bimp.max + 1 + ".jpg";
        File file = new File(pathUrl);
        file.mkdirs();// 创建文件夹
        fileName = pathUrl + imageName;
        return fileName;
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (paysuccess) {
            goToPostQuestion();
        }
        adapter.notifyDataSetChanged();
    }


    /**
     * 发布问题
     */
    public void goToPostQuestion() {
//        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
//
//        httpDatas.getData("上半屏数据", Request.Method.POST, UrlBuilder.POSTASKQUESTION_URL, map, mHandler, RequestCode.FILE_DATA, false);


        RequestParams params = new RequestParams();
        params.addBodyParameter("userId", Global.getUserId(getContext()));
        params.addBodyParameter("questionTitle", etAskQuestion.getText().toString());
        params.addBodyParameter("questionData", etAskContent.getText().toString());
        params.addBodyParameter("rewardMoney", Float.parseFloat(tvPriceTotal.getText().toString()) + "");//悬赏金额
        params.addBodyParameter("isanonymity", isniming);//是否匿名
        params.addBodyParameter("questionType", tvClassify.getText().toString());//问题类型
        params.addBodyParameter("finalMoney", Float.parseFloat(tvPriceTotal.getText().toString()) + "");//悬赏金额减去代金劵
        Log.e("finalMoney", Float.parseFloat(tvPriceTotal.getText().toString()) + "");
        if (CouponActivity.issave) {
            params.addBodyParameter("discountCouponId", discountCoupon.getDiscountCouponId());//用户代金劵id
            params.addBodyParameter("voucherMoney", discountCoupon.getDiscountCouponMoney());//代金劵
//            params.addBodyParameter("finalMoney", (Float.parseFloat(tvPriceTotal.getText().toString()) - Float.parseFloat(tvCoupon.getText().toString())) + "");//悬赏金额减去代金劵
        } else {

            params.addBodyParameter("discountCouponId", "");
            params.addBodyParameter("voucherMoney", "0");//代金劵
        }
        //发布图片
        List<String> list = new ArrayList<String>();

        List<NameValuePair> formparams = new ArrayList<NameValuePair>();

        for (int i = 0; i < Bimp.drr.size(); i++) {
            String Str = Bimp.drr.get(i).substring(
                    Bimp.drr.get(i).lastIndexOf("/") + 1,
                    Bimp.drr.get(i).lastIndexOf("."));
            int f = i + 1;
            list.add(FileUtils.SDPATH + f + ".jpg");
            Log.e("lsit", FileUtils.SDPATH + f + ".jpg");


            params.addBodyParameter("file" + f, new File(Environment.getExternalStorageDirectory()
                    + "/formats/" + f + ".jpg"));

        }

        if (paysuccess) {
            params.addBodyParameter("z", "z");//支付宝或者微信的话z="z"
            PostQuestionPayActivity.weinxinpay = false;
            PostQuestionPayActivity.zhifubao = false;
        } else {
            params.addBodyParameter("z", "");
        }
        HttpUtils http = new HttpUtils();
//        try {
//            ResponseStream responseStream = http.sendSync(HttpRequest.HttpMethod.POST,
//                    com.lvshandian.asktoask.utils.UrlBuilder.serverUrl + UrlBuilder.POSTASKQUESTION_URL,
//                    params);
//            ToastUtils.showSnackBar(snackView, "发布问题成功");
//            if (responseStream.getStatusCode() == 200) {
//                Bimp.drr.clear();
//                ToastUtils.showSnackBar(snackView, "发布问题成功");
//                paysuccess = false;
//                CouponActivity.issave = false;
//                tvPriceTotal.setText("0");
//                isniming = "0";
//                finish();
//            }

//        } catch (HttpException e) {
//            e.printStackTrace();
//        }


        http.send(HttpRequest.HttpMethod.POST,
                com.lvshandian.asktoask.utils.UrlBuilder.serverUrl + UrlBuilder.POSTASKQUESTION_URL,
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                        User.getUser().getUser_money();
                        if (!mDialog.isShowing()) {
                            mDialog.show();
                        }
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        try {
                            JSONObject object = new JSONObject(result);
                            if ("金额不足!".equals(object.getString("msg"))) {//去支付界面
                                mDialog.dismiss();
                                new DialogView(getContext(), findViewById(R.id.ll_root_popuwindws), 0, "余额不足,是否去支付界面", "确定", "取消",
                                        new DialogView.MyCallback() {
                                            @Override
                                            public void refreshActivity() {
                                                goToPay();//去支付界面
                                            }
                                        });
                                return;


                            } else if ("OK".equals(object.getString("msg"))) {
                                mDialog.dismiss();
                                Bimp.drr.clear();
                                ToastUtils.showSnackBar(snackView, "发布问题成功");
                                paysuccess = false;
                                CouponActivity.issave = false;
                                tvPriceTotal.setText("0");
                                isniming = "0";
                                finish();
                            }
                        } catch (org.json.JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                    }
                });

    }


    @Subscribe
    public void onEventMainThread(String suc) {
        ToastUtils.showSnackBar(snackView, "问题发布成功");
        Log.d("aaa", "发布问题的金额" + Float.parseFloat(tvPriceTotal.getText().toString()) + "");
        goToPostQuestion();
    }

    @Override
    protected void onDestroy() {
        Bimp.drr.clear();
        Bimp.bmp.clear();
        Bimp.max = 0;
        Bimp.act_bool = true;
        PostQuestionActivity.this.finish();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(this);
        tvReward.setOnClickListener(this);
        tvAskQuestion.setOnClickListener(this);
        llCompon.setOnClickListener(this);
        tvReward0.setOnClickListener(this);
        tvReward1.setOnClickListener(this);
        tvReward2.setOnClickListener(this);
        tvReward5.setOnClickListener(this);
        tvReward11.setOnClickListener(this);
        tvReward15.setOnClickListener(this);
        tvReward25.setOnClickListener(this);
        tvReward50.setOnClickListener(this);
        tvReward100.setOnClickListener(this);
        tvReward200.setOnClickListener(this);
        llClassify.setOnClickListener(this);
        rlReward.setOnClickListener(this);
        etInputMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(etInputMoney.getText().toString())) {
                    tvPriceTotal.setText("0");
                    tvCoupon.setText("无");
                } else {
                    if (tvCoupon.getText().toString().equals("无")) {
                        tvPriceTotal.setText(Integer.parseInt(etInputMoney.getText().toString()) + "");
                    } else {
                        tvPriceTotal.setText(Integer.parseInt(etInputMoney.getText().toString() + "") - Integer.parseInt(tvCoupon.getText().toString()) + "");
//                        rewardmoney = etInputMoney.getText().toString();
                    }
                }
                tvReward0.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward1.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward2.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward5.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward11.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward15.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward25.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward50.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward100.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward200.setBackgroundResource(R.drawable.post_money_unselect);
            }
        });
        etInputMoney.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {//获得焦点
                    tvReward0.setBackgroundResource(R.drawable.post_money_unselect);
                    tvReward1.setBackgroundResource(R.drawable.post_money_unselect);
                    tvReward2.setBackgroundResource(R.drawable.post_money_unselect);
                    tvReward5.setBackgroundResource(R.drawable.post_money_unselect);
                    tvReward11.setBackgroundResource(R.drawable.post_money_unselect);
                    tvReward15.setBackgroundResource(R.drawable.post_money_unselect);
                    tvReward25.setBackgroundResource(R.drawable.post_money_unselect);
                    tvReward50.setBackgroundResource(R.drawable.post_money_unselect);
                    tvReward100.setBackgroundResource(R.drawable.post_money_unselect);
                    tvReward200.setBackgroundResource(R.drawable.post_money_unselect);
                    etInputMoney.setText("");
                }
            }
        });


        etAskContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvShowNumsPostQuestion.setText(etAskContent.getText().toString().length() + "/300");
            }
        });


        ckNiming.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isniming = "1";//1是匿名
                } else {
                    isniming = "0";
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                new DialogView(getContext(), findViewById(R.id.ll_root_popuwindws), 0, "是否放弃编辑", "确定", "取消",
                        new DialogView.MyCallback() {


                            @Override
                            public void refreshActivity() {
                                isThread = false;
                                Bimp.drr.clear();
                                Bimp.bmp.clear();
                                Bimp.max = 0;
                                Bimp.act_bool = true;
                                PostQuestionActivity.this.finish();
                            }
                        });
                break;
            case R.id.rl_reward:
                if (rlRewardMoney.getVisibility() == View.VISIBLE) {
                    rlRewardMoney.setVisibility(View.GONE);
                } else {
                    rlRewardMoney.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_ask_question:
                if (panDuan()) {

                    Log.d("aaa", "打印传过去的值" + Float.parseFloat(tvPriceTotal.getText().toString()));


                    //发布问题
                    goToPostQuestion();
                }
                break;
            case R.id.ll_compon://去优惠劵
                goToConponActivity();
                break;
            case R.id.ll_classify://选择分类
                int size = InteractionFragment.listtypes.size();
                String[] arr = InteractionFragment.listtypes.toArray(new String[size]);//使用了第二种接口，返回值和参数均为结
                GenderPopupWindow popupWindow = new GenderPopupWindow(this, arr, 2, "请选择问题类型");
                popupWindow.setGetGenderListener(new GenderPopupWindow.GenderListener() {
                    @Override
                    public void getGender(String gender) {
                        tvClassify.setText(gender);
                        User.getUser().setUser_sex(gender);
                    }
                });
                popupWindow.showPopupWindow(findViewById(R.id.ll_root_popuwindws));
                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.5f;
                getWindow().setAttributes(lp);
                break;

            case R.id.tv_reward_0:
                rewardmoney = "0";
                etInputMoney.setText("");
                tvReward0.setBackgroundResource(R.drawable.post_money_select);//tv_reward_0
                tvReward1.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward2.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward5.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward11.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward15.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward25.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward50.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward100.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward200.setBackgroundResource(R.drawable.post_money_unselect);
                tvPriceTotal.setText("0");
                etInputMoney.clearFocus();
                tvCoupon.setText("无");
                break;
            case R.id.tv_reward_1:
                rewardmoney = "1";
                etInputMoney.setText("");
                tvReward0.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward1.setBackgroundResource(R.drawable.post_money_select);
                tvReward2.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward5.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward11.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward15.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward25.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward50.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward100.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward200.setBackgroundResource(R.drawable.post_money_unselect);
                tvPriceTotal.setText("1");
                etInputMoney.clearFocus();
                tvCoupon.setText("无");

                break;
            case R.id.tv_reward_2:
                rewardmoney = "2";
                etInputMoney.setText("");
                tvReward0.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward1.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward2.setBackgroundResource(R.drawable.post_money_select);
                tvReward5.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward11.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward15.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward25.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward50.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward100.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward200.setBackgroundResource(R.drawable.post_money_unselect);
                tvPriceTotal.setText("2");
                etInputMoney.clearFocus();
                tvCoupon.setText("无");
                break;
            case R.id.tv_reward_5:
                rewardmoney = "5";
                etInputMoney.setText("");
                tvReward0.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward1.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward2.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward5.setBackgroundResource(R.drawable.post_money_select);
                tvReward11.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward15.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward25.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward50.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward100.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward200.setBackgroundResource(R.drawable.post_money_unselect);
                tvPriceTotal.setText("5");
                etInputMoney.clearFocus();
                tvCoupon.setText("无");
                break;
            case R.id.tv_reward_11:
                rewardmoney = "11";
                etInputMoney.setText("");
                tvReward0.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward1.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward2.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward5.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward11.setBackgroundResource(R.drawable.post_money_select);
                tvReward15.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward25.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward50.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward100.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward200.setBackgroundResource(R.drawable.post_money_unselect);
                tvPriceTotal.setText("11");
                etInputMoney.clearFocus();
                tvCoupon.setText("无");
                break;
            case R.id.tv_reward_15:
                rewardmoney = "15";
                etInputMoney.setText("");
                tvReward0.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward1.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward2.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward5.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward11.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward15.setBackgroundResource(R.drawable.post_money_select);
                tvReward25.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward50.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward100.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward200.setBackgroundResource(R.drawable.post_money_unselect);
                tvPriceTotal.setText("15");
                etInputMoney.clearFocus();
                tvCoupon.setText("无");
                break;
            case R.id.tv_reward_25:
                rewardmoney = "25";
                etInputMoney.setText("");
                tvReward0.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward1.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward2.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward5.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward11.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward15.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward25.setBackgroundResource(R.drawable.post_money_select);
                tvReward50.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward100.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward200.setBackgroundResource(R.drawable.post_money_unselect);
                tvPriceTotal.setText("25");
                etInputMoney.clearFocus();
                tvCoupon.setText("无");
                break;
            case R.id.tv_reward_50:
                rewardmoney = "50";
                etInputMoney.setText("");
                tvReward0.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward1.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward2.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward5.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward11.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward15.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward25.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward50.setBackgroundResource(R.drawable.post_money_select);
                tvReward100.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward200.setBackgroundResource(R.drawable.post_money_unselect);
                tvPriceTotal.setText("50");
                etInputMoney.clearFocus();
                tvCoupon.setText("无");
                break;
            case R.id.tv_reward_100:
                rewardmoney = "100";
                etInputMoney.setText("");
                tvReward0.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward1.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward2.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward5.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward11.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward15.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward25.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward50.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward100.setBackgroundResource(R.drawable.post_money_select);
                tvReward200.setBackgroundResource(R.drawable.post_money_unselect);
                tvPriceTotal.setText("100");
                etInputMoney.clearFocus();
                tvCoupon.setText("无");
                break;
            case R.id.tv_reward_200:
                rewardmoney = "200";
                etInputMoney.setText("");
                tvReward0.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward1.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward2.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward5.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward11.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward15.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward25.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward50.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward100.setBackgroundResource(R.drawable.post_money_unselect);
                tvReward200.setBackgroundResource(R.drawable.post_money_select);
                tvPriceTotal.setText("200");
                etInputMoney.clearFocus();
                tvCoupon.setText("无");
                break;
        }
    }

    /**
     * 优惠劵 发布问题
     */

    private void goToConponActivity() {
        Intent intent = new Intent(this, CouponActivity.class);
        intent.putExtra("pricetotal", tvPriceTotal.getText().toString() + "");
        startActivityForResult(intent, recodecoupon);

    }

    /**
     * 去支付
     */

    private void goToPay() {
        Intent intent = new Intent(this, PostQuestionPayActivity.class);
        intent.putExtra("price", tvPriceTotal.getText().toString());
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

        }
        switch (requestCode) {
            case TAKE_PICTURE:
                if (Bimp.drr.size() < 9 && resultCode == -1) {
                    Bimp.drr.add(path);
                }
                break;
            case recodecoupon://优惠劵回调
                if (CouponActivity.issave) {

                    discountCoupon = CouponActivity.bean;
                    tvCoupon.setText(discountCoupon.getDiscountCouponMoney());
                    if (!TextUtils.isEmpty(etInputMoney.getText().toString() + "")) {
                        tvPriceTotal.setText(Integer.parseInt(etInputMoney.getText().toString() + "") - Integer.parseInt(tvCoupon.getText().toString()) + "");

                    } else {
                        tvPriceTotal.setText(Integer.parseInt(rewardmoney) - Integer.parseInt(tvCoupon.getText().toString()) + "");

                    }

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    String imageName;
    String pathUrl;

    /**
     * 存储缩放的图片
     *
     * @param
     */
    private void saveScalePhoto(Bitmap bitmap) {
        // 照片全路径
        String fileName = "";
        // 文件夹路径
        pathUrl = Environment.getExternalStorageDirectory().getPath() + "/mymy/";
        imageName = String.valueOf(System.currentTimeMillis()) + ".jpg";
        FileOutputStream fos = null;
        File file = new File(pathUrl);
        file.mkdirs();// 创建文件夹
        fileName = pathUrl + file.separator + imageName;
        try {
            fos = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showAlert() {
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
                                        openCameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                                        File out = new File(getPhotopath());
                                        Uri uri = Uri.fromFile(out);
                                        // 获取拍照后未压缩的原图片，并保存在uri路径中
                                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                        startActivityForResult(openCameraIntent, TAKE_PICTURE);

                                    }
                                });
                                break;
                            //调用相册
                            case 2:
                                PermisionUtils.newInstance().checkWriteStoragePermission(getWindow().getDecorView().findViewById(android.R.id.content), new PermisionUtils.OnPermissionGrantedLintener() {
                                    @Override
                                    public void permissionGranted() {
                                        Intent intent = new Intent(PostQuestionActivity.this,
                                                AlbumActivity.class);
                                        startActivity(intent);
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
     * 判断发布问题是否符合要求
     */
    private boolean panDuan() {
        if (TextUtils.isEmpty(tvClassify.getText().toString()) || tvClassify.getText().toString().equals("无")) {
            ToastUtils.showSnackBar(snackView, "请选择分类");
            return false;
        }
        if (TextUtils.isEmpty(etAskQuestion.getText().toString())) {
            ToastUtils.showSnackBar(snackView, "请输入标题");
            return false;
        }
        return true;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_post_question_layout;
    }


}
