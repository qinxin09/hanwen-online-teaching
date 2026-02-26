package com.example.bilibili.dao;

import com.example.bilibili.pojo.Votes;
import com.example.bilibili.pojo.VotesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VotesMapper {
    long countByExample(VotesExample example);

    int deleteByExample(VotesExample example);

    int insert(Votes record);

    int insertSelective(Votes record);

    List<Votes> selectByExample(VotesExample example);

    int updateByExampleSelective(@Param("record") Votes record, @Param("example") VotesExample example);

    int updateByExample(@Param("record") Votes record, @Param("example") VotesExample example);
}