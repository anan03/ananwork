package com.lvshandian.menshen.analysesms.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.android.volley.Request;
import com.lvshandian.menshen.R;
import com.lvshandian.menshen.UrlBuilder;
import com.lvshandian.menshen.analysesms.SmsSettingActivity;
import com.lvshandian.menshen.base.CommonAdapter;
import com.lvshandian.menshen.base.ViewHolder;
import com.lvshandian.menshen.bean.Keyworkinfo;
import com.lvshandian.menshen.httprequest.HttpDatas;
import com.lvshandian.menshen.httprequest.RequestCode;
import com.lvshandian.menshen.utils.LogUtils;
import com.lvshandian.menshen.utils.XUtils;
import com.lvshandian.menshen.view.DialogView;
import com.lvshandian.menshen.view.ToastUtils;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhang on 2016/10/27.
 */


public class SmsSettingAdapter extends CommonAdapter<Keyworkinfo> {
    public View llPart;
    public Context conText;
    private int tag;
    public ArrayList<Keyworkinfo> list;
    HttpDatas httpDatas;
    private int position;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            int tagCode = data.getInt(HttpDatas.code);

            switch (msg.what) {
                //删除关键字成功
                case RequestCode.DELETE_KEYWORK_URL://删除成功
                    if (tagCode == RequestCode.REQUEST_CODE) {

                        ToastUtils.showSnackBar(llPart, data.getString(HttpDatas.info));
                    }
                    //更新数据库进行删除
                    XUtils.deleteEntity(Keyworkinfo.class, "ids", list.get(tag).getKeywordId() + "");
                    //进行页面的刷新
                    list.remove(tag);
                    LogUtils.e("list" + list.toString());
                    notifyDataSetChanged();
                    break;


            }
        }
    };

    public SmsSettingAdapter(View llPart, Context context, ArrayList<Keyworkinfo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        httpDatas = new HttpDatas(mContext, llPart);
        this.conText = context;
        this.llPart = llPart;
        this.list = mDatas;

    }

    @Override
    public void convert(ViewHolder helper, final Keyworkinfo item, final int position) {
        this.position = position;
        LogUtils.e("list" + mDatas.toString());
        LogUtils.e("list.size" + mDatas.size());
        if (item.getKeyword().trim() != null && !item.getKeyword().trim().equals("")) {
            LogUtils.e("item" + item);
            helper.getView(R.id.ll_part).setVisibility(View.VISIBLE);
            helper.setText(R.id.anay_item_tv_phone, item.getKeyword());
            if (item.getUserId() == 0) {
                helper.getView(R.id.img).setVisibility(View.GONE);
            } else {
                helper.getView(R.id.img).setVisibility(View.VISIBLE);
            }


        } else {
            helper.getView(R.id.ll_part).setVisibility(View.GONE);
        }

        helper.getView(R.id.img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DialogView(conText, SmsSettingActivity.view, "删除关键字", "确定", "取消", "关键字删除后将不被拦截", new DialogView.MyCallback() {
                    @Override
                    public void refreshActivity() {

                        tag = position;
                        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
                        map.put("ids", item.getKeywordId() + "");
                        httpDatas.getData("删除短信关键字", Request.Method.POST, UrlBuilder.DELETE_KEYWORK_URL, map, mHandler, RequestCode.DELETE_KEYWORK_URL);
//
                    }

                    @Override
                    public void refreshActivity(String tag) {
                    }

                    @Override
                    public void refreshActivity(String context, String minString, String maxString) {

                    }
                });

            }
        });
    }

}


