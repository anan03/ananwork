package com.lvshandian.asktoask.entry;

import java.util.List;

/**
 * 给我的留言的bean on 2016/10/25.
 */
public class LeaveMeBean {

    public String msg;
    public String status;
    public List<Datebean> data;
    public static class Datebean{
        public String extend1;
        public String extend2;
        public String leaveEssageId;
        public String leaverData;
        public String leavedId;
        public long leaverDate;
        public String leaverId;
        public String questionId;

    }


}

