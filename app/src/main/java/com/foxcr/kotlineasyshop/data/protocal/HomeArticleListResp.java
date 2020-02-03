package com.foxcr.kotlineasyshop.data.protocal;

import java.util.List;

public class HomeArticleListResp {

    /**
     * curPage : 1
     * datas : [{"apkLink":"","audit":1,"author":"","chapterId":502,"chapterName":"自助","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":11622,"link":"https://blog.csdn.net/hfy8971613/article/details/103967595","niceDate":"2020-01-21 15:36","niceShareDate":"2020-01-21 15:36","origin":"","prefix":"","projectLink":"","publishTime":1579592190000,"selfVisible":0,"shareDate":1579592190000,"shareUser":"feiyang","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"Android中的线程及线程池全面理解~","type":0,"userId":31360,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":502,"chapterName":"自助","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":11602,"link":"https://www.jianshu.com/p/2112e452a894","niceDate":"2020-01-20 09:58","niceShareDate":"2020-01-20 09:58","origin":"","prefix":"","projectLink":"","publishTime":1579485497000,"selfVisible":0,"shareDate":1579485497000,"shareUser":"吊儿郎当","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"对着那Glide最新版本就是一顿暴讲 2（数据加载篇）","type":0,"userId":2554,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":229,"chapterName":"AOP","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":11597,"link":"https://juejin.im/post/5e2478346fb9a02fbc4ad2cc","niceDate":"2020-01-20 01:20","niceShareDate":"2020-01-20 01:10","origin":"","prefix":"","projectLink":"","publishTime":1579454420000,"selfVisible":0,"shareDate":1579453802000,"shareUser":"鸿洋","superChapterId":227,"superChapterName":"注解 & 反射 & AOP","tags":[],"title":"Gradle Transform + ASM 探索","type":0,"userId":2,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":97,"chapterName":"音视频","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":11598,"link":"https://juejin.im/post/5e1d86706fb9a02fed0de5ec","niceDate":"2020-01-20 01:20","niceShareDate":"2020-01-20 01:10","origin":"","prefix":"","projectLink":"","publishTime":1579454408000,"selfVisible":0,"shareDate":1579453817000,"shareUser":"鸿洋","superChapterId":95,"superChapterName":"多媒体技术","tags":[],"title":"Android音视频开发:音频非压缩编码和压缩编码","type":0,"userId":2,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":444,"chapterName":"androidx","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":11561,"link":"https://juejin.im/post/5e232d01e51d455801624c06","niceDate":"2020-01-20 01:06","niceShareDate":"2020-01-19 13:15","origin":"","prefix":"","projectLink":"","publishTime":1579453577000,"selfVisible":0,"shareDate":1579410911000,"shareUser":"Lgxing","superChapterId":183,"superChapterName":"5.+高新技术","tags":[],"title":"Androidx 下 Fragment 懒加载的新实现","type":0,"userId":29390,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"xiaoyang","chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>经常我们在开发过程中，遇到 UI 滚动失效，宽高获取不到等，有经验的程序员可能会指导你说：<\/p>\r\n<p>你 post 一个 Runnable，在\u201c下一帧\u201d就可以拿到了。<\/p>\r\n<p>很多我们我们一试，果然可以，崇拜之情涌上心头。<\/p>\r\n<p>那么今天我们要问<\/p>\r\n<ol>\r\n<li>handler. postRunnable 和 \u201c下一帧\u201d的说法有关系吗？<\/li>\r\n<li>如果你在一个方法中，for 循环 handler.post 多个 Runnable，如果有问题1 中帧的说法，那么这些 Runnable 执行应该有个固定的间隔，你可以实测一下，有这样一个间隔吗？ 如果没有，为什么？<\/li>\r\n<\/ol>\r\n<p>年前倒计时了~<\/p>","envelopePic":"","fresh":false,"id":11506,"link":"https://www.wanandroid.com/wenda/show/11506","niceDate":"2020-01-20 01:05","niceShareDate":"2020-01-14 23:37","origin":"","prefix":"","projectLink":"","publishTime":1579453533000,"selfVisible":0,"shareDate":1579016234000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"问答","url":"/article/list/0?cid=440"}],"title":"每日一问  &ldquo;别慌，你 post 一个 Runnable ，在下一帧就可以拿到了。&rdquo;这种说法对吗？","type":0,"userId":2,"visible":1,"zan":16},{"apkLink":"","audit":1,"author":"xiaoyang","chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>很多时候，我们核心页面打开速度会成为一项性能指标，这时候你可能会听到老鸟告诉你一个比较屌的方案：<\/p>\r\n<p>View提前在异步线程预加载。<\/p>\r\n<p>具体就是可能在使用之前，在某个时机，在某个子线程中，提前inflate某个布局文件，大家都知道inflate文件需要一个LayoutInflater，中间会使用到一个参数是Context，由于你需要提前预加载，那么被迫就得使用Application作为这个Context，而且在大多时候，确实可行。<\/p>\r\n<p>那么问题来了：<\/p>\r\n<p>使用applicationContext初始化View会有什么潜在的风险吗？或者哪些View的能力会失效？<\/p>","envelopePic":"","fresh":false,"id":11521,"link":"https://www.wanandroid.com/wenda/show/11521","niceDate":"2020-01-20 01:05","niceShareDate":"2020-01-15 16:21","origin":"","prefix":"","projectLink":"","publishTime":1579453529000,"selfVisible":0,"shareDate":1579076500000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"问答","url":"/article/list/0?cid=440"}],"title":"每日一问  &ldquo;你这个布局可以在进入这个页面之前预加载，这样页面渲染就快了&rdquo;可能有什么潜在的问题吗？","type":0,"userId":2,"visible":1,"zan":4},{"apkLink":"","audit":1,"author":"互联网侦察","chapterId":421,"chapterName":"互联网侦察","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":11581,"link":"https://mp.weixin.qq.com/s/29yLBfeApKqE5XsHlEgdyg","niceDate":"2020-01-19 00:00","niceShareDate":"2020-01-19 20:56","origin":"","prefix":"","projectLink":"","publishTime":1579363200000,"selfVisible":0,"shareDate":1579438585000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/421/1"}],"title":"三分钟基础：CPU 到底是怎么认识代码的？","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"鸿洋","chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":11572,"link":"https://mp.weixin.qq.com/s/fi7oM2WfEsWHTVDVbRQULQ","niceDate":"2020-01-18 00:00","niceShareDate":"2020-01-19 20:52","origin":"","prefix":"","projectLink":"","publishTime":1579276800000,"selfVisible":0,"shareDate":1579438324000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"一个移动端开发者，对未来的思考","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":502,"chapterName":"自助","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":11545,"link":"https://juejin.im/post/5e2135b251882521245bb0e8","niceDate":"2020-01-17 14:00","niceShareDate":"2020-01-17 12:34","origin":"","prefix":"","projectLink":"","publishTime":1579240835000,"selfVisible":0,"shareDate":1579235694000,"shareUser":"rain9155","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"这是一份关于HTTP协议的学习总结","type":0,"userId":12884,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":502,"chapterName":"自助","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":11541,"link":"https://www.jianshu.com/p/48d9e4d5d75d","niceDate":"2020-01-17 10:16","niceShareDate":"2020-01-17 10:16","origin":"","prefix":"","projectLink":"","publishTime":1579227404000,"selfVisible":0,"shareDate":1579227404000,"shareUser":"吊儿郎当","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"对着那Glide最新版本就是一顿暴讲","type":0,"userId":2554,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":502,"chapterName":"自助","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":11540,"link":"https://juejin.im/post/5e2109e25188254c257c40c6","niceDate":"2020-01-17 09:53","niceShareDate":"2020-01-17 09:53","origin":"","prefix":"","projectLink":"","publishTime":1579225991000,"selfVisible":0,"shareDate":1579225991000,"shareUser":"JsonChao","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"Android主流三方库源码分析（三、深入理解Glide源码）","type":0,"userId":611,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"鸿洋","chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":11571,"link":"https://mp.weixin.qq.com/s/KibDQpDbQ32yTykEIzKpPQ","niceDate":"2020-01-17 00:00","niceShareDate":"2020-01-19 20:51","origin":"","prefix":"","projectLink":"","publishTime":1579190400000,"selfVisible":0,"shareDate":1579438307000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"Activity 的 36 个问题，你还记得几个？","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"郭霖","chapterId":409,"chapterName":"郭霖","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":11576,"link":"https://mp.weixin.qq.com/s/FtsszSrpmLx00OSrgkT9BQ","niceDate":"2020-01-17 00:00","niceShareDate":"2020-01-19 20:53","origin":"","prefix":"","projectLink":"","publishTime":1579190400000,"selfVisible":0,"shareDate":1579438400000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/409/1"}],"title":"Kotlin vs Flutter，我到底应该怎么选？","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"互联网侦察","chapterId":421,"chapterName":"互联网侦察","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":11580,"link":"https://mp.weixin.qq.com/s/XbpDp1mMPXGYIBQb5uEFfg","niceDate":"2020-01-17 00:00","niceShareDate":"2020-01-19 20:56","origin":"","prefix":"","projectLink":"","publishTime":1579190400000,"selfVisible":0,"shareDate":1579438565000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/421/1"}],"title":"什么是广播路由算法？如何解决广播风暴？","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"承香墨影","chapterId":411,"chapterName":"承香墨影","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":11583,"link":"https://mp.weixin.qq.com/s/b29tz2xvNJKG35FrHKQ8QA","niceDate":"2020-01-17 00:00","niceShareDate":"2020-01-19 20:58","origin":"","prefix":"","projectLink":"","publishTime":1579190400000,"selfVisible":0,"shareDate":1579438699000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/411/1"}],"title":"Android 黑科技保活实现原理揭秘","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":304,"chapterName":"基础源码","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":11528,"link":"https://juejin.im/entry/5e1d72a4f265da3df245dc1d","niceDate":"2020-01-16 21:37","niceShareDate":"2020-01-16 08:51","origin":"","prefix":"","projectLink":"","publishTime":1579181855000,"selfVisible":0,"shareDate":1579135912000,"shareUser":"goweii","superChapterId":245,"superChapterName":"Java深入","tags":[],"title":"Android进阶之路&mdash;&mdash;Serializable序列化","type":0,"userId":20382,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":494,"chapterName":"广场","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":11518,"link":"https://juejin.im/post/5e1e6cf66fb9a0301828ca0a","niceDate":"2020-01-16 21:37","niceShareDate":"2020-01-15 13:05","origin":"","prefix":"","projectLink":"","publishTime":1579181822000,"selfVisible":0,"shareDate":1579064721000,"shareUser":"Lgxing","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"深入探索Android布局优化（下）","type":0,"userId":29390,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":296,"chapterName":"阅读","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":11527,"link":"https://juejin.im/post/5e1d36955188252c4f2ba56f","niceDate":"2020-01-16 21:36","niceShareDate":"2020-01-16 08:47","origin":"","prefix":"","projectLink":"","publishTime":1579181772000,"selfVisible":0,"shareDate":1579135648000,"shareUser":"于慢慢家的吴蜀黍","superChapterId":202,"superChapterName":"延伸技术","tags":[],"title":"未来 Android 开发的从业方向","type":0,"userId":1260,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":502,"chapterName":"自助","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":11532,"link":"https://juejin.im/post/5e1fb9386fb9a0300a4501a6","niceDate":"2020-01-16 13:01","niceShareDate":"2020-01-16 13:01","origin":"","prefix":"","projectLink":"","publishTime":1579150917000,"selfVisible":0,"shareDate":1579150917000,"shareUser":"JsonChao","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"Android主流三方库源码分析（二、深入理解Retrofit源码）","type":0,"userId":611,"visible":1,"zan":0}]
     * offset : 0
     * over : false
     * pageCount : 396
     * size : 20
     * total : 7912
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
         * apkLink :
         * audit : 1
         * author :
         * chapterId : 502
         * chapterName : 自助
         * collect : false
         * courseId : 13
         * desc :
         * envelopePic :
         * fresh : false
         * id : 11622
         * link : https://blog.csdn.net/hfy8971613/article/details/103967595
         * niceDate : 2020-01-21 15:36
         * niceShareDate : 2020-01-21 15:36
         * origin :
         * prefix :
         * projectLink :
         * publishTime : 1579592190000
         * selfVisible : 0
         * shareDate : 1579592190000
         * shareUser : feiyang
         * superChapterId : 494
         * superChapterName : 广场Tab
         * tags : []
         * title : Android中的线程及线程池全面理解~
         * type : 0
         * userId : 31360
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
        public int originId;
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
}
