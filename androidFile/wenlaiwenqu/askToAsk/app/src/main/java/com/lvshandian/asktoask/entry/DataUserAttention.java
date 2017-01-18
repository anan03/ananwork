package com.lvshandian.asktoask.entry;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/21.
 * 创建我的模块关注实体类
 */
public class DataUserAttention {


    /**
     * status : 200
     * msg : OK
     * data : [{"userHeadImg":"http://101.201.120.234:8080/wlwq/resource//images/147505710690921.jpg","attentoredId":"147505675642345","userPraise":0,"userRealName":"北京市朝阳区","userCollect":7,"attentorId":null,"extend2":null,"isapprove":"0"},{"userHeadImg":"http://101.201.120.234:8080/wlwq/resource//images/147505710690921.jpg","attentoredId":"147505675642345","userPraise":0,"userRealName":"北京市朝阳区","userCollect":7,"attentorId":null,"extend2":null,"isapprove":"0"},{"userHeadImg":null,"attentoredId":"2","userPraise":0,"userRealName":"张亚楠","userCollect":0,"attentorId":null,"extend2":null,"isapprove":"1"},{"userHeadImg":"http://101.201.120.234:8080/wlwq/resource//images/147507210334159.jpg","attentoredId":"147507204339246","userPraise":0,"userRealName":"鸟人还是超人","userCollect":8,"attentorId":null,"extend2":null,"isapprove":"1"},{"userHeadImg":"http://101.201.120.234:8080/wlwq/resource//images/147517532627251.jpg","attentoredId":"147514992113254","userPraise":0,"userRealName":"奥特曼打小怪兽","userCollect":21,"attentorId":null,"extend2":null,"isapprove":"1"},{"userHeadImg":"http://101.201.120.234:8080/wlwq/resource//images/147517532627251.jpg","attentoredId":"147514992113254","userPraise":0,"userRealName":"奥特曼打小怪兽","userCollect":21,"attentorId":null,"extend2":null,"isapprove":"1"},{"userHeadImg":null,"attentoredId":"2","userPraise":0,"userRealName":"张亚楠","userCollect":0,"attentorId":null,"extend2":null,"isapprove":"1"},{"userHeadImg":"http://101.201.120.234:8080/wlwq/resource//images/147507210334159.jpg","attentoredId":"147507204339246","userPraise":0,"userRealName":"鸟人还是超人","userCollect":8,"attentorId":null,"extend2":null,"isapprove":"1"},{"userHeadImg":"http://101.201.120.234:8080/wlwq/resource//images/147507210334159.jpg","attentoredId":"147507204339246","userPraise":0,"userRealName":"鸟人还是超人","userCollect":8,"attentorId":null,"extend2":null,"isapprove":"1"}]
     */

    private int status;
    private String msg;
    /**
     * userHeadImg : http://101.201.120.234:8080/wlwq/resource//images/147505710690921.jpg
     * attentoredId : 147505675642345
     * userPraise : 0
     * userRealName : 北京市朝阳区
     * userCollect : 7
     * attentorId : null
     * extend2 : null
     * isapprove : 0
     */

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
        private String attentoredId;
        private int userPraise;
        private String userRealName;
        private int userCollect;
        private int attentorId;
        private Object extend2;
        private String isapprove;
        public boolean isfocus;

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
            this.attentoredId = attentoredId;
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

        public int getAttentorId() {
            return attentorId;
        }

        public void setAttentorId(int attentorId) {
            this.attentorId = attentorId;
        }

        public Object getExtend2() {
            return extend2;
        }

        public void setExtend2(Object extend2) {
            this.extend2 = extend2;
        }

        public String getIsapprove() {
            return isapprove;
        }

        public void setIsapprove(String isapprove) {
            this.isapprove = isapprove;
        }
    }
}
