package com.lvshandian.asktoask.entry;

/**
 * 用户 on 2016/9/2.
 */
public class User {

    public volatile static User mUser;
    private String user_answer;
    private String user_ask;
    private String user_collect;
    private String user_fans;
    private String user_grade;
    private String user_head_img;
    private String user_major;
    private String user_real_name;
    private String user_nick_name;
    private String user_praise;
    private String user_school;
    private String user_sex;
    private String user_sign;
    private String area;
    private String user_attentions;
    private String isapprove;
    private String user_money;
    private String user_life_photo;
    private String UserId = "";
    private String extend1;//背景图片

    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1;
    }

    public String getUser_life_photo() {
        return user_life_photo;
    }

    public void setUser_life_photo(String user_life_photo) {
        this.user_life_photo = user_life_photo;
    }

    public void user_money(String user_money) {
        this.user_money = user_money;
    }

    public String getUser_money() {
        return user_money;
    }

    public void setUser_money(String user_money) {
        this.user_money = user_money;
    }

    public String getIsapprove() {
        return isapprove;
    }

    public void setIsapprove(String isapprove) {
        this.isapprove = isapprove;
    }

    public static User getmUser() {
        return mUser;
    }

    public static void setmUser(User mUser) {
        User.mUser = mUser;
    }

    public String getUser_attentions() {
        return user_attentions;
    }

    public void setUser_attentions(String user_attentions) {
        this.user_attentions = user_attentions;
    }

    private User() {
    }

    public static User getUser() {
        if (mUser == null) {
            synchronized (User.class) {
                mUser = (mUser == null) ? new User() : mUser;
            }
        }
        return mUser;
    }

    public String getUser_real_name() {
        return user_real_name;
    }

    public void setUser_real_name(String user_real_name) {
        this.user_real_name = user_real_name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }



    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUser_answer() {
        return user_answer;
    }

    public void setUser_answer(String user_answer) {
        this.user_answer = user_answer;
    }

    public String getUser_ask() {
        return user_ask;
    }

    public void setUser_ask(String user_ask) {
        this.user_ask = user_ask;
    }

    public String getUser_collect() {
        return user_collect;
    }

    public void setUser_collect(String user_collect) {
        this.user_collect = user_collect;
    }

    public String getUser_fans() {
        return user_fans;
    }

    public void setUser_fans(String user_fans) {
        this.user_fans = user_fans;
    }


    public String getUser_grade() {
        return user_grade;
    }

    public void setUser_grade(String user_grade) {
        this.user_grade = user_grade;
    }

    public String getUser_head_img() {
        return user_head_img;
    }

    public void setUser_head_img(String user_head_img) {
        this.user_head_img = user_head_img;
    }

    public String getUser_major() {
        return user_major;
    }

    public void setUser_major(String user_major) {
        this.user_major = user_major;
    }

    public String getUser_nick_name() {
        return user_nick_name;
    }

    public void setUser_nick_name(String user_nick_name) {
        this.user_nick_name = user_nick_name;
    }

    public String getUser_praise() {
        return user_praise;
    }

    public void setUser_praise(String user_praise) {
        this.user_praise = user_praise;
    }

    public String getUser_school() {
        return user_school;
    }

    public void setUser_school(String user_school) {
        this.user_school = user_school;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_sign() {
        return user_sign;
    }

    public void setUser_sign(String user_sign) {
        this.user_sign = user_sign;
    }

    public void cloneUser(User otherUser) {
        this.setUser_answer(otherUser.getUser_answer());
        this.setUser_ask(otherUser.getUser_ask());
        this.setUser_collect(otherUser.getUser_collect());
        this.setUser_fans(otherUser.getUser_fans());
        this.setUser_grade(otherUser.getUser_grade());
        this.setUser_head_img(otherUser.getUser_head_img());
        this.setUser_major(otherUser.getUser_major());
        this.setUser_nick_name(otherUser.getUser_nick_name());
        this.setUser_praise(otherUser.getUser_praise());
        this.setUser_school(otherUser.getUser_school());
        this.setUser_sex(otherUser.getUser_sex());
        this.setUser_sign(otherUser.getUser_sign());
        this.setUser_real_name(otherUser.getUser_real_name());
        this.setArea(otherUser.getArea());
        this.setUser_attentions(otherUser.getUser_attentions());
        this.user_money(otherUser.getUser_money());
        this.setUser_life_photo(otherUser.getUser_life_photo());
        this.setExtend1(otherUser.getExtend1());
    }
}
