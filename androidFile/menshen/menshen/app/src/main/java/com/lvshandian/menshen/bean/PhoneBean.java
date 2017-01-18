package com.lvshandian.menshen.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by zhang on 2016/11/5.
 * 创建手机号码短信拦截类数据库
 */
@Table(name = "tbl_phonesqlite")
public class PhoneBean implements Serializable {


    /**
     * createdDate : 1478340414000
     * dnseg : 123
     * dnsegId : 8
     * dnsegType : 2
     * status : 1
     * userId : 5
     */
    private static final long serialVersionUID = 1L;
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "createdDate")
    private long createdDate;
    @Column(name = "dnseg")
    private String dnseg;
    @Column(name = "dnsegId")
    private int dnsegId;
    @Column(name = "dnsegType")
    private int dnsegType;
    @Column(name = "status")
    private int status;
    @Column(name = "userId")
    private int userId;

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public String getDnseg() {
        return dnseg;
    }

    public void setDnseg(String dnseg) {
        this.dnseg = dnseg;
    }

    public int getDnsegId() {
        return dnsegId;
    }

    public void setDnsegId(int dnsegId) {
        this.dnsegId = dnsegId;
    }

    public int getDnsegType() {
        return dnsegType;
    }

    public void setDnsegType(int dnsegType) {
        this.dnsegType = dnsegType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "PhoneBean{" +
                "createdDate=" + createdDate +
                ", dnseg='" + dnseg + '\'' +
                ", dnsegId=" + dnsegId +
                ", dnsegType=" + dnsegType +
                ", status=" + status +
                ", userId=" + userId +
                '}';
    }
}
