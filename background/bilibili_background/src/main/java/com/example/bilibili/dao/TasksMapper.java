package com.example.bilibili.dao;

import com.example.bilibili.pojo.Tasks;
import com.example.bilibili.pojo.TasksExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TasksMapper {
    long countByExample(TasksExample example);

    int deleteByExample(TasksExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Tasks record);

    int insertSelective(Tasks record);

    List<Tasks> selectByExample(TasksExample example);

    Tasks selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Tasks record, @Param("example") TasksExample example);

    int updateByExample(@Param("record") Tasks record, @Param("example") TasksExample example);

    int updateByPrimaryKeySelective(Tasks record);

    int updateByPrimaryKey(Tasks record);
}