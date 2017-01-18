package com.lvshandian.menshen.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by zhang on 2016/10/28.
 * 创建短信以保存短信.以及通话的拦截字段；
 */
@Table(name = "tbl_children_sms")
public class SmsBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "smszp")//短信诈骗
    private String smszp;
    @Column(name = "smslj")//垃圾短信
    private String smslj;
    @Column(name = "smswjz")//伪基站
    private String smswjz;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSmszp() {
        return smszp;
    }

    public void setSmszp(String smszp) {
        this.smszp = smszp;
    }

    public String getSmslj() {
        return smslj;
    }

    public void setSmslj(String smslj) {
        this.smslj = smslj;
    }

    public String getSmswjz() {
        return smswjz;
    }

    public void setSmswjz(String smswjz) {
        this.smswjz = smswjz;
    }

    @Override
    public String toString() {
        return "SmsBean{" +
                "id=" + id +
                ", smszp='" + smszp + '\'' +
                ", smslj='" + smslj + '\'' +
                ", smswjz='" + smswjz + '\'' +
                '}';
    }
}
