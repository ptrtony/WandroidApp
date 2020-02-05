package com.foxcr.kotlineasyshop.data.protocal;

import java.util.List;

public class CoinRankListResp {

    /**
     * curPage : 1
     * datas : [{"coinCount":8141,"level":82,"rank":1,"userId":20382,"username":"g**eii"},{"coinCount":7312,"level":74,"rank":2,"userId":3559,"username":"A**ilEyon"},{"coinCount":6654,"level":67,"rank":3,"userId":27535,"username":"1**08491840"},{"coinCount":5775,"level":58,"rank":4,"userId":29303,"username":"深**士"},{"coinCount":5602,"level":57,"rank":5,"userId":2,"username":"x**oyang"},{"coinCount":5181,"level":52,"rank":6,"userId":1260,"username":"于**家的吴蜀黍"},{"coinCount":5006,"level":51,"rank":7,"userId":28694,"username":"c**ng0218"},{"coinCount":4728,"level":48,"rank":8,"userId":9621,"username":"S**24n"},{"coinCount":4583,"level":46,"rank":9,"userId":3753,"username":"S**phenCurry"},{"coinCount":4563,"level":46,"rank":10,"userId":863,"username":"m**qitian"},{"coinCount":4532,"level":46,"rank":11,"userId":1534,"username":"j**gbin"},{"coinCount":4441,"level":45,"rank":12,"userId":7710,"username":"i**Cola7"},{"coinCount":4398,"level":44,"rank":13,"userId":25793,"username":"F**_2014"},{"coinCount":4381,"level":44,"rank":14,"userId":2068,"username":"i**Cola"},{"coinCount":4335,"level":44,"rank":15,"userId":28607,"username":"S**Brother"},{"coinCount":4312,"level":44,"rank":16,"userId":7809,"username":"1**23822235"},{"coinCount":4305,"level":44,"rank":17,"userId":7891,"username":"h**zkp"},{"coinCount":4297,"level":43,"rank":18,"userId":14829,"username":"l**changwen"},{"coinCount":4286,"level":43,"rank":19,"userId":833,"username":"w**lwaywang6"},{"coinCount":4248,"level":43,"rank":20,"userId":27,"username":"y**ochoo"},{"coinCount":4248,"level":43,"rank":21,"userId":12351,"username":"w**igeny"},{"coinCount":4170,"level":42,"rank":22,"userId":26707,"username":"p**xc.com"},{"coinCount":4156,"level":42,"rank":23,"userId":7590,"username":"陈**啦啦啦"},{"coinCount":4146,"level":42,"rank":24,"userId":29076,"username":"f**ham"},{"coinCount":4140,"level":42,"rank":25,"userId":12467,"username":"c**yie"},{"coinCount":4123,"level":42,"rank":26,"userId":25419,"username":"蔡**打篮球"},{"coinCount":4113,"level":42,"rank":27,"userId":1871,"username":"l**shifu"},{"coinCount":4101,"level":42,"rank":28,"userId":12331,"username":"R**kieJay"},{"coinCount":4101,"level":42,"rank":29,"userId":2160,"username":"R**iner"},{"coinCount":4089,"level":41,"rank":30,"userId":7541,"username":"l**64301766"}]
     * offset : 0
     * over : false
     * pageCount : 578
     * size : 30
     * total : 17313
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
         * coinCount : 8141
         * level : 82
         * rank : 1
         * userId : 20382
         * username : g**eii
         */

        public int coinCount;
        public int level;
        public int rank;
        public int userId;
        public String username;


    }
}
