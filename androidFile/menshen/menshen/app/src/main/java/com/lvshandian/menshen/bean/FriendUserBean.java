package com.lvshandian.menshen.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * 朋友工具类
 * Created by sll on 2016/10/13.
 */
@Table(name = "friend_user_bean")
public class FriendUserBean implements Serializable {

    /**
     * "ISBLOCK":0,
     * PHONE : 18701593115
     * NAME : 13641168243
     * HEADPORTRAIT : http://192.168.1.165:8080/uploadFiles/uploadImgs/headportrait/d3cf0026fa
     * GROUPNAME : 好友
     * FRIENDPHONE : 13641168243
     * GROUPINF_ID : 1
     */
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "PHONE")
    private String PHONE;
    @Column(name = "NAME")
    private String NAME;
    @Column(name = "HEADPORTRAIT")
    private String HEADPORTRAIT;
    @Column(name = "GROUPNAME")
    private String GROUPNAME;
    @Column(name = "FRIENDPHONE")
    private String FRIENDPHONE;
    @Column(name = "GROUPINF_ID")
    private String GROUPINF_ID;
    @Column(name = "ISBLOCK")
    private int ISBLOCK;

    public int getISBLOCK() {
        return ISBLOCK;
    }

    public void setISBLOCK(int ISBLOCK) {
        this.ISBLOCK = ISBLOCK;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getHEADPORTRAIT() {
        return HEADPORTRAIT;
    }

    public void setHEADPORTRAIT(String HEADPORTRAIT) {
        this.HEADPORTRAIT = HEADPORTRAIT;
    }

    public String getGROUPNAME() {
        return GROUPNAME;
    }

    public void setGROUPNAME(String GROUPNAME) {
        this.GROUPNAME = GROUPNAME;
    }

    public String getFRIENDPHONE() {
        return FRIENDPHONE;
    }

    public void setFRIENDPHONE(String FRIENDPHONE) {
        this.FRIENDPHONE = FRIENDPHONE;
    }

    public String getGROUPINF_ID() {
        return GROUPINF_ID;
    }

    public void setGROUPINF_ID(String GROUPINF_ID) {
        this.GROUPINF_ID = GROUPINF_ID;
    }
}
