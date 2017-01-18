package com.lvshandian.asktoask.entry;

import java.util.List;

/**
 * 答咖搜索 on 2016/9/29.
 */
public class AnswerSearch {



    public int status;
    public String msg;
   

    public List<DataBean> data;
    

    public List<DataBean> getData() {
        return data;
    }
    
    public static class DataBean {
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
        public boolean isfocus;


      
    }
}
