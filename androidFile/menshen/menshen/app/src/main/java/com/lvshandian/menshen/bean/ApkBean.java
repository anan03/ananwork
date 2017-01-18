package com.lvshandian.menshen.bean;

import java.io.Serializable;

/**
 * Created by zhang on 2016/12/7.
 */

public class ApkBean implements Serializable {


    /**
     * appSizeMax : 20
     * appSizeSmall : 1
     * earlyWarningId : 4
     * name : 土豆
     * type : 1
     */

    private Double appSizeMax;
    private Double appSizeSmall;
    private int earlyWarningId;
    private String name;
    private int type;

    public Double getAppSizeMax() {
        return appSizeMax;
    }

    public void setAppSizeMax(Double appSizeMax) {
        this.appSizeMax = appSizeMax;
    }

    public Double getAppSizeSmall() {
        return appSizeSmall;
    }

    public void setAppSizeSmall(Double appSizeSmall) {
        this.appSizeSmall = appSizeSmall;
    }

    public int getEarlyWarningId() {
        return earlyWarningId;
    }

    public void setEarlyWarningId(int earlyWarningId) {
        this.earlyWarningId = earlyWarningId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
