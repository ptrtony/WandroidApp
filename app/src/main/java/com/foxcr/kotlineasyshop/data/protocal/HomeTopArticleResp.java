package com.foxcr.kotlineasyshop.data.protocal;

import java.util.List;

public class HomeTopArticleResp {

    /**
     * apkLink :
     * audit : 1
     * author : xiaoyang
     * chapterId : 440
     * chapterName : 官方
     * collect : false
     * courseId : 13
     * desc : <p>马上过年啦，每日一问也要停止更新一段时间了，很荣幸我们提出了大概 70 个问题，每个问题都非常值得大家去深入思考，也可以拨正了一些表达不够严谨的观点。</p>
     <p>过节期间会先专注与问答的编辑功能，文章会不定期更新，因为我有更新文章插件，所以看到好文，点一下就更新了，操作还是比较方便的。</p>
     <p>这里祝大家新年快乐，我们来年见。</p>
     <p>当然如果大家有收获，也欢迎大家<a href="https://www.wanandroid.com/blog/show/2030">赞助本站</a>，毕竟饭还是要吃的，各位么么哒，来年继续加油。</p>
     * envelopePic :
     * fresh : false
     * id : 11552
     * link : https://www.wanandroid.com/wenda/show/11552
     * niceDate : 2020-01-20 01:05
     * niceShareDate : 2020-01-18 12:59
     * origin :
     * prefix :
     * projectLink :
     * publishTime : 1579453524000
     * selfVisible : 0
     * shareDate : 1579323552000
     * shareUser :
     * superChapterId : 440
     * superChapterName : 问答
     * tags : [{"name":"问答","url":"/article/list/0?cid=440"}]
     * title : 每日一问  2019年终致辞
     * type : 1
     * userId : 2
     * visible : 1
     * zan : 0
     */

    public String apkLink;
    public int audit;
    public String author;
    public int chapterId;
    public String chapterName;
    public boolean collect;
    public int courseId;
    public String desc;
    public String envelopePic;
    public boolean fresh;
    public int id;
    public String link;
    public String niceDate;
    public String niceShareDate;
    public String origin;
    public String prefix;
    public String projectLink;
    public long publishTime;
    public int selfVisible;
    public long shareDate;
    public String shareUser;
    public int superChapterId;
    public String superChapterName;
    public String title;
    public int type;
    public int userId;
    public int visible;
    public int zan;
    public List<TagsBean> tags;


    public static class TagsBean {
        /**
         * name : 问答
         * url : /article/list/0?cid=440
         */

        public String name;
        public String url;

    }
}
