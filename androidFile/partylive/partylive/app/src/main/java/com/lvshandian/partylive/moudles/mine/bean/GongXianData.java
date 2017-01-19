package com.lvshandian.partylive.moudles.mine.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gjj on 2016/11/29.
 */

public class GongXianData implements Serializable{

    /**
     * numPerPage : 20
     * pageNum : 1
     * result : [{"gender":"1","id":"12914","investment":"13364","level":"9","live":"0","nickName":"葬爱家族~冷少2","online":"0","picUrl":"http://image.miulive.cc/41300.png","points":"552736","signature":"欢迎加入葬爱家族","status":"1","vip":"0"}]
     * totalItems : 1
     * totalPages : 1
     */

    private int numPerPage;
    private String pageNum;
    private int totalItems;
    private int totalPages;
    private List<GongXianBean> result;

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

    public List<GongXianBean> getResult() {
        return result;
    }

    public void setResult(List<GongXianBean> result) {
        this.result = result;
    }

}
