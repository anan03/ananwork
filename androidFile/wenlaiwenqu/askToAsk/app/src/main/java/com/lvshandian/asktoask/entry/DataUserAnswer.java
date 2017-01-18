package com.lvshandian.asktoask.entry;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/21.
 * <p/>
 * 创建我的模块回答实体类
 */
public class DataUserAnswer implements Serializable {


    /**
     * status : 200
     * msg : OK
     * data : {"cId":"147600661319243,147601069589839,147600808293638,147601739646611","pId":"147601069589839,147602099490193","userAndQuestions":[{"userId":"147601067035339","userHeadImg":"http://101.201.120.234:8080/wlwq/resource//images/147601084252972.jpg","userRealName":"张亚楠","userSex":"男","userSchool":"河南理工","userGrade":"大一","userMajor":"计算机","questionId":"147602099490193","questionTitle":"图拼游戏来吧","questionImgs":"http://101.201.120.234:8080/wlwq/resource//images/147602099489553.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489504.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489526.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489588.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489572.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489508.jpg","questionPublishDate":1476020994000,"questionCollection":0,"questionPraise":1,"questionMoney":0,"questionData":"拼图","answerNum":3},{"userId":"147601067035339","userHeadImg":"http://101.201.120.234:8080/wlwq/resource//images/147601084252972.jpg","userRealName":"张亚楠","userSex":"男","userSchool":"河南理工","userGrade":"大一","userMajor":"计算机","questionId":"147602099490193","questionTitle":"图拼游戏来吧","questionImgs":"http://101.201.120.234:8080/wlwq/resource//images/147602099489553.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489504.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489526.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489588.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489572.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489508.jpg","questionPublishDate":1476020994000,"questionCollection":0,"questionPraise":1,"questionMoney":0,"questionData":"拼图","answerNum":3},{"userId":"147601067035339","userHeadImg":"http://101.201.120.234:8080/wlwq/resource//images/147601084252972.jpg","userRealName":"张亚楠","userSex":"男","userSchool":"河南理工","userGrade":"大一","userMajor":"计算机","questionId":"147602099490193","questionTitle":"图拼游戏来吧","questionImgs":"http://101.201.120.234:8080/wlwq/resource//images/147602099489553.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489504.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489526.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489588.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489572.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489508.jpg","questionPublishDate":1476020994000,"questionCollection":0,"questionPraise":1,"questionMoney":0,"questionData":"拼图","answerNum":3},{"userId":"147601067035339","userHeadImg":"http://101.201.120.234:8080/wlwq/resource//images/147601084252972.jpg","userRealName":"张亚楠","userSex":"男","userSchool":"河南理工","userGrade":"大一","userMajor":"计算机","questionId":"147601739646611","questionTitle":"北京好玩吗","questionImgs":"http://101.201.120.234:8080/wlwq/resource//images/147601739646090.jpg,http://101.201.120.234:8080/wlwq/resource//images/147601739646018.jpg,http://101.201.120.234:8080/wlwq/resource//images/147601739646018.jpg,http://101.201.120.234:8080/wlwq/resource//images/147601739646064.jpg,http://101.201.120.234:8080/wlwq/resource//images/147601739646021.jpg,http://101.201.120.234:8080/wlwq/resource//images/147601739646021.jpg,http://101.201.120.234:8080/wlwq/resource//images/147601739646186.jpg,http://101.201.120.234:8080/wlwq/resource//images/147601739646121.jpg","questionPublishDate":1476017396000,"questionCollection":1,"questionPraise":0,"questionMoney":0,"questionData":"风景区挺多的","answerNum":1},{"userId":"147601067035339","userHeadImg":"http://101.201.120.234:8080/wlwq/resource//images/147601084252972.jpg","userRealName":"张亚楠","userSex":"男","userSchool":"河南理工","userGrade":"大一","userMajor":"计算机","questionId":"147601069589839","questionTitle":"有问题吗","questionImgs":"","questionPublishDate":1476010695000,"questionCollection":1,"questionPraise":1,"questionMoney":0,"questionData":"有的","answerNum":1}]}
     */