//        helper.getView(R.id.img).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                new DialogView(conText, SmsSettingActivity.view, "删除关键字", "确定", "取消", 2, new DialogView.MyCallback() {
//                    @Override
//                    public void refreshActivity() {
//
//                    }
//
//                    @Override
//                    public void refreshActivity(String tag) {
//
//
////                        int currentItem = SmsSettingActivity.mViewPager.getCurrentItem();
////                        SmsBean smsBean = (SmsBean) XUtils.findAll(SmsBean.class).get(0);
////                        LogUtils.e("log开始" + smsBean.toString());
////
////                        if (currentItem == 0) {//第一个界面诈骗
////                            String[] strings = TextUtils.convertStrToArray(smsBean.getSmszp().trim());
////                            String str = "";
////
////                            LogUtils.e("tagstrleng" + strings.length);
////                            for (int i = 0; i < strings.length; i++) {
////                                if (!item.equals(strings[i].trim())) {
////                                    str = str + strings[i].trim() + ",";
////                                }
////                            }
////                            if (str.equals("") || str.equals(",")) {
////
////                                XUtils.update(SmsBean.class, "id", smsBean.getId() + "", "smszp", null);
////                                list.remove(position);
////                                notifyDataSetChanged();
////                                return;
////                            }
////                            XUtils.update(SmsBean.class, "id", smsBean.getId() + "", "smszp", str.trim());
////                        } else if (currentItem == 1) {//第二个界面垃圾短信
////
////                            String[] strings = TextUtils.convertStrToArray(smsBean.getSmslj().trim());
////                            String str = "";
////                            for (int i = 0; i < strings.length; i++) {
////                                if (!item.equals(strings[i].trim())) {
////                                    str = str + strings[i].trim() + ",";
////                                }
////                            }
////                            if (str.equals("") || str.equals(",")) {
////                                XUtils.update(SmsBean.class, "id", smsBean.getId() + "", "smslj", null);
////                                list.remove(position);
////                                notifyDataSetChanged();
////                                return;
////                            }
////
////                            XUtils.update(SmsBean.class, "id", smsBean.getId() + "", "smslj", str.trim());
////                        } else if (currentItem == 2) {//第三个界面伪基站
////                            String[] strings = TextUtils.convertStrToArray(smsBean.getSmswjz().trim());
////                            String str = "";
////                            for (int i = 0; i < strings.length; i++) {
////                                if (!item.equals(strings[i].trim())) {
////                                    str = str + strings[i].trim() + ",";
////                                }
////                            }
////                            if (str.equals("") || str.equals(",")) {
////                                XUtils.update(SmsBean.class, "id", smsBean.getId() + "", "smswjz", null);
////                                list.remove(position);
////                                notifyDataSetChanged();
////                                return;
////                            }
////
////                            XUtils.update(SmsBean.class, "id", smsBean.getId() + "", "smswjz", str.trim());
////                        }
////
////                        SmsBean smsBean1 = (SmsBean) XUtils.findAll(SmsBean.class).get(0);
////                        LogUtils.e("log结束" + smsBean1.toString());
//                        list.remove(position);
//                        notifyDataSetChanged();
//
//                        //得到添加的关键字存到数据库
////                        ToastUtils.showSnackBar(snackView, tag + mViewPager.getCurrentItem());
////                        SmsBean smsBean = (SmsBean) XUtils.findAll(SmsBean.class).get(0);
//
////                        if (null == smsBean) {//数据库无数据---直接插入
////                            LogUtils.e("数据库无数据---直接插入");
//////                            SmsBean smsBeanb = new SmsBean();
////                            if (mViewPager.getCurrentItem() == 0) {
////
////
////                                XUtils.update(SmsBean.class, "id", smsBean.getId() + "", "smszp", tag + ",");
////                            } else if (mViewPager.getCurrentItem() == 1) {
////                                XUtils.update(SmsBean.class, "id", smsBean.getId() + "", "smslj", tag + ",");
////                            } else if (mViewPager.getCurrentItem() == 2) {
////                                XUtils.update(SmsBean.class, "id", smsBean.getId() + "", "smswjz", tag + ",");
////                            }
////
////
////                        } else {//有数据时修改添加,并且更新数据
////
////                            if (mViewPager.getCurrentItem() == 0) {
////                                String[] strings = TextUtils.convertStrToArray(smsBean.getSmszp());
////                                String str = "";
////
////                                LogUtils.e("tagstrleng" + strings.length);
////                                for (int i = 0; i < strings.length; i++) {
////                                    str = str + strings[i] + ",";
////                                }
////                                LogUtils.e("tagstr" + str + tag + ",");
////                                XUtils.update(SmsBean.class, "id", smsBean.getId() + "", "smszp", str + tag + ",");
////                            } else if (mViewPager.getCurrentItem() == 1) {
////
////                                String[] strings = TextUtils.convertStrToArray(smsBean.getSmslj());
////                                String str = "";
////                                for (int i = 0; i < strings.length; i++) {
////                                    str = str + strings[i] + ",";
////                                }
////                                XUtils.update(SmsBean.class, "id", smsBean.getId() + "", "smslj", str + tag + ",");
////                            } else if (mViewPager.getCurrentItem() == 2) {
////
////                                String[] strings = TextUtils.convertStrToArray(smsBean.getSmswjz());
////                                String str = "";
////                                for (int i = 0; i < strings.length; i++) {
////                                    str = str + strings[i] + ",";
////                                }
////                                XUtils.update(SmsBean.class, "id", smsBean.getId() + "", "smswjz", str + tag + ",");
////                            }
////
////                        }
////
////                        try {
////                            Bundle[] bundles = fragmnetpageradapter.getmBundles();
////                            Bundle bundlesa = bundles[mViewPager.getCurrentItem()];
////                            ArrayList<String> lists = bundlesa.getStringArrayList("list");
////                            lists.add(tag);
////                            bundlesa.putStringArrayList("list", lists);
////                            bundles[mViewPager.getCurrentItem()] = bundlesa;
////                            /**
////                             *刷新数据
////                             */
////                            for (int j = 0; j < smsNumUi; j++) {
////                                fragmentClass[j] = new FragmentSeeting();
////                            }
////                            mBundles = bundles;
////                            fragmnetpageradapter.setFragments(fragmentClass);
////
////                        } catch (IllegalAccessException e) {
////                            e.printStackTrace();
////                        } catch (InstantiationException e) {
////                            e.printStackTrace();
////                        }
//                    }
//                });
//
//
//                LogUtils.e("点击了" + position);
//            }
//        });

