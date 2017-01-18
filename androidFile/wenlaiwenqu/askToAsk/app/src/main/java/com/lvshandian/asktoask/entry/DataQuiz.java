package com.lvshandian.asktoask.entry;

import java.util.List;

/**
 * Created by zhang on 2016/10/6.
 * <p>
 * 创建提问实体类
 */
public class DataQuiz {

    private int status;
    private String msg;

    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String questionId;
        private String questionTitle;
        private String questionData;
        private String questionImgs;
        private String questionType;
        private String questionMoney;
        private String isanonymity;
        private String userId;
        private long questionPublishDate;
        private String questionDeleteDate;
        private String status;
        private String questionCollection;
        private String questionPraise;
        private String voucherMoney;
        private String finalMoney;
        private int answerNum;
        private String extend1;
        private String extend2;

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

        public String getQuestionMoney() {
            return questionMoney;
        }

        public void setQuestionMoney(String questionMoney) {
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

        public String getQuestionCollection() {
            return questionCollection;
        }

        public void setQuestionCollection(String questionCollection) {
            this.questionCollection = questionCollection;
        }

        public String getQuestionPraise() {
            return questionPraise;
        }

        public void setQuestionPraise(String questionPraise) {
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
    }
}
