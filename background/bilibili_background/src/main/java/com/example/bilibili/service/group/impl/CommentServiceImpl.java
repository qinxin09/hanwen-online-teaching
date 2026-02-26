package com.example.bilibili.service.group.impl;

import com.example.bilibili.dao.CommentMapper;
import com.example.bilibili.dao.VideoGroupMapper;
import com.example.bilibili.dao.diy.MyMapper;
import com.example.bilibili.dao.redis.MyRedis;
import com.example.bilibili.pojo.Comment;
import com.example.bilibili.pojo.CommentExample;
import com.example.bilibili.pojo.VideoGroup;
import com.example.bilibili.service.group.CommentService;
import com.example.bilibili.util.Result;
import com.example.bilibili.util.redis.MyRedisKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    CommentMapper commentMapper;

    @Resource
    VideoGroupMapper videoGroupMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    MyRedis myRedis;

    @Override
    public Result sendComment(Comment comment) {
        //获取评论量
        VideoGroup videoGroup = videoGroupMapper.selectByPrimaryKey(comment.getGroupId());
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andGroupIdEqualTo(comment.getGroupId());
        Long commentCount = commentMapper.countByExample(commentExample);
        videoGroup.setCommentCount(commentCount+1);
        //更新到合集
        videoGroupMapper.updateByPrimaryKey(videoGroup);

        myRedis.deleteByKey(redisTemplate,MyRedisKeys.VIDEO_GROUP_INDEX);

        if(commentMapper.insert(comment)>0){
            return new Result().successResult("success",null);
        }else{
            return new Result().errorResult("error",null);
        }

    }

    @Override
    public int deleteGroupCommentByGroupId(String groupId) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andGroupIdEqualTo(groupId);
        return commentMapper.deleteByExample(commentExample);
    }
}
