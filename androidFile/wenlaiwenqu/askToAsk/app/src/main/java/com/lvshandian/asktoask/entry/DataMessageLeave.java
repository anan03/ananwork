package com.lvshandian.asktoask.entry;

import java.io.Serializable;
import java.util.List;

/**
 * 消息中的留言bean列表
 * on 2016/9/22.
 */
public class DataMessageLeave implements Serializable {



    private int status;
    private String msg;


    private DataBean2 data;

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

    public DataBean2 getData() {
        return data;
    }

    public void setData(DataBean2 data) {
        this.data = data;
    }

    public static class DataBean2 {
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

        public static class DataBean implements Serializable{
            private String userId;
            private String userName;
            private String userPassword;
            private String userRegistDate;
            private String userLoginDate;
            private String userUpdateDate;
            private String userDeleteDate;
            private String status;
            private String salt;
            private String isapprove;
            private String userHeadImg;
            private String userSign;
            private String userSex;
            private String userRealName;
            private String userSchool;
            private String userGrade;
            private String userMajor;
            private String area;
            private String userNickName;
            private String userQqName;
            private String userQqPassword;
            private String userLifePhoto;
            private String userStudentCard;
            private String userIdentityCardFront;
            private String userIdentityCardVerso;
            private String extend1;
            private String extend2;
            public String leaverData;



            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserPassword() {
                return userPassword;
            }

            public void setUserPassword(String userPassword) {
                this.userPassword = userPassword;
            }

            public String getUserRegistDate() {
                return userRegistDate;
            }

            public void setUserRegistDate(String userRegistDate) {
                this.userRegistDate = userRegistDate;
            }

            public String getUserLoginDate() {
                return userLoginDate;
            }

            public void setUserLoginDate(String userLoginDate) {
                this.userLoginDate = userLoginDate;
            }

            public String getUserUpdateDate() {
                return userUpdateDate;
            }

            public void setUserUpdateDate(String userUpdateDate) {
                this.userUpdateDate = userUpdateDate;
            }

            public String getUserDeleteDate() {
                return userDeleteDate;
            }

            public void setUserDeleteDate(String userDeleteDate) {
                this.userDeleteDate = userDeleteDate;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getSalt() {
                return salt;
            }

            public void setSalt(String salt) {
                this.salt = salt;
            }

            public String getIsapprove() {
                return isapprove;
            }

            public void setIsapprove(String isapprove) {
                this.isapprove = isapprove;
            }

            public String getUserHeadImg() {
                return userHeadImg;
            }

            public void setUserHeadImg(String userHeadImg) {
                this.userHeadImg = userHeadImg;
            }

            public String getUserSign() {
                return userSign;
            }

            public void setUserSign(String userSign) {
                this.userSign = userSign;
            }

            public String getUserSex() {
                return userSex;
            }

            public void setUserSex(String userSex) {
                this.userSex = userSex;
            }

            public String getUserRealName() {
                return userRealName;
            }

            public void setUserRealName(String userRealName) {
                this.userRealName = userRealName;
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

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getUserNickName() {
                return userNickName;
            }

            public void setUserNickName(String userNickName) {
                this.userNickName = userNickName;
            }

            public String getUserQqName() {
                return userQqName;
            }

            public void setUserQqName(String userQqName) {
                this.userQqName = userQqName;
            }

            public String getUserQqPassword() {
                return userQqPassword;
            }

            public void setUserQqPassword(String userQqPassword) {
                this.userQqPassword = userQqPassword;
            }

            public String getUserLifePhoto() {
                return userLifePhoto;
            }

            public void setUserLifePhoto(String userLifePhoto) {
                this.userLifePhoto = userLifePhoto;
            }

            public String getUserStudentCard() {
                return userStudentCard;
            }

            public void setUserStudentCard(String userStudentCard) {
                this.userStudentCard = userStudentCard;
            }

            public String getUserIdentityCardFront() {
                return userIdentityCardFront;
            }

            public void setUserIdentityCardFront(String userIdentityCardFront) {
                this.userIdentityCardFront = userIdentityCardFront;
            }

            public String getUserIdentityCardVerso() {
                return userIdentityCardVerso;
            }

            public void setUserIdentityCardVerso(String userIdentityCardVerso) {
                this.userIdentityCardVerso = userIdentityCardVerso;
            }

            public String getExtend1() {
                return extend1;
            }

            public void setExtend1(String extend1) {
                this.extend1 = extend1;
            }

            public String getExtend2() {
                return extend2;
            }

            public void setExtend2(String extend2) {
                this.extend2 = extend2;
            }
        }
    }
}
