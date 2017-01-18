package com.lvshandian.asktoask.entry;

import java.util.List;

/**
 * Created by 王伟 on 2016/9/19.
 *答咖的列表和了轮播图
 *
 * */
public class Answer {

    public int status;
    public String msg;
    public DataBean data;
    public static class DataBean {

        public List<ClientUsersBean> clientUsers;

        public List<CarouselPicturesBean> carouselPictures;

        public static class ClientUsersBean {
            public String userId;
            public String isapprove;
            public String userHeadImg;
            public String userRealName;
            public int userPraise;
            public int userCollect;
            public String extend2;
            public boolean isfocus;
            public String extend1;
        }

        public static class CarouselPicturesBean {
            public String carouselId;
            public String carouselUrl;
            public String status;


        }
    }
}
