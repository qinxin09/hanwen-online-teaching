package com.example.bilibili.service.admin.impl;

import com.example.bilibili.dao.*;
import com.example.bilibili.dao.diy.MyMapper;
import com.example.bilibili.pojo.*;
import com.example.bilibili.pojo.diy.MyAdminTree;
import com.example.bilibili.pojo.diy.VideoGroupInfo;
import com.example.bilibili.service.admin.AdminService;
import com.example.bilibili.util.MD5Util;
import com.example.bilibili.util.Result;
import com.example.bilibili.util.jwt.JwtUtils;
import com.example.bilibili.util.redis.MyRedisKeys;
import com.example.bilibili.dao.redis.MyRedis;
import com.example.bilibili.util.state.AdminState;
import com.example.bilibili.util.state.PermissionRole;
import com.example.bilibili.util.state.UserState;
import com.example.bilibili.util.state.VideoGroupState;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    AdminMapper adminMapper;
    @Resource
    UserMapper userMapper;

    @Resource
    MyMapper myMapper;

    @Resource
    VideoGroupMapper videoGroupMapper;
    @Resource
    PermissionsMapper permissionsMapper;

    @Resource
    AdminTreeMapper adminTreeMapper;

    @Resource
    TasksMapper tasksMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    MyRedis myRedis;
    public List<Admin> selectAdminByExample(Admin admin){
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andPhoneEqualTo(admin.getPhone());
        criteria.andPasswordEqualTo(admin.getPassword());
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        return adminList;
    }

    @Override
    public Result login(Admin admin0) {
        AdminExample adminExample = new AdminExample();
        adminExample.createCriteria().andPhoneEqualTo(admin0.getPhone());
        List<Admin> adminList = adminMapper.selectByExample(adminExample);


        //判断账号是否存在
        if(adminList.size()==1){
            String password=adminList.get(0).getPassword();
            String password2=new MD5Util().digest(admin0.getPassword());
            //判断密码是否正确
            if(password2.equals(password)){
                Map<String,Object> resultMap = new HashMap<>();
                resultMap.put("admin",adminList.get(0));
                System.out.println("String.valueOf(admin.getId())=="+String.valueOf(adminList.get(0).getId()));
                String token = JwtUtils.generateToken(String.valueOf(adminList.get(0).getId()));
                resultMap.put("token",token);
                return new Result().successResult("登录成功",resultMap);
            }else{
                return new Result().errorResult("密码错误！",null);
            }
        }else if(adminList.size()==0){
            return new Result().errorResult("账号不存在！",null);
        }
        return new Result().errorResult("登录失败！",null);
    }
    @Override
    public Result registDo(Admin admin) {
        admin.setPassword(new MD5Util().digest(admin.getPassword()));
//        admin.setLv();
        List<Admin> adminList = selectAdminByExample(admin);
        if(adminList.size()>0){
            return new Result().errorResult("手机号已被注册过了！",null);
        }
        int r = adminMapper.insert(admin);
        if(r>0){
            return new Result().successResult("注册成功！",null);
        }else{
            return new Result().errorResult("注册失败！",null);
        }
    }

    @Override
    public Result getUserList(Long adminId) {
        if(adminMapper.selectByPrimaryKey(adminId)!=null){
            Map<String,Object> resultMap = new HashMap<>();
            List<User> userList = userMapper.selectByExample(new UserExample());
            List<User> normal = new ArrayList<>();
            List<User> frozen = new ArrayList<>();
            for(User u : userList){
                if(u.getStatus() == UserState.NORMAL){
                    normal.add(u);
                } else if(u.getStatus() == UserState.FROZEN){
                    frozen.add(u);
                }
            }
            resultMap.put("normal",normal);
            resultMap.put("block",frozen);
            return new Result().successResult("获取用户列表成功",resultMap);
        }
        return null;
    }

    @Override
    public Admin getAdminById(Long adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }

    @Override
    public Result editUserState(Long adminId, Long userId, byte status) {
        if(adminMapper.selectByPrimaryKey(adminId)!=null){
            User user = userMapper.selectByPrimaryKey(userId);
            user.setStatus(status);
            userMapper.updateByPrimaryKey(user);
            return new Result().successResult("修改成功",null);
        }
        return new Result().errorResult("修改失败",null);
    }

    @Override
    public Result getGroupList(Long adminId) {
        if(adminMapper.selectByPrimaryKey(adminId)!=null){
            //存在管理员则认为是管理员在操作
            List<VideoGroupInfo> videoGroupInfoList = myMapper.selectVideoGroupInfo();
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("groupList",videoGroupInfoList);
            return new Result().successResult("获取合集数据成功!",resultMap);
        }
        return new Result().errorResult("获取合集数据失败！",null);
    }

    @Override
    public Result editGroupState(String groupId, int state) {
        VideoGroup videoGroup = videoGroupMapper.selectByPrimaryKey(groupId);
        if(videoGroup.getState() == VideoGroupState.PASSED || state==VideoGroupState.PASSED){
            Boolean deleteDone = myRedis.deleteByKey(redisTemplate, MyRedisKeys.VIDEO_GROUP_INDEX);
            System.out.println("将已通过审核的合集修改状态 或 设置合集状态为通过，更新redis缓存："+deleteDone);
        }
        videoGroup.setState(state);
        if(videoGroupMapper.updateByPrimaryKey(videoGroup) > 0){
            return new Result().successResult("success",state);
        }
        return new Result().errorResult("error",state);
    }

    @Override
    public Map<String, Object> getAdminMenu(Long adminId) {
        //获取admin 等级
        Admin admin = adminMapper.selectByPrimaryKey(adminId);
        if(admin.getLv()==null){
            return null;
        }
        int lv = admin.getLv();
//        一级菜单列表
        List<MyAdminTree> menuTreeList = myMapper.getMenuTree(1L,String.valueOf(lv));
        Map<String,Object> resultMap = new HashMap<>();

        if(menuTreeList!=null){
            //循环每个一级菜单列表
            for(MyAdminTree myAdminTrees : menuTreeList){
                //获取二级菜单列表
                Long ftreeId = myAdminTrees.getStreeid();
                List<MyAdminTree> menuTreeList2=myMapper.getMenuTree(ftreeId, String.valueOf(lv));
                myAdminTrees.setAdminTreeList(menuTreeList2);
            }
            List<Permissions> permissionList = this.getAllPermissions(adminId);
            resultMap.put("adminMenu",menuTreeList);
            resultMap.put("admin",admin);
            resultMap.put("permissionList",permissionList);
            resultMap.put("roles", PermissionRole.getPermissionRole());

            return resultMap;
        }else{
            System.out.println("没有查询到菜单列表！");
        }
        return null;
    }

    @Override
    public Result deleteUserById(Long adminId, Long userId) {
        //获取admin,检查是否具有此权限
        Admin admin = adminMapper.selectByPrimaryKey(adminId);
        int lv = admin.getLv();
        if(this.checkDeleteUserPermission(lv)){
            //删除用户
            if(userMapper.deleteByPrimaryKey(userId)>0){
                return new Result().successResult("删除用户成功！",null);
            }
            return new Result().errorResult("删除用户失败！",null);
        }else{
            return new Result().errorResult("警告！您的权限不能删除用户！",null);
        }

    }



    @Override
    public boolean checkDeleteGroupPermission(int lv) {
        return this.checkDeletePermission(lv);
    }

    private boolean checkDeletePermission(int lv) {
        PermissionsExample permissionsExample = new PermissionsExample();
        permissionsExample.createCriteria().andLevalEqualTo(lv);
        List<Permissions> permissionList = permissionsMapper.selectByExample(permissionsExample);
        if(permissionList.size()!=1){
            return false;
        }
        String[] roles = permissionList.get(0).getRoles().split(",");
        for (int i = 0; i < roles.length; i++) {
            if(roles[i].equals(lv+"")){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkDeleteUserPermission(Integer lv) {
        return this.checkDeletePermission(lv);
    }


    @Override
    public List<Admin> getAdminList(Long adminId) {
        //验证超级管理员等级
        Admin admin = this.getAdminById(adminId);
        if(this.checkSuperAdmin(admin.getLv())){
            AdminExample adminExample = new AdminExample();
//            找出除超级管理员之外的所有管理员
            adminExample.createCriteria().andIdNotEqualTo(adminId);
            return adminMapper.selectByExample(adminExample);
        }
        return null;
    }

    @Override
    public boolean checkSuperAdmin(int lv) {
        return lv==AdminState.SUPER_ADMIN_LV;
    }

    @Override
    public Result updAdmin(Admin admin, Long superAdminId) {
        Admin superAdmin = getAdminById(superAdminId);
        if(superAdmin!=null){
            if(this.checkSuperAdmin(superAdmin.getLv())){
                if(adminMapper.updateByPrimaryKeySelective(admin) >0 ){
                    return new Result().successResult("修改管理员成功");
                }else{
                    return new Result().errorResult("修改失败");
                }
            }else {
                return new Result().errorResult("权限不足！");
            }
        }
        return new Result().errorResult("未获取到您的身份！");
    }

    @Override
    public Result deleteAdminById(Long adminId, Long superAdminId) {
//        判断超级管理员
        Admin superAdmin = getAdminById(superAdminId);
        if(this.checkSuperAdmin(superAdmin.getLv())){
            if(adminMapper.deleteByPrimaryKey(adminId)>0){
                return new Result().successResult("删除成功");
            }
            return new Result().errorResult("删除失败");
        }else{
            return new Result().errorResult("您的权限不足！");
        }

    }

    @Override
    public List<Permissions> getAllPermissions(Long superAdminId) {
        Admin admin = this.getAdminById(superAdminId);
        if(admin!=null){
            List<Permissions> permissionsList = permissionsMapper.selectByExample(new PermissionsExample());
            return permissionsList;
        }else{
            return null;
        }

    }

    @Override
    public int addPermission(Long superAdminId, Permissions permissions) {
        Admin admin = this.getAdminById(superAdminId);
        if(admin!=null && this.checkSuperAdmin(admin.getLv())){
            return permissionsMapper.insert(permissions);
        }
        return 0;
    }

    @Override
    public int updPermission(Long superAdminId, Permissions permissions) {
        Admin admin = this.getAdminById(superAdminId);
        if(admin!=null && this.checkSuperAdmin(admin.getLv())){
            PermissionsExample permissionsExample = new PermissionsExample();
            permissionsExample.createCriteria().andLevalEqualTo(permissions.getLeval());
            List<Permissions> permissionsList=permissionsMapper.selectByExample(permissionsExample);
            if(permissionsList.size()==1){
                permissions.setId(permissionsList.get(0).getId());
                return permissionsMapper.updateByPrimaryKeySelective(permissions);
            }
        }
        return 0;
    }

    @Override
    public boolean checkSuperAdmin(Long adminID) {
        Admin admin = this.getAdminById(adminID);
        if(admin!=null && this.checkSuperAdmin(admin.getLv())){
            return true;
        }
        return false;
    }

    @Override
    public int updateAdminTreeById_LV(Long streeID, String lvs) {
        AdminTree adminTree = adminTreeMapper.selectByPrimaryKey(streeID);
        adminTree.setLv(lvs);
        return adminTreeMapper.updateByPrimaryKey(adminTree);
    }

    public int deletePermissionByLv(int lv){
        PermissionsExample permissionsExample = new PermissionsExample();
        permissionsExample.createCriteria().andLevalEqualTo(lv);
        return permissionsMapper.deleteByExample(permissionsExample);
    }

    @Override
    public boolean deleteAdminTreeLvByLv(Integer leval) {
        //删除权限表
        this.deletePermissionByLv(leval);

        //找到含有leval的所有列表
        List<AdminTree> adminTreeList = myMapper.getMenuTreeByLv(String.valueOf(leval));
        if(adminTreeList.size()==0){
            return false;
        }
        for(AdminTree a : adminTreeList){
            String[] lvs = a.getLv().split(",");
            List<String> lvList = new ArrayList<>(Arrays.asList(lvs));
            for(int i = 0; i < lvList.size(); i++){
                if(lvList.get(i).equals(String.valueOf(leval))){
                    lvList.remove(i);
                    i--;
                }
            }
            a.setLv(String.join(",", lvList));
            //更新每个菜单列表
            adminTreeMapper.updateByPrimaryKey(a);
        }
        try{
            System.out.println(new ObjectMapper().writeValueAsString(adminTreeList));
        }catch (Exception e){}
        return true;
    }

    @Override
    public boolean saveTask(Tasks tasks) {
        if(tasksMapper.selectByPrimaryKey(tasks.getId())!=null){
            return tasksMapper.updateByPrimaryKey(tasks)>0;
        }
        return tasksMapper.insert(tasks)>0;
    }

    @Override
    public int deleteTasks(Long id) {
        return tasksMapper.deleteByPrimaryKey(id);
    }


}
