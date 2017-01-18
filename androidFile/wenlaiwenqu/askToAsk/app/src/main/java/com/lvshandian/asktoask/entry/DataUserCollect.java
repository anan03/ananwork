package com.lvshandian.asktoask.entry;

import java.util.List;

/**
 * Created by Administrator on 2016/9/21.
 * <p/>
 * 创建收藏解析类
 */
public class DataUserCollect {

   

    public int status;
    public String msg;
  

    public DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        public String cId;
        public String pId;


        public List<QuestionsBean> questions;

        public String getCId() {
            return cId;
        }

        public void setCId(String cId) {
            this.cId = cId;
        }

        public String getPId() {
            return pId;
        }

        public void setPId(String pId) {
            this.pId = pId;
        }

        public List<QuestionsBean> getQuestions() {
            return questions;
        }

        public void setQuestions(List<QuestionsBean> questions) {
            this.questions = questions;
        }

        public static class QuestionsBean {
            public String questionId;
            public String questionTitle;
            public String questionData;
            public String questionImgs;
            public String questionType;
            public double questionMoney;
            public String isanonymity;
            public String userId;
            public long questionPublishDate;
            public String questionDeleteDate;
            public String status;
            public int questionCollection;
            public int questionPraise;
            public String voucherMoney;
            public String finalMoney;
            public int answerNum;
            public String extend1;
            public String extend2;
            public String userHeadImg;
            public String userRealName;
            public String isapprove;
            public String userSex;
            public String userSchool;
            public String userGrade;
            public String userMajor;
            public boolean isCollect;
            public boolean isParse;


            public String getQuestionId() {
                return questionId;
            }

            public void setQuestionId(String questionId) {
                this.questionId = questionId;
            }

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

            public String getQuestionImgs() {
                return questionImgs;
            }

            public void setQuestionImgs(String questionImgs) {
                this.questionImgs = questionImgs;
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

            public String getQuestionDeleteDate() {
                return questionDeleteDate;
            }

            public void setQuestionDeleteDate(String questionDeleteDate) {
                this.questionDeleteDate = questionDeleteDate;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
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

            public String getVoucherMoney() {
                return voucherMoney;
            }

            public void setVoucherMoney(String voucherMoney) {
                this.voucherMoney = voucherMoney;
            }

            public String getFinalMoney() {
                return finalMoney;
            }

            public void setFinalMoney(String finalMoney) {
                this.finalMoney = finalMoney;
            }

            public int getAnswerNum() {
                return answerNum;
            }

            public void setAnswerNum(int answerNum) {
                this.answerNum = answerNum;
            }

            public String getExtend1() {
                return extend1;
            }

            public void setExtend1(String extend1) {
                this.extend1 = extend1;
            }

            public String getExtend2() {
                return extend2;
            }

            public void setExtend2(String extend2) {
                this.extend2 = extend2;
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

            public String getIsapprove() {
                return isapprove;
            }

            public void setIsapprove(String isapprove) {
                this.isapprove = isapprove;
            }

            public String getUserSex() {
                return userSex;
            }

            public void setUserSex(String userSex) {
                this.userSex = userSex;
            }

            public String getUserSchool() {
                return userSchool;
            }

            public void setUserSchool(String userSchool) {
                this.userSchool = userSchool;
            }

            public String getUserGrade() {
                return userGrade;
            }

            public void setUserGrade(String userGrade) {
                this.userGrade = userGrade;
            }

            public String getUserMajor() {
                return userMajor;
            }

            public void setUserMajor(String userMajor) {
                this.userMajor = userMajor;
            }
        }
    }
}