    private int status;
    private String msg;
    /**
     * cId : 147600661319243,147601069589839,147600808293638,147601739646611
     * pId : 147601069589839,147602099490193
     * userAndQuestions : [{"userId":"147601067035339","userHeadImg":"http://101.201.120.234:8080/wlwq/resource//images/147601084252972.jpg","userRealName":"张亚楠","userSex":"男","userSchool":"河南理工","userGrade":"大一","userMajor":"计算机","questionId":"147602099490193","questionTitle":"图拼游戏来吧","questionImgs":"http://101.201.120.234:8080/wlwq/resource//images/147602099489553.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489504.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489526.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489588.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489572.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489508.jpg","questionPublishDate":1476020994000,"questionCollection":0,"questionPraise":1,"questionMoney":0,"questionData":"拼图","answerNum":3},{"userId":"147601067035339","userHeadImg":"http://101.201.120.234:8080/wlwq/resource//images/147601084252972.jpg","userRealName":"张亚楠","userSex":"男","userSchool":"河南理工","userGrade":"大一","userMajor":"计算机","questionId":"147602099490193","questionTitle":"图拼游戏来吧","questionImgs":"http://101.201.120.234:8080/wlwq/resource//images/147602099489553.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489504.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489526.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489588.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489572.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489508.jpg","questionPublishDate":1476020994000,"questionCollection":0,"questionPraise":1,"questionMoney":0,"questionData":"拼图","answerNum":3},{"userId":"147601067035339","userHeadImg":"http://101.201.120.234:8080/wlwq/resource//images/147601084252972.jpg","userRealName":"张亚楠","userSex":"男","userSchool":"河南理工","userGrade":"大一","userMajor":"计算机","questionId":"147602099490193","questionTitle":"图拼游戏来吧","questionImgs":"http://101.201.120.234:8080/wlwq/resource//images/147602099489553.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489504.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489526.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489588.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489572.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489508.jpg","questionPublishDate":1476020994000,"questionCollection":0,"questionPraise":1,"questionMoney":0,"questionData":"拼图","answerNum":3},{"userId":"147601067035339","userHeadImg":"http://101.201.120.234:8080/wlwq/resource//images/147601084252972.jpg","userRealName":"张亚楠","userSex":"男","userSchool":"河南理工","userGrade":"大一","userMajor":"计算机","questionId":"147601739646611","questionTitle":"北京好玩吗","questionImgs":"http://101.201.120.234:8080/wlwq/resource//images/147601739646090.jpg,http://101.201.120.234:8080/wlwq/resource//images/147601739646018.jpg,http://101.201.120.234:8080/wlwq/resource//images/147601739646018.jpg,http://101.201.120.234:8080/wlwq/resource//images/147601739646064.jpg,http://101.201.120.234:8080/wlwq/resource//images/147601739646021.jpg,http://101.201.120.234:8080/wlwq/resource//images/147601739646021.jpg,http://101.201.120.234:8080/wlwq/resource//images/147601739646186.jpg,http://101.201.120.234:8080/wlwq/resource//images/147601739646121.jpg","questionPublishDate":1476017396000,"questionCollection":1,"questionPraise":0,"questionMoney":0,"questionData":"风景区挺多的","answerNum":1},{"userId":"147601067035339","userHeadImg":"http://101.201.120.234:8080/wlwq/resource//images/147601084252972.jpg","userRealName":"张亚楠","userSex":"男","userSchool":"河南理工","userGrade":"大一","userMajor":"计算机","questionId":"147601069589839","questionTitle":"有问题吗","questionImgs":"","questionPublishDate":1476010695000,"questionCollection":1,"questionPraise":1,"questionMoney":0,"questionData":"有的","answerNum":1}]
     */

    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String cId;
        private String pId;
        /**
         * userId : 147601067035339
         * userHeadImg : http://101.201.120.234:8080/wlwq/resource//images/147601084252972.jpg
         * userRealName : 张亚楠
         * userSex : 男
         * userSchool : 河南理工
         * userGrade : 大一
         * userMajor : 计算机
         * questionId : 147602099490193
         * questionTitle : 图拼游戏来吧
         * questionImgs : http://101.201.120.234:8080/wlwq/resource//images/147602099489553.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489504.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489526.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489588.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489572.jpg,http://101.201.120.234:8080/wlwq/resource//images/147602099489508.jpg
         * questionPublishDate : 1476020994000
         * questionCollection : 0
         * questionPraise : 1
         * questionMoney : 0
         * questionData : 拼图
         * answerNum : 3
         */

