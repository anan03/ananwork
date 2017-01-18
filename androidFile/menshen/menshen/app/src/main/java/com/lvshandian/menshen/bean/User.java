package com.lvshandian.menshen.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by zhang on 2016/10/26.
 */
@Table(name = "menshen_user_bean")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * cratedDate : 1477480564000
     * headPortraitImgUrl : http://img.hb.aicdn.com/6efd0741811f618867acc1c55a06d99709b4ef132141-1J1yrM_fw236
     * integral : 0
     * password : 00c66aaf5f2c3f49946f15c1ad2ea0d3
     * phone : 18513884422
     * status : 1
     * updatedDate : 1477485109000
     * userId : 1
     * userName : 18513884422
     * userType : 1
     */
    @Column(name = "id", isId = true)
    private int id;
    //子女的Id
    @Column(name = "cratedDate")
    private long cratedDate;
    @Column(name = "headPortraitImgUrl")
    private String headPortraitImgUrl;
    @Column(name = "integral")
    private int integral;
    @Column(name = "password")
    private String password;
    @Column(name = "phone")
    private String phone;
    @Column(name = "status")
    private int status;
    @Column(name = "updatedDate")
    private long updatedDate;
    @Column(name = "userId")
    private int userId;
    @Column(name = "userName")
    private String userName;
    @Column(name = "userType")
    private int userType;

    public long getCratedDate() {
        return cratedDate;
    }

    public void setCratedDate(long cratedDate) {
        this.cratedDate = cratedDate;
    }

    public String getHeadPortraitImgUrl() {
        return headPortraitImgUrl;
    }

    public void setHeadPortraitImgUrl(String headPortraitImgUrl) {
        this.headPortraitImgUrl = headPortraitImgUrl;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(long updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", cratedDate=" + cratedDate +
                ", headPortraitImgUrl='" + headPortraitImgUrl + '\'' +
                ", integral=" + integral +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                ", updatedDate=" + updatedDate +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userType=" + userType +
                '}';
    }
}
