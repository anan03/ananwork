package com.lvshandian.partylive.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gjj on 2016/11/18.
 */

public class InviteUserBean implements Serializable {

    /**
     * pageNum : 1
     * numPerPage : 50
     * result : [{"fansNum":"1","spendGoldCoin":"0","gender":"1","level":"1","signature":"哈哈","nickName":"五月","goldCoin":"0","phoneNum":"13521999263","userName":"13521999263","points":"5","picUrl":"http://image.miulive.cc/avatar/12880/objectKey-13521999263-headerpic-1479450629.png","followNum":"0","online":"1","id":"12880","vip":"0","live":"0","receivedGoldCoin":"0"}]
     * totalItems : 1
     * totalPages : 1
     */

    private String pageNum;
    private int numPerPage;
    private int totalItems;
    private int totalPages;
    private List<ResultBean> result;

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public int getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }


}
