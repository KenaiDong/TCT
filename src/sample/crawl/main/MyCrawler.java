package sample.crawl.main;

import sample.crawl.link.LinkFilter;
import sample.crawl.link.Links;
import sample.crawl.page.Page;
import sample.crawl.page.PageParserTool;
import sample.crawl.page.RequestAndResponseTool;
import sample.crawl.util.FileTool;

import java.util.Set;

public class MyCrawler {

    /**
     * 使用种子初始化 URL 队列
     *
     * @param seeds 种子 URL
     * @return
     */
    private void initCrawlerWithSeeds(String[] seeds) {
        for (int i = 0; i < seeds.length; i++){
            Links.addUnvisitedUrlQueue(seeds[i]);
        }
    }


    /**
     * 抓取过程
     *
     * @param seeds
     * @return
     */
    public void crawling(String[] seeds) {

        //初始化 URL 队列
        initCrawlerWithSeeds(seeds);

        //定义过滤器，提取以 http://www.baidu.com 开头的链接
        LinkFilter filter = new LinkFilter() {
            public boolean accept(String url) {
                if (url.startsWith("img.alicdn.com"))
                    return true;
                else
                    return false;
            }
        };


        //循环条件：待抓取的链接不空且抓取的网页不多于 1000
        while (!Links.unVisitedUrlQueueIsEmpty()  && Links.getVisitedUrlNum() <= 1000) {
            //先从待访问的序列中取出第一个；
            String visitUrl = (String) Links.removeHeadOfUnVisitedUrlQueue();
            if (visitUrl == null){
                continue;
            }
            //根据URL得到page;
            Page page = RequestAndResponseTool.sendRequstAndGetResponse(visitUrl);
            String url = page.getUrl();
            if (url.contains("jpg")){
                url = url.replaceAll("_430x430q90.jpg","");
                url = url.replaceAll("_60x60q90.jpg","");
                FileTool.saveToFile(url);
                //将已经访问过的链接放入已访问的链接中；
                Links.addVisitedUrlSet(visitUrl);
            }
            //得到超链接
            Set<String> links = PageParserTool.getLinks(page,"img");
            for (String link : links) {
                Links.addUnvisitedUrlQueue(link);
                System.out.println("新增爬取路径: " + link);
            }
        }
    }


    //main 方法入口
    public static void main(String[] args) {
        MyCrawler crawler = new MyCrawler();
        crawler.crawling(new String[]{"https://detail.tmall.com/item.htm?id=601068388480&ali_refid=a3_430583_1006:1209870099:N:hKrcep2uDFOHVCltwy+Ouj0No/Yy8i0K:4cb1db31ae231023e3932b7afb6e3b21&ali_trackid=1_4cb1db31ae231023e3932b7afb6e3b21&spm=a230r.1.14.1"});
    }
}
