package com.example.bilibili.service.group.impl;

import com.example.bilibili.dao.CommentMapper;
import com.example.bilibili.dao.ConcernMapper;
import com.example.bilibili.dao.diy.MyMapper;
import com.example.bilibili.pojo.CommentExample;
import com.example.bilibili.pojo.ConcernExample;
import com.example.bilibili.pojo.diy.Interaction;
import com.example.bilibili.pojo.diy.MyComment;
import com.example.bilibili.service.group.InteractionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InteractionServiceImpl implements InteractionService {
    @Resource
    CommentMapper commentMapper;
    @Resource
    MyMapper myMapper;
    @Resource
    ConcernMapper concernMapper;
    @Override
    public Interaction getInteraction(Long userId,Long authorId, String groupId) {
        //获取评论区
        List<MyComment> commentList = myMapper.getCommentsByGroupId(groupId);
        //获取关注的状态
        ConcernExample concernExample = new ConcernExample();
        ConcernExample.Criteria criteria = concernExample.createCriteria();
        criteria.andUseridEqualTo(userId);
        criteria.andAuthoridEqualTo(authorId);
        Boolean isConcerned = concernMapper.selectByExample(concernExample).size()==1;
        Interaction interaction = new Interaction();
        interaction.setMyCommentList(commentList);
        interaction.setIsConcerned(isConcerned);

        return interaction;
    }
}
