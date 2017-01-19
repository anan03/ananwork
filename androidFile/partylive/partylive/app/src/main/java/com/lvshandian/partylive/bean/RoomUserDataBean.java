package com.lvshandian.partylive.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sll on 2016/12/2.
 */

/**
 * 直播是最上边观众头像列表类
 *
 * @author sll
 * @time 2016/12/2 16:56
 */
public class RoomUserDataBean implements Serializable {

    /**
     * pageNum : 1
     * numPerPage : 50
     * result : [{"picUrl":"http://wx.qlogo.cn/mmopen/C9QznKczMm1Cs7BzBr9YMDyPzPVo0JwRsvibPdg0OWgfiaJwEBzQSYdox70vHfibnyxE3wJI0e2XStjPyejRZricqr62KPPTRNya/0","gender":"1","level":"1","signature":"","nickName":"柯明","vip":"0","userId":"10342","roomId":"21"},{"picUrl":"http://image.scale.inke.com/imageproxy2/dimgm/scaleImage?url=http://img.meelive.cn/NDMyNjQxNDY2MDg3MzY5.jpg&w=120&s=80&h=120&c=0&o=0","gender":"0","level":"1","signature":"像风一样自由，像酒一样孤单","nickName":"澄宝宝","vip":"0","userId":"10175","roomId":"21"},{"picUrl":"http://image.scale.inke.com/imageproxy2/dimgm/scaleImage?url=http://img.meelive.cn/Mjg3NDcxNDY2MDExMjg0.jpg&w=120&s=80&h=120&c=0&o=0","gender":"0","level":"1","signature":"","nickName":"     浪啊","vip":"0","userId":"10300","roomId":"21"},{"picUrl":"http://image.scale.inke.com/imageproxy2/dimgm/scaleImage?url=http://img.meelive.cn/NzU3OTQxNDY1MjkzMjIw.jpg&w=120&s=80&h=120&c=0&o=0","gender":"1","level":"1","signature":"各位加关注哦","nickName":"狂奔的蜗牛","vip":"0","userId":"10253","roomId":"21"},{"picUrl":"http://image.scale.inke.com/imageproxy2/dimgm/scaleImage?url=http://img.meelive.cn/ODg0ODAxNDY0OTc3OTE2.jpg&w=120&s=80&h=120&c=0&o=0","gender":"0","level":"1","signature":".榜8➕vx","nickName":"烦！","vip":"0","userId":"10174","roomId":"21"},{"picUrl":"http://q.qlogo.cn/qqapp/1104658198/46C1F536A112B5413306E1D9E59423E0/100","gender":"1","level":"1","signature":"","nickName":"小二逼","vip":"0","userId":"10015","roomId":"21"},{"picUrl":"http://wx.qlogo.cn/mmopen/oMErA8iccgSod7j6JI14CRaCDIphOGqluOs2XbGrTiacNTcxNCqvGvjmeNDYNxCSLuQRoQk0Gic9PqwcqbyvokwohqMPFWM9Xmf/0","gender":"1","level":"1","signature":"","nickName":"布川内酷","vip":"0","userId":"10367","roomId":"21"},{"picUrl":"http://image.scale.inke.com/imageproxy2/dimgm/scaleImage?url=http://img.meelive.cn/MTM2NjExNDY1MzIwMDQx.jpg&w=120&s=80&h=120&c=0&o=0","gender":"0","level":"1","signature":"","nickName":"安娜、","vip":"0","userId":"10111","roomId":"21"},{"picUrl":"http://image.scale.inke.com/imageproxy2/dimgm/scaleImage?url=http://img.meelive.cn/MTM1NTIxNDY1NTU1NTkz.jpg&w=120&s=80&h=120&c=0&o=0","gender":"0","level":"1","signature":"","nickName":"爱笑的眼泪","vip":"0","userId":"10101","roomId":"21"},{"picUrl":"http://image.scale.inke.com/imageproxy2/dimgm/scaleImage?url=http://img.meelive.cn/ODA3ODAxNDYyNjY5Nzk2.jpg&w=120&s=80&h=120&c=0&o=0","gender":"1","level":"1","signature":"","nickName":"Sea","vip":"0","userId":"10356","roomId":"21"},{"picUrl":"http://image.scale.inke.com/imageproxy2/dimgm/scaleImage?url=http://img.meelive.cn/NDkzNjYxNDY1NDA4MzEy.jpg&w=120&s=80&h=120&c=0&o=0","gender":"1","level":"1","signature":"","nickName":"昆明纽森名车汇➡️汪疯","vip":"0","userId":"10100","roomId":"21"},{"picUrl":"http://image.scale.inke.com/imageproxy2/dimgm/scaleImage?url=http://img.meelive.cn/ODQ5MTMxNDY2MTIwNzk2.jpg&w=120&s=80&h=120&c=0&o=0","gender":"1","level":"1","signature":"我就是我，不一样的烟火","nickName":"$","vip":"0","userId":"10355","roomId":"21"},{"picUrl":"http://image.scale.inke.com/imageproxy2/dimgm/scaleImage?url=http://img.meelive.cn/MTc3MDcxNDY1NjE1OTIy.jpg&w=120&s=80&h=120&c=0&o=0","gender":"1","level":"1","signature":"","nickName":"富家 宝贝 小跟班","vip":"0","userId":"10050","roomId":"21"},{"picUrl":"http://image.scale.inke.com/imageproxy2/dimgm/scaleImage?url=http://img.meelive.cn/Mzg5ODgxNDYxNzUzMTM1.jpg&w=120&s=80&h=120&c=0&o=0","gender":"1","level":"1","signature":"想减肥加583843190","nickName":"龍宇","vip":"0","userId":"10196","roomId":"21"},{"picUrl":"http://image.scale.inke.com/imageproxy2/dimgm/scaleImage?url=http://img.meelive.cn/MTAxNDkxNDYyODA4NDE2.jpg&w=120&s=80&h=120&c=0&o=0","gender":"1","level":"1","signature":"封","nickName":"等风来","vip":"0","userId":"10344","roomId":"21"},{"picUrl":"http://image.scale.inke.com/imageproxy2/dimgm/scaleImage?url=http://img.meelive.cn/OTA2MTkxNDYzMTI5ODg1.jpg&w=120&s=80&h=120&c=0&o=0","gender":"1","level":"1","signature":"","nickName":"文俊","vip":"0","userId":"10161","roomId":"21"},{"picUrl":"http://image.scale.inke.com/imageproxy2/dimgm/scaleImage?url=http://img.meelive.cn/MTU4ODUxNDU5NzM2MDY1.jpg&w=120&s=80&h=120&c=0&o=0","gender":"1","level":"1","signature":"我想要的只是正常的生活","nickName":"人家白着呢","vip":"0","userId":"10022","roomId":"21"}]
     * totalItems : 17
     * totalPages : 1
     */

    private String pageNum;
    private int numPerPage;
    private int totalItems;
    private int totalPages;
    /**
     * picUrl : http://wx.qlogo.cn/mmopen/C9QznKczMm1Cs7BzBr9YMDyPzPVo0JwRsvibPdg0OWgfiaJwEBzQSYdox70vHfibnyxE3wJI0e2XStjPyejRZricqr62KPPTRNya/0
     * gender : 1
     * level : 1
     * signature :
     * nickName : 柯明
     * vip : 0
     * userId : 10342
     * roomId : 21
     */

    private List<RoomUserBean> result;

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

    public List<RoomUserBean> getResult() {
        return result;
    }

    public void setResult(List<RoomUserBean> result) {
        this.result = result;
    }

}
