package com.example.bilibili.service.group;

import com.example.bilibili.pojo.diy.Interaction;
import com.example.bilibili.pojo.diy.MyComment;

import java.util.List;


public interface InteractionService {
    Interaction getInteraction(Long userId,Long authorId, String groupId);
}
