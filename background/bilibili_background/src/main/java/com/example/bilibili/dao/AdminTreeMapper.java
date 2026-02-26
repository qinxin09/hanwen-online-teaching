package com.example.bilibili.dao;

import com.example.bilibili.pojo.AdminTree;
import com.example.bilibili.pojo.AdminTreeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdminTreeMapper {
    long countByExample(AdminTreeExample example);

    int deleteByExample(AdminTreeExample example);

    int deleteByPrimaryKey(Long streeid);

    int insert(AdminTree record);

    int insertSelective(AdminTree record);

    List<AdminTree> selectByExample(AdminTreeExample example);

    AdminTree selectByPrimaryKey(Long streeid);

    int updateByExampleSelective(@Param("record") AdminTree record, @Param("example") AdminTreeExample example);

    int updateByExample(@Param("record") AdminTree record, @Param("example") AdminTreeExample example);

    int updateByPrimaryKeySelective(AdminTree record);

    int updateByPrimaryKey(AdminTree record);
}