package com.example.bilibili.service.group;

import com.example.bilibili.pojo.MyCollectKey;

public interface CollectService {
    MyCollectKey getCollectById(Long userId , String groupId);
    int delCollectById(MyCollectKey key);
    long countByGroupId(String groupId);
    int insertCollect(Long userId,String groupId);
}
