package com.lvshandian.partylive.moudles.nearby.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sll on 2016/12/21.
 */

public class UserNearbyBean implements Serializable {

    /**
     * content : [{"id":"12919","nickName":"Carol","picUrl":"http://wx.qlogo.cn/mmopen/hIz3ylFIYSibBoibZBexhq8kNMzMwvaqiams8QeThCoRryPqiah2o3ib1XCNaCiaqOtTKDNad50uMQZRfqhmJIWf3YLQ/0","signature":null,"gender":"0","age":null,"address":"北京市","level":"1","location":"40.00388,116.40256","online":"1","distance":"0.01"},{"id":"12957","nickName":"异客","picUrl":"http://wx.qlogo.cn/mmopen/PiajxSqBRaEIoofKqk4dOCOYjRve1Q6pkBmX3fcyI5TmmvaHpRJP8TEYZOFS4uAVqjUx3ibD3xRZGEQccanuVFWA/0","signature":null,"gender":"1","age":null,"address":"北京市","level":"1","location":"40.00400,116.40249","online":"0","distance":"0.01"},{"id":"10903","nickName":"香蕉萝卜","picUrl":"http://image.miulive.cc/avatar/10903/objectKey-15120061552-headerpic-1478505064.png","signature":"帅的不得了","gender":"1","age":null,"address":"北京市","level":"1","location":"40.00421,116.40252","online":"0","distance":"0.01"},{"id":"12904","nickName":"lll has","picUrl":"http://image.miulive.cc/avatar/12904/objectKey-18801357679-headerpic-1479951558.png","signature":"lo\u2006o\u2006o","gender":"0","age":null,"address":"北京市","level":"1","location":"40.00449,116.40180","online":"0","distance":"0.01"},{"id":"12858","nickName":"小内","picUrl":"http://image.miulive.cc/57144.png","signature":"我们的狂怒你驾驭不住","gender":"1","age":null,"address":"北京市","level":"17","location":"40.00634,116.40370","online":"0","distance":"0.01"},{"id":"14082","nickName":"刘洪飞","picUrl":"http://wx.qlogo.cn/mmopen/ajNVdqHZLLBF6yicUsRH7q9DZrbJhtoqkKWnFJibm0azmBHIbzN7jjyeibogYZO8JFmoN1N84pKzGu1SEqy5Lujiaw/0","signature":null,"gender":"1","age":null,"address":"北京市","level":"1","location":"40.01102,116.34601","online":"0","distance":"6.00"},{"id":"10960","nickName":"异客MAN","picUrl":"http://image.miulive.cc/avatar/10960/objectKey-18942948090-headerpic-1472119513.png","signature":"离开地球表面","gender":"1","age":null,"address":"北京市","level":"1","location":"40.04892,116.40703","online":"0","distance":"2.00"},{"id":"13758","nickName":"情绪不由己控","picUrl":"http://q.qlogo.cn/qqapp/1105632511/943FFA09137D0E798B051EF074F47646/100","signature":null,"gender":"1","age":null,"address":"北京市","level":"1","location":"39.96107,116.43382","online":"0","distance":"4.00"},{"id":"13951","nickName":"lixin","picUrl":"http://q.qlogo.cn/qqapp/1105632511/95C1590C4E6936A73A298829E2D260C1/100","signature":null,"gender":"1","age":null,"address":"北京市","level":"1","location":"39.99750,116.33854","online":"0","distance":"7.00"},{"id":"12901","nickName":"简单的快乐","picUrl":"http://wx.qlogo.cn/mmopen/PiajxSqBRaELP7npfjNrhD5X62Fkg61VNgUN57EAJsKkkfFuYa4oS1eMoaOny3obAUvJnIX2u1qarCZWgKyibudA/0","signature":null,"gender":"1","age":null,"address":"北京市","level":"1","location":"39.97008,116.32299","online":"0","distance":"9.00"},{"id":"12781","nickName":"ajj","picUrl":"http://image.miulive.cc/avatar/12781/objectKey-18672782282-headerpic-1480038437.png","signature":"啦啦啦","gender":"1","age":null,"address":"北京市","level":"1","location":"40.07780,116.40386","online":"0","distance":"3.00"},{"id":"13532","nickName":"对方正在输入...","picUrl":"http://wx.qlogo.cn/mmopen/DZw8QNzibJ06Fwf0k3syo5Ex98FG3ibpjcAOV8XibkGA0QuDv67qmtia5WomMIQcgmJgyjomegtF1SjcK38ELDHhyeGca6XmaUA9/0","signature":null,"gender":"1","age":null,"address":"北京市","level":"1","location":"40.00192,116.51530","online":"0","distance":"12.00"},{"id":"13801","nickName":"向来痴","picUrl":"http://image.miulive.cc/avatar/13801/objectKey-F3A2D7C5626C1EF86A68E793CF3C0A10-headerpic-1482164517.png","signature":null,"gender":"1","age":null,"address":"北京市","level":"1","location":"40.06706,116.32146","online":"0","distance":"9.00"},{"id":"12805","nickName":"RED","picUrl":"http://image.miulive.cc/avatar/12805/objectKey-17778080038-headerpic-1479229025.png","signature":"风光的背后，不是沧桑就是肮脏！","gender":"1","age":null,"address":"北京市","level":"1","location":"39.99723,116.52410","online":"0","distance":"13.00"},{"id":"14000","nickName":"Michael","picUrl":"http://q.qlogo.cn/qqapp/1105632511/8A2E3BC7407F321CFDF996FBABF00808/100","signature":null,"gender":"1","age":null,"address":"北京市","level":"1","location":"39.96654,116.29011","online":"0","distance":"12.00"},{"id":"13303","nickName":"柚子","picUrl":"http://image.miulive.cc/avatar/13303/objectKey-18311226279-headerpic-1481997930.png","signature":"➕","gender":"0","age":null,"address":"北京市","level":"1","location":"39.92618,116.47445","online":"0","distance":"8.00"},{"id":"12765","nickName":"戒不掉的爱","picUrl":"http://wx.qlogo.cn/mmopen/hIz3ylFIYSicZPl3jtFKC5tN6gq0RZiccOmpmwuGKnicZyHxvwapvA7pNG6KIWNwibO5WbWg0B3cC7jJQkBOFxtYmHLWUwP3dWfC/0","signature":"呵呵哒","gender":"1","age":null,"address":"北京市","level":"1","location":"39.90376,116.42830","online":"0","distance":"5.00"},{"id":"13520","nickName":"警察好","picUrl":"http://wx.qlogo.cn/mmopen/PiajxSqBRaEKy8ay2CDN5VnBO2r3dfSLtjbyRt089LWpGe9blUIclnRk56z6E7Uialia9FY7G3iaRoZwF5ibqicHCJ2g/0","signature":null,"gender":"1","age":null,"address":"北京市","level":"1","location":"40.00897,116.25982","online":"0","distance":"15.00"},{"id":"12764","nickName":"Steven麒麟","picUrl":"http://wx.qlogo.cn/mmopen/ajNVdqHZLLBCAG2DuG3LCNUfcFiahHw1iaYkzjhK2MHTx9A77sgJmZCmBOgOT406It4AHLtxsgOI3CohOjYEc59Q/0","signature":null,"gender":"1","age":null,"address":"北京市","level":"1","location":"39.88948,116.42819","online":"0","distance":"6.00"},{"id":"13324","nickName":"小哈","picUrl":"http://wx.qlogo.cn/mmopen/hIz3ylFIYSic0TKh0ap1J3Ak52edTS6XCicgtibFicLLrnxLwH5JvZuKMdkLYco91ibiaTBuJqkqEDkPMIh3ZYy6sliaQ/0","signature":null,"gender":"1","age":null,"address":"北京市","level":"1","location":"39.90934,116.26734","online":"0","distance":"15.00"},{"id":"12713","nickName":"专业直播二十年","picUrl":"http://image.miulive.cc/avatar/12713/objectKey-13260465188-headerpic-1477660841.png","signature":"我不是你想像","gender":"0","age":null,"address":"北京市","level":"1","location":"39.88950,116.51142","online":"0","distance":"13.00"},{"id":"13941","nickName":"下半夜吻你","picUrl":"http://q.qlogo.cn/qqapp/1105632511/7BCCCA0D50A64D1C62557C56F2FC0371/100","signature":null,"gender":"1","age":null,"address":"北京市","level":"1","location":"39.84687,116.37657","online":"0","distance":"8.00"},{"id":"13788","nickName":"哈哈","picUrl":"http://q.qlogo.cn/qqapp/1105632511/07DD0AA42D5100C77A19262544D09879/100","signature":null,"gender":"1","age":null,"address":"北京市","level":"1","location":"39.92775,116.61882","online":"0","distance":"24.00"},{"id":"13592","nickName":"Missing","picUrl":"http://q.qlogo.cn/qqapp/1105632511/1F99666EF0E672AD4DF40D77A937838A/100","signature":null,"gender":"1","age":null,"address":"北京市","level":"1","location":"39.90289,116.63931","online":"0","distance":"26.00"},{"id":"13013","nickName":"蛮","picUrl":"http://wx.qlogo.cn/mmopen/nItVPqcFtfvZkVo6uEr0r5DnMMKC1u557n09ZNoJdc5kticrC3l3IuuzXNhvlF3dmGHt3kNwQyjRWZbJbnGMBa2kpUgL9oHev/0","signature":null,"gender":"1","age":null,"address":"北京市","level":"20","location":"39.88452,116.64580","online":"0","distance":"27.00"},{"id":"12927","nickName":"怿呈18614086876","picUrl":"http://wx.qlogo.cn/mmopen/DZw8QNzibJ04kOwCbe3LB44EZkZ2tsefG2N2sK7LP7JWD0B8oMLADdgqTFsTCiaRz7qwqcuxl4zmSNtxwrvxQJxw/0","signature":null,"gender":"1","age":null,"address":"北京市","level":"1","location":"39.75169,116.35251","online":"0","distance":"13.00"},{"id":"13593","nickName":"dkdd","picUrl":"http://image.miulive.cc/avatar/13593/objectKey-13161058680-headerpic-1482115289.png","signature":"cc","gender":"1","age":null,"address":"北京市","level":"1","location":"39.74930,116.13347","online":"0","distance":"32.00"},{"id":"13478","nickName":"A.","picUrl":"http://q.qlogo.cn/qqapp/1105632511/9D47F3EDACE04DBC7B3D8B560BB5415C/100","signature":null,"gender":"1","age":null,"address":"北京市","level":"1","location":"40.30261,116.62573","online":"0","distance":"28.00"},{"id":"13059","nickName":"杰西    ","picUrl":"http://wx.qlogo.cn/mmopen/EZvX3ONXjaSHkR3hW0ll0CQ41vNuUqCeaUZfVR0UibQzTsjxcp4enc86HOIoiakDdrH1LJwPMRPCH6tEiarPCzOH0w8lFBAS7t5/0","signature":null,"gender":"1","age":null,"address":"Langfang","level":"1","location":"39.86620,116.84280","online":"0","distance":"49.00"},{"id":"14012","nickName":"我就是我  。","picUrl":"http://q.qlogo.cn/qqapp/1105632511/01C86EB31C5EB5ECAF079CD22572F10A/100","signature":null,"gender":"1","age":null,"address":"廊坊市","level":"1","location":"39.98578,117.04721","online":"0","distance":"71.00"}]
     * totalElements : 275
     * last : false
     * totalPages : 10
     * sort : null
     * first : false
     * numberOfElements : 30
     * size : 30
     * number : 1
     */

    private int totalElements;
    private boolean last;
    private int totalPages;
    private Object sort;
    private boolean first;
    private int numberOfElements;
    private int size;
    private int number;
    /**
     * id : 12919
     * nickName : Carol
     * picUrl : http://wx.qlogo.cn/mmopen/hIz3ylFIYSibBoibZBexhq8kNMzMwvaqiams8QeThCoRryPqiah2o3ib1XCNaCiaqOtTKDNad50uMQZRfqhmJIWf3YLQ/0
     * signature : null
     * gender : 0
     * age : null
     * address : 北京市
     * level : 1
     * location : 40.00388,116.40256
     * online : 1
     * distance : 0.01
     */

    private List<ContentBean> content;

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public Object getSort() {
        return sort;
    }

    public void setSort(Object sort) {
        this.sort = sort;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }
}
