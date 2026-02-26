package com.example.bilibili.dao;

import com.example.bilibili.pojo.Crawler;
import com.example.bilibili.pojo.CrawlerExample;
import com.example.bilibili.pojo.CrawlerWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CrawlerMapper {
    long countByExample(CrawlerExample example);

    int deleteByExample(CrawlerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CrawlerWithBLOBs record);

    int insertSelective(CrawlerWithBLOBs record);

    List<CrawlerWithBLOBs> selectByExampleWithBLOBs(CrawlerExample example);

    List<Crawler> selectByExample(CrawlerExample example);

    CrawlerWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CrawlerWithBLOBs record, @Param("example") CrawlerExample example);

    int updateByExampleWithBLOBs(@Param("record") CrawlerWithBLOBs record, @Param("example") CrawlerExample example);

    int updateByExample(@Param("record") Crawler record, @Param("example") CrawlerExample example);

    int updateByPrimaryKeySelective(CrawlerWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(CrawlerWithBLOBs record);

    int updateByPrimaryKey(Crawler record);
}