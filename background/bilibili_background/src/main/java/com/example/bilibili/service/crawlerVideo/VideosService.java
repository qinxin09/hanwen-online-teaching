package com.example.bilibili.service.crawlerVideo;

import com.example.bilibili.pojo.CrawlerWithBLOBs;

import java.util.List;

public interface VideosService {
    List<CrawlerWithBLOBs> getVideos();

    int addVideos(List<CrawlerWithBLOBs> crawlerWithBLOBsList);
}
