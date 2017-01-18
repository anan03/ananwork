package com.lvshandian.asktoask.entry;

/**
 * Created by Administrator on 2016/9/28 0028.
 * 创建用户优惠券Item
 */
public class DiscountCoupon {


    private String discountCouponId;
    private String discountCouponMoney;
    private String discountCouponDate;
    private String discountCouponDated;
    private String status;
    private String userId;

    public String getDiscountCouponId() {
        return discountCouponId;
    }

    public void setDiscountCouponId(String discountCouponId) {
        this.discountCouponId = discountCouponId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDiscountCouponMoney() {
        return discountCouponMoney;
    }

    public void setDiscountCouponMoney(String discountCouponMoney) {
        this.discountCouponMoney = discountCouponMoney;
    }

    public String getDiscountCouponDate() {
        return discountCouponDate;
    }

    public void setDiscountCouponDate(String discountCouponDate) {
        this.discountCouponDate = discountCouponDate;
    }

    public String getDiscountCouponDated() {
        return discountCouponDated;
    }

    public void setDiscountCouponDated(String discountCouponDated) {
        this.discountCouponDated = discountCouponDated;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "Youhuiquanitem{" +
                "discountCouponId='" + discountCouponId + '\'' +
                ", discountCouponMoney='" + discountCouponMoney + '\'' +
                ", discountCouponDate='" + discountCouponDate + '\'' +
                ", discountCouponDated='" + discountCouponDated + '\'' +
                ", status='" + status + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
