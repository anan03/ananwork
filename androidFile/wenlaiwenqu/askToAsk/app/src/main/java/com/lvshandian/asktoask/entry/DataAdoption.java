package com.lvshandian.asktoask.entry;

import java.util.List;

/**
 * Created by zhang on 2016/10/6.
 * <p/>
 * 创建采纳率实体类
 */
public class DataAdoption {


    /**
     * status : 200
     * msg : OK
     * data : [{"answerId":"147488096355479","questionId":"147485849438024","quizzerId":"1","answererId":"1","answerData":"哥哥","answerDate":1474880963000,"answerDated":null,"status":null,"isaccept":"1","extend1":"执念客测试使用","extend2":null},{"answerId":"147513510259308","questionId":"147513456708378","quizzerId":"1","answererId":"1","answerData":"默默","answerDate":1475135102000,"answerDated":null,"status":null,"isaccept":"1","extend1":"测试采纳、","extend2":null},{"answerId":"147513513459232","questionId":"147513493479014","quizzerId":"147512779708778","answererId":"1","answerData":"哈哈","answerDate":1475135134000,"answerDated":null,"status":null,"isaccept":"1","extend1":"人","extend2":null}]
     */

    private int status;
    private String msg;
    /**
     * answerId : 147488096355479
     * questionId : 147485849438024
     * quizzerId : 1
     * answererId : 1
     * answerData : 哥哥
     * answerDate : 1474880963000
     * answerDated : null
     * status : null
     * isaccept : 1
     * extend1 : 执念客测试使用
     * extend2 : null
     */

    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String answerId;
        private String questionId;
        private String quizzerId;
        private String answererId;
        private String answerData;
        private long answerDate;
        private Object answerDated;
        private Object status;
        private String isaccept;
        private String extend1;
        private Object extend2;

        public String getAnswerId() {
            return answerId;
        }

        public void setAnswerId(String answerId) {
            this.answerId = answerId;
        }

        public String getQuestionId() {
            return questionId;
        }

        public void setQuestionId(String questionId) {
            this.questionId = questionId;
        }

        public String getQuizzerId() {
            return quizzerId;
        }

        public void setQuizzerId(String quizzerId) {
            this.quizzerId = quizzerId;
        }

        public String getAnswererId() {
            return answererId;
        }

        public void setAnswererId(String answererId) {
            this.answererId = answererId;
        }

        public String getAnswerData() {
            return answerData;
        }

        public void setAnswerData(String answerData) {
            this.answerData = answerData;
        }

        public long getAnswerDate() {
            return answerDate;
        }

        public void setAnswerDate(long answerDate) {
            this.answerDate = answerDate;
        }

        public Object getAnswerDated() {
            return answerDated;
        }

        public void setAnswerDated(Object answerDated) {
            this.answerDated = answerDated;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public String getIsaccept() {
            return isaccept;
        }

        public void setIsaccept(String isaccept) {
            this.isaccept = isaccept;
        }

        public String getExtend1() {
            return extend1;
        }

        public void setExtend1(String extend1) {
            this.extend1 = extend1;
        }

        public Object getExtend2() {
            return extend2;
        }

        public void setExtend2(Object extend2) {
            this.extend2 = extend2;
        }
    }
}
