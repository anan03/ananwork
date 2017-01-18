package com.lvshandian.menshen.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * 创建个人的信息表
 * Created by zhang on 2016/7/28.
 */
@Table(name = "tbl_children_user")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "childId")
    private int childId;
    @Column(name = "account")
    private String account;
    @Column(name = "name")
    private String name;
    @Column(name = "head")
    private String head;
    @Column(name = "phone")
    private String phone;
    //token值
    @Column(name = "token")
    private String token;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", childId=" + childId +
                ", account='" + account + '\'' +
                ", name='" + name + '\'' +
                ", head='" + head + '\'' +
                ", phone='" + phone + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}