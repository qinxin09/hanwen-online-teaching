package com.example.bilibili.dao;

import com.example.bilibili.pojo.Concern;
import com.example.bilibili.pojo.ConcernExample;
import com.example.bilibili.pojo.ConcernKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConcernMapper {
    long countByExample(ConcernExample example);

    int deleteByExample(ConcernExample example);

    int deleteByPrimaryKey(ConcernKey key);

    int insert(Concern record);

    int insertSelective(Concern record);

    List<Concern> selectByExample(ConcernExample example);

    Concern selectByPrimaryKey(ConcernKey key);

    int updateByExampleSelective(@Param("record") Concern record, @Param("example") ConcernExample example);

    int updateByExample(@Param("record") Concern record, @Param("example") ConcernExample example);

    int updateByPrimaryKeySelective(Concern record);

    int updateByPrimaryKey(Concern record);
}