package com.lvshandian.asktoask.entry;

import java.util.List;

/**
 * 答咖里的首页搜索返回答师 on 2016/10/14.
 */
public class AnswerSearchHome {



    public int status;
    public String msg;
    public List<DataBean> data;


    public static class DataBean {
        public boolean isfocus;
        public String userId;
        public String userName;
        public String userPassword;
        public String userRegistDate;
        public String userLoginDate;
        public String userUpdateDate;
        public String userDeleteDate;
        public String status;
        public String salt;
        public String isapprove;
        public String userHeadImg;
        public String userSign;
        public String userSex;
        public String userRealName;
        public String userSchool;
        public String userGrade;
        public String userMajor;
        public String area;
        public String userNickName;
        public String userQqName;
        public String userQqPassword;
        public String userLifePhoto;
        public String userStudentCard;
        public String userIdentityCardFront;
        public String userIdentityCardVerso;
        public String extend1;
        public String extend2;
        public int userPraise;
        public int userCollect;
        public String userFans;
        public String userAttentions;
        public String userAnswer;
        public String userAsk;


    }

      
    
}
