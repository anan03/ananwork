package com.lvshandian.asktoask.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by ldb on 2015/3/25.  接口地址
 */
public class UrlBuilder {

    // 服务器
    public static final String serverUrl = "http://wenlaiwenqu.cn";//公网
    //    public static final String serverUrl = "http://101.201.120.234:8080";//公网
//    public static final String serverUrl = "http://192.168.0.109:8080"; //测试
    public final String THIRDPARTY_LOGIN_URL = "/wlwq/appthirdparty/login";                     //第三方登录
    public final String LOGIN_URL = "/wlwq/appuser/login.do";                                  //登录
    public final String REGIST_URL = "/wlwq/appuser/regist.do";                                //注册
    public final String SMS_COOD = "/wlwq/appsms/getSms.do";                                  //短信验证
    public final String WANGJIMIMA_URL = "/wlwq/appuser/resetPassword.do";                     //修改密码
    public final String MINE_HALFSCREENDATA = "/wlwq/appapp/myInformation.do";                 //我的页 上半屏幕数据
    public final String MINE_YIJIANFANKUI_URL = "/wlwq/appfeedback/addFeedback.do";            //意见反馈
    public final String MINE_HUOQUYAOQINGMA_URL = "/wlwq/appuser/findInvitationCode.do";       //获取邀请码
    public final String MINE_XIUGAISHOUJIHAO_URL = "/wlwq/appuser/resetPhone.do";              //更改手机号
    public final String WODEXUANSHANG_GETXUANSHANG_URL = "/wlwq/appmyReward/reward";        //我的悬赏
    public final String WODEXUANSHANG_TIXIAN_URL = "/wlwq/appcash/alipayCash.do";              //提现
    public final String MINE_YOUHUIQUAN_URL = "/wlwq/appdiscountCoupon/fmdiscop.do";           //优惠券列表
    //    public final String MINE_DUIHUANYOUHUIQUAN_URL = "/wlwq/appcdkey/cdkey.do";                //兑换优惠券
    public final String MINE_DUIHUANYOUHUIQUAN_URL = "/wlwq/appcdkey/getCdkey.do";                //兑换优惠券
    public final String MINE_XIUGAIGERENXINXI_URL = "/wlwq/appuser/resetHeadPicture.do";       //修改个人信息
    public final String HUDONG_TUIJIAN_URL = "/wlwq/appinteraction/recommends.do";       //互动列表推荐信息
    public final String HUDONG_JINRI_URL = "/wlwq/appinteraction/toDayQuestion.do";        //互动今日数据
    public final String HUDONG_HOOT_DOOR = "/wlwq/appinteraction/hotQuestions.do";        //互动热门数据
    public final String HUDONG_GREAT_NUMBER = "/wlwq/appinteraction/greatNumber.do";        //互动高悬赏数据
    public final String HUDONG_UNSOLVED = "/wlwq/appinteraction/unsolved.do";        //互动未解决数据
    public final String HUDONG_IMAGE_URL = "/wlwq/appinteraction/interacteTop.do";        //互动轮播图和问题类型
    public final String HUDONG_SEARCH_URL = "/wlwq/appinteraction/searchByHotWord.do";        //互动搜索
    public final String HUDONG_DETAIL_MASTER_ANSWER = "/wlwq/appreply/findAnswersBy3Id.do";  //互动详情答师回答列表
    public final String HUDONG_DETAIL_ANSWER_QUESTION = "/wlwq/appreply/answer.do";  //互动详情回答问题发送问题答案
    public final String HUDONG_QUESTION_TYPE_URL = "/wlwq/appquestion/drycargo.do";  //互动问题类型分页加载
    public final String HUDONGWARNCONTENT_URL = "/wlwq/appReport/getReportTypes.do";  // 互动举报内容


    public final String ANSWER_CHIEFLY_URL = "/wlwq/appmennakagahapati/carouselAndHotTeacher.do";  //答咖首页banner图和列表
    public final String ANSWER_PLACE_URL = "/wlwq/appmennakagahapati/searchByAreaKey.do";  //答咖搜索地区答师列表
    public static final String ANSWER_DETAIL_ANSWER_ASKPENPLE = "/wlwq/appquestion/questionInfo.do";//答咖详情跳转到提问者的信息
    public final String ANSWERMASTERAPPROVE_URL = "/wlwq/appuser/authentication.do";  // 答师认证
    public final String ANSWERSEARCH_URL = "/wlwq/appmennakagahapati/findByHotWord.do";  //答咖搜索
    public final String ANSERSEARCHDETAIL_URL = "/wlwq/appmennakagahapati/mennakagahapatiInfo.do";//答咖详情
    public final String ANSWER_SCHOOL_URL = "/wlwq/appmennakagahapati/searchBySchoolKey.do";  //答咖搜索学校答师列表
    public final String ASK_URL = "/wlwq/appquiz/findQuizs.do";//答咖详情提问
    public final String LEAVE_WORDS_URL = "/wlwq/appleaverM/leaverMessage.do";//答咖详情留言
    public final String ANSWER_APPROVE_CURRENT_URL = "/wlwq/appuser/clickAuthentication.do";  //点击认证时 答咖
    public static String MESSAGE_READ_BOOK = "/wlwq/appleaverM/updateLeaverMessage.do";////取消留言人未读书


