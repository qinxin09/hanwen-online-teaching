package com.example.bilibili.util.JsonUtil;

import com.example.bilibili.pojo.User;
import com.example.bilibili.pojo.diy.VideoGroupInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonTransObj {
    public static List<Map<String, String>> toVideoList(String json) {
        if (json == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Map<String, String>> result = mapper.readValue(json, List.class);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static List<VideoGroupInfo> toVideoGroupInfoList(String json) throws JsonProcessingException {

            ObjectMapper objectMapper = new ObjectMapper(); // 创建 ObjectMapper 对象
            JavaType type = objectMapper.getTypeFactory().constructParametricType(List.class, VideoGroupInfo.class); // 创建 List<VideoGroupInfo> 类型
            List<VideoGroupInfo> obj = objectMapper.readValue(json, type); // 将 JSON 字符串转换成 Java 对象的 List

//            List<VideoGroupInfo> obj2 = null;
//
//            List<VideoGroupInfo> obj = objectMapper.readValue(json, obj2.getClass());
            return obj;

    }

    public static void main(String[] args) {
        String json = "[{\"title\":\"Video1\",\"url\":\"http://www.video1.com\"},{\"title\":\"Video2\",\"url\":\"http://www.video2.com\"}]";
        json="";
        List<Map<String, String>> videoList = toVideoList(json);

        for(Map<String, String> video : videoList) {
            System.out.println(video.get("title") + " : " + video.get("url"));
        }
    }

//    public static User toUser(String user) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper(); // 创建 ObjectMapper 对象
//            JavaType type = objectMapper.getTypeFactory().constructParametricType(List.class, VideoGroupInfo.class); // 创建 List<VideoGroupInfo> 类型
//            User obj = objectMapper.readValue(user, type); // 将 JSON 字符串转换成 Java 对象的 List
//            return obj;
//        }catch (JsonProcessingException e){
//            System.out.println("转User出错！");
//            return null;
//        }
//    }
//    public static User toUser(String userJson) throws JsonProcessingException {
//            ObjectMapper objectMapper = new ObjectMapper();
//            User user = new User();
//            Map<String,Object> map = new HashMap<String,Object>();
//            map = (Map<String,Object>) objectMapper.readValue(userJson, map.getClass());
//            return user;
//
//    }
}
