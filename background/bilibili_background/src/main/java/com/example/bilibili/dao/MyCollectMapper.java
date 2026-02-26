package com.example.bilibili.dao;

import com.example.bilibili.pojo.MyCollectExample;
import com.example.bilibili.pojo.MyCollectKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MyCollectMapper {
    long countByExample(MyCollectExample example);

    int deleteByExample(MyCollectExample example);

    int deleteByPrimaryKey(MyCollectKey key);

    int insert(MyCollectKey record);

    int insertSelective(MyCollectKey record);

    List<MyCollectKey> selectByExample(MyCollectExample example);

    int updateByExampleSelective(@Param("record") MyCollectKey record, @Param("example") MyCollectExample example);

    int updateByExample(@Param("record") MyCollectKey record, @Param("example") MyCollectExample example);
}