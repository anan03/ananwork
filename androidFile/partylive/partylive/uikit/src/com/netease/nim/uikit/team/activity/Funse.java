package com.netease.nim.uikit.team.activity;

import java.util.List;

/**
 * Created by gjj on 2016/12/6.
 */

public class Funse {

    /**
     * numPerPage : 20
     * pageNum : 1
     * result : [{"follow":"0","gender":"1","level":"4","nickName":"♞","picUrl":"http://q.qlogo.cn/qqapp/1105632511/1B6A53AC32C23C4F00724980C89023FC/100","roomId":"3196","userId":"12813","vip":"0"},{"follow":"1","gender":"1","level":"1","nickName":"哇哈哈","picUrl":"http://image.miulive.cc/avatar/12947/objectKey-13501011647-headerpic-1480683415.png","roomId":"3272","signature":"哦耶","userId":"12947","vip":"0"},{"follow":"1","gender":"1","level":"2","nickName":"111","picUrl":"http://image.miulive.cc/27076.png","roomId":"3371","signature":"666","userId":"12956","vip":"0"},{"follow":"0","gender":"1","level":"1","nickName":"阿楠","picUrl":"http://q.qlogo.cn/qqapp/1105632511/8E7927534131376927851EF77F1331F2/100","roomId":"3210","userId":"12817","vip":"0"},{"follow":"0","gender":"1","level":"2","nickName":"小兵狡猾","picUrl":"http://image.miulive.cc/avatar/12859/objectKey-18513884422-headerpic-1479698927.png","roomId":"3368","signature":"","userId":"12859","vip":"0"},{"follow":"1","gender":"0","level":"10","nickName":"哈哈哈哈","picUrl":"http://image.miulive.cc/avatar/12718/objectKey-15227949757-headerpic-1480500908.png","roomId":"3184","signature":"还^_^哈哈哈啊","userId":"12718","vip":"0"},{"follow":"0","gender":"0","level":"1","nickName":"珍珍","picUrl":"http://image.miulive.cc/avatar/12923/objectKey-15311450564-headerpic-1480384638.png","roomId":"3169","signature":"我是大头，大头大头，下雨不愁","userId":"12923","vip":"0"},{"follow":"1","gender":"1","level":"3","nickName":"啦啦","picUrl":"http://image.miulive.cc/avatar/12709/objectKey-13641168243-headerpic-1480672854.png","roomId":"3189","signature":"啦啦队","userId":"12709","vip":"0"},{"follow":"1","gender":"1","level":"13","nickName":"葬爱家族~冷少2555","picUrl":"http://image.miulive.cc/41300.png","roomId":"2988","signature":"欢迎加入葬爱家族","userId":"12914","vip":"0"},{"follow":"0","gender":"1","level":"3","nickName":"大圣","picUrl":"http://image.miulive.cc/63781.png","roomId":"2770","signature":"没什么","userId":"12916","vip":"0"},{"follow":"0","gender":"0","level":"1","nickName":"专业直播二十年","picUrl":"http://image.miulive.cc/avatar/12713/objectKey-13260465188-headerpic-1477660841.png","roomId":"3024","signature":"我不是你想像","userId":"12713","vip":"0"},{"follow":"1","gender":"1","level":"13","nickName":"狡猾小兵","picUrl":"http://image.miulive.cc/11834.png","roomId":"3273","signature":"我的大刀早已饥渴难耐","userId":"12858","vip":"0"},{"follow":"0","gender":"1","level":"6","nickName":"partyLive客服","picUrl":"http://tva2.sinaimg.cn/crop.0.0.750.750.1024/6813ac72jw8er63kg13oqj20ku0ku0u7.jpg","roomId":"990","signature":"哈哈哈","userId":"1","vip":"0"}]
     * totalItems : 13
     * totalPages : 1
     */

    private int numPerPage;
    private String pageNum;
    private int totalItems;
    private int totalPages;
    private List<FunseBean> result;

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

    public List<FunseBean> getResult() {
        return result;
    }

    public void setResult(List<FunseBean> result) {
        this.result = result;
    }
}
