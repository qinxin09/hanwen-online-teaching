package com.example.bilibili.dao;

import com.example.bilibili.pojo.MyLikeExample;
import com.example.bilibili.pojo.MyLikeKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MyLikeMapper {
    long countByExample(MyLikeExample example);

    int deleteByExample(MyLikeExample example);

    int deleteByPrimaryKey(MyLikeKey key);

    int insert(MyLikeKey record);

    int insertSelective(MyLikeKey record);

    List<MyLikeKey> selectByExample(MyLikeExample example);

    int updateByExampleSelective(@Param("record") MyLikeKey record, @Param("example") MyLikeExample example);

    int updateByExample(@Param("record") MyLikeKey record, @Param("example") MyLikeExample example);
}