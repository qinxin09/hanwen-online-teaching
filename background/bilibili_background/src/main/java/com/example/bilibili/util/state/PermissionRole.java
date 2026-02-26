package com.example.bilibili.util.state;

import java.util.HashMap;
import java.util.Map;

public class PermissionRole {
    public static Map<String,Integer> getPermissionRole(){
        Map<String,Integer> map = new HashMap<>();
        map.put("增添",1);
        map.put("删除",2);
        map.put("编辑",3);
        map.put("查询",4);
        return map;
    }

    public static void main(String[] args) {
        Map map = getPermissionRole();
        System.out.println(map.get("增添"));
    }
}
