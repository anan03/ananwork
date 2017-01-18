package com.lvshandian.menshen.bean;

/**
 * Created by zhang on 2016/10/27.
 */

public class VersonBean {


    /**
     * versionUpdatingId : 2
     * system : android
     * versionNumber : 2.0
     * downloadUrl : http://data.fanrenqiji.com/upload/14745442015019593.apk
     * status : 1
     * createdDate : null
     * updatedDate : null
     */

    private String versionUpdatingId;
    private String system;
    private double versionNumber;
    private String downloadUrl;
    private int status;
    private Object createdDate;
    private Object updatedDate;

    public String getVersionUpdatingId() {
        return versionUpdatingId;
    }

    public void setVersionUpdatingId(String versionUpdatingId) {
        this.versionUpdatingId = versionUpdatingId;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public double getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(double versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Object createdDate) {
        this.createdDate = createdDate;
    }

    public Object getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Object updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "VersonBean{" +
                "versionUpdatingId='" + versionUpdatingId + '\'' +
                ", system='" + system + '\'' +
                ", versionNumber=" + versionNumber +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", status=" + status +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
