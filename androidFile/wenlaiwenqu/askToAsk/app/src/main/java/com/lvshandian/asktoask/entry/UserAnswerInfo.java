package com.lvshandian.asktoask.entry;

import java.util.List;

/**
 * Created by zhang on 2016/10/4.
 * 创建回答详情界面回答列表实体类
 */
public class UserAnswerInfo {
    private int pageNum;
    private int pageSize;
    private int totalRecord;
    private int startIndex;
    private int totalPage;
    private int start;
    private int end;

    private List<DataBean> data;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String questionId;
        public String quizzerId;
        public String answererId;
        private String answerData;
        private long answerDate;
        private String isaccept;
        private String userHeadImg;
        private String userRealName;
        private String userSex;
        private String userSchool;
        private String userGrade;
        public String answerId;
        private String userMajor;
        public boolean ischeck;

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

        public String getIsaccept() {
            return isaccept;
        }

        public void setIsaccept(String isaccept) {
            this.isaccept = isaccept;
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

        @Override
        public String toString() {
            return "DataBean{" +
                    "questionId='" + questionId + '\'' +
                    ", quizzerId='" + quizzerId + '\'' +
                    ", answererId='" + answererId + '\'' +
                    ", answerData='" + answerData + '\'' +
                    ", answerDate=" + answerDate +
                    ", isaccept='" + isaccept + '\'' +
                    ", userHeadImg='" + userHeadImg + '\'' +
                    ", userRealName='" + userRealName + '\'' +
                    ", userSex='" + userSex + '\'' +
                    ", userSchool='" + userSchool + '\'' +
                    ", userGrade='" + userGrade + '\'' +
                    ", userMajor='" + userMajor + '\'' +
                    '}';
        }
    }
}
