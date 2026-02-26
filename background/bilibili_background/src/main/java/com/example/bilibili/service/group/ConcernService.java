package com.example.bilibili.service.group;

import com.example.bilibili.util.Result;

public interface ConcernService {
    Result doConcern(Long userId, Long authorId);
    Boolean checkConcern(Long userId,Long authorId);
}
