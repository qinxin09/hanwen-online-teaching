package com.example.bilibili.service.group.impl;


import com.example.bilibili.dao.CommentMapper;
import com.example.bilibili.dao.MyCollectMapper;
import com.example.bilibili.dao.MyLikeMapper;
import com.example.bilibili.dao.VideoGroupMapper;
import com.example.bilibili.dao.diy.MyMapper;
import com.example.bilibili.dao.redis.MyRedis;
import com.example.bilibili.pojo.*;
import com.example.bilibili.pojo.diy.VideoGroupInfo;
import com.example.bilibili.service.group.VideoGroupService;
import com.example.bilibili.util.JsonUtil.JsonTransObj;
import com.example.bilibili.util.JsonUtil.ObjectToJson;
import com.example.bilibili.util.Result;
import com.example.bilibili.util.file.FileUtils;
import com.example.bilibili.util.redis.MyRedisKeys;
import com.example.bilibili.util.state.VideoGroupState;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VideoGroupServiceImpl  implements VideoGroupService {
    @Resource
    VideoGroupMapper videoGroupMapper;
    @Resource
    MyMapper myMapper;
    @Resource
    CommentMapper commentMapper;

    @Resource
    MyCollectMapper myCollectMapper;

    @Resource
    MyLikeMapper myLikeMapper;

    @Autowired
    private MyRedis myRedis;
    @Autowired
    private RedisTemplate redisTemplate;

    private Object takeRedisCache(String key) {
        return myRedis.takeValue(key,redisTemplate);
    }

    @Override
    public Result insertVideoGroup(VideoGroupWithBLOBs videoGroupWithBLOBs){
        if( videoGroupMapper.insert(videoGroupWithBLOBs) > 0 ){
            return new Result().successResult("添加合集成功!",null);
        }
        return new Result().errorResult("添加合集失败！",null);
    }



    @Override
    public List<VideoGroupWithBLOBs> selectVideoGroupByAuthorId(Long authorId) {
        VideoGroupExample videoGroupExample = new VideoGroupExample();
        videoGroupExample.createCriteria().andAuthorIdEqualTo(authorId);
        List<VideoGroupWithBLOBs> videoGroupList = videoGroupMapper.selectByExampleWithBLOBs(videoGroupExample);
        return videoGroupList;
    }

    @Override
    public Result getVideoGroupByAuthorId(Long authorId) {
        List<VideoGroupWithBLOBs> videoGroupList = this.selectVideoGroupByAuthorId(authorId);
        if(videoGroupList.size()>0){
            return new Result().successResult("获取成功",videoGroupList);
        }
        return new Result().errorResult("获取失败",null);
    }

    public VideoGroupWithBLOBs getVideoGroupById(String id) {
        return videoGroupMapper.selectByPrimaryKey(id);
    }


    @Override
    public int updVideoGroupByRecord(VideoGroupWithBLOBs record) {
        this.deletRedisCache();
        return videoGroupMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    public void deletRedisCache(){
        myRedis.deleteByKey(redisTemplate, MyRedisKeys.VIDEO_GROUP_INDEX);
    }
//    @Override
//    public int deleteVideoGroupById(String groupid) {
//        this.deletRedisCache();
//        return videoGroupMapper.deleteByPrimaryKey(groupid);
//    }
    private int deleteGroupCommentByGroupId(String groupId) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andGroupIdEqualTo(groupId);
        return commentMapper.deleteByExample(commentExample);
    }
    private int deleteGroupCollectByGroupId(String groupId) {
        MyCollectExample myCollectExample = new MyCollectExample();
        myCollectExample.createCriteria().andGroupIdEqualTo(groupId);
        return myCollectMapper.deleteByExample(myCollectExample);
    }
    @Override
    public int deleteVideoGroupById(String groupid,Long userId) {
        this.deletRedisCache();
//        删除合集
        int group_db_done = videoGroupMapper.deleteByPrimaryKey(groupid);
//        删除评论
        int comment_db_done = this.deleteGroupCommentByGroupId(groupid);
        //删除收藏
        int collect_db_done = this.deleteGroupCollectByGroupId(groupid);
        //删除赞
        int like_db_done = this.deleteGroupLikeByGroupId(groupid);
        //删除文件
        Map<String,String> map = new HashMap<>();
        map.put("type","groupDir");
        map.put("userId",String.valueOf(userId));
        map.put("groupid",groupid);
        map.put("mode","read");
        Boolean file_deleted = FileUtils.deleteFolderStart(map);
        return group_db_done;
    }

    private int deleteGroupLikeByGroupId(String groupid) {
        MyLikeExample myLikeExample = new MyLikeExample();
        myLikeExample.createCriteria().andGroupIdEqualTo(groupid);
        return myLikeMapper.deleteByExample(myLikeExample);
    }

    @Override
    public Result personalRequestGroup(Long id, Boolean isSelf) {
        Map<String,Object> data = new HashMap<>();
        //1 查询用户本人的所有合集
        List<VideoGroupInfo> myVideoGroup = myMapper.selectVideoGroupInfoByUserId(id);
        if(!isSelf){
            //移除个人中心页未审核的合集
            for(int i=0;i<myVideoGroup.size();i++){
                if(myVideoGroup.get(i).getState()!= VideoGroupState.PASSED){
                    myVideoGroup.remove(i);
                    i--;
                }
            }
        }
        //存入本人自己的所有合集
        data.put("myVideoGroup",myVideoGroup);
        //仅本人可以查看收藏、赞过的
        if(isSelf){
            //获取收藏的
            System.out.println("获取本人的收藏");
            List<VideoGroupInfo> myCollection = myMapper.selectVideoGroupInfo_CollectionByUserId(id);
            data.put("myCollect",myCollection);

            System.out.println("获取本人的合集待审核的");
            List<VideoGroupInfo> myWait = myMapper.selectVideoGroupInfo_WaitByUserId(id);
            data.put("myWait",myWait);

            //获取赞过的
            List<VideoGroupInfo> myLike = myMapper.selectVideoGroupInfo_LikedByUserId(id);
            data.put("myLike",myLike);

        }
        return new Result().successResult("success",data);
    }

    @Override
    public List<VideoGroupInfo> getVideoGroupInfo() {
        return myMapper.selectVideoGroupInfoPassed();
    }

    @Override
    public List<VideoGroupInfo> selectVideoGroupInfoByHot(Integer limitNum) {
        return myMapper.selectVideoGroupInfoByHot(limitNum);
    }

    @Override
    public int addPlayCount(String groupId, int num) {
        VideoGroup videoGroup = videoGroupMapper.selectByPrimaryKey(groupId);
        Long oldPlayCount = videoGroup.getPlayCount();
        Long playCount = oldPlayCount==null?0:oldPlayCount + num;
        videoGroup.setPlayCount(playCount);
        System.out.println("播放量改变，更新缓存");
        if(myRedis.isRedisConnected()){
            try{
                String json =(String) takeRedisCache(MyRedisKeys.VIDEO_GROUP_INDEX);
                List<VideoGroupInfo> videoGroupInfoList = JsonTransObj.toVideoGroupInfoList(json);
                for(VideoGroupInfo v:videoGroupInfoList){
                    if(v.getId().equals(groupId)){
                        v.setPlayCount(playCount);
                        break;
                    }
                }
                json = ObjectToJson.trans(videoGroupInfoList);
                myRedis.putValue(MyRedisKeys.VIDEO_GROUP_INDEX,json,redisTemplate);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }


        return videoGroupMapper.updateByPrimaryKey(videoGroup);
    }



//    @Cacheable(value = "getVideoGroupInfo" ,key = "")
//    public List<VideoGroupInfo> getVideoGroupInfo() {
//        List<VideoGroupInfo> list = null;
//        if(redisTemplate.hasKey("videoGroupInfo")) { // 判断缓存中是否存在
//            System.out.println("已从redis中获取到 合集数据");
//            String listJson = (String) redisTemplate.opsForValue().get("videoGroupInfo"); // 直接从缓存中返回
//            list = JsonTransObj.toVideoGroupInfoList(listJson);
//
//        } else {
//            System.out.println("没有获取到 合集数据");
//            list = myMapper.selectVideoGroupInfo(); // 查询数据
//            String json = ObjectToJson.trans(list);
//            redisTemplate.opsForValue().set("videoGroupInfo", json); // 将查询结果存入缓存
////            redisTemplate.opsForSet().add("videoGroupInfo", json);
//        }
//        return list;
//    }
}
