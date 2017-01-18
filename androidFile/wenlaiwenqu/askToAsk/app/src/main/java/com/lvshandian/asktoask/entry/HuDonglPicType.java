package com.lvshandian.asktoask.entry;

import java.util.List;

/**
 * 答咖图片类型 on 2016/9/30.
 */
public class HuDonglPicType {
    public int status;
    public String msg;
    public DataBean data;
    public static class DataBean {
        public String cId;
        public String pId;
        public PageBeanBean pageBean;
        public static class PageBeanBean {
            public int pageNum;
            public int pageSize;
            public int totalRecord;
            public int startIndex;
            public int totalPage;
            public int start;
            public int end;
            public List<DataBeanInner> data;
            public static class DataBeanInner {
                public String questionId;
                public String questionTitle;
                public String questionData;
                public String questionImgs;
                public String questionType;
                public double questionMoney;
                public String isanonymity;
                public String userId;
                public long questionPublishDate;
                public Object questionDeleteDate;
                public Object status;
                public int questionCollection;
                public int questionPraise;
                public Object voucherMoney;
                public Object finalMoney;
                public int answerNum;
                public String extend1;
                public Object extend2;
                public String userHeadImg;
                public String userRealName;
                public String isapprove;
                public String userSex;
                public String userSchool;
                public String userGrade;
                public String userMajor;
                public boolean iscollect;
                public boolean ispraise;

                @Override
                public String toString() {
                    return "DataBeanInner{" +
                            "questionId='" + questionId + '\'' +
                            ", questionTitle='" + questionTitle + '\'' +
                            ", questionData='" + questionData + '\'' +
                            ", questionImgs='" + questionImgs + '\'' +
                            ", questionType='" + questionType + '\'' +
                            ", questionMoney=" + questionMoney +
                            ", isanonymity='" + isanonymity + '\'' +
                            ", userId='" + userId + '\'' +
                            ", questionPublishDate=" + questionPublishDate +
                            ", questionDeleteDate=" + questionDeleteDate +
                            ", status=" + status +
                            ", questionCollection=" + questionCollection +
                            ", questionPraise=" + questionPraise +
                            ", voucherMoney=" + voucherMoney +
                            ", finalMoney=" + finalMoney +
                            ", answerNum=" + answerNum +
                            ", extend1='" + extend1 + '\'' +
                            ", extend2=" + extend2 +
                            ", userHeadImg='" + userHeadImg + '\'' +
                            ", userRealName='" + userRealName + '\'' +
                            ", isapprove='" + isapprove + '\'' +
                            ", userSex='" + userSex + '\'' +
                            ", userSchool='" + userSchool + '\'' +
                            ", userGrade='" + userGrade + '\'' +
                            ", userMajor='" + userMajor + '\'' +
                            ", iscollect=" + iscollect +
                            ", ispraise=" + ispraise +
                            '}';
                }
            }
        }
    }
}
