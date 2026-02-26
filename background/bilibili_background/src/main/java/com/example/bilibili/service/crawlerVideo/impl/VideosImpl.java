package com.example.bilibili.service.crawlerVideo.impl;

import com.example.bilibili.dao.CrawlerMapper;
import com.example.bilibili.dao.diy.MyMapper;
import com.example.bilibili.dao.diy.VideosMapper;
import com.example.bilibili.pojo.CrawlerExample;
import com.example.bilibili.pojo.CrawlerWithBLOBs;
import com.example.bilibili.service.crawlerVideo.VideosService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VideosImpl implements VideosService {
    @Resource
    CrawlerMapper crawlerMapper;
    @Resource
    VideosMapper videosMapper;
    @Override
    public List<CrawlerWithBLOBs> getVideos() {
        return crawlerMapper.selectByExampleWithBLOBs(new CrawlerExample());
    }

    @Override
    public int addVideos(List<CrawlerWithBLOBs> crawlerWithBLOBsList) {
        return videosMapper.batchInsertVideos(crawlerWithBLOBsList);
    }
}
