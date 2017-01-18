package com.lvshandian.asktoask.entry;

import java.util.List;

/**
 * Created by Administrator on 2016/9/13.
 */
public class HuDongImg {


    /**
     * status : 200
     * msg : OK
     * data : {"carouselPictures":[{"carouselId":"1","carouselUrl":"http://192.168.0.123:8080/wlwq","status":"1"}],"questionTypes":[{"id":"2","questionTypeName":"情感","extend1":"2"},{"id":"1","questionTypeName":"学术","extend1":"1"},{"id":"3","questionTypeName":"艺考","extend1":"3"},{"id":"4","questionTypeName":"干货","extend1":"4"}]}
     */

    private int status;
    private String msg;
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

        private List<CarouselPicturesBean> carouselPictures;

        private List<QuestionTypesBean> questionTypes;

        public List<CarouselPicturesBean> getCarouselPictures() {
            return carouselPictures;
        }

        public void setCarouselPictures(List<CarouselPicturesBean> carouselPictures) {
            this.carouselPictures = carouselPictures;
        }

        public List<QuestionTypesBean> getQuestionTypes() {
            return questionTypes;
        }

        public void setQuestionTypes(List<QuestionTypesBean> questionTypes) {
            this.questionTypes = questionTypes;
        }

        public static class CarouselPicturesBean {
            private String carouselId;
            private String carouselUrl;
            private String status;

            public String getCarouselId() {
                return carouselId;
            }

            public void setCarouselId(String carouselId) {
                this.carouselId = carouselId;
            }

            public String getCarouselUrl() {
                return carouselUrl;
            }

            public void setCarouselUrl(String carouselUrl) {
                this.carouselUrl = carouselUrl;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }

        public static class QuestionTypesBean {
            private String id;
            private String questionTypeName;
            private String extend1;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getQuestionTypeName() {
                return questionTypeName;
            }

            public void setQuestionTypeName(String questionTypeName) {
                this.questionTypeName = questionTypeName;
            }

            public String getExtend1() {
                return extend1;
            }

            public void setExtend1(String extend1) {
                this.extend1 = extend1;
            }
        }
    }
}
