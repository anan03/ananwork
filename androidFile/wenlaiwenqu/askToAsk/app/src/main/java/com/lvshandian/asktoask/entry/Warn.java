package com.lvshandian.asktoask.entry;

import java.util.List;

/**
 * 举报内容 on 2016/9/27.
 */
public class Warn {
    

    public int status;
    public String msg;

    public List<DataBean> data;


    public static class DataBean {
        public DataBean(Boolean isselect, String reportTypeId, String reportData, String insertDate, String insertDated, String status, String extend1, String extend2) {
            this.isselect = isselect;
            this.reportTypeId = reportTypeId;
            this.reportData = reportData;
            this.insertDate = insertDate;
            this.insertDated = insertDated;
            this.status = status;
            this.extend1 = extend1;
            this.extend2 = extend2;
        }

        public Boolean isselect=false;
        public String reportTypeId;
        public String reportData;
        public String insertDate;
        public String insertDated;
        public String status;
        public String extend1;
        public String extend2;


    }
}
