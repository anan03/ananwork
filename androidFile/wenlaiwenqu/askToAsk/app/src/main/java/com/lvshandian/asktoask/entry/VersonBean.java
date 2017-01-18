package com.lvshandian.asktoask.entry;

/**
 * Created by zhang on 2016/10/21.
 */

public class VersonBean {


    /**
     * versionId : null
     * versionNumber : 1.4
     * addDate : null
     * versionUrl : 20161010/12c29feb7fa74cdf9157fd31ad4585b6.apk
     * versionDescription : 2222222222222222222
     * status : 0
     * isdel : 0
     * extend1 : null
     * extend2 : null
     */

    private Object versionId;
    private String versionNumber;
    private Object addDate;
    private String versionUrl;
    private String versionDescription;
    private String status;
    private String isdel;
    private Object extend1;
    private Object extend2;

    public Object getVersionId() {
        return versionId;
    }

    public void setVersionId(Object versionId) {
        this.versionId = versionId;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public Object getAddDate() {
        return addDate;
    }

    public void setAddDate(Object addDate) {
        this.addDate = addDate;
    }

    public String getVersionUrl() {
        return versionUrl;
    }

    public void setVersionUrl(String versionUrl) {
        this.versionUrl = versionUrl;
    }

    public String getVersionDescription() {
        return versionDescription;
    }

    public void setVersionDescription(String versionDescription) {
        this.versionDescription = versionDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsdel() {
        return isdel;
    }

    public void setIsdel(String isdel) {
        this.isdel = isdel;
    }

    public Object getExtend1() {
        return extend1;
    }

    public void setExtend1(Object extend1) {
        this.extend1 = extend1;
    }

    public Object getExtend2() {
        return extend2;
    }

    public void setExtend2(Object extend2) {
        this.extend2 = extend2;
    }

    @Override
    public String toString() {
        return "VersonBean{" +
                "versionId=" + versionId +
                ", versionNumber='" + versionNumber + '\'' +
                ", addDate=" + addDate +
                ", versionUrl='" + versionUrl + '\'' +
                ", versionDescription='" + versionDescription + '\'' +
                ", status='" + status + '\'' +
                ", isdel='" + isdel + '\'' +
                ", extend1=" + extend1 +
                ", extend2=" + extend2 +
                '}';
    }
}
