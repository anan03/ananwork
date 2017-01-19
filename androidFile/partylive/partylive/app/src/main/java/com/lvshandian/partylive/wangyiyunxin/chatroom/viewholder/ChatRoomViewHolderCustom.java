package com.lvshandian.partylive.wangyiyunxin.chatroom.viewholder;

import android.graphics.Color;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.bean.CustomGiftBean;
import com.lvshandian.partylive.bean.CustomLightBean;
import com.lvshandian.partylive.bean.CustomdateBean;
import com.lvshandian.partylive.utils.JavaBeanMapUtils;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.utils.Sharedpreferences;
import com.lvshandian.partylive.utils.TextUtils;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.common.util.sys.ScreenUtil;
import com.netease.nim.uikit.session.emoji.MoonUtil;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderBase;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderText;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sll on 2016/11/22.
 */

/**
 * 直播室聊天的ViewHolder，用于填充直播室聊天数据，根据扩展消息的type不同展示不同数据
 *
 * @author sll
 * @time 2016/11/22 17:07
 */
public class ChatRoomViewHolderCustom extends MsgViewHolderBase {
    @Override
    protected boolean isChatRoom() {
        return true;
    }

    @Override
    protected boolean isShowBubble() {
        return false;
    }

    @Override
    protected boolean isShowHeadImage() {
        return true;
    }

    @Override
    public void setNameTextView() {
        nameContainer.setPadding(ScreenUtil.dip2px(6), 0, 0, 0);
        ChatRoomViewHolderHelper.setNameTextView((ChatRoomMessage) message, nameTextView, nameIconView, context);
        nameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick();
            }
        });
        nameIconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick();
            }
        });

    }

    @Override
    protected int getContentResId() {
        return com.netease.nim.uikit.R.layout.nim_message_item_text_join;
    }

    @Override
    protected void inflateContentView() {

    }

    @Override
    protected void bindContentView() {
        TextView bodyTextView = findViewById(com.netease.nim.uikit.R.id.nim_message_item_text_body_join);
        TextView bodyLightView = findViewById(com.netease.nim.uikit.R.id.nim_message_item_light_body);
        bodyTextView.setTextColor(Color.WHITE);
        bodyTextView.setPadding(ScreenUtil.dip2px(6), 0, 0, 0);
        bodyTextView.setMovementMethod(LinkMovementMethod.getInstance());
        bodyTextView.setOnLongClickListener(longClickListener);
        bodyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick();
            }
        });
        bodyTextView.setVisibility(View.VISIBLE);
        bodyLightView.setVisibility(View.GONE);
        messageItemLayout.setVisibility(View.VISIBLE);
        String text = "";
        if (message.getRemoteExtension() != null) {
            Map<String, Object> remote = message.getRemoteExtension();
            String typeText = (String) remote.get("type");
            LogUtils.i("WangYi", "type:" + typeText + "\nremoteExtension:" + remote);
            if (!TextUtils.isEmpty(typeText)) {
                int type = Integer.parseInt(typeText);
                /**  101//创建表演
                 102 //观看表演
                 103//开始表演
                 104 //结束自己创建表演
                 105 //进入房间
                 106 //离开房间
                 107 //点亮
                 108 //点赞
                 109 //用户打赏
                 110 //观众开始连线
                 111 //观众断开连线
                 112 //主播恢复前台拍摄
                 113 //主播切换到后台
                 */
                switch (type) {
                    case 102:
                        //观众想看表演
                        //愿意支付你的表演，要加油哦！
                        text = "愿意支付你的表演，要加油哦！";
                        break;
                    case 103:
                        //主播开始表演
                        //主播开始表演了，请支付5，没支付的看不到哦！
                        //{type=103, data={"show_id":"99","cost":"5"}}
                        text = ((ChatRoomMessage) message).getChatRoomMessageExtension().getSenderNick() + "开始表演了，请支付，没支付的看不到哦！";
                        try {
                            JSONObject jsonObject = new JSONObject((String) remote.get("data"));
                            text = ((ChatRoomMessage) message).getChatRoomMessageExtension().getSenderNick() + "开始表演了，请支付" + jsonObject.get("cost") + "，没支付的看不到哦！";
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 104:
                        //主播结束自己创建表演
                        //主播表演结束了，谢谢大家支持！
                        text = "主播表演结束了，谢谢大家支持！";
                        break;
                    case 105:
                        String ZhuBoId = (String) Sharedpreferences.getParam(context, "ZhuBoId", "");
                        Map map = (Map) remote.get("data");
                        CustomdateBean customdate = null;
                        if (map != null && map.size() > 0) {
                            LogUtils.i("WangYi", "map.size:" + map.size());
                            customdate = JavaBeanMapUtils.mapToBean((Map) remote.get("data"), CustomdateBean.class);
                        }
                        LogUtils.i("WangYi", "ZhuBoId:" + ZhuBoId);
                        //进入房间
                        String id = customdate.getUserId();
                        if (TextUtils.isEmpty(id)) {
                            id = customdate.getId();
                        }
                        if (customdate != null && id != null && id.equals(ZhuBoId)) {
                            //如果是主播 "创建房间成功";
                            text = "创建房间成功";
                        } else {
                            //"进入房间";
                            text = "进入房间";
                        }
                        break;
//                    case 106:
//                        //离开房间
//                        // = "主播" + data.get("roomNickname") + "结束直播";
//                        //return ChatRoomViewHolderCustom.class;
//                        break;
                    case 107:
                        //点亮
                        text = "点亮了";
                        bodyLightView.setVisibility(View.VISIBLE);
                        break;
//                    case 108:
//                        //点赞
//                        break;
                    case 109:
                        bodyTextView.setTextColor(context.getResources().getColor(R.color.tv_main));
                        CustomGiftBean customGiftBean = JavaBeanMapUtils.mapToBean(remote, CustomGiftBean.class);
                        if (customGiftBean != null) {
                            LogUtils.i("WangYi", "customGiftBean != null");
                            text = (String) remote.get("gift_item_message");
//                            text=customGiftBean.getGift_item_message();
                        } else {
                            LogUtils.i("WangYi", "customGiftBean == null");
                        }
                        break;
//                    case 110:
//                        break;
//                    case 111:
//                        break;
                    case 112:
                        //主播恢复前台拍摄
                        text = "主播恢复前台拍摄";
                        break;
                    case 113:
                        //主播切换到后台
                        text = "主播切换到后台";
                        break;
                    case 200:
                        //关于点播的本地消息
                        text = (String) remote.get("text");
                        break;
                    default:
                        //默认不显示任何东西
//                        messageItemLayout.setPadding(0, 0, 0, 0);
                        bodyTextView.setVisibility(View.GONE);
                        messageItemLayout.setVisibility(View.GONE);
//                        return ChatRoomMsgViewHolderNotification.class;
                        break;

                }
            }
        }

        MoonUtil.identifyFaceExpression(NimUIKit.getContext(), bodyTextView, text, ImageSpan.ALIGN_BOTTOM);
    }

}
