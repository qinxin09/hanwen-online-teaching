package com.example.bilibili.pojo.diy;

import com.example.bilibili.pojo.Votes;
import com.example.bilibili.pojo.Works;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MyWorks extends Works {
    private String userName;
    private String taskName;
    private int sum;
    private List<Votes> VoteList;

}
