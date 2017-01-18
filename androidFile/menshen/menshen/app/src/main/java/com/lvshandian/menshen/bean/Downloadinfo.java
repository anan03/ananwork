package com.lvshandian.menshen.bean;

import android.graphics.drawable.Drawable;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by zhang on 2016/11/12.
 */
@Table(name = "tbl_downloadinfo")
public class Downloadinfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "appIcon")
    private Drawable appIcon; // 应用程序图标i
    @Column(name = "pkgName")
    private String pkgName; // 应用程序所对应的包名
    @Column(name = "appName")
    private String appName;// 应用程序的名称private Drawable appIcon;
    @Column(name = "apkSize")
    private String apkSize;// 应用程序的名称private Drawable appIcon;
    @Column(name = "activityName")
    private String activityName;// 应用程序的名称private Drawable appIcon;


    public String getApkSize() {
        return apkSize;
    }

    public void setApkSize(String apkSize) {
        this.apkSize = apkSize;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public String toString() {
        return "Downloadinfo{" +
                "appIcon=" + appIcon +
                ", pkgName='" + pkgName + '\'' +
                ", appName='" + appName + '\'' +
                ", apkSize='" + apkSize + '\'' +
                ", activityName='" + activityName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Downloadinfo that = (Downloadinfo) o;

        if (id != that.id) return false;
        if (appIcon != null ? !appIcon.equals(that.appIcon) : that.appIcon != null) return false;
        if (pkgName != null ? !pkgName.equals(that.pkgName) : that.pkgName != null) return false;
        if (appName != null ? !appName.equals(that.appName) : that.appName != null) return false;
        if (apkSize != null ? !apkSize.equals(that.apkSize) : that.apkSize != null) return false;
        return activityName != null ? activityName.equals(that.activityName) : that.activityName == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (appIcon != null ? appIcon.hashCode() : 0);
        result = 31 * result + (pkgName != null ? pkgName.hashCode() : 0);
        result = 31 * result + (appName != null ? appName.hashCode() : 0);
        result = 31 * result + (apkSize != null ? apkSize.hashCode() : 0);
        result = 31 * result + (activityName != null ? activityName.hashCode() : 0);
        return result;
    }
}
