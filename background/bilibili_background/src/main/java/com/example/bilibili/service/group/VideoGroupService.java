package com.example.bilibili.service.group;


import com.example.bilibili.pojo.VideoGroupWithBLOBs;
import com.example.bilibili.pojo.diy.VideoGroupInfo;
import com.example.bilibili.util.Result;

import java.util.List;

public interface VideoGroupService {
    Result insertVideoGroup(VideoGroupWithBLOBs videoGroupWithBLOBs);

    List<VideoGroupWithBLOBs> selectVideoGroupByAuthorId(Long authorId);

    Result getVideoGroupByAuthorId(Long authorId);
    VideoGroupWithBLOBs getVideoGroupById(String id);
    int updVideoGroupByRecord(VideoGroupWithBLOBs record);
    int deleteVideoGroupById(String groupid,Long userId);
    Result personalRequestGroup(Long id, Boolean isSelf);
    List<VideoGroupInfo> getVideoGroupInfo();
    List<VideoGroupInfo> selectVideoGroupInfoByHot(Integer limitNum);

    int addPlayCount(String groupId, int num);
}
