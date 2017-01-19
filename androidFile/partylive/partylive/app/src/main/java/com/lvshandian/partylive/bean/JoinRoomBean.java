package com.lvshandian.partylive.bean;

import java.util.List;

/**
 * Created by sll on 2016/12/16.
 */

public class JoinRoomBean {

    /**
     * success : true
     * code : 1
     * msg : 该用户进入过房间
     * obj : [{"recordId":2149,"roomId":4598,"userId":12858,"obligate1":null,"obligate2":null,"obligate3":null,"createTime":"2016-12-16 17:50:01","updateTime":"2016-12-16 17:50:01"}]
     */

    private boolean success;
    private int code;
    private String msg;
    /**
     * recordId : 2149
     * roomId : 4598
     * userId : 12858
     * obligate1 : null
     * obligate2 : null
     * obligate3 : null
     * createTime : 2016-12-16 17:50:01
     * updateTime : 2016-12-16 17:50:01
     */

    private List<ObjBean> obj;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ObjBean> getObj() {
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        private int recordId;
        private int roomId;
        private int userId;
        private Object obligate1;
        private Object obligate2;
        private Object obligate3;
        private String createTime;
        private String updateTime;

        public int getRecordId() {
            return recordId;
        }

        public void setRecordId(int recordId) {
            this.recordId = recordId;
        }

        public int getRoomId() {
            return roomId;
        }

        public void setRoomId(int roomId) {
            this.roomId = roomId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public Object getObligate1() {
            return obligate1;
        }

        public void setObligate1(Object obligate1) {
            this.obligate1 = obligate1;
        }

        public Object getObligate2() {
            return obligate2;
        }

        public void setObligate2(Object obligate2) {
            this.obligate2 = obligate2;
        }

        public Object getObligate3() {
            return obligate3;
        }

        public void setObligate3(Object obligate3) {
            this.obligate3 = obligate3;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
