package com.example.bilibili.service.task;

import com.example.bilibili.pojo.Tasks;

import java.util.List;

public interface TasksService {
    List<Tasks> getTasks();
    List<Tasks> getAllTasks();
}
