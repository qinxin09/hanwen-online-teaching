package com.example.bilibili.service.user;

import com.example.bilibili.pojo.User;
import com.example.bilibili.pojo.UserExample;
import com.example.bilibili.util.Result;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> selectUserByExample(UserExample userExample);
    Result LoginDao(User user);
    Result RegistDao(User user);

    User selectUserById(Long id);

    Result updUserById(User user);


    Result updPasswordById(Long id,String password);




    Long getBeLike(Long authorId);

    Long getFens(Long authorId);

    Long getConcernNumById(Long userId);

    Map<String, Object> selectVideoGroupInfoByCategory();

    boolean checkVip(Long userId);
}
