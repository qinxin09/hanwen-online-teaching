package com.example.bilibili.service.task;

import com.example.bilibili.dao.WorksMapper;
import com.example.bilibili.pojo.Votes;
import com.example.bilibili.pojo.Works;
import com.example.bilibili.pojo.Works;
import com.example.bilibili.pojo.diy.MyWorks;

import javax.annotation.Resource;
import java.util.List;

public interface WorksService {

    List<MyWorks> getActiveWorks();
    List<MyWorks> getAllWorks();


    int addWork(Works works);

    int updataWork(Works works);

    int setVotes(Votes votes);

    List<Votes> getVotes(Votes votes);

    int updVotes(Votes votes);

    List<Votes> countTouristVotes(String touristId);

    int deleteWork(Works works);
}
