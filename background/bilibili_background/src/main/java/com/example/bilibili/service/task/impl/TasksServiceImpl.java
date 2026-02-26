package com.example.bilibili.service.task.impl;

import com.example.bilibili.dao.TasksMapper;
import com.example.bilibili.pojo.Tasks;
import com.example.bilibili.pojo.TasksExample;
import com.example.bilibili.service.task.TasksService;
import com.example.bilibili.util.state.TasksState;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TasksServiceImpl implements TasksService {
    @Resource
    TasksMapper tasksMapper;
    @Override
    public List<Tasks> getTasks() {
        TasksExample tasksExample = new TasksExample();
        tasksExample.createCriteria().andStateNotEqualTo(TasksState.WAIT);//除了未发布的，都可以
        return tasksMapper.selectByExample(tasksExample);
    }

    @Override
    public List<Tasks> getAllTasks() {
        return tasksMapper.selectByExample(new TasksExample());
    }
}
