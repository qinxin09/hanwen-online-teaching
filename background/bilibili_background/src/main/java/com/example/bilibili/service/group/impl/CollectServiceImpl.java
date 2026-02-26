package com.example.bilibili.service.group.impl;

import com.example.bilibili.dao.MyCollectMapper;
import com.example.bilibili.pojo.MyCollectExample;
import com.example.bilibili.pojo.MyCollectKey;
import com.example.bilibili.service.group.CollectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class CollectServiceImpl implements CollectService {
    @Resource
    MyCollectMapper collectMapper;

    @Override
    public MyCollectKey getCollectById(Long userId, String groupId) {
        MyCollectExample collectExample = new MyCollectExample();
        MyCollectExample.Criteria criteria = collectExample.createCriteria();
        criteria.andGroupIdEqualTo(groupId);
        criteria.andUserIdEqualTo(userId);
        List<MyCollectKey> collectKeys = collectMapper.selectByExample(collectExample);
        if(collectKeys.size()==1){
            return collectKeys.get(0);
        }
        return null;
    }

    @Override
    public int delCollectById(MyCollectKey key) {
        return collectMapper.deleteByPrimaryKey(key);
    }

    @Override
    public long countByGroupId(String groupId) {
        MyCollectExample collectExample = new MyCollectExample();
        MyCollectExample.Criteria criteria = collectExample.createCriteria();
        criteria.andGroupIdEqualTo(groupId);
        return collectMapper.countByExample(collectExample);
    }

    @Override
    public int insertCollect(Long userId, String groupId) {
        MyCollectKey myCollectKey = new MyCollectKey();
        myCollectKey.setGroupId(groupId);
        myCollectKey.setUserId(userId);
        return collectMapper.insert(myCollectKey);
    }
}
