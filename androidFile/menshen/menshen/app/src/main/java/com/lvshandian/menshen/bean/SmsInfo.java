package com.lvshandian.menshen.bean;

import java.io.Serializable;

/**
 * Created by zhang on 2016/10/27.
 * 创建短信对象
 */

public class SmsInfo implements Serializable {

    public String _id = "";//短信序号
    public String address = "";//address：发件人地址，即手机号，如+86138138000
    public long date;//date：日期，long型，如1346988516，可以对日期显示格式进行设置
    public String body;//短信类型1是接收到的，2是已发出
    public String person;//person：发件人，如果发件人在通讯录中则为具体姓名，陌生人为null
    public int read;//read：是否阅读0未读，1已读
    public int type;//短信类型1是接收到的，2是已发出
    public String thread_id;//短信回话的Id
    public int upLoadType;

    public void setUpLoadType(int upLoadType) {
        this.upLoadType = upLoadType;
    }

    public int getUpLoadType() {
        return upLoadType;
    }

    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }

    public SmsInfo(String _id, String address, long date, String body, String person, int read, int type, String thread_id) {
        this._id = _id;
        this.address = address;
        this.date = date;
        this.body = body;
        this.person = person;
        this.read = read;
        this.type = type;
        this.thread_id = thread_id;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SmsInfo{" +
                "_id='" + _id + '\'' +
                ", address='" + address + '\'' +
                ", date=" + date +
                ", body='" + body + '\'' +
                ", person='" + person + '\'' +
                ", read=" + read +
                ", type=" + type +
                ", thread_id='" + thread_id + '\'' +
                ", upLoadType=" + upLoadType +
                '}';
    }

    //    public int action;// 1代表设置为已读，2表示删除短信
//
//
//    public String thread_id = "";


    //                  _id：短信序号，如100
//
//            　　thread_id：对话的序号，如100，与同一个手机号互发的短信，其序号是相同的
//    　　
//            　　address：发件人地址，即手机号，如+86138138000
//            　　
//            　　person：发件人，如果发件人在通讯录中则为具体姓名，陌生人为null
//    　　
//            　　date：日期，long型，如1346988516，可以对日期显示格式进行设置
//    　　
//            　　protocol：协议0SMS_RPOTO短信，1MMS_PROTO彩信
//    　　
//            　　read：是否阅读0未读，1已读
//    　　
//            　　status：短信状态-1接收，0complete,64pending,128failed
//    　　
//            　　type：短信类型1是接收到的，2是已发出
//    　　
//            　　body：短信具体内容
//    　　
//            　　service_center：短信服务中心号码编号，如+8613800755500


}