        private List<UserAndQuestionsBean> userAndQuestions;

        public String getCId() {
            return cId;
        }

        public void setCId(String cId) {
            this.cId = cId;
        }

        public String getPId() {
            return pId;
        }

        public void setPId(String pId) {
            this.pId = pId;
        }

        public List<UserAndQuestionsBean> getUserAndQuestions() {
            return userAndQuestions;
        }

        public void setUserAndQuestions(List<UserAndQuestionsBean> userAndQuestions) {
            this.userAndQuestions = userAndQuestions;
        }

        public static class UserAndQuestionsBean implements Serializable {
            private String userId;
            private String userHeadImg;
            private String userRealName;
            private String userSex;
            private String userSchool;
            private String userGrade;
            private String userMajor;
            private String questionId;
            private String questionTitle;
            private String questionImgs;
            private long questionPublishDate;
            private int questionCollection;
            private int questionPraise;
            private int questionMoney;
            private String questionData;
            private int answerNum;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserHeadImg() {
                return userHeadImg;
            }

            public void setUserHeadImg(String userHeadImg) {
                this.userHeadImg = userHeadImg;
            }

            public String getUserRealName() {
                return userRealName;
            }

            public void setUserRealName(String userRealName) {
                this.userRealName = userRealName;
            }

            public String getUserSex() {
                return userSex;
            }

            public void setUserSex(String userSex) {
                this.userSex = userSex;
            }

            public String getUserSchool() {
                return userSchool;
            }

            public void setUserSchool(String userSchool) {
                this.userSchool = userSchool;
            }

            public String getUserGrade() {
                return userGrade;
            }

            public void setUserGrade(String userGrade) {
                this.userGrade = userGrade;
            }

            public String getUserMajor() {
                return userMajor;
            }

            public void setUserMajor(String userMajor) {
                this.userMajor = userMajor;
            }

            public String getQuestionId() {
                return questionId;
            }

            public void setQuestionId(String questionId) {
                this.questionId = questionId;
            }

            public String getQuestionTitle() {
                return questionTitle;
            }

            public void setQuestionTitle(String questionTitle) {
                this.questionTitle = questionTitle;
            }

            public String getQuestionImgs() {
                return questionImgs;
            }

            public void setQuestionImgs(String questionImgs) {
                this.questionImgs = questionImgs;
            }

            public long getQuestionPublishDate() {
                return questionPublishDate;
            }

            public void setQuestionPublishDate(long questionPublishDate) {
                this.questionPublishDate = questionPublishDate;
            }

            public int getQuestionCollection() {
                return questionCollection;
            }

            public void setQuestionCollection(int questionCollection) {
                this.questionCollection = questionCollection;
            }

            public int getQuestionPraise() {
                return questionPraise;
            }

            public void setQuestionPraise(int questionPraise) {
                this.questionPraise = questionPraise;
            }

            public int getQuestionMoney() {
                return questionMoney;
            }

            public void setQuestionMoney(int questionMoney) {
                this.questionMoney = questionMoney;
            }

            public String getQuestionData() {
                return questionData;
            }

            public void setQuestionData(String questionData) {
                this.questionData = questionData;
            }

            public int getAnswerNum() {
                return answerNum;
            }

            public void setAnswerNum(int answerNum) {
                this.answerNum = answerNum;
            }
        }
    }
}
