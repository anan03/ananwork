package com.lvshandian.asktoask.entry;

import java.util.List;

/**
 * 互动详情里面回答问题的人 on 2016/10/6.
 */
public class HuDongDetail {
    public int status;
    public String msg;
    public DataBean data;


    public static class DataBean {
        public int pageNum;
        public int pageSize;
        public int totalRecord;
        public int startIndex;
        public int totalPage;
        public int start;
        public int end;
        public List<DataBeanNei> data;



        public static class DataBeanNei {
            public String questionId;
            public String quizzerId;
            public String answererId;
            public String answerData;
            public long answerDate;
            public String isaccept;
            public Object extend1;
            public String userHeadImg;
            public String userRealName;
            public String userSex;
            public String userSchool;
            public String userGrade;
            public String userMajor;

        }
    }
}
