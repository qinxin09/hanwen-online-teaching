package com.example.bilibili.dao.diy;

import com.example.bilibili.pojo.AdminTree;
import com.example.bilibili.pojo.diy.MyAdminTree;
import com.example.bilibili.pojo.diy.MyComment;
import com.example.bilibili.pojo.diy.VideoGroupInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyMapper {
    List<VideoGroupInfo> selectVideoGroupInfo_LikedByUserId(Long id);

    List<VideoGroupInfo> selectVideoGroupInfo_WaitByUserId(Long id);
    List<VideoGroupInfo> selectVideoGroupInfo_CollectionByUserId(Long userId);
    List<VideoGroupInfo> selectVideoGroupInfoByUserId(Long authorId);

    List<VideoGroupInfo> selectVideoGroupInfo();

    List<VideoGroupInfo> selectVideoGroupInfoByHot(@Param("limitNum") Integer limitNum);

//    评论
    List<MyComment> getCommentsByGroupId(@Param("groupId") String groupid);

    Long countLikeById(Long authorId);

    Long getFens(Long authorId);

    Long getConcernNumById(Long userId);

    List<VideoGroupInfo> selectVideoGroupInfoPassed();
    List<MyAdminTree> getMenuTree(@Param("ftreeID")Long ftreeID,@Param("lv")String lv);

    List<AdminTree> getMenuTreeByLv(@Param("lv")String lv);

}
