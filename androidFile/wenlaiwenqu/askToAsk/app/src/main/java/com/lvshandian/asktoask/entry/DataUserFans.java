package com.lvshandian.asktoask.entry;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/21.
 * 创建我的模块粉丝实体类
 */
public class DataUserFans {

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
        private String userHeadImg;
        public  String  attentoredId;
        private int userPraise;
        private String userRealName;
        private int userCollect;
        private String attentorId;
        private String extend2;
        private String isapprove;
        public Boolean isfocus;

        public String getUserHeadImg() {
            return userHeadImg;
        }

        public void setUserHeadImg(String userHeadImg) {
            this.userHeadImg = userHeadImg;
        }

        public String getAttentoredId() {
            return attentoredId;
        }

        public void setAttentoredId(String attentoredId) {
            this.attentorId = attentoredId;
        }

        public int getUserPraise() {
            return userPraise;
        }

        public void setUserPraise(int userPraise) {
            this.userPraise = userPraise;
        }

        public String getUserRealName() {
            return userRealName;
        }

        public void setUserRealName(String userRealName) {
            this.userRealName = userRealName;
        }

        public int getUserCollect() {
            return userCollect;
        }

        public void setUserCollect(int userCollect) {
            this.userCollect = userCollect;
        }

        public String getAttentorId() {
            return attentorId;
        }

        public void setAttentorId(String attentorId) {
            this.attentorId = attentorId;
        }

        public String getExtend2() {
            return extend2;
        }

        public void setExtend2(String extend2) {
            this.extend2 = extend2;
        }

        public String getIsapprove() {
            return isapprove;
        }

        public void setIsapprove(String isapprove) {
            this.isapprove = isapprove;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "userHeadImg='" + userHeadImg + '\'' +
                    ", attentoredId='" + attentoredId + '\'' +
                    ", userPraise=" + userPraise +
                    ", userRealName='" + userRealName + '\'' +
                    ", userCollect=" + userCollect +
                    ", attentorId='" + attentorId + '\'' +
                    ", extend2='" + extend2 + '\'' +
                    ", isapprove='" + isapprove + '\'' +
                    '}';
        }
    }
}
