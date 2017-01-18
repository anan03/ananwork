package com.lvshandian.asktoask.entry;

import java.util.List;

/**
 * 答咖提问 on 2016/9/30.
 */
public class AnswerAskItem {



    public int status;
    public String msg;

    public List<DataBean> data;

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
        public Object questionId;
        public Object questionTitle;
        public String questionData;
        public Object questionImgs;
        public Object questionType;
        public Object questionMoney;
        public Object isanonymity;
        public Object userId;
        public long questionPublishDate;
        public Object questionDeleteDate;
        public Object status;
        public Object questionCollection;
        public Object questionPraise;
        public Object voucherMoney;
        public Object finalMoney;
        public int answerNum;
        public Object extend1;
        public Object extend2;


    }
}
