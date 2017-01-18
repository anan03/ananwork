package com.lvshandian.asktoask.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.entry.DataUserAnswer;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 分享工具类 on 2016/4/14.
 */
public class ShareUtils {

    //分享地址
    public static String share = "http://wenlaiwenqu.cn:8080/wenlaiwenqu/page.html";  //这个是打开之后的链接  这个应该是一个下载链接
    public static String ImagString = "http://wenlaiwenqu.cn:8080/wlwq/resource//images/question.png";
    public static String sharehudong="http://wenlaiwenqu.cn/wlwq/appquestion/before.do?";


    /**
     * 推按好友的分享
     * @param context
     * @param index
     * @param content
     * @param listener
     * @param urlimg
     */
    public static void share(Activity context, int index, String content, PlatformActionListener listener, String urlimg) {
        switch (index) {
            case 0: {
                shareWeiBo(context, false, content, content, listener);
                break;
            }
            case 1: {
                shareWeChat(context, false, "",content, listener, urlimg);
                break;
            }
            case 2: {
                shareQQ(context, false, content, urlimg, listener);
                break;
            }
            case 3: {
                shareQZone(context,false,"",content,urlimg,listener);
                break;
            }
            case 4: {
                shareWeChatQuan(context,false,content,"",listener,urlimg);
                break;
            }


        }
    }

    /**
     * 去分享
     * @param context
     */
    public static void goToShare(Context context,DataUserAnswer.DataBean.UserAndQuestionsBean item) {
        ShareSDK.initSDK(context);
        //一键分享
        showShare(context,item);


    }

