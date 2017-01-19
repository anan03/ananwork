package com.lvshandian.partylive.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/12/9.
 */

public class WXPayBean {

    /**
     * return_value : {"bank_commission_fee":0,"bank_commission_rate":0.001,"liquidator_commission_fee":0,"liquidator_commission_rate":0,"net_receipt_amount":0.01,"nonce_str":"VXVmHcaIoEZklYgmGO0w0u8UXaCUqA5z","package":"Sign=WXPay","pay_platform_fee":0,"pay_platform_rate":0.006,"prepay_id":"wx20161209165305e9e65a4d230692588291","sign":"4EAAAB9C81A5C844275D0DD3A2454A93","timestamp":"1481273585","total_fee":0.01}
     * success : true
     */

    private ReturnValueBean return_value;
    private boolean success;

    public ReturnValueBean getReturn_value() {
        return return_value;
    }

    public void setReturn_value(ReturnValueBean return_value) {
        this.return_value = return_value;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public  class ReturnValueBean {
        /**
         * bank_commission_fee : 0.0
         * bank_commission_rate : 0.001
         * liquidator_commission_fee : 0.0
         * liquidator_commission_rate : 0.0
         * net_receipt_amount : 0.01
         * nonce_str : VXVmHcaIoEZklYgmGO0w0u8UXaCUqA5z
         * package : Sign=WXPay
         * pay_platform_fee : 0.0
         * pay_platform_rate : 0.006
         * prepay_id : wx20161209165305e9e65a4d230692588291
         * sign : 4EAAAB9C81A5C844275D0DD3A2454A93
         * timestamp : 1481273585
         * total_fee : 0.01
         */

        private double bank_commission_fee;
        private double bank_commission_rate;
        private double liquidator_commission_fee;
        private double liquidator_commission_rate;
        private double net_receipt_amount;
        private String nonce_str;
        @SerializedName("package")
        private String packageX;
        private double pay_platform_fee;
        private double pay_platform_rate;
        private String prepay_id;
        private String sign;
        private String timestamp;
        private double total_fee;

        public double getBank_commission_fee() {
            return bank_commission_fee;
        }

        public void setBank_commission_fee(double bank_commission_fee) {
            this.bank_commission_fee = bank_commission_fee;
        }

        public double getBank_commission_rate() {
            return bank_commission_rate;
        }

        public void setBank_commission_rate(double bank_commission_rate) {
            this.bank_commission_rate = bank_commission_rate;
        }

        public double getLiquidator_commission_fee() {
            return liquidator_commission_fee;
        }

        public void setLiquidator_commission_fee(double liquidator_commission_fee) {
            this.liquidator_commission_fee = liquidator_commission_fee;
        }

        public double getLiquidator_commission_rate() {
            return liquidator_commission_rate;
        }

        public void setLiquidator_commission_rate(double liquidator_commission_rate) {
            this.liquidator_commission_rate = liquidator_commission_rate;
        }

        public double getNet_receipt_amount() {
            return net_receipt_amount;
        }

        public void setNet_receipt_amount(double net_receipt_amount) {
            this.net_receipt_amount = net_receipt_amount;
        }

        public String getNonce_str() {
            return nonce_str;
        }

        public void setNonce_str(String nonce_str) {
            this.nonce_str = nonce_str;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public double getPay_platform_fee() {
            return pay_platform_fee;
        }

        public void setPay_platform_fee(double pay_platform_fee) {
            this.pay_platform_fee = pay_platform_fee;
        }

        public double getPay_platform_rate() {
            return pay_platform_rate;
        }

        public void setPay_platform_rate(double pay_platform_rate) {
            this.pay_platform_rate = pay_platform_rate;
        }

        public String getPrepay_id() {
            return prepay_id;
        }

        public void setPrepay_id(String prepay_id) {
            this.prepay_id = prepay_id;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public double getTotal_fee() {
            return total_fee;
        }

        public void setTotal_fee(double total_fee) {
            this.total_fee = total_fee;
        }
    }
}
