package com.lvshandian.asktoask.common.http;

/**
 * author: newlq on 2016/9/1 17:08.
 * email： @lvshandian.com
 * company:北京绿闪电科技有限公司
 * details：请求常量类， 用于判断请求与handler 回调的msg.what 的值 是否是统一请求
 */
public interface RequestCode {

    /**
     * 默认系统登录请求码
     */
    int LOGIN_TAG = 1121;         //第三方登录
    int SIMPLE_LOGIN_URL_REQUESTCODE = 100;         //普通登录
    int SIMPLE_REGISTER_URL_REQUESTCODE = 101;      //普通注册
    int SMS_COOD = 103;      //注册验证码发送
    int SMS_ALTER_COOD = 104;      //修改密码验证码发送
    int SMS_FIND_COOD = 105;      //密码验证码发送
    int SMS_ALTERPHONE_COOD = 106;      //更换手机号码
    int WANGJIMIMA_URL_REQUESTCODE = 102;      //忘记密码

    int MINE_UPHAFLSCRRENDATA_REQUESTCODE = 500;
    int MINE_XIUGAISHOUJIHAO_REQUESTCODE = 501;    //修改手机号
    int MINE_WANGJIMIMA_REQUESTCODE = 502;          //忘记手机号
    int MINE_YIJIANFANKUI_REQUESTCODE = 503;          //意见反馈
    int GETXUANSHANG_REQUESTCODE = 504;                         //获取悬赏所得到的金额
    int GETXUANSHANG_TIXIAN_REQUESTCODE = 505;          //确认支付宝提现
    int YOUHUIQUAN_YOUHUIQUANLIEBIAO_REQUESTCODE = 506;          //优惠券列表
    int YOUHUIQUAN_DUIHUANYOUHUIQUAN_REQUESTCODE = 507;          //兑换优惠券
    int XIUGAIGERENXINXI_REQUESTCODE = 508;                      //修改个人信息
    int XIUGAIGERENXINXI_COED = 510;//修改个人信息2
    int XIUGAIGRENXINXIofONE_REQUESTCODE = 509;                      //修改个人信息其中的一项
    int HUDONGTUIJIAN_RECODE = 600;    //互动推荐
    int HUDONGBANNER_RECODE = 601;     //互动轮播图
    int HUDONGSEARCH_RECODE = 602;      //互动搜索
    int HUDONGTODAY_RECODE = 603;    //互动今日
    int HUDONGGREAT_RECODE = 604;    //互动高悬赏
    int HUDONGUNSOLVED_RECODE = 605;//互动未解决
    int HUDONG_DETAIL_MASTER_ANSWER = 607;//互动详情里面的答师回答
    int HUDONGHOTDOOR = 606;    //互动热门
    int ANSWER_CODE = 700;      //答咖的界面
    int ANSER_SEARCH = 701;       //答咖搜索
    int ANSWER_DETAIL = 702;      //答咖详情
    int ANSWER_DETAIL_ANSWER_ASK_MEN = 709;//答咖详情 回答跳转提问者详情
    int USERCOLLECT_CODE = 701;      //我的模块收藏
    int USERATTENTION_CODE = 702;      //我的模块关注
    int USERFANSES_CODE = 703;      //我的模块粉丝
    int USERANSWER_DETAILS_CODE = 706;      //我的模块回答
    int USERATTENTION_DETAILS_CODE = 707;      //我的模块关注详情
    int LIKE_CODE = 708;      //我的模块关注详情关注
    int ATTENTION_AUIZ_CODE = 716;      //提问
    int ATTENTION_ANDWER_CODE = 717;  // 回答
    int ATTENTION_ADOPTIONRATE_CODE = 715;      //采纳率
    int USERANSWER_YESCODE = 710;      //收藏成功
    int USERANSWER_NOCODE = 711;      //取消收藏
    int ANSWERQUESTIONS = 714;      //回答问题
    int USERANSWER_LIKEYESCODE = 712;      //点赞成功
    int USERANSWER_LIKENOCODE = 713;      //取消点赞
    int USERQUIZ_CODE = 704;      //我的模块提问
    int INVITE_CODE = 704;      //邀请码
    int INVITE_CODE_SUCCESS = 999;      //邀请码成功了告诉后台
    int USERANSWER_CODE = 705;      //我的模块回答
    int MESSAGE_LEAVE_CODE = 800;      //消息模块留言
    int MESSAGE_LEAVE_CODE_MYLIST = 801;      //消息模块我的列表
    int MESSAEG_NO_READ_READ = 890;      //消息模块我的列表
    int INSTATION_MESSAGET = 803;      //我的模块中的站内信
    int INSTATION_MESSAGET_DETAIL = 800;      //我的模块中的站内信详情
    int MESSAGE_LEAVE_ANSWER = 801;      //消息留言回答
    int PRAISE_CODE = 900;  //点赞
    int USER_ANSWER_QUWSTION_COOD = 902;  //回答详情界面回答问题
    int USER_ANSWER_QUWSTION_COOD_INFO = 903;  //回答详情界面，回答列表；
    int USER_ANSWER_DATAILS_CODE = 905;  //回答详情界面，回答列表；
    int USER_AQUZA_DETAILS_CODE = 904;  //提问详情界面id；
    int USER_ACCEPT = 906;  //采纳
    int PRAISE_CANCLE_CODE = 901;  //取消点赞
    int COLLECT_CODE = 1000;   //收藏
    int COLLECT_CANCLE_CODE = 1001;   //收藏
    int POST_QUESTION_CODE = 999;   //发布问题
    int HUDONG_WARN_CODE = 1111;   //互动详情里面的举报内容
    int HUDONG_WARN_SUCCESS_CODE = 1112;  //互动详情里面的举报成功
    int ANSWER_QUESTION_CODE = 1113;      //回答问题
    int FOCUS_RECODE = 1114;   //关注
    int CANCLE_FOCUS_CODE = 1115;//取消关注
    int ASK_COUPON = 1116;  //发布问题里有的优惠劵
    int LEAVE_WORDS = 1117;   //答咖详情里的留言
    int LEAVE_WORDS_MESSAGE = 1117;   //详情留言
    int ASK_CODE = 1118;     //答咖详情里的提问
    int ACCEPT_CODE = 1119;  //答咖里的采纳
    int HU_DONG_QUESTION_TYPE = 1200;//互动问题类型列表 图片信息
    int ANSWER_APPROVE_CURRENT = 1201;  //答咖认证时
    int ANSWER_MASTER_APPROVE = 1203;    //答咖认证提交
    int ANSWER_SEARCH_PLACE = 1204;       //答咖认证时选择地区
    int ANSWER_SEARCH_SCHOOL = 1205;       //答咖认证时选择学校
    int VERSION_CODE = 1206;
    int FILE_DATA = 1027;
}
