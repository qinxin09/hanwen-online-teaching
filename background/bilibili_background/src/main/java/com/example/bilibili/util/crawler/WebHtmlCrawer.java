package com.example.bilibili.util.crawler;
/**
 * <!--        Jsoup是一款Java HTML解析器，用于提取和操作HTML数据。-->
 *         <dependency>
 *             <groupId>org.apache.poi</groupId>
 *             <artifactId>poi</artifactId>
 *             <version>4.1.0</version>
 *         </dependency>
 *         <dependency>
 *             <groupId>org.jsoup</groupId>
 *             <artifactId>jsoup</artifactId>
 *             <version>1.14.3</version>
 *         </dependency>
 */

import com.example.bilibili.pojo.CrawlerWithBLOBs;
import com.example.bilibili.util.file.FileRoot;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 网页爬虫
 */

public class WebHtmlCrawer {

    /*
    https://haokan.baidu.com/haokan/ui-web/video/rec?tab=recommend&act=pcFeed&pd=pc&num=20&shuaxin_id=16889480640000
    https://haokan.baidu.com/haokan/ui-web/video/feed?time=1688947611290

    //影视 https://haokan.baidu.com/haokan/ui-web/video/rec?tab=yingshi_new&act=pcFeed&pd=pc&num=20&shuaxin_id=16889506000000
    //音乐 https://haokan.baidu.com/haokan/ui-web/video/rec?tab=yinyue_new&act=pcFeed&pd=pc&num=20&shuaxin_id=16889506600000
     */


    private static void downloadImage(String imageUrl,String savePath) throws IOException {
        URL url = new URL(imageUrl);
        URLConnection connection = url.openConnection();
        connection.connect();


        // 检查保存路径是否存在，如果不存在则创建
        File saveFile = new File(savePath);
        File parentDir = saveFile.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (BufferedInputStream inputStream = new BufferedInputStream(connection.getInputStream());
             FileOutputStream outputStream = new FileOutputStream(saveFile)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }


    public List<CrawlerWithBLOBs>  getVideos() throws IOException {

        String url = "https://www.zhihuishu.com/portals_h5/2clearning.html#/courseResource?type=1"; //需要解析script
        url="https://haokan.baidu.com/";
        Document document = Jsoup.parse(new URL(url), 30000);
//        System.out.println(document);
        Element chinajobs = document.getElementById("rooot");
        Elements ssr_video = chinajobs.getElementsByClass("ssr-video");

        System.out.println("ssr-video-------------------------------\n"+ssr_video);

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("一览表");

        HSSFRow row0 = sheet.createRow(0);
        row0.createCell(0).setCellValue("封面");
        row0.createCell(1).setCellValue("播放地址");
        row0.createCell(2).setCellValue("时长");
        row0.createCell(3).setCellValue("标题");
        row0.createCell(4).setCellValue("作者");
        row0.createCell(5).setCellValue("播放量");

        List<CrawlerWithBLOBs> crawlerList = new ArrayList<>();
        for(int j=0;j<ssr_video.size();j++){
            Elements ssr_videoitem = ssr_video.get(j).getElementsByClass("ssr-videoitem");
            //video
            Elements videoItem = ssr_videoitem.get(0).getElementsByClass("ssr-videoitem-img");
            //cover
            String style = videoItem.get(0).attr("style");
            String CoverImgUrl = style.substring(style.indexOf("(")+1,style.indexOf(")"));
            System.out.println("封面: "+CoverImgUrl);

            //videoUrl
            String videoUrl = videoItem.get(0).attr("href");
            System.out.println("videoUrl: "+videoUrl);

            //length
            String videoLength = videoItem.get(0).getElementsByClass("ssr-img-bottom-text").get(0).text();
            System.out.println("时长: "+videoLength);
            //title
            String title = ssr_videoitem.get(0).getElementsByClass("ssr-bottom").get(0).getElementsByClass("ssr-videoitem-title").get(0).text();
            System.out.println("标题: "+title);

            //authorName
            String authorName = ssr_videoitem.get(0).getElementsByClass("ssr-bottom").get(0).getElementsByClass("ssr-bottom-username").get(0).select("a").text();
            System.out.println("作者: "+authorName);

            //播放量
            String palyCount = ssr_videoitem.get(0).getElementsByClass("ssr-bottom").get(0).getElementsByClass("ssr-videoitem-extinfo").get(0).text();
            System.out.println("播放量: "+palyCount);

            //将获得到的数据放入表格中
            HSSFRow row = sheet.createRow(j+1);
            row.createCell(0).setCellValue(CoverImgUrl);
            row.createCell(1).setCellValue(videoUrl);
            row.createCell(2).setCellValue(videoLength);
            row.createCell(3).setCellValue(title);
            row.createCell(4).setCellValue(authorName);
            row.createCell(5).setCellValue(palyCount);

//            还是下载图片吧
            String coverImgName = new Date().getTime()+"";
            String createCoverUrl = "/image/getImg?type=crawlerCoverImg&fileName="+coverImgName +".jpg";
            String savePath = FileRoot.FILE_ROOT+"/mybilibili/crawler/img/"+coverImgName +".jpg";

            CrawlerWithBLOBs crawlerWithBLOBs = new CrawlerWithBLOBs();
            crawlerWithBLOBs.setCoverUrl(createCoverUrl);
            crawlerWithBLOBs.setVideoUrl(videoUrl);
            crawlerWithBLOBs.setLength(videoLength);
            crawlerWithBLOBs.setTitle(title);
            crawlerWithBLOBs.setAuthorName(authorName);
            crawlerWithBLOBs.setPlayCount(palyCount);
            crawlerList.add(crawlerWithBLOBs);
            this.downloadImage(CoverImgUrl,savePath);
        }
        try {
            FileOutputStream fout = new FileOutputStream(FileRoot.FILE_ROOT+"/爬虫数据.xls");
            wb.write(fout);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return crawlerList;
    }

}