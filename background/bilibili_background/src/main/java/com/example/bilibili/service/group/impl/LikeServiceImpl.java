package com.example.bilibili.service.group.impl;

import com.example.bilibili.dao.MyLikeMapper;
import com.example.bilibili.pojo.MyLikeExample;
import com.example.bilibili.pojo.MyLikeKey;
import com.example.bilibili.service.group.LikeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LikeServiceImpl implements LikeService {
    @Resource
    MyLikeMapper likeMapper;
    @Override
    public MyLikeKey getLikeById(Long userId, String groupid) {
        MyLikeExample likeExample = new MyLikeExample();
        MyLikeExample.Criteria criteria = likeExample.createCriteria();
        criteria.andGroupIdEqualTo(groupid);
        criteria.andUserIdEqualTo(userId);
        List<MyLikeKey> likeKeys = likeMapper.selectByExample(likeExample);
        if(likeKeys.size()==1){
            return likeKeys.get(0);
        }else{
            return null;
        }
    }

    @Override
    public int delLikeById(MyLikeKey key) {
        return likeMapper.deleteByPrimaryKey(key);
    }

    @Override
    public long countByGroupId(String groupId) {
        MyLikeExample likeExample = new MyLikeExample();
        MyLikeExample.Criteria criteria = likeExample.createCriteria();
        criteria.andGroupIdEqualTo(groupId);
        return likeMapper.countByExample(likeExample);
    }

    @Override
    public int insertLike(Long userId, String groupId) {
        MyLikeKey likeKey=new MyLikeKey();
        likeKey.setGroupId(groupId);
        likeKey.setUserId(userId);
        return likeMapper.insert(likeKey);
    }
}
