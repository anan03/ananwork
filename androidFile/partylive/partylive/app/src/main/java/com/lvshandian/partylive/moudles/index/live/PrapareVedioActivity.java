package com.lvshandian.partylive.moudles.index.live;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.lvshandian.partylive.R;
import com.lvshandian.partylive.UrlBuilder;
import com.lvshandian.partylive.base.BaseActivity;
import com.lvshandian.partylive.bean.CreatReadyBean;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.httprequest.RequestCode;
import com.lvshandian.partylive.interf.ResultListener;
import com.lvshandian.partylive.utils.AliYunImageUtils;
import com.lvshandian.partylive.utils.BitmapUtils;
import com.lvshandian.partylive.utils.CameraUtil;
import com.lvshandian.partylive.utils.JsonUtil;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.utils.PermisionUtils;
import com.lvshandian.partylive.view.RoundDialog;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;


/**
 * Created by gjj on 2016/12/2.
 */

public class PrapareVedioActivity extends BaseActivity {
    @Bind(R.id.tv_right_now)
    TextView tvRightNow;
    @Bind(R.id.iv_camera_switch)
    ImageView ivCameraSwitch;
    @Bind(R.id.iv_x)
    ImageView ivX;
    @Bind(R.id.layout_header)
    AutoLinearLayout layoutHeader;
    @Bind(R.id.surfaceView)
    SurfaceView surfaceView;

