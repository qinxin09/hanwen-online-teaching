package com.example.bilibili.service.group.impl;

import com.example.bilibili.dao.ConcernMapper;
import com.example.bilibili.pojo.Concern;
import com.example.bilibili.pojo.ConcernExample;
import com.example.bilibili.pojo.ConcernKey;
import com.example.bilibili.service.group.ConcernService;
import com.example.bilibili.util.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ConcernServiceImpl implements ConcernService {
    @Resource
    ConcernMapper concernMapper;

    public List<Concern> getConcernList(Long userId, Long authorId){
        ConcernExample concernExample = new ConcernExample();
        ConcernExample.Criteria criteria = concernExample.createCriteria();
        criteria.andUseridEqualTo(userId);
        criteria.andAuthoridEqualTo(authorId);
        List<Concern> concernList = concernMapper.selectByExample(concernExample);
        return concernList;
    }
    @Override
    public Result doConcern(Long userId, Long authorId) {
//        ConcernExample concernExample = new ConcernExample();
//        ConcernExample.Criteria criteria = concernExample.createCriteria();
//        criteria.andUseridEqualTo(userId);
//        criteria.andAuthoridEqualTo(authorId);
//        List<Concern> concernList = concernMapper.selectByExample(concernExample);
        List<Concern> concernList = getConcernList(userId,authorId);
        if(concernList.size()==1){
            ConcernKey concernKey = new ConcernKey();
            concernKey.setAuthorid(authorId);
            concernKey.setUserid(userId);
            if(concernMapper.deleteByPrimaryKey(concernKey)>0){
                return new Result().successResult("取消关注成功",false);
            }
            return new Result().errorResult("取消关注失败",null);
        }else if(concernList.size()==0){
            Concern concern = new Concern();
            concern.setAuthorid(authorId);
            concern.setUserid(userId);
            concern.setTime(new Date());
            if(concernMapper.insert(concern)>0){
                return new Result().successResult("关注成功",true);
            }
            return new Result().errorResult("关注失败",null);
        }
        return new Result().errorResult("未知原因",null);
    }

    @Override
    public Boolean checkConcern(Long userId, Long authorId) {
        List<Concern> concernList = getConcernList(userId,authorId);
        return concernList.size()==1;
    }
}
