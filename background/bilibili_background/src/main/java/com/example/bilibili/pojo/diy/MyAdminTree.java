package com.example.bilibili.pojo.diy;

import com.example.bilibili.pojo.AdminTree;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MyAdminTree extends AdminTree {
    List<MyAdminTree> adminTreeList;
}