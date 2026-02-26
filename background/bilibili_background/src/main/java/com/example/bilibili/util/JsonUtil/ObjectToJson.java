package com.example.bilibili.util.JsonUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectToJson {
    public static String trans(Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch (JsonProcessingException e) {
            System.out.println("obj转json出错了？");
            return null;
        }
    }
}
