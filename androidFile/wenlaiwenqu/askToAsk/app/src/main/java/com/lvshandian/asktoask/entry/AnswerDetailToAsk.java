package com.lvshandian.asktoask.entry;

/**
 * 答咖详情跳转回答列表跳转到提问者信息 on 2016/10/20.
 */
public class AnswerDetailToAsk {
    public int status;
    public String msg;
    public DataBean data;
    public static class DataBean {
        public String cId;
        public String pId;
        public UserAndQuestionBean userAndQuestion;
        public static class UserAndQuestionBean {
            public String userId;
            public String userHeadImg;
            public String userRealName;
            public String userSex;
            public String userSchool;
            public String userGrade;
            public String userMajor;
            public String questionId;
            public String questionTitle;
            public String questionImgs;
            public long questionPublishDate;
            public int questionCollection;
            public int questionPraise;
            public double questionMoney;
            public String questionData;
            public int answerNum;
            public String extend1;
            public boolean isCollect;
            public boolean isParse;
            public String isapprove;
            public String isanonymity;
            public String questionType;
            public double getQuestionMoney;

        }
    }
}
