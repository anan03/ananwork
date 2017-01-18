package com.lvshandian.menshen.bean;

import java.io.Serializable;

/**
 * Created by zhang on 2016/11/6.
 */

public class SmsUpload implements Serializable {
    /**
     * 类型
     */
    private int smsUploadType;
    /**
     * 用户id
     */
    private int userId;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 备注姓名
     */
    private String noteName;
    /**
     * 发送时间
     */
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.sql.Date receptionTime;
    /**
     * 内容
     */
    private String content;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public java.sql.Date getReceptionTime() {
        return receptionTime;
    }

    public void setReceptionTime(java.sql.Date receptionTime) {
        this.receptionTime = receptionTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSmsUploadType() {

        return smsUploadType;
    }

    public SmsUpload(int smsUploadType, int userId, String phone, String noteName, java.sql.Date receptionTime, String content) {
        this.smsUploadType = smsUploadType;
        this.userId = userId;
        this.phone = phone;
        this.noteName = noteName;
        this.receptionTime = receptionTime;
        this.content = content;
    }

    public void setSmsUploadType(int smsUploadType) {
        this.smsUploadType = smsUploadType;
    }

    @Override
    public String toString() {
        return "SmsUpload{" +
                "smsUploadType=" + smsUploadType +
                ", userId=" + userId +
                ", phone='" + phone + '\'' +
                ", noteName='" + noteName + '\'' +
                ", receptionTime=" + receptionTime +
                ", content='" + content + '\'' +
                '}';
    }
}
