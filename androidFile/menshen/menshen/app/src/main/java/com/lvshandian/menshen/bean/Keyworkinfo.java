package com.lvshandian.menshen.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by zhang on 2016/11/1.
 * <p>
 * 创建关键字对象
 */
@Table(name = "tbl_keyworkinfo")
public class Keyworkinfo implements Serializable {


    /**
     * createdDate : 1477984388000
     * keyword : 抽奖了
     * keywordId : 1
     * keywordType : 1
     * status : 1
     * updatedDate : 1477986171000
     * userId : 1
     * 创建关键字短信数据库
     */
    private static final long serialVersionUID = 1L;
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "createdDate")
    private long createdDate;
    @Column(name = "keyword")
    private String keyword;
    @Column(name = "keywordId")
    private int keywordId;
    @Column(name = "keywordType")
    private int keywordType;
    @Column(name = "status")
    private int status;
    @Column(name = "updatedDate")
    private long updatedDate;
    @Column(name = "userId")
    private int userId;

    public long getCreatedDate() {
        return createdDate;
    }

    public Keyworkinfo() {
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(int keywordId) {
        this.keywordId = keywordId;
    }

    public int getKeywordType() {
        return keywordType;
    }

    public void setKeywordType(int keywordType) {
        this.keywordType = keywordType;
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

    public Keyworkinfo(String keyword, int keywordType) {
        this.keyword = keyword;
        this.keywordType = keywordType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Keyworkinfo{" +
                "createdDate=" + createdDate +
                ", keyword='" + keyword + '\'' +
                ", keywordId=" + keywordId +
                ", keywordType=" + keywordType +
                ", status=" + status +
                ", updatedDate=" + updatedDate +
                ", userId=" + userId +
                '}';
    }
}
