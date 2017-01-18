package com.lvshandian.asktoask.entry;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 王伟 on 2016/9/10.
 * 互动推荐
 * 互动推荐列表bean
 */
public class HuDongItem {
    public int status;
    private String msg;
    private DataBean data;
    public  static class DataBean {
        public PageBean pageBean;

        public String pId;
        public String cId;

        public static class PageBean{
            private int pageNum;
            private int pageSize;
            private int totalRecord;
            private int startIndex;
            private int totalPage;
            private int start;
            private int end;
            public List<DataBean2> data;
            public static class DataBean2 implements Serializable{
                public DataBean2() {

                }

                @Override
                public String toString() {
                    return "DataBean2{" +
                            "questionPraise=" + questionPraise +
                            ", questionCollection=" + questionCollection +
                            ", ispraise=" + ispraise +
                            ", iscollect=" + iscollect +
                            '}';
                }

                public DataBean2(String questionTitle, String questionData, String questionType, double questionMoney, String isanonymity, String userId, long questionPublishDate, int questionCollection, int questionPraise, int answerNum, String userHeadImg, String userRealName, String userSex, String questionImgs, String userSchool, String userGrade, String userMajor, String questionId, String isapprove, String extend1, boolean iscollect, boolean ispraise) {
                    this.questionTitle = questionTitle;
                    this.questionData = questionData;
                    this.questionType = questionType;
                    this.questionMoney = questionMoney;
                    this.isanonymity = isanonymity;
                    this.userId = userId;
                    this.questionPublishDate = questionPublishDate;
                    this.questionCollection = questionCollection;
                    this.questionPraise = questionPraise;
                    this.answerNum = answerNum;
                    this.userHeadImg = userHeadImg;
                    this.userRealName = userRealName;
                    this.userSex = userSex;
                    this.questionImgs = questionImgs;
                    this.userSchool = userSchool;
                    this.userGrade = userGrade;
                    this.userMajor = userMajor;
                    this.questionId = questionId;
                    this.isapprove = isapprove;
                    this.extend1 = extend1;
                    this.iscollect = iscollect;
                    this.ispraise = ispraise;
                }
                public String questionTitle;
                public String questionData;
                public String questionType;
                public double questionMoney;
                public String isanonymity;
                public String userId;
                public long questionPublishDate;
                public int questionCollection;
                public int questionPraise;
                public int answerNum;
                public String userHeadImg;
                public String userRealName;
                public String userSex;
                public String questionImgs;
                public String userSchool;
                public String userGrade;
                public String userMajor;

                public String getQuestionId() {
                    return questionId;
                }

                public void setQuestionId(String questionId) {
                    this.questionId = questionId;
                }

                public String questionId;
                public String isapprove;
                public String extend1;
                public boolean iscollect;
                public boolean ispraise;
                public boolean iscollectonclick;
                public boolean ispraiseonclick;

                public String getQuestionTitle() {
                    return questionTitle;
                }

                public void setQuestionTitle(String questionTitle) {
                    this.questionTitle = questionTitle;
                }

                public String getQuestionData() {
                    return questionData;
                }

                public void setQuestionData(String questionData) {
                    this.questionData = questionData;
                }

                public String getQuestionType() {
                    return questionType;
                }

                public void setQuestionType(String questionType) {
                    this.questionType = questionType;
                }

                public double getQuestionMoney() {
                    return questionMoney;
                }

                public void setQuestionMoney(double questionMoney) {
                    this.questionMoney = questionMoney;
                }

                public String getIsanonymity() {
                    return isanonymity;
                }

                public void setIsanonymity(String isanonymity) {
                    this.isanonymity = isanonymity;
                }

                public String getUserId() {
                    return userId;
                }

                public void setUserId(String userId) {
                    this.userId = userId;
                }

                public long getQuestionPublishDate() {
                    return questionPublishDate;
                }

                public void setQuestionPublishDate(long questionPublishDate) {
                    this.questionPublishDate = questionPublishDate;
                }

                public int getQuestionCollection() {
                    return questionCollection;
                }

                public void setQuestionCollection(int questionCollection) {
                    this.questionCollection = questionCollection;
                }

                public int getQuestionPraise() {
                    return questionPraise;
                }

                public void setQuestionPraise(int questionPraise) {
                    this.questionPraise = questionPraise;
                }

                public int getAnswerNum() {
                    return answerNum;
                }

                public void setAnswerNum(int answerNum) {
                    this.answerNum = answerNum;
                }

                public String getUserHeadImg() {
                    return userHeadImg;
                }

                public void setUserHeadImg(String userHeadImg) {
                    this.userHeadImg = userHeadImg;
                }

                public String getUserRealName() {
                    return userRealName;
                }

                public void setUserRealName(String userRealName) {
                    this.userRealName = userRealName;
                }

                public String getUserSex() {
                    return userSex;
                }

                public void setUserSex(String userSex) {
                    this.userSex = userSex;
                }

            }
        }
    }




    }





