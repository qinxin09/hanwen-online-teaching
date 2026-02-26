package com.example.bilibili.dao;

import com.example.bilibili.pojo.VideoGroup;
import com.example.bilibili.pojo.VideoGroupExample;
import com.example.bilibili.pojo.VideoGroupWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VideoGroupMapper {
    long countByExample(VideoGroupExample example);

    int deleteByExample(VideoGroupExample example);

    int deleteByPrimaryKey(String id);

    int insert(VideoGroupWithBLOBs record);

    int insertSelective(VideoGroupWithBLOBs record);

    List<VideoGroupWithBLOBs> selectByExampleWithBLOBs(VideoGroupExample example);

    List<VideoGroup> selectByExample(VideoGroupExample example);

    VideoGroupWithBLOBs selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") VideoGroupWithBLOBs record, @Param("example") VideoGroupExample example);

    int updateByExampleWithBLOBs(@Param("record") VideoGroupWithBLOBs record, @Param("example") VideoGroupExample example);

    int updateByExample(@Param("record") VideoGroup record, @Param("example") VideoGroupExample example);

    int updateByPrimaryKeySelective(VideoGroupWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(VideoGroupWithBLOBs record);

    int updateByPrimaryKey(VideoGroup record);
}