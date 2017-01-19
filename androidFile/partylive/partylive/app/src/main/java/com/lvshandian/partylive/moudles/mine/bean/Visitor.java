package com.lvshandian.partylive.moudles.mine.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gjj on 2016/11/23.
 * 最近访客全部
 */

public class Visitor implements Serializable{

    /**
     * numPerPage : 20
     * pageNum : 1
     * result : [{"gender":"1","level":"10","nickName":"lvtu","picUrl":"http://image.miulive.cc/avatar/12718/objectKey-15227949757-headerpic-1478952140.png","roomId":"2292","signature":"其实是","userId":"12718","vip":"0"},{"gender":"1","level":"7","nickName":"monkey","picUrl":"http://image.miulive.cc/avatar/12724/objectKey-15071474099-headerpic-1478143256.png","roomId":"2190","signature":"分享是真爱，你是我的菜！","userId":"12724","vip":"1"}]
     * totalItems : 2
     * totalPages : 1
     */

    private int numPerPage;
    private String pageNum;
    private int totalItems;
    private int totalPages;
    private List<RecentVisitorBean> result;

    public int getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<RecentVisitorBean> getResult() {
        return result;
    }

    public void setResult(List<RecentVisitorBean> result) {
        this.result = result;
    }
}
