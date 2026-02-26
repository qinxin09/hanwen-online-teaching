package com.example.bilibili.util.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

public class Test_temp {
    public static void getVideos() throws IOException {

        String url = "https://www.zhihuishu.com/portals_h5/2clearning.html#/courseResource?type=1"; //需要解析script
        url="https://hanyu.sogou.com/result?query=%E6%98%AF&mzid=70230901";
        Document document = Jsoup.parse(new URL(url), 30000);
        Element chinajobs = document.getElementById("zuci");
        Elements ssr_video = chinajobs.getElementsByClass("text-default");

        for(Element a:ssr_video){
            System.out.println(a.text());
        }

    }

    public static void main(String[] args) throws IOException {
        getVideos();
    }
}
