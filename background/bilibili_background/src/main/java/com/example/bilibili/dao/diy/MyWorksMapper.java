package com.example.bilibili.dao.diy;

import com.example.bilibili.pojo.Votes;
import com.example.bilibili.pojo.diy.MyWorks;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyWorksMapper {
    List<MyWorks> getActiveWorks();
    int insertVotes(Votes votes);

    List<MyWorks> getAllWorks();

    int deleteWorks(@Param("id") String id);
}
