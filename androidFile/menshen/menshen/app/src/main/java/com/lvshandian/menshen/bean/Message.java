package com.lvshandian.menshen.bean;

import java.io.Serializable;

/**
 * Created by zhang on 2016/11/1.
 * 创建消息通知界面对象
 */

public class Message implements Serializable {


    /**
     * systemMessagesId : 3
     * userId : 1
     * messageType : 1
     * title : 2
     * isRead : 0
     * status : 1
     * createdDate : 1477918442000
     * updatedDate : null
     * deletedDate : null
     * content : 2
     */

    private int systemMessagesId;
    private int userId;
    private int messageType;
    private String title;
    private int isRead;
    private int status;
    private long createdDate;
    private Object updatedDate;
    private Object deletedDate;
    private String content;

    @Override
    public String toString() {
        return "Message{" +
                "systemMessagesId=" + systemMessagesId +
                ", userId=" + userId +
                ", messageType=" + messageType +
                ", title='" + title + '\'' +
                ", isRead=" + isRead +
                ", status=" + status +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", deletedDate=" + deletedDate +
                ", content='" + content + '\'' +
                '}';
    }

    public int getSystemMessagesId() {
        return systemMessagesId;
    }

    public void setSystemMessagesId(int systemMessagesId) {
        this.systemMessagesId = systemMessagesId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public Object getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Object updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Object getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Object deletedDate) {
        this.deletedDate = deletedDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