    public final String USER_COLLECT = "/wlwq/appquestion/myCollections.do";  //我的模块收藏
    public final String USER_ATTENTION = "/wlwq/appuser/myAttentions.do";  //我的模块关注
    public final String USER_FANDEDETAILS = "/wlwq/appuser/myFansesInfo.do";  //我的模块粉丝详情界面
    public final String USER_ATTENTIONDETAILS = "/wlwq/appuser/myAttentionInfo.do";  //我的模块关注详情界面
    public final String USER_FANAES = "/wlwq/appuser/myFanses.do";//我的模块粉丝请求
    public final String USER_QUIZ = "/wlwq/appquestion/myAsks.do";//我的模块提问
    public final String USER_ANSWERS = "/wlwq/appquestion/myAnswers.do";//我的模块回答
    public final static String USER_QUIZ_DETAILS = "/wlwq/appquestion/myAskInfoById.do";//我的模块回答
    public final static String USER_ACCEPT = "/wlwq/appAccept/acceptAnswers.do";//我的提问模块：采纳
    public static final String USER_ANSWER_QUESTION_INFO = "/wlwq/appreply/findAnswersBy2Id.do";//我的模块回答详情列表；
    public static final String USER_ANSWER_DATAILS = "/wlwq/appquestion/myAnswersInfo.do";//我的模块回答详情，回答问题接口；
    public static final String USER_ANSWER_QUESTION = "/wlwq/appreply/answer.do";//我的模块回答详情，回答详情列表接口；


    public static final String MESSAGER_LEAVE = "/wlwq/appleaverM/findLeaver.do";//消息留言界面
    public static final String MESSAGER_LEAVE_ANSWER = "/wlwq/appleaverM/leaverMessage.do";//消息留言回答
    public static final String PRAISE_URL = "/wlwq/appquestion/clickPraise.do";//点赞
    public static final String PRAISE_CANCEL_URL = "/wlwq/appquestion/cancleParise.do";//取消点赞
    public static final String COLLECT_URL = "/wlwq/appmutually/collection.do";  //收藏
    public static final String COLLECT_CANCLE_URL = "/wlwq/appmutually/cancleCollection.do";  //取消收藏
    public static final String COLLECT_HEAD = "/wlwq/appuser/testHeadImg";  //修改个人头像
    public final String HUDONGWARN_URL = "/wlwq/appReport/reportOthers.do";  // 互动详情举报
    //    public final String POSTASKQUESTION_URL = "/wlwq/appquestion/publishQuestion.do";  // 发布问题
    public final String POSTASKQUESTION_URL = "/wlwq/appquestion/az_publishQuestion.do";  // 发布问题测试


    public static final String FOCUS_ANSWER_URL = "/wlwq/appattent/clickAttention.do";//关注
    public static final String ATTENTION_ADOPTIONRATE = "/wlwq/appmennakagahapati/findAcceptAnswers.do";//采纳率
    public static final String ATTENTION_AUIZ = "/wlwq/appquiz/findQuizs.do";//提问
    public static final String ATTENTION_ANDWER = "/wlwq/appquestion/myAnswers.do";//回答
    public static final String CANCLE_FOCUS_ANSWER_URL = "/wlwq/appattent/cancelAttention.do";//取消关注
    public final String COUPON_URL = "/wlwq/appdiscountCoupon/fmdiscop.do";//发布问题优惠劵
    public final String LEAVE_MESSAGE_ME = "/wlwq/appleaverM/leaverDatas.do";//留言详情 列表接口
    public static final String LEAVE_INSTATION_EAMIL_URL = "/wlwq/appMail/myMails.do";//消息模块中的站内信列表


    public static final String LEAVE_INSTATION_EAMIL_DETAIL_URL = "/wlwq/appMail/mailInfo.do";//我的模块中站内信信息详情

    public final String LEAVE_WORDS_CODEURL = "/wlwq/appleaverM/leaverMessage.do";//留言接口
    public final String ACCEPT_URL = "/wlwq/appmennakagahapati/findAcceptAnswers.do";//采纳率
    public final String INVITE_URL = "/wlwq/appuser/findInvitationCode";  //邀请码 推荐好友获取到验证码


    public final String INVITE_URL_SUCCESS = "/wlwq/appuser/recommend.do";  //邀请码 推荐好友获取到验证码

    public final String VERSION_URL = "/wlwq/appVersion/findNewVersion.do";  //版本更新


    /**
     * 构建请求url
     *
     * @param route
     * @param params
     * @return
     */
    public String build(String route, Map<String, String> params) {
        StringBuilder sb = new StringBuilder(serverUrl);//.append("/").append(serverName);
        sb.append(route);
        if (params != null && params.size() > 0) {
            int index = 0;
            for (String key : params.keySet()) {
                String mark = index++ == 0 ? "?" : "&";
                try {
                    String value = URLEncoder.encode(params.get(key), "UTF-8");
                    sb.append(mark).append(key).append("=").append(value);
                } catch (UnsupportedEncodingException e) {
                }
            }
        }
        return sb.toString();
    }

    public String buildImageUrl(String url) {
        StringBuilder sb = new StringBuilder(serverUrl);//.append("/").append(serverName);
        sb.append("/upload/");
        sb.append(url);
        return sb.toString();
    }

    public String thumbnailImageUrl(String url) {
        StringBuilder STR = new StringBuilder();
        for (int i = 0; i < url.length(); i++) {
            char ch = url.charAt(i);
            if (!(url.charAt(i) + "").equals("\\")) {
                STR.append(url.charAt(i));
            }


        }
        return STR.toString();
    }


}
