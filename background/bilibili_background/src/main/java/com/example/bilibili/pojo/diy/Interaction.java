package com.example.bilibili.pojo.diy;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Interaction {
    private List<MyComment> myCommentList;
    private Boolean isConcerned;

}