    @Bind(R.id.btn_press)
    TextView btnPress;
    @Bind(R.id.ll_bottom_recoder)
    AutoLinearLayout llBottomRecoder;
    @Bind(R.id.all_picture_befor)
    AutoLinearLayout allPictureBefor;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_re_picture)
    TextView tvRePicture;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.all_picture_after)
    AutoLinearLayout allPictureAfter;
    @Bind(R.id.v_view)
    ImageView vView;
    @Bind(R.id.afl_surface)
    AutoFrameLayout aflSurface;
    @Bind(R.id.iv_image)
    ImageView ivImage;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.btn_normal)
    Button btnNormal;
    @Bind(R.id.arl_buttom)
    AutoRelativeLayout arlButtom;
    @Bind(R.id.v_anim)
    View vAnim;
    @Bind(R.id.btn_secret)
    Button btnSecret;
    @Bind(R.id.btn_files)
    TextView btnFiles;
    @Bind(R.id.all_picture)
    AutoLinearLayout allPicture;
    private SurfaceHolder mHolder;


    private String address = "火星";
    private int height;
    private int ivImageHeight;
    private LocationManager mLocationManager;

    private int screenWidth;
    private int screenHeight;
    private int picHeight;


    //默认前置或者后置相机 这里暂时设置为前置
    private int mCameraId = CAMERA_FRONT;
    private boolean isFrontCamera = true;
    private Camera mCamera;
    private AlphaAnimation mAnim;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {
                //关注请求接收数据
                case RequestCode.START_LIVE:
                    CreatReadyBean creatReadyBean = JsonUtil.json2Bean(json, CreatReadyBean.class);
                    releaseCamera();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("START", creatReadyBean);
                    LogUtils.e("START: " + creatReadyBean.toString());
                    Intent intent = new Intent(mContext, StartLiveActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    defaultFinish();
                    break;
            }
        }
    };
    private RoundDialog mSecretDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_befor_vedio;
    }


    @Override
    protected void initListener() {
        ivCameraSwitch.setOnClickListener(this);
        ivX.setOnClickListener(this);
        btnPress.setOnClickListener(this);
        tvRePicture.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        btnNormal.setOnClickListener(this);
        btnSecret.setOnClickListener(this);
        btnFiles.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initialized() {
        initAima();
        init();
        initQuitDialog();

        ViewTreeObserver viewTreeObserver = vView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                height = vView.getHeight();
                ivImageHeight = ivImage.getHeight();
                LogUtils.e("vViewHeight: " + PrapareVedioActivity.this.height);
            }
        });

        PermisionUtils.newInstance().checkLocationPermission(this, new PermisionUtils.OnPermissionGrantedLintener() {
            @Override
            public void permissionGranted() {
                initLocation();
            }
        });

    }

    private void initLocation() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();// 生成位置提供者的条件
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);// 电量
        criteria.setCostAllowed(true);// 是否允许话费

        String provider = mLocationManager.getBestProvider(criteria, true);
        long minTime = 30 * 1000;// 获取位置的最短时间限制
        float minDistance = 0;// 获取位置的最短距离限制
        mLocationManager.requestLocationUpdates(provider, minTime, minDistance, listener);
    }

    private LocationListener listener = new LocationListener() {

        // 状态变化 连接状态的改变
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        // 定位服务的打卡
        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub

        }

        // 定位服务的关闭
        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onLocationChanged(Location location) {
            double latitude = location.getLatitude();// 纬度
            double longitude = location.getLongitude();// 经度
            float accuracy = location.getAccuracy();// 精确度
            double altitude = location.getAltitude();// 海拔
            LogUtils.e("latitude =" + latitude + "longitude=" + longitude + "\naccuracy=" + accuracy + "\naltitude=" + altitude);

            getCity(latitude, longitude);
        }
    };


    /**
     * 根据经纬度获取城市
     *
     * @param latitude
     * @param longitude
     */
    private void getCity(double latitude, double longitude) {
        Geocoder gc = new Geocoder(this, Locale.getDefault());
        List<Address> locationList = null;
        try {
            locationList = gc.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (locationList != null && locationList.size() > 0) {
            Address address = locationList.get(0);//得到Address实例
            LogUtils.e("Address: " + address.toString());
            String countryName = address.getCountryName();//得到国家名称，比如：中国
            LogUtils.e("countryName = " + countryName);
            String locality = address.getLocality();//得到城市名称，比如：北京市
            LogUtils.e("locality = " + locality);

            tvAddress.setText(locality);
            for (int i = 0; address.getAddressLine(i) != null; i++) {
                String addressLine = address.getAddressLine(i);//得到周边信息，包括街道等，i=0，得到街道名称
                LogUtils.e("addressLine = " + addressLine);
            }
        }
    }

    public void init() {
        mHolder = surfaceView.getHolder();
        mHolder.addCallback(mCallBack);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        vAnim.startAnimation(mAnim);

        String payForVideoChat = appUser.getPayForVideoChat();
        tvMoney.setText(payForVideoChat);
    }


    private SurfaceHolder.Callback mCallBack = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            //在surface创建的时候开启相机预览
            startPreview(mCamera, holder);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            //在相机改变的时候调用此方法， 此时应该先停止预览， 然后重新启动
            mCamera.stopPreview();
            startPreview(mCamera, holder);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            //在destroy的时候释放相机资源
            releaseCamera();
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_camera_switch:
                switchCamera();
                break;
            case R.id.iv_x:
                defaultFinish();
                break;
            case R.id.btn_press:
                PermisionUtils.newInstance().checkCameraPermission(this, new PermisionUtils.OnPermissionGrantedLintener() {
                    @Override
                    public void permissionGranted() {
                        captrue();
                    }
                });

                break;
            case R.id.btn_files:
                PermisionUtils.newInstance().checkWriteStoragePermission(this, new PermisionUtils.OnPermissionGrantedLintener() {
                    @Override
                    public void permissionGranted() {
                        releaseCamera();
                        getPicture();
                    }
                });
                break;
            case R.id.iv_back:
            case R.id.tv_re_picture:
                releaseCamera();
                mCamera = getCamera(mCameraId);
                if (mHolder != null) {
                    startPreview(mCamera, mHolder);
                }
                surfaceView.setVisibility(View.VISIBLE);
                ivImage.setVisibility(View.INVISIBLE);
                allPictureBefor.setVisibility(View.VISIBLE);
                allPictureAfter.setVisibility(View.INVISIBLE);
                allPicture.setVisibility(View.VISIBLE);
                arlButtom.setVisibility(View.INVISIBLE);
                break;
            case R.id.btn_normal:
                uploadImage("", "");
                break;
            case R.id.btn_secret:
                if (mSecretDialog != null && !mSecretDialog.isShowing()) {
                    mSecretDialog.show();
                }
                break;
        }
    }

    /**
     * 跳转系统图款选取图片
     */
    private void getPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_IMAGE);

        /*Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
        openAlbumIntent.setType("image*//*");
        startActivityForResult(openAlbumIntent, REQUEST_CODE_IMAGE);*/
    }

    private final int REQUEST_CODE_IMAGE = 1000;

    /**
     * 上传图片到阿里云(直播封页图)
     *
     * @param roomPw  私密直播的密码 (普通直播传空)
     * @param roomPay 私密直播需要支付的金币 (普通直播传空)
     */
    private void uploadImage(final String roomPw, final String roomPay) {
        LogUtils.e("直播图片: " + PATH);
        AliYunImageUtils.newInstance().uploadImage(mContext, PATH, new ResultListener() {
            @Override
            public void onSucess(String data) {
                for (Bitmap bitmap : mBits) {
                    if (bitmap != null && !bitmap.isRecycled()) {
                        bitmap.recycle();
                        bitmap = null;
                    }
                }
                LogUtils.e("直播图片: " + data);
                ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
                map.put("name", appUser.getNickName());
                map.put("city", tvAddress.getText().toString().trim());
                map.put("userId", appUser.getId());
                map.put("privateChat", "1");
                map.put("payForChat", appUser.getPayForVideoChat());
                map.put("livePicUrl", data);
                if (!TextUtils.isEmpty(roomPw) && !TextUtils.isEmpty(roomPay)) {
                    map.put("roomPw", roomPw);
                    map.put("roomPay", roomPay);
                    map.put("privateFlag", "1");
                }
                httpDatas.getDataForJson("开启直播", Request.Method.POST, UrlBuilder.START, map, mHandler, RequestCode.START_LIVE);
            }

            @Override
            public void onFaild() {
                showToast("上传封面失败，请重试");
            }
        });
    }

    private List<Bitmap> mBits = new ArrayList<>();

    private void captrue() {
        mCamera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                //将data 转换为位图 或者你也可以直接保存为文件使用 FileOutputStream
                //这里我相信大部分都有其他用处把 比如加个水印 后续再讲解
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                if (!mBits.contains(bitmap)) {
                    mBits.add(bitmap);
                }
                handleBitmap(bitmap);
            }
        });
    }

    /**
     * 处理bitmap
     *
     * @param bitmap 拍照或系统图库里返回的图片
     */
    private void handleBitmap(Bitmap bitmap) {
        Bitmap saveBitmap = CameraUtil.getInstance().setTakePicktrueOrientation(mCameraId, bitmap);
        surfaceView.setVisibility(View.INVISIBLE);
        ivImage.setVisibility(View.VISIBLE);
        allPictureBefor.setVisibility(View.INVISIBLE);
        allPictureAfter.setVisibility(View.VISIBLE);
        allPicture.setVisibility(View.INVISIBLE);
        arlButtom.setVisibility(View.VISIBLE);

        ivImage.setImageBitmap(saveBitmap);
        int height = saveBitmap.getHeight();
        LogUtils.e("saveBitmap: " + height);
        Bitmap bitmap1 = Bitmap.createBitmap(saveBitmap, 0, 0, saveBitmap.getWidth(), saveBitmap.getHeight() * height / ivImageHeight);
        BitmapUtils.saveJPGE_After(mContext, bitmap1, PATH, 100);

        if (!bitmap1.isRecycled()) {
            LogUtils.e("bitmap1：" + bitmap1);
            bitmap1.recycle();
        }
        if (!bitmap.isRecycled()) {
            LogUtils.e("bitmap：" + bitmap);
            bitmap.recycle();
        }
    }

    private final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "a.jpeg";

    /**
     * 切换摄像头
     */
    public void switchCamera() {
        releaseCamera();
//        mCameraId = mCameraId == CAMERA_BACK ? CAMERA_FRONT : CAMERA_BACK;
        mCameraId = (mCameraId + 1) % mCamera.getNumberOfCameras();
        mCamera = getCamera(mCameraId);
        if (mHolder != null) {
            isFrontCamera = !isFrontCamera;
            startPreview(mCamera, mHolder);
        }
    }

    private static final int CAMERA_FRONT = 1;
    private static final int CAMERA_BACK = 0;


    /**
     * 获取Camera实例
     *
     * @return
     */
    private Camera getCamera(int id) {
        releaseCamera();
        Camera camera = null;
        try {
            camera = Camera.open(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return camera;
    }

    /**
     * 释放相机资源
     */
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    /**
     * 预览相机
     */
    private void startPreview(Camera camera, SurfaceHolder holder) {
        try {
            //这里要设置相机的一些参数，下面会详细说下
            setupCamera(camera);
            camera.setPreviewDisplay(holder);
            //亲测的一个方法 基本覆盖所有手机 将预览矫正
            CameraUtil.getInstance().setCameraDisplayOrientation(this, mCameraId, camera);

            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置
     */
    private void setupCamera(Camera camera) {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            List<String> focusModes = parameters.getSupportedFocusModes();
            if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
                // Autofocus mode is supported 自动对焦
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                // parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            }

            //这里第三个参数为最小尺寸 getPropPreviewSize方法会对从最小尺寸开始升序排列 取出所有支持尺寸的最小尺寸
            Camera.Size previewSize = CameraUtil.getInstance().getPropPreviewSize(parameters.getSupportedPreviewSizes(), screenWidth);
            parameters.setPreviewSize(previewSize.width, previewSize.height);

            Camera.Size pictrueSize = CameraUtil.getInstance().getPropPictureSize(parameters.getSupportedPictureSizes(), screenWidth);
            parameters.setPictureSize(pictrueSize.width, pictrueSize.height);
            camera.setParameters(parameters);
            picHeight = screenWidth * previewSize.width / previewSize.height;

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(screenWidth, picHeight);
            //这里当然可以设置拍照位置 比如居中 我这里就置顶了
            params.gravity = Gravity.CENTER;
            surfaceView.setLayoutParams(params);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera == null) {
            mCamera = getCamera(mCameraId);
            if (mHolder != null && mCamera != null) {
                startPreview(mCamera, mHolder);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    /**
     * 拍摄前红点动画
     */
    private void initAima() {
        mAnim = new AlphaAnimation(0, 1);
        mAnim.setDuration(800);
        mAnim.setRepeatCount(Integer.MAX_VALUE);
        mAnim.setRepeatMode(Animation.RESTART);
    }


    /**
     * 确认退出对话框
     */
    private void initQuitDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_secret_video, null);
        mSecretDialog = new RoundDialog(this, view);
        mSecretDialog.setCanceledOnTouchOutside(false);

        final EditText etPwd = (EditText) view.findViewById(R.id.et_pwd);
        final EditText etCoin = (EditText) view.findViewById(R.id.et_coin);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tvSure = (TextView) view.findViewById(R.id.tv_sure);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSecretDialog != null && mSecretDialog.isShowing()) {
                    mSecretDialog.dismiss();
                }
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = etPwd.getText().toString().trim();
                if (TextUtils.isEmpty(pwd)) {
                    showToast("密码不能为空");
                    return;
                }

                String coin = etCoin.getText().toString().trim();
                try {
                    Integer integer = Integer.valueOf(coin);
                    if (integer == 0) {
                        showToast("请输入金币数");
                        return;
                    }
                } catch (Exception e) {
                    showToast("请输入金币数");
                    return;
                }

                if (mSecretDialog != null && mSecretDialog.isShowing()) {
                    mSecretDialog.dismiss();
                }
                uploadImage(pwd, coin);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                Bitmap photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                if (!mBits.contains(photo)) {
                    mBits.add(photo);
                }
                handlePicutreBitmap(photo);
                LogUtils.e("photo: " + photo);
                LogUtils.e("Bitmap: " + getBitmapSize(photo));

            } catch (FileNotFoundException e) {
                LogUtils.e("FileNotFoundException: " + e.toString());
            }
        }
    }

    private void handlePicutreBitmap(Bitmap bitmap) {
        surfaceView.setVisibility(View.INVISIBLE);
        ivImage.setVisibility(View.VISIBLE);
        allPictureBefor.setVisibility(View.INVISIBLE);
        allPictureAfter.setVisibility(View.VISIBLE);
        allPicture.setVisibility(View.INVISIBLE);
        arlButtom.setVisibility(View.VISIBLE);
        ivImage.setImageBitmap(bitmap);

        BitmapUtils.saveJPGE_After(mContext, bitmap, PATH, 100);

       /* if (!bitmap.isRecycled()) {
            LogUtils.e("bitmap：" + bitmap);
            bitmap.recycle();
        }*/
    }


    /**
     * 得到bitmap的大小
     */
    public int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            return bitmap.getByteCount();
        }
        // 在低版本中用一行的字节x高度
        return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
    }
}
