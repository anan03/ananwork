package com.lvshandian.partylive.bean;

/**
 * Created by Administrator on 2016/11/25.
 */

public class GiftBean {

    /**
     * id : 1
     * name : GOAL
     * staticIcon : http://img.meelive.cn/ODIzMjExNDY1ODcyMzgw.jpg
     * currencyAmount : 1
     * combo : 1  可以连点1
     * createTime : 1466492347523
     */

    private String id;
    private String name;
    private String staticIcon;
    private String currencyAmount;
    private String combo;
    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStaticIcon() {
        return staticIcon;
    }

    public void setStaticIcon(String staticIcon) {
        this.staticIcon = staticIcon;
    }

    public String getCurrencyAmount() {
        return currencyAmount;
    }

    public void setCurrencyAmount(String currencyAmount) {
        this.currencyAmount = currencyAmount;
    }

    public String getCombo() {
        return combo;
    }

    public void setCombo(String combo) {
        this.combo = combo;
    }

    public String getCreateTime() {
        return createTime;
    }



    @Override
    public String toString() {
        return "GiftBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", staticIcon='" + staticIcon + '\'' +
                ", currencyAmount='" + currencyAmount + '\'' +
                ", combo='" + combo + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