    /**
     * 一键分享
     * @param context
     */
    private static void showShare(Context context,DataUserAnswer.DataBean.UserAndQuestionsBean item) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle(item.getQuestionTitle());
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(ShareUtils.sharehudong + "questionId=" + item.getQuestionId() + "&" + "userId=" +Global.getUserId(context));
        // text是分享文本，所有平台都需要这个字段
        oks.setText(item.getQuestionData());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setImageUrl("http://101.201.120.234:8080/wlwq/resource//images/147556213401877.jpg");
        Resources res = context.getResources();

        Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.question);
        PicUtils.saveMyBitmap(bmp, "test");
        oks.setImagePath("/sdcard/test.jpg");
        oks.setUrl(ShareUtils.sharehudong + "questionId=" + item.getQuestionId() + "&" + "userId=" +Global.getUserId(context));
        // text是分享文本，所有平台都需要这个字段
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(context.getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(ShareUtils.sharehudong + "questionId=" + item.getQuestionId() + "&" +"userId=" +Global.getUserId(context));
        // text是分享文本，所有平台都需要这个字段

// 启动分享GUI
        oks.show(context);
    }

    /**
     *
     * 推荐好友的 qq分享
     * @param context
     * @param showContentEdit 是否显示编辑页   推荐好友时
     * @dw QQ
     */
    public static void shareQQ(final Activity context, boolean showContentEdit, String content, String image, PlatformActionListener listener) {
        ShareSDK.initSDK(context);
        QQ.ShareParams sp = new QQ.ShareParams();
        sp.setTitle("问来问去");//标题
        sp.setTitleUrl(share); // 标题的超链接
        sp.setText(content);
        Resources res=context.getResources();
        Bitmap bmp=BitmapFactory.decodeResource(res, R.drawable.question);
        PicUtils.saveMyBitmap(bmp, "test");
        sp.setImagePath("/sdcard/test.jpg");
//        sp.setImageUrl(ImagString);
        sp.setSite(share);
        sp.setSiteUrl(share);
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(listener);
        qq.share(sp);
    }
    /**
     * 推荐好友的分享
     */
    public static void shareWeChat(final Activity context, boolean showContentEdit,String title, String content, PlatformActionListener listener, String urlimg) {

        ShareSDK.initSDK(context);
        Wechat.ShareParams sp = new Wechat.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setTitle(title);
        sp.setTitleUrl(share); // 标题的超链接  就是点击后的链接
        sp.setText(content);
        sp.setUrl(share);
        Resources res=context.getResources();
        Bitmap bmp=BitmapFactory.decodeResource(res, R.drawable.question);
        PicUtils.saveMyBitmap(bmp, "test");
        sp.setImagePath("/sdcard/test.jpg");
        sp.setSite(share);
        sp.setSiteUrl(share);
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        wechat.setPlatformActionListener(listener);
        wechat.share(sp);

    }

    /**
     *
     * 推荐的
     *
     * 微信朋友圈分享
     * @param context
     * @param showContentEdit 是否显示编辑页
     * @dw wechat
     */
    public static void shareWeChatQuan(final Activity context, boolean showContentEdit, String content,String title, PlatformActionListener listener, String image) {

        ShareSDK.initSDK(context);
        final Platform weixin = ShareSDK.getPlatform(context, WechatMoments.NAME);
        WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setUrl(share);
        sp.setTitle(title);
        Resources res=context.getResources();
        Bitmap bmp=BitmapFactory.decodeResource(res, R.drawable.question);
        PicUtils.saveMyBitmap(bmp, "test");
        sp.setImagePath("/sdcard/test.jpg");
        weixin.share(sp);

    }
    /**
     *
     *
     * 互动详情的分享
     * @param context
     * @param showContentEdit 是否显示编辑页
     * @dw wechat
     */
    public static void shareWeChat(final Activity context, boolean showContentEdit,String title, String content, PlatformActionListener listener, String urlimg,String url) {

        ShareSDK.initSDK(context);
        Wechat.ShareParams sp = new Wechat.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setTitle(title);
        sp.setTitleUrl(url); // 标题的超链接
        sp.setText(content);
        sp.setUrl(url);
        Resources res=context.getResources();
        Bitmap bmp=BitmapFactory.decodeResource(res, R.drawable.question);
        PicUtils.saveMyBitmap(bmp, "test");
        sp.setImagePath("/sdcard/test.jpg");
        sp.setSite(url);
        sp.setSiteUrl(url);
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        wechat.setPlatformActionListener(listener);
        wechat.share(sp);

    }



    /**
     * 详情的
     *
     * 微信朋友圈分享
     * @param context
     * @param showContentEdit 是否显示编辑页
     * @dw wechat
     */
    public static void shareWeChatQuan(final Activity context, boolean showContentEdit, String content,String title, PlatformActionListener listener, String image,String url) {
        Log.d("aaa","微信朋友圈分享的"+url);
        ShareSDK.initSDK(context);
        WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setUrl(url);
        sp.setTitle(title);
        sp.setTitleUrl(url); // 标题的超链接
        sp.setText(content);
        sp.setSite(url);
        sp.setSiteUrl(url);
        Resources res=context.getResources();
        Bitmap bmp=BitmapFactory.decodeResource(res, R.drawable.question);
        PicUtils.saveMyBitmap(bmp, "test");
        sp.setImagePath("/sdcard/test.jpg");
        Platform wechatMoments = ShareSDK.getPlatform (WechatMoments.NAME);
        wechatMoments.setPlatformActionListener(listener);
        wechatMoments.share(sp);

    }



    /**
     * 互动详情的qq分享
     *
     *
     * @param context
     * @param showContentEdit 是否显示编辑
     * @dw QQ
     */
    public static void shareQQ(Activity context, boolean showContentEdit, String content, String title,String image, PlatformActionListener listener,String url) {
        ShareSDK.initSDK(context);
        QQ.ShareParams sp = new QQ.ShareParams();
        sp.setTitle(title);
        sp.setTitleUrl(url); // 标题的超链接
        sp.setText(content);
        Resources res=context.getResources();
        Bitmap bmp=BitmapFactory.decodeResource(res, R.drawable.question);
        PicUtils.saveMyBitmap(bmp, "test");
        sp.setImagePath("/sdcard/test.jpg");
//        sp.setImageUrl(ImagString);
        sp.setSite(url);
        sp.setSiteUrl(url);
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(listener);
        qq.share(sp);
    }

    /**
     * @dw shareQZone
     * @param context
     * @param showContentEdit  是否显示编辑页
     *
     *     这里是推荐的
     */
    public static void shareQZone( Activity context,boolean showContentEdit,String title,String content,String image,PlatformActionListener listener) {
        ShareSDK.initSDK(context);
        QZone.ShareParams sp = new QZone.ShareParams();
        sp.setTitle(title);
        Resources res=context.getResources();
        Bitmap bmp=BitmapFactory.decodeResource(res, R.drawable.question);
        PicUtils.saveMyBitmap(bmp, "test");
        sp.setImagePath("/sdcard/test.jpg");
        sp.setTitleUrl(share); // 标题的超链接
        sp.setText(content);
//        sp.setImageUrl(ImagString);
        sp.setSite(share);
        sp.setSiteUrl(share);
        Platform qzone = ShareSDK.getPlatform (QZone.NAME);
        qzone.setPlatformActionListener(listener);
        qzone.share(sp);
    }

    /**
     * 这个是分享到空间的互动详情的
     *
     *
     * @dw shareQZone
     * @param context
     * @param showContentEdit  是否显示编辑页
     */
    public static void shareQZone( Activity context,boolean showContentEdit,String title,String content,String ImagString,PlatformActionListener listener,String url) {

        ShareSDK.initSDK(context);
        QZone.ShareParams sp = new QZone.ShareParams();
        Resources res=context.getResources();
        Bitmap bmp=BitmapFactory.decodeResource(res, R.drawable.question);
        PicUtils.saveMyBitmap(bmp, "test");
        sp.setImagePath("/sdcard/test.jpg");
        sp.setTitle(title);
        sp.setTitleUrl(url); // 标题的超链接
        sp.setText(content);
//        sp.setImageUrl(ImagString);
        sp.setSite(share);
        Platform qzone = ShareSDK.getPlatform (QZone.NAME);
        qzone.setPlatformActionListener(listener);
        qzone.share(sp);
    }


    /**
     * @param context
     * @param showContentEdit 是否显示编辑页
     * @dw shareWeiBo
     */
    public static void shareWeiBo(final Activity context, boolean showContentEdit, String content, String image, PlatformActionListener listener) {
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        oks.setSilent(true);
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        oks.setPlatform(SinaWeibo.NAME);
        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(context.getString(R.string.shartitle));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(share);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(content);
        Resources res=context.getResources();
        Bitmap bmp=BitmapFactory.decodeResource(res, R.drawable.question);
        PicUtils.saveMyBitmap(bmp, "test");
        oks.setImagePath("/sdcard/test.jpg");
//        oks.setImageUrl(ImagString);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(share);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        //oks.setComment(context.getString(R.string.shartitle));
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(context.getString(R.string.app_name));
        oks.setCallback(listener);
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(share);
        // 启动分享GUI
        oks.show(context);
    }

    /**
     * @param context
     * @param showContentEdit 是否显示编辑页
     * @dw shareWeiBo
     */
    public static void shareWeiBo(final Activity context, boolean showContentEdit, String content, String image, PlatformActionListener listener,String url) {
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        oks.setSilent(true);
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        oks.setPlatform(SinaWeibo.NAME);
        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(context.getString(R.string.shartitle));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(content);
        Resources res=context.getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.question);
        PicUtils.saveMyBitmap(bmp, "test");
        oks.setImagePath("/sdcard/test.jpg");
//        oks.setImageUrl(ImagString);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        //oks.setComment(context.getString(R.string.shartitle));
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(context.getString(R.string.app_name));
        oks.setCallback(listener);
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);
        // 启动分享GUI
        oks.show(context);
    }


}
