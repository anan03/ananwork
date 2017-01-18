package com.lvshandian.asktoask.entry;

import java.io.Serializable;
import java.util.List;

/**
 *  我的模块中的站内信 on 2016/10/26.
 */
public class InstationMessageBean {

   

    public int status;
    public String msg;
  

    public List<DataBean> data;

  

    public static class DataBean implements Serializable{
        public String id;
        public String ageStart;
        public String ageEnd;
        public String userSex;
        public String area;
        public String message;
        public String isdel;
        public String status;
        public long addDate;
        public String extend1;
        public String extend2;


    }
}