//    public SmsSettingAdapter(View llPart, Context context, List<Keyworkinfo> mDatas, int itemLayoutId) {
//        super(context, mDatas, itemLayoutId);
//        this.conText = context;
//        this.llPart = llPart;
//        this.list = mDatas;
//    }

//    @Override
//    public void convert(ViewHolder helper, ArrayList<Keyworkinfo> item, int position) {
//        LogUtils.e("list" + mDatas.toString());
//        LogUtils.e("list.size" + mDatas.size());
//        if (item.get(position).getKeyword().trim() != null && !item.get(position).getKeyword().trim().equals("")) {
//            LogUtils.e("item" + item);
//            helper.getView(R.id.ll_part).setVisibility(View.VISIBLE);
//            helper.setText(R.id.anay_item_tv_phone, item.get(position).getKeyword());
//        } else {
//            helper.getView(R.id.ll_part).setVisibility(View.GONE);
//        }
//
//    }


//    @Override
//    public void convert(ViewHolder helper, final String item, final int position) {
//        LogUtils.e("list" + mDatas.toString());
//        LogUtils.e("list.size" + mDatas.size());
//        if (item.trim() != null && !item.trim().equals("")) {
//            LogUtils.e("item" + item);
//            helper.getView(R.id.ll_part).setVisibility(View.VISIBLE);
//            helper.setText(R.id.anay_item_tv_phone, item);
//        } else {
//            helper.getView(R.id.ll_part).setVisibility(View.GONE);
//        }
//        helper.getView(R.id.img).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                new DialogView(conText, SmsSettingActivity.view, "删除关键字", "确定", "取消", 2, new DialogView.MyCallback() {
//                    @Override
//                    public void refreshActivity() {
//
//                    }
//
//                    @Override
//                    public void refreshActivity(String tag) {
//
//
////                        int currentItem = SmsSettingActivity.mViewPager.getCurrentItem();
////                        SmsBean smsBean = (SmsBean) XUtils.findAll(SmsBean.class).get(0);
////                        LogUtils.e("log开始" + smsBean.toString());
////
////                        if (currentItem == 0) {//第一个界面诈骗
////                            String[] strings = TextUtils.convertStrToArray(smsBean.getSmszp().trim());
////                            String str = "";
////
////                            LogUtils.e("tagstrleng" + strings.length);
////                            for (int i = 0; i < strings.length; i++) {
////                                if (!item.equals(strings[i].trim())) {
////                                    str = str + strings[i].trim() + ",";
////                                }
////                            }
////                            if (str.equals("") || str.equals(",")) {
////
////                                XUtils.update(SmsBean.class, "id", smsBean.getId() + "", "smszp", null);
////                                list.remove(position);
////                                notifyDataSetChanged();
////                                return;
////                            }
////                            XUtils.update(SmsBean.class, "id", smsBean.getId() + "", "smszp", str.trim());
////                        } else if (currentItem == 1) {//第二个界面垃圾短信
////
////                            String[] strings = TextUtils.convertStrToArray(smsBean.getSmslj().trim());
////                            String str = "";
////                            for (int i = 0; i < strings.length; i++) {
////                                if (!item.equals(strings[i].trim())) {
////                                    str = str + strings[i].trim() + ",";
////                                }
////                            }
////                            if (str.equals("") || str.equals(",")) {
////                                XUtils.update(SmsBean.class, "id", smsBean.getId() + "", "smslj", null);
////                                list.remove(position);
////                                notifyDataSetChanged();
////                                return;
////                            }
////
////                            XUtils.update(SmsBean.class, "id", smsBean.getId() + "", "smslj", str.trim());
////                        } else if (currentItem == 2) {//第三个界面伪基站
////                            String[] strings = TextUtils.convertStrToArray(smsBean.getSmswjz().trim());
////                            String str = "";
////                            for (int i = 0; i < strings.length; i++) {
////                                if (!item.equals(strings[i].trim())) {
////                                    str = str + strings[i].trim() + ",";
////                                }
////                            }
////                            if (str.equals("") || str.equals(",")) {
////                                XUtils.update(SmsBean.class, "id", smsBean.getId() + "", "smswjz", null);
////                                list.remove(position);
////                                notifyDataSetChanged();
////                                return;
////                            }
////
////                            XUtils.update(SmsBean.class, "id", smsBean.getId() + "", "smswjz", str.trim());
////                        }
////
////                        SmsBean smsBean1 = (SmsBean) XUtils.findAll(SmsBean.class).get(0);
////                        LogUtils.e("log结束" + smsBean1.toString());
//                        list.remove(position);
//                        notifyDataSetChanged();
//
//                        //得到添加的关键字存到数据库
////                        ToastUtils.showSnackBar(snackView, tag + mViewPager.getCurrentItem());
////                        SmsBean smsBean = (SmsBean) XUtils.findAll(SmsBean.class).get(0);
//
////                        if (null == smsBean) {//数据库无数据---直接插入
////                            LogUtils.e("数据库无数据---直接插入");
//////                            SmsBean smsBeanb = new SmsBean();
////                            if (mViewPager.getCurrentItem() == 0) {
////
////
////                                XUtils.update(SmsBean.class, "id", smsBean.getId() + "", "smszp", tag + ",");
////                            } else if (mViewPager.getCurrentItem() == 1) {
////                                XUtils.update(SmsBean.class, "id", smsBean.getId() + "", "smslj", tag + ",");
////                            } else if (mViewPager.getCurrentItem() == 2) {
////                                XUtils.update(SmsBean.class, "id", smsBean.getId() + "", "smswjz", tag + ",");
////                            }
////
////
////                        } else {//有数据时修改添加,并且更新数据
////
////                            if (mViewPager.getCurrentItem() == 0) {
////                                String[] strings = TextUtils.convertStrToArray(smsBean.getSmszp());
////                                String str = "";
////
////                                LogUtils.e("tagstrleng" + strings.length);
////                                for (int i = 0; i < strings.length; i++) {
////                                    str = str + strings[i] + ",";
////                                }
////                                LogUtils.e("tagstr" + str + tag + ",");
////                                XUtils.update(SmsBean.class, "id", smsBean.getId() + "", "smszp", str + tag + ",");
////                            } else if (mViewPager.getCurrentItem() == 1) {
////
////                                String[] strings = TextUtils.convertStrToArray(smsBean.getSmslj());
////                                String str = "";
////                                for (int i = 0; i < strings.length; i++) {
////                                    str = str + strings[i] + ",";
////                                }
////                                XUtils.update(SmsBean.class, "id", smsBean.getId() + "", "smslj", str + tag + ",");
////                            } else if (mViewPager.getCurrentItem() == 2) {
////
////                                String[] strings = TextUtils.convertStrToArray(smsBean.getSmswjz());
////                                String str = "";
////                                for (int i = 0; i < strings.length; i++) {
////                                    str = str + strings[i] + ",";
////                                }
////                                XUtils.update(SmsBean.class, "id", smsBean.getId() + "", "smswjz", str + tag + ",");
////                            }
////
////                        }
////
////                        try {
////                            Bundle[] bundles = fragmnetpageradapter.getmBundles();
////                            Bundle bundlesa = bundles[mViewPager.getCurrentItem()];
////                            ArrayList<String> lists = bundlesa.getStringArrayList("list");
////                            lists.add(tag);
////                            bundlesa.putStringArrayList("list", lists);
////                            bundles[mViewPager.getCurrentItem()] = bundlesa;
////                            /**
////                             *刷新数据
////                             */
////                            for (int j = 0; j < smsNumUi; j++) {
////                                fragmentClass[j] = new FragmentSeeting();
////                            }
////                            mBundles = bundles;
////                            fragmnetpageradapter.setFragments(fragmentClass);
////
////                        } catch (IllegalAccessException e) {
////                            e.printStackTrace();
////                        } catch (InstantiationException e) {
////                            e.printStackTrace();
////                        }
//                    }
//                });
//
//
//                LogUtils.e("点击了" + position);
//            }
//        });
//
//
//    }


