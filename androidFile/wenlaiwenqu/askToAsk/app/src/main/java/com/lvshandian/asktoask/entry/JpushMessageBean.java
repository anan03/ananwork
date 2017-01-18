package com.lvshandian.asktoask.entry;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhang on 2016/10/8.
 */
public class JpushMessageBean {



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

    public static class DataBean implements Serializable {
        private String mailId;
        private String userId;
        private String mailTitle;
        private long mailDate;
        private String mailData;
        private Object status;
        private Object extend1;
        private Object extend2;

        public String getMailId() {
            return mailId;
        }

        public void setMailId(String mailId) {
            this.mailId = mailId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getMailTitle() {
            return mailTitle;
        }

        public void setMailTitle(String mailTitle) {
            this.mailTitle = mailTitle;
        }

        public long getMailDate() {
            return mailDate;
        }

        public void setMailDate(long mailDate) {
            this.mailDate = mailDate;
        }

        public String getMailData() {
            return mailData;
        }

        public void setMailData(String mailData) {
            this.mailData = mailData;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public Object getExtend1() {
            return extend1;
        }

        public void setExtend1(Object extend1) {
            this.extend1 = extend1;
        }

        public Object getExtend2() {
            return extend2;
        }

        public void setExtend2(Object extend2) {
            this.extend2 = extend2;
        }
    }
}
