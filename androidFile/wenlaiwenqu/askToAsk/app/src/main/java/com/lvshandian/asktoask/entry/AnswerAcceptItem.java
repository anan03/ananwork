package com.lvshandian.asktoask.entry;

import java.util.List;

/**
 * 答咖采纳 on 2016/9/30.
 */
public class AnswerAcceptItem {



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
        public String answerId;
        public String questionId;
        public String quizzerId;
        public String answererId;
        public String answerData;
        public long answerDate;
        public Object answerDated;
        public Object status;
        public String isaccept;
        public String extend1;
        public Object extend2;


    }
}
