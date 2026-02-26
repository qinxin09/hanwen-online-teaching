package com.example.bilibili.service.group;

import com.example.bilibili.pojo.Comment;
import com.example.bilibili.util.Result;

public interface CommentService {
    Result sendComment(Comment comment);
    int deleteGroupCommentByGroupId(String groupId);
}
