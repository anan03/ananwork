package com.lvshandian.asktoask.entry;

import java.util.List;

/**
 *  on 2016/9/23.
 *  答咖详情
 */
public class AnswerDetail {
    public int status;
    public String msg;
    public DataBean data;

    public static class DataBean {
        public int userAnswer;
        public int userFans;
        public String userHeadImg;
        public int userAttentions;
        public String userGrade;
        public String adoptionRates;
        public String userSchool;
        public String userSex;
        public int userAsk;
        public String userRealName;
        public String userMajor;
        public String isAttention;
        public String extend2;
        public List<AnswerBean> answer;

        public int getUserAnswer() {
            return userAnswer;
        }

        public void setUserAnswer(int userAnswer) {
            this.userAnswer = userAnswer;
        }

        public int getUserFans() {
            return userFans;
        }

        public void setUserFans(int userFans) {
            this.userFans = userFans;
        }


        public static class AnswerBean {
            public String answerId;
            public String questionId;
            public String quizzerId;
            public String answererId;
            public String answerData;
            public long answerDate;
            public String answerDated;
            public String status;
            public String isaccept;
            public String extend1;
            public String extend2;


        }
    }
}
