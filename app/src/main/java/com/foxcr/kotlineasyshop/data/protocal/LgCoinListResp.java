package com.foxcr.kotlineasyshop.data.protocal;

import java.util.List;

public class LgCoinListResp {

    /**
     * curPage : 1
     * datas : [{"coinCount":14,"date":1580894732000,"desc":"2020-02-05 17:25:32 签到 , 积分：10 + 4","id":141025,"reason":"签到","type":1,"userId":43100,"userName":"宏洋大婶呐"},{"coinCount":13,"date":1580783133000,"desc":"2020-02-04 10:25:33 签到 , 积分：10 + 3","id":140549,"reason":"签到","type":1,"userId":43100,"userName":"宏洋大婶呐"},{"coinCount":12,"date":1580659647000,"desc":"2020-02-03 00:07:27 签到 , 积分：10 + 2","id":140054,"reason":"签到","type":1,"userId":43100,"userName":"宏洋大婶呐"},{"coinCount":11,"date":1580610908000,"desc":"2020-02-02 10:35:08 签到 , 积分：10 + 1","id":139823,"reason":"签到","type":1,"userId":43100,"userName":"宏洋大婶呐"},{"coinCount":10,"date":1580551104000,"desc":"2020-02-01 17:58:24 签到 , 积分：10 + 0","id":139667,"reason":"签到","type":1,"userId":43100,"userName":"宏洋大婶呐"}]
     * offset : 0
     * over : true
     * pageCount : 1
     * size : 20
     * total : 5
     */

    public int curPage;
    public int offset;
    public boolean over;
    public int pageCount;
    public int size;
    public int total;
    public List<DatasBean> datas;


    public static class DatasBean {
        /**
         * coinCount : 14
         * date : 1580894732000
         * desc : 2020-02-05 17:25:32 签到 , 积分：10 + 4
         * id : 141025
         * reason : 签到
         * type : 1
         * userId : 43100
         * userName : 宏洋大婶呐
         */

        public int coinCount;
        public long date;
        public String desc;
        public int id;
        public String reason;
        public int type;
        public int userId;
        public String userName;

    }
}
