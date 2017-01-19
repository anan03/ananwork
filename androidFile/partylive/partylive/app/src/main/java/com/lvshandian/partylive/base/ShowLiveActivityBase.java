//package com.lvshandian.partylive.base;
//
//import android.animation.Animator;
//import android.animation.AnimatorSet;
//import android.animation.ObjectAnimator;
//import android.animation.PropertyValuesHolder;
//import android.graphics.drawable.AnimationDrawable;
//import android.os.Handler;
//import android.os.PowerManager;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.EditText;
//import android.widget.FrameLayout;
//import android.widget.GridView;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.google.gson.Gson;
//import com.lvshandian.yeyan.R;
//import com.lvshandian.yeyan.api.remote.ApiUtils;
//import com.lvshandian.yeyan.bean.ChatBean;
//import com.lvshandian.yeyan.bean.SendGiftBean;
//import com.lvshandian.yeyan.bean.UserBean;
//import com.lvshandian.yeyan.ui.server.ChatServer;
//import com.lvshandian.yeyan.utils.InputMethodUtils;
//import com.lvshandian.yeyan.utils.ThreadManager;
//import com.lvshandian.yeyan.widget.AvatarView;
//import com.lvshandian.yeyan.widget.HorizontalListView;
//import com.lvshandian.yeyan.widget.StrokeTextView;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//
//import butterknife.InjectView;
//
///**
// * @athor 魏鹏
// * @dw 直播间基类 本类主要封装了直播间豪华礼物动画 和一些公共逻辑
// */
//public abstract class ShowLiveActivityBase extends BaseActivity {
//    @InjectView(R.id.rl_live_root)
//    protected RelativeLayout mRoot;
//
//    @InjectView(R.id.ll_show_gift_animator)    //连送礼物动画显示区
//    protected LinearLayout mShowGiftAnimator;
//
//    @InjectView(R.id.tv_live_num)
//    protected TextView mLiveNum;               //观众数量
//
//    @InjectView(R.id.tv_yingpiao_num)
//    protected TextView mYpNum;
//
//    @InjectView(R.id.lv_live_room)
//    protected ListView mLvChatList;            //聊天listview
//
//    @InjectView(R.id.iv_live_chat)
//    protected ImageView mLiveChat;             //点击chat按钮
//
//    @InjectView(R.id.iv_live_emcee_head)
//    protected AvatarView mEmceeHead;           //左上角主播head
//
//    @InjectView(R.id.ll_bottom_menu)
//    protected RelativeLayout mButtonMenu;      //底部menu
//
//    @InjectView(R.id.fl_bottom_menu)
//    protected FrameLayout mButtonMenuFrame;
//
//    @InjectView(R.id.ll_live_chat_edit)
//    protected LinearLayout mLiveChatEdit;      //chatInput
//
//    @InjectView(R.id.et_live_chat_input)
//    protected EditText mChatInput;
//
//    @InjectView(R.id.rl_livestop)
//    protected RelativeLayout mLayoutLiveStop;
//
//    @InjectView(R.id.ll_yp_labe)
//    LinearLayout mYpLabe;                       //观众数量
//
//    @InjectView(R.id.hl_room_user_list)
//    protected HorizontalListView mUserList;              //观众列别listview
//
//    protected Gson mGson = new Gson();
//
//    protected Map<Integer,View> mGiftShowNow = new HashMap<>();//当前正在显示的两个动画
//
//    protected Map<Integer,SendGiftBean> mGiftShowQueue = new HashMap(); //礼物消息队列
//
//
//    /**
//    * @dw 礼物队列List
//    * mLuxuryGiftCruisesShowQueue(豪华游轮) mLuxuryGiftCarShowQueue(红色轿车) mLuxuryGiftFireworksShowQueue(烟花)
//    * mLuxuryGiftPlainShowQueue(飞机)
//    * 是否执行完毕
//    * isFireworkGiftAnimationPlayEnd,isCruisesGiftAnimationPlayEnd,isCarGiftAnimationPlayEnd,isPlainGiftAnimationPlayEnd
//    * */
//    protected List<SendGiftBean> mLuxuryGiftFireworksShowQueue = new ArrayList<>();
//
//    protected List<SendGiftBean> mLuxuryGiftCruisesShowQueue = new ArrayList<>();
//
//    protected List<SendGiftBean> mLuxuryGiftCarShowQueue = new ArrayList<>();
//
//    protected List<SendGiftBean> mLuxuryGiftPlainShowQueue = new ArrayList<>();
//
//    protected boolean isFireworkGiftAnimationPlayEnd = true;
//
//    protected boolean isCruisesGiftAnimationPlayEnd = true;
//
//    protected boolean isCarGiftAnimationPlayEnd = true;
//
//    protected boolean isPlainGiftAnimationPlayEnd = true;
//
//    protected int mShowGiftFirstUid = 0;
//
//    protected int mShowGiftSecondUid = 0;
//    /*====================================================================================*/
//
//    protected PowerManager.WakeLock mWl;
//
//    //socket服务器连接状态
//    protected boolean mConnectionState = false;
//
//    //聊天adapter
//    protected ChatListAdapter mChatListAdapter = new ChatListAdapter();
//
//    //用户列表adapter
//    protected UserListAdapter mUserListAdapter = new UserListAdapter();
//
//    protected List<ChatBean> mChats = new ArrayList<>(); //聊天list
//
//    protected List<UserBean> mUsers = new ArrayList<>();     //用户列表list
//
//    protected List<GridView> mGiftViews = new ArrayList<>();
//
//    //socket逻辑
//    protected ChatServer mChatServer;
//
//    protected UserBean mUser;
//
//    protected int ACE_TEX_TO_USER = 0;
//
//    protected AnimationDrawable animationDrawable;
//
//    protected Handler mHandler = new Handler();
//
//    protected void sendChat() {
//        String sendMsg = mChatInput.getText().toString();
//        Log.i("ldb","链接状态："+mConnectionState);
//        if(mConnectionState && !sendMsg.equals("")){
//            mChatInput.setText("");
//            mChatServer.doSendMsg(sendMsg, mUser,ACE_TEX_TO_USER);
//            hideEditText();
//        }
//    }
//    @Override
//    protected boolean hasActionBar() {
//        return false;
//    }
//
//    //进入显示礼物队列信息初始化
//    protected View initShowEvenSentSendGift(SendGiftBean mSendGiftInfo){
//        View view = getLayoutInflater().inflate(R.layout.item_show_gift_animator, null);
//        if(mShowGiftFirstUid == 0){
//            mShowGiftFirstUid = mSendGiftInfo.getUid();
//        }else{
//            mShowGiftSecondUid = mSendGiftInfo.getUid();
//        }
//        mGiftShowNow.put(mSendGiftInfo.getUid(), view);
//        return view;
//    }
//    //定时检测当前显示礼物是否超时过期
//    protected boolean timingDelGiftAnimation(int index){
//
//        int id = index == 1?mShowGiftFirstUid:mShowGiftSecondUid;
//        SendGiftBean mSendGiftInfo = mGiftShowQueue.get(id);
//        if(mSendGiftInfo != null){
//            long time = System.currentTimeMillis() - mSendGiftInfo.getSendTime();
//            if((time > 4000) && (mShowGiftAnimator != null)){//超时
//
//                mShowGiftAnimator.removeView(mGiftShowNow.get(id));
//                mGiftShowQueue.remove(id);
//                if(index == 1){
//                    mShowGiftFirstUid = 0;
//                }else{
//                    mShowGiftSecondUid = 0;
//                }
//                mGiftShowNow.remove(id);
//
//                //从礼物队列中获取一条新的礼物信息进行显示
//                if(mGiftShowQueue.size() != 0){
//                    Iterator iterator = mGiftShowQueue.entrySet().iterator();
//                    while (iterator.hasNext()){
//                        Map.Entry entry = (Map.Entry) iterator.next();
//                        SendGiftBean sendGift = (SendGiftBean) entry.getValue();
//
//                        if(mShowGiftFirstUid != sendGift.getUid() && mShowGiftSecondUid != sendGift.getUid()){//判断队列中的第一个礼物是否已经正在显示
//                            showEvenSentGiftAnimation(initShowEvenSentSendGift(sendGift),sendGift,1);
//                            break;
//                        }
//                    }
//                }
//                return false;
//            }else{
//                return true;
//            }
//        }
//        return true;
//    }
//
//
//    //点亮
//    protected void showLit() {
//
//        ThreadManager.getThreadPool().execute(new Runnable() {
//            @Override
//            public void run() {
//                int[] heartImg = new int[]{R.mipmap.plane_heart_cyan,R.mipmap.plane_heart_pink,R.mipmap.plane_heart_red,R.mipmap.plane_heart_yellow};
//                final Random ran = new Random();
//                final ImageView heart = new ImageView(ShowLiveActivityBase.this);
//                //点亮的背景图片
//                heart.setBackgroundResource(heartImg[ran.nextInt(4)]);
//                //尺寸参数
//                heart.setLayoutParams(new RelativeLayout.LayoutParams(100
//                        ,100));
//////                heart.setLayoutParams(new RelativeLayout.LayoutParams(TDevice.pxTodip(ShowLiveActivityBase.this,100)
////                        ,TDevice.pxTodip(ShowLiveActivityBase.this,100)));
//                final int screenW = getWindowManager().getDefaultDisplay().getWidth();
//                //x位置
//                heart.setX(screenW - screenW/3);
//                //y位置
//                heart.setY(getWindowManager().getDefaultDisplay().getHeight()-200);
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mRoot.addView(heart);
//                        //点亮xy 平移动画
//                        ObjectAnimator translationX = ObjectAnimator.ofFloat(heart,"translationX",ran.nextInt(500)+(screenW-200) - (screenW/3));
//                        ObjectAnimator translationY = ObjectAnimator.ofFloat(heart,"translationY",ran.nextInt(getWindowManager().getDefaultDisplay().getHeight()/2) + 200);
//                        //渐变动画
//                        ObjectAnimator alpha = ObjectAnimator.ofFloat(heart,"alpha",0f);
//                        //xy放大动画
//                        ObjectAnimator scaleX = ObjectAnimator.ofFloat(heart,"scaleX",0.8f,1f);
//                        ObjectAnimator scaleY = ObjectAnimator.ofFloat(heart,"scaleY",0.8f,1f);
//                        AnimatorSet set = new AnimatorSet();
//                        set.playTogether(translationX,translationY,alpha,scaleX,scaleY);
//                        set.setDuration(5000);
//                        set.addListener(new Animator.AnimatorListener() {
//                            @Override
//                            public void onAnimationStart(Animator animation) {
//
//                            }
//
//                            @Override
//                            public void onAnimationEnd(Animator animation) {
//                                if(null != mRoot ){
//                                    mRoot.removeView(heart);
//                                }
//                            }
//
//                            @Override
//                            public void onAnimationCancel(Animator animation) {
//
//                            }
//
//                            @Override
//                            public void onAnimationRepeat(Animator animation) {
//
//                            }
//                        });
//                        set.start();
//                    }
//                });
//            }
//        });
//
//
//    }
//    /**
//    * @dw 飞机
//    * */
//    protected void showPlainAnimation(SendGiftBean mSendGiftBean){
//        if(!isPlainGiftAnimationPlayEnd){
//            return;
//        }
//        //飞机动画初始化
//        isPlainGiftAnimationPlayEnd = false;
//        //撒花的颜色
//        final int[] colorArr = new int[]{R.color.red,R.color.yellow,R.color.blue,R.color.lite_blue,R.color.orange,R.color.pink};
//
//        final View plainView = getLayoutInflater().inflate(R.layout.view_live_gift_animation_plain,null);
//        mRoot.addView(plainView);
//        final RelativeLayout mRootAnimation = (RelativeLayout) plainView.findViewById(R.id.rl_animation_flower);
//        ImageView imageView = (ImageView) plainView.findViewById(R.id.iv_animation_plain);
//        AnimationDrawable plainAnimationDrawable = (AnimationDrawable) imageView.getBackground();
//        plainAnimationDrawable.start();
//        int screenW = getWindowManager().getDefaultDisplay().getWidth();
//        ObjectAnimator plainAnimator = ObjectAnimator.ofFloat(plainView,"translationX",screenW,0);
//        plainAnimator.setDuration(3000);
//        plainAnimator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//
//                Random random = new Random();
//                int num = random.nextInt(50) + 10;
//                int width = mRootAnimation.getWidth();
//                int height = mRootAnimation.getHeight();
//                //获取花朵
//                for(int i  = 0; i<num; i ++){
//                    int color = random.nextInt(5);
//                    int x  = random.nextInt(50);
//                    final int tx = random.nextInt(width);
//                    final int ty = random.nextInt(height);
//                    final TextView flower = new TextView(ShowLiveActivityBase.this);
//                    flower.setX(x);
//                    flower.setText("❀");
//                    flower.setTextColor(getResources().getColor(colorArr[color]));
//                    flower.setTextSize(50);
//                    //每个花朵的动画
//                    mRootAnimation.addView(flower);
//                    flower.animate().alpha(0f).translationX(tx).translationY(ty).setDuration(5000).start();
//
//                }
//                //飞机移动到中间后延迟一秒钟,开始继续前行-x
//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        ObjectAnimator plainAnimator = ObjectAnimator.ofFloat(plainView,"translationX",-plainView.getWidth());
//                        plainAnimator.setDuration(2000);
//                        plainAnimator.addListener(new Animator.AnimatorListener() {
//                            @Override
//                            public void onAnimationStart(Animator animation) {
//
//                            }
//
//                            @Override
//                            public void onAnimationEnd(Animator animation) {
//                                if(null != plainView){
//                                    if(null != mRoot){
//                                        mRoot.removeView(plainView);
//                                    }
//                                    mLuxuryGiftPlainShowQueue.remove(0);
//                                    isPlainGiftAnimationPlayEnd = true;
//                                    if(mLuxuryGiftPlainShowQueue.size() > 0){
//                                        showPlainAnimation(mLuxuryGiftPlainShowQueue.get(0));
//                                    }
//                                }
//
//                            }
//
//                            @Override
//                            public void onAnimationCancel(Animator animation) {
//
//                            }
//
//                            @Override
//                            public void onAnimationRepeat(Animator animation) {
//
//                            }
//                        });
//                        plainAnimator.start();
//                    }
//                },4000);
//
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });//n^m ^ n^m ^ m
//        plainAnimator.start();//❀
//    }
//    /**
//    * @dw 红色小轿车动画
//    * @author 魏鹏
//    * */
//    protected void showRedCarAnimation(SendGiftBean sendGiftBean){
//        if(!isCarGiftAnimationPlayEnd){
//            return;
//        }
//        isCarGiftAnimationPlayEnd = false;
//        //获取汽车动画布局
//        final View generalCar = getLayoutInflater().inflate(R.layout.view_live_gift_animation_car_general,null);
//        //添加到当前页面
//        mRoot.addView(generalCar);
//        //获取到汽车image控件
//        final ImageView redCar = (ImageView) generalCar.findViewById(R.id.iv_animation_red_car);
//        //获取背景
//        animationDrawable = (AnimationDrawable) redCar.getBackground();
//        animationDrawable.start();
//        final int screenW = getWindowManager().getDefaultDisplay().getWidth();
//        final int screenH = getWindowManager().getDefaultDisplay().getHeight();
//        final int vw = redCar.getLayoutParams().width;
//        //汽车动画init
//        ObjectAnimator ox = ObjectAnimator.ofFloat(generalCar,"translationX",screenW + vw,screenW/2-vw/2);
//        ox.setDuration(1500);
//        ObjectAnimator oy = ObjectAnimator.ofFloat(generalCar,"translationY",screenH >> 2);
//        //设置背景帧动画
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playTogether(ox,oy);
//        animatorSet.setDuration(1500);
//        animatorSet.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(final Animator animation) {
//                //小汽车停在中间
//                redCar.setBackgroundResource(R.drawable.gift_car_red_animation2);
//                animationDrawable = (AnimationDrawable) redCar.getBackground();
//                animationDrawable.start();
//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //小汽车切换帧动画开始继续移动向-x
//                        redCar.setBackgroundResource(R.drawable.gift_car_red_animation);
//                        animationDrawable = (AnimationDrawable) redCar.getBackground();
//                        animationDrawable.start();
//                        generalCar.animate().translationX(~vw)
//                                .withEndAction(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        //小汽车从底部重新回来切换帧动画
//                                        redCar.setBackgroundResource(R.drawable.gift_car_red_animation3);
//                                        animationDrawable = (AnimationDrawable) redCar.getBackground();
//                                        animationDrawable.start();
//                                        ObjectAnimator oX = ObjectAnimator.ofFloat(generalCar,"translationX",screenW,screenW/2-vw/2);
//                                        ObjectAnimator oY = ObjectAnimator.ofFloat(generalCar,"translationY",screenH/2,screenH >> 2);
//
//                                        //重新初始化帧动画参数
//                                        AnimatorSet animatorSet = new AnimatorSet();
//                                        animatorSet.playTogether(oX,oY);
//                                        animatorSet.setDuration(2000);
//                                        animatorSet.addListener(new Animator.AnimatorListener() {
//                                            @Override
//                                            public void onAnimationStart(Animator animation) {
//
//                                            }
//
//                                            @Override
//                                            public void onAnimationEnd(Animator animation) {
//                                                //小汽车加速帧动画切换
//                                                redCar.setBackgroundResource(R.drawable.gift_car_red_animation4);
//                                                animationDrawable = (AnimationDrawable) redCar.getBackground();
//                                                animationDrawable.start();
//                                                generalCar.animate().translationX(-vw).translationY(0)
//                                                        .setDuration(1000)
//                                                        .withEndAction(new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                //从汽车队列中移除,开始下一个汽车动画
//                                                                if(generalCar == null)return;
//                                                                mRoot.removeView(generalCar);
//                                                                mLuxuryGiftCarShowQueue.remove(0);
//                                                                animationDrawable= null;
//                                                                isCarGiftAnimationPlayEnd = true;
//                                                                if(mLuxuryGiftCarShowQueue.size() > 0){
//                                                                    showRedCarAnimation(mLuxuryGiftCarShowQueue.get(0));
//                                                                }
//                                                            }
//                                                        })
//                                                        .start();
//
//                                            }
//
//                                            @Override
//                                            public void onAnimationCancel(Animator animation) {
//
//                                            }
//
//                                            @Override
//                                            public void onAnimationRepeat(Animator animation) {
//
//                                            }
//                                        });
//                                        animatorSet.start();
//
//                                    }
//                                })
//                                .setDuration(1000).start();
//                    }
//                },500);
//
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
//        animatorSet.start();
//
//
//    }
//    /**
//    * @dw 邮轮
//    * @author 魏鹏
//    * */
//    protected void showCruisesAnimation(SendGiftBean mSendGiftBean){
//        if(!isCruisesGiftAnimationPlayEnd){
//            return;
//        }
//        isCruisesGiftAnimationPlayEnd = false;
//        final View cruisesView = getLayoutInflater().inflate(R.layout.view_live_gift_animation_cruises,null);
//        final int ww = getWindowManager().getDefaultDisplay().getWidth();
//        //游轮上的用户头像
//        AvatarView mUhead = (AvatarView) cruisesView.findViewById(R.id.live_cruises_uhead);
//        ((TextView)cruisesView.findViewById(R.id.tv_live_cruises_uname)).setText(mSendGiftBean.getNicename());
//        mUhead.setAvatarUrl(mSendGiftBean.getAvatar());
//
//        mRoot.addView(cruisesView);
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) cruisesView.getLayoutParams();
//        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        cruisesView.setLayoutParams(params);
//        final RelativeLayout cruises = (RelativeLayout) cruisesView.findViewById(R.id.rl_live_cruises);
//
//        //游轮开始平移动画
//        cruises.animate().translationX(ww/2 + ww/3).translationY(120f).withEndAction(new Runnable() {
//            @Override
//            public void run() {
//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //游轮移动到中间后重新开始移动
//                        cruises.animate().translationX(ww*2).translationY(200f).setDuration(3000)
//                                .withEndAction(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        //结束后从队列移除开始下一个邮轮动画
//                                        if(mRoot == null)return;
//                                        mRoot.removeView(cruisesView);
//                                        mLuxuryGiftCruisesShowQueue.remove(0);
//                                        isCruisesGiftAnimationPlayEnd = true;
//                                        if(mLuxuryGiftCruisesShowQueue.size() > 0){
//                                            showCruisesAnimation(mLuxuryGiftCruisesShowQueue.get(0));
//                                        }
//                                    }
//                                }).start();
//                    }
//                },2000);
//
//            }
//        }).setDuration(3000).start();
//
//        //邮轮海水动画
//        ImageView seaOne = (ImageView) cruisesView.findViewById(R.id.iv_live_water_one);
//        ImageView seaTwo = (ImageView) cruisesView.findViewById(R.id.iv_live_water_one2);
//        ObjectAnimator obj = ObjectAnimator.ofFloat(seaOne,"translationX",-50,50);
//        obj.setDuration(1000);
//        obj.setRepeatCount(-1);
//        obj.setRepeatMode(ObjectAnimator.REVERSE);
//        obj.start();
//        ObjectAnimator obj2 = ObjectAnimator.ofFloat(seaTwo,"translationX",50,-50);
//        obj2.setDuration(1000);
//        obj2.setRepeatCount(-1);
//        obj2.setRepeatMode(ObjectAnimator.REVERSE);
//        obj2.start();
//    }
//    /**
//     * @dw 烟花
//     * @author 魏鹏
//     * */
//    protected void showFireworksAnimation(SendGiftBean mSendGiftBean){
//        if(!isFireworkGiftAnimationPlayEnd){
//            return;
//        }
//        isFireworkGiftAnimationPlayEnd = false;
//        //初始化烟花动画
//        final ImageView animation = new ImageView(this);
//        animation.setBackgroundResource(R.drawable.gift_fireworks_heart_animation);
//        AnimationDrawable an = (AnimationDrawable) animation.getBackground();
//        an.setOneShot(true);
//        mRoot.addView(animation);
//        RelativeLayout.LayoutParams pa = (RelativeLayout.LayoutParams) animation.getLayoutParams();
//        pa.width = 400;
//        pa.height = pa.WRAP_CONTENT;
//        pa.addRule(RelativeLayout.CENTER_IN_PARENT);
//        animation.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        animation.setLayoutParams(pa);
//        an.start();
//        int duration = 0;
//        for(int i = 0; i< an.getNumberOfFrames(); i++){
//            duration += an.getDuration(i);
//        }
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //删除当前礼物,开始队列下一个
//                if(mRoot == null)return;
//                mRoot.removeView(animation);
//                mLuxuryGiftFireworksShowQueue.remove(0);
//                isFireworkGiftAnimationPlayEnd = true;
//                if(mLuxuryGiftFireworksShowQueue.size() > 0){
//                    showFireworksAnimation(mLuxuryGiftFireworksShowQueue.get(0));
//                }
//
//            }
//        },duration);
//
//    }
//    /**
//     * @dw 连送
//     * @author 魏鹏
//     * */
//    protected void showEvenSentGiftAnimation(final View mShowGiftLayout,final SendGiftBean gitInfo,int num) {
//        final AvatarView mGiftIcon = (AvatarView) mShowGiftLayout.findViewById(R.id.av_gift_icon);
//        final StrokeTextView mGiftNum = (StrokeTextView) mShowGiftLayout.findViewById(R.id.tv_show_gift_num);
//        ((AvatarView) mShowGiftLayout.findViewById(R.id.av_gift_uhead)).setAvatarUrl(gitInfo.getAvatar());
//        ((TextView) mShowGiftLayout.findViewById(R.id.tv_gift_uname)).setText(gitInfo.getNicename());
//        ((TextView) mShowGiftLayout.findViewById(R.id.tv_gift_gname)).setText(gitInfo.getGiftname());
//        mGiftIcon.setAvatarUrl(gitInfo.getGifticon());
//
//        if(mShowGiftAnimator != null){
//            mShowGiftAnimator.addView(mShowGiftLayout);//添加到动画区域显示效果
//        }
//        //动画开始平移
//        ObjectAnimator oa1 = ObjectAnimator.ofFloat(mShowGiftLayout,"translationX",-340f,0f);
//        oa1.setDuration(300);
//        oa1.start();
//        oa1.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                showGiftNumAnimation(mGiftNum, gitInfo.getUid());
//                //礼物图片平移动画
//                ObjectAnimator giftIconAnimator = ObjectAnimator.ofFloat(mGiftIcon, "translationX", -40f, (mShowGiftLayout.getRight() - mGiftIcon.getWidth()*2));
//                giftIconAnimator.setDuration(500);
//                giftIconAnimator.start();
//                //获取当前礼物是正在显示的哪一个
//                final int index = mShowGiftFirstUid == gitInfo.getUid() ? 1 : 2;
//                if (mHandler != null) {
//                    mHandler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (timingDelGiftAnimation(index)) {
//                                if (mHandler != null) {
//                                    mHandler.postDelayed(this, 1000);
//                                }
//                            } else {
//                                mHandler.removeCallbacks(this);
//                            }
//
//                        }
//                    }, 1000);
//                }
//
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
//    }
//
//
//    /**
//    * @dw 礼物数量增加动画
//    * */
//    protected void showGiftNumAnimation(TextView tv,int uid){
//        tv.setText("X" + mGiftShowQueue.get(uid).getGiftcount());
//        PropertyValuesHolder p1 = PropertyValuesHolder.ofFloat("scaleX",1f,0.2f,1f);
//        PropertyValuesHolder p2 = PropertyValuesHolder.ofFloat("scaleY",1f,0.2f,1f);
//        ObjectAnimator.ofPropertyValuesHolder(tv,p1,p2).setDuration(200).start();
//        mGiftShowQueue.get(uid).setSendTime(System.currentTimeMillis());//重置发送时间
//    }
//
//    protected void showOrdinaryGiftInit(final SendGiftBean mSendGiftInfo){
//        //礼物动画
//        View mShowGiftLayout = mGiftShowNow.get(mSendGiftInfo.getUid());
//        mSendGiftInfo.setSendTime(System.currentTimeMillis());
//        boolean isShow = false;//是否刚刚加入显示队列
//        boolean isFirst = false;
//
//        if(mGiftShowQueue.get(mSendGiftInfo.getUid()) == null){//是否是第一次送礼物
//            mGiftShowQueue.put(mSendGiftInfo.getUid(),mSendGiftInfo);//加入礼物消息队列
//            isFirst = true;
//        }
//        //是否是新的礼物类型
//        boolean isNewGift = !(mSendGiftInfo.getGiftid() == mGiftShowQueue.get(mSendGiftInfo.getUid()).getGiftid());
//        if((mGiftShowNow.size() < 2) && (mShowGiftLayout == null)){ //当前的显示礼物list不够两条并且当前送礼物用户不在list中
//            mShowGiftLayout = initShowEvenSentSendGift(mSendGiftInfo);//初始化显示礼物布局和信息
//            isShow = true;
//        }
//        if(mShowGiftLayout != null){//是否正在显示
//            isShow = true;
//        }
//        if(isNewGift){
//            ((AvatarView)mShowGiftLayout.findViewById(R.id.av_gift_icon)).setAvatarUrl(mSendGiftInfo.getGifticon());
//            ((StrokeTextView)mShowGiftLayout.findViewById(R.id.tv_show_gift_num)).setText("X1");
//            ((TextView)mShowGiftLayout.findViewById(R.id.tv_gift_gname)).setText(mSendGiftInfo.getGiftname());
//            mGiftShowQueue.put(mSendGiftInfo.getUid(), mSendGiftInfo);//新礼物覆盖之前older礼物信息
//        }
//        if(mSendGiftInfo.getEvensend().equals("y")&&(!isFirst)&&(!isNewGift)){//判断当前礼物是否属于连送礼物
//            //是连送礼物,将消息队列中的礼物赠送数量加1
//            mGiftShowQueue.get(mSendGiftInfo.getUid()).setGiftcount(mGiftShowQueue.get(mSendGiftInfo.getUid()).getGiftcount() + 1);
//        }
//
//        if(isShow&&isFirst){//存在礼物显示队列并且是第一次送礼物进行初始化动画
//            showEvenSentGiftAnimation(mShowGiftLayout,mSendGiftInfo,1);
//        }else if(isShow&&(!isNewGift)){//存在显示队列并且不是新礼物进行数量加一动画
//            showGiftNumAnimation((StrokeTextView) mShowGiftLayout.findViewById(R.id.tv_show_gift_num), mSendGiftInfo.getUid());
//        }
//    }
//    protected void showGiftInit(SendGiftBean mSendGiftInfo){
//        //票数更新
//        if(null!=mYpNum && !mYpNum.getText().toString().equals("") && null!=mSendGiftInfo){
//            mYpNum.setText(String.valueOf(Integer.parseInt(mYpNum.getText().toString()) + mSendGiftInfo.getTotalcoin()));
//        }else if(null!=mYpNum && mYpNum.getText().toString().equals("")){
//            mYpNum.setText(String.valueOf(mSendGiftInfo.getTotalcoin()));
//        }
//        if(mSendGiftInfo.getGiftid() == 22){//烟花礼物
//            mLuxuryGiftFireworksShowQueue.add(mSendGiftInfo);
//            showFireworksAnimation(mSendGiftInfo);
//        }else if(mSendGiftInfo.getGiftid() == 21){//游轮礼物
//            mLuxuryGiftCruisesShowQueue.add(mSendGiftInfo);
//            showCruisesAnimation(mSendGiftInfo);
//        }else if(mSendGiftInfo.getGiftid() == 9){//红色小轿车
//            mLuxuryGiftCarShowQueue.add(mSendGiftInfo);
//            showRedCarAnimation(mSendGiftInfo);
//        }else if(mSendGiftInfo.getGiftid() == 19){//飞机礼物
//            mLuxuryGiftPlainShowQueue.add(mSendGiftInfo);
//            showPlainAnimation(mSendGiftInfo);
//        }else{
//            showOrdinaryGiftInit(mSendGiftInfo);//普通连送礼物
//        }
//
//
//    }
//
//
//    //聊天adapter
//    protected class ChatListAdapter extends BaseAdapter {
//
//        @Override
//        public int getCount() {
//            return mChats.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return mChats.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder viewHolder;
//            if(convertView == null){
//                convertView = getLayoutInflater().inflate(R.layout.item_live_chat,null);
//                viewHolder = new ViewHolder();
//                viewHolder.mChat1 = (TextView) convertView.findViewById(R.id.tv_chat_1);
//                viewHolder.mChat2 = (TextView) convertView.findViewById(R.id.tv_chat_2);
//                convertView.setTag(viewHolder);
//            }else {
//                viewHolder = (ViewHolder) convertView.getTag();
//            }
//
//            ChatBean c = mChats.get(position);
//            viewHolder.mChat1.setText(c.getUserNick());
//            viewHolder.mChat2.setText(c.getSendChatMsg());
//            return convertView;
//        }
//
//    }
//    //观众列表adapter
//    protected class UserListAdapter extends BaseAdapter {
//
//        @Override
//        public int getCount() {
//            return mUsers.size() ;
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return mUsers.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolderUserList viewHolderUserList;
//            if(convertView == null ){
//                convertView = getLayoutInflater().inflate(R.layout.item_live_user_list,null);
//                viewHolderUserList = new ViewHolderUserList();
//                viewHolderUserList.mUhead = (AvatarView) convertView.findViewById(R.id.av_userHead);
//                convertView.setTag(viewHolderUserList);
//
//            }else{
//                viewHolderUserList = (ViewHolderUserList) convertView.getTag();
//            }
//
//            UserBean user = mUsers.get(position);
//            viewHolderUserList.mUhead.setAvatarUrl(user.getAvatar());
//            return convertView;
//        }
//    }
//    protected class ViewHolder{
//        TextView mChat1,mChat2;
//    }
//    protected class ViewHolderUserList{
//        public AvatarView mUhead;
//    }
//    //显示输入框
//    protected void showEditText() {
//
//        mChatInput.setFocusable(true);
//        mChatInput.setFocusableInTouchMode(true);
//        mChatInput.requestFocus();
//        InputMethodUtils.toggleSoftKeyboardState(this);
//        mLiveChatEdit.setVisibility(View.VISIBLE);
//        mButtonMenu.setVisibility(View.GONE);
//    }
//    //隐藏输入框
//    protected void hideEditText(){
//        if(mLiveChatEdit.getVisibility() != View.GONE && InputMethodUtils.isShowSoft(this)){
//            InputMethodUtils.closeSoftKeyboard(this);
//            mButtonMenu.setVisibility(View.VISIBLE);
//            mLiveChatEdit.setVisibility(View.GONE);
//            ACE_TEX_TO_USER = 0;
//        }
//    }
//    //添加一条聊天
//    protected void addChatMessage(ChatBean c){
//        if(mChats.size()>30)mChats.remove(0);
//        mChats.add(c);
//        mChatListAdapter.notifyDataSetChanged();
//        mLvChatList.setSelection(mChats.size()-1);
//    }
//    public abstract void dialogReply(UserBean toUser);
//    //僵尸粉丝
//    protected void addZombieFans(String zombies){
//        String fans = ApiUtils.checkIsSuccess(zombies);
//        if(null != fans){
//            try {
//                //设置在线用户数量
//                mLiveNum.setText(String.valueOf(ChatServer.LIVEUSERNUMS));
//                JSONArray fansJsonArray = new JSONArray(fans);
//                if(fansJsonArray.length() > 0){
//                    for(int i = 0; i < fansJsonArray.length() ; i++)
//                        mUsers.add(mGson.fromJson(fansJsonArray.getString(i),UserBean.class));
//                    mUserListAdapter.notifyDataSetChanged();
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//}
