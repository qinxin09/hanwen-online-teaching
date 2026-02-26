package com.example.bilibili.dao;

import com.example.bilibili.pojo.Works;
import com.example.bilibili.pojo.WorksExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WorksMapper {
    long countByExample(WorksExample example);

    int deleteByExample(WorksExample example);

    int deleteByPrimaryKey(String id);

    int insert(Works record);

    int insertSelective(Works record);

    List<Works> selectByExample(WorksExample example);

    Works selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Works record, @Param("example") WorksExample example);

    int updateByExample(@Param("record") Works record, @Param("example") WorksExample example);

    int updateByPrimaryKeySelective(Works record);

    int updateByPrimaryKey(Works record);
}