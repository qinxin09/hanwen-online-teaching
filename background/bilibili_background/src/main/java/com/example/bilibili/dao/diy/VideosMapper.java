package com.example.bilibili.dao.diy;

import com.example.bilibili.pojo.CrawlerWithBLOBs;

import java.util.List;

public interface VideosMapper {
    int batchInsertVideos(List<CrawlerWithBLOBs> crawlerWithBLOBsList);
}
