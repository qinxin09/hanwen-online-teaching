package com.example.bilibili.service.group;

import com.example.bilibili.pojo.MyLikeKey;

public interface LikeService {
    MyLikeKey getLikeById(Long userId, String groupId);

    int delLikeById(MyLikeKey key);

    long countByGroupId(String groupId);

    int insertLike(Long userId, String groupId);
}
