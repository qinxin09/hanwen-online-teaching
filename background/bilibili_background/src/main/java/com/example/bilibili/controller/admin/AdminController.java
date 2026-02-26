package com.example.bilibili.controller.admin;

import com.example.bilibili.pojo.*;
import com.example.bilibili.service.admin.AdminService;
import com.example.bilibili.service.group.VideoGroupService;
import com.example.bilibili.service.task.TasksService;
import com.example.bilibili.service.task.WorksService;
import com.example.bilibili.util.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;
    @Autowired
    TasksService tasksService;
    @Autowired
    VideoGroupService videoGroupService;

    @Autowired
    WorksService worksService;

    @RequestMapping("/loginDo")
    @ResponseBody
    public Result loginDo(@RequestBody Admin admin){
        return adminService.login(admin);
    }

    @RequestMapping("/registDo")
    @ResponseBody
    public Result registDo(@RequestBody Admin admin){
        return adminService.registDo(admin);
    }

    @RequestMapping("/getUserList")
    @ResponseBody
    public Result getUserList(HttpServletRequest request){
        String adminId = (String) request.getAttribute("userInfo");
        System.out.println("adminId="+adminId);
        return adminService.getUserList(Long.valueOf(adminId));
    }

    @RequestMapping("/getGroupList")
    @ResponseBody
    public Result getGroupList(HttpServletRequest request){
        String adminId = (String) request.getAttribute("userInfo");
        System.out.println("adminId="+adminId);
        return adminService.getGroupList(Long.valueOf(adminId));
    }

    @RequestMapping("/editUserState")
    @ResponseBody
    public Result editUserState(@RequestBody Map<String,Object> map, HttpServletRequest request){
        String adminId = (String) request.getAttribute("userInfo");
        Long userId = Long.valueOf((Integer)map.get("userId"));
        byte status = (byte) ((int) map.get("status"));
        try{
            System.out.println(new ObjectMapper().writeValueAsString(map));
        }catch (Exception e){

        }
        return adminService.editUserState(Long.valueOf(adminId),userId,status);
    }

    @RequestMapping("/deleteUser")
    @ResponseBody
    public Result deleteUser(@RequestBody Map<String,Object> map, HttpServletRequest request){
        String adminId = (String) request.getAttribute("userInfo");
        Long userId = Long.valueOf((Integer)map.get("userId"));
        Admin admin = adminService.getAdminById(Long.valueOf(adminId));
        if(adminService.checkDeleteUserPermission(admin.getLv())){
            //        删除合集及其相关数据
            List<VideoGroupWithBLOBs> videoGroupList = videoGroupService.selectVideoGroupByAuthorId(userId);
            for(VideoGroupWithBLOBs v:videoGroupList){
                videoGroupService.deleteVideoGroupById(v.getId(),userId);
            }
            //用户表中删除
            return adminService.deleteUserById(Long.valueOf(adminId),userId);
        }
        return new Result().errorResult("操作异常",null);

    }

    @RequestMapping("/editGroupState")
    @ResponseBody
    public Result editGroupState(@RequestBody Map<String,Object> map,HttpServletRequest request){
        String adminId = (String) request.getAttribute("userInfo");
        String groupId = (String) map.get("id");
        int state = (int) map.get("state");
        return adminService.editGroupState(groupId,state);
    }
    @RequestMapping("/adminDeleteGroup")
    @ResponseBody
    public Result adminDeleteGroup(@RequestBody Map<String,Object> map,HttpServletRequest request){
        String adminId = (String) request.getAttribute("userInfo");
        String groupId = (String) map.get("groupId");
        String userId = String.valueOf(map.get("userId")) ;

        //获取管理员
        //获取admin,检查是否具有此权限
        Admin admin = adminService.getAdminById(Long.valueOf(adminId));
        int lv = admin.getLv();
        if(adminService.checkDeleteGroupPermission(lv) || adminService.checkSuperAdmin(lv)){
            if(videoGroupService.deleteVideoGroupById(groupId,Long.valueOf(userId))>0){
                System.out.println("删除合集及相关数据成功");
                return new Result().successResult("删除合集及相关数据成功");
            }
            System.out.println("身份通过，删除失败，操作失败");
        }else{
            System.out.println("权限不足");
        }
        return new Result().errorResult("删除合集失败");
    }


    @RequestMapping("/getAdminMenu")
    @ResponseBody
    public Result getAdminMenu(HttpServletRequest request){
        String adminId = (String) request.getAttribute("userInfo");
        System.out.println("getAdminMenu: adminId="+adminId);
        Map<String,Object> resultMap = adminService.getAdminMenu(Long.valueOf(adminId));

        if(resultMap!=null){
            return new Result().successResult("获取成功！",resultMap);
        }
        return new Result().errorResult("获取失败！");
    }
    @RequestMapping("/getAdminList")
    @ResponseBody
    public Result getAdminList(HttpServletRequest request){
        String adminId = (String) request.getAttribute("userInfo");
        System.out.println("getAdminMenu: adminId="+adminId);
        List<Admin> adminList = adminService.getAdminList(Long.valueOf(adminId));
        if(adminList.size()>0){
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("adminList",adminList);
//            resultMap.put("permission", AdminState.getPermissionMap());
            return new Result().successResult("获取成功",resultMap);
        }
        return new Result().errorResult("获取失败",null);
    }
    @RequestMapping("/updAdmin")
    @ResponseBody
    public Result updAdmin(@RequestBody Admin admin , HttpServletRequest request) {
        String adminId = (String) request.getAttribute("userInfo");
        return adminService.updAdmin(admin,Long.valueOf(adminId));
    }
    @RequestMapping("/deleteAdmin")
    @ResponseBody
    public Result deleteAdmin(@RequestBody Map<String,Object> map , HttpServletRequest request) {
        String superAdminId = (String) request.getAttribute("userInfo");
        Long adminId = Long.valueOf((Integer) map.get("adminId"));
        return adminService.deleteAdminById(adminId,Long.valueOf(superAdminId));
    }

    @RequestMapping("/getPermissionList")
    @ResponseBody
    public Result getPermissionList(HttpServletRequest request) {
        String superAdminId = (String) request.getAttribute("userInfo");
        List<Permissions> permissionsList = adminService.getAllPermissions(Long.valueOf(superAdminId));
        if(permissionsList.size()==0){
            return new Result().errorResult("获取失败！");
        }
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("permissionsList",permissionsList);
        return new Result().successResult("获取成功！",resultMap);
    }
    @RequestMapping("/savePermission")
    @ResponseBody
    public Result savePermission(@RequestBody Map<String,Object> map, HttpServletRequest request) {
        String superAdminId = (String) request.getAttribute("userInfo");
        if(!adminService.checkSuperAdmin(Long.valueOf(superAdminId))){
            return new Result().errorResult("您不是超级管理员");
        }
        Permissions permissions=new Permissions();
        Map<String,Object> permissionsmap= (Map<String,Object>) map.get("permissions");
        permissions.setCreatetime(new Date());
        permissions.setItems((String) permissionsmap.get("items"));
        permissions.setName((String)permissionsmap.get("name"));
        permissions.setLeval((Integer) permissionsmap.get("leval"));
        permissions.setRoles((String) permissionsmap.get("roles"));

        ArrayList<Map<String,Object>> admintree = (ArrayList<Map<String,Object>>) map.get("admintree");


        String mode = (String) map.get("mode");
        String msg = "";
        if(mode.equals("add")){
            if(adminService.addPermission(Long.valueOf((superAdminId)),permissions)>0){
                msg+="add ok;";
            }
        } else if (mode.equals("edit")) {
            if(adminService.updPermission(Long.valueOf((superAdminId)),permissions)>0){
                msg+="upd ok";
            }
        }
        //更新菜单列表
        for(int i=0;i<admintree.size();i++){
            String lvs = (String) admintree.get(i).get("lv");
            Long streeID = Long.valueOf((Integer) admintree.get(i).get("streeid"));
            int r=adminService.updateAdminTreeById_LV(streeID,lvs);
            System.out.format("lvs=%s  streeID=%s 修改菜单结果：%d\n",lvs,String.valueOf(streeID),r);
        }
        return new Result().successResult(msg);
    }

    @RequestMapping("/deletePermission")
    @ResponseBody
    public Result deletePermission(@RequestBody Map<String,Object> map, HttpServletRequest request) {
        String superAdminId = (String) request.getAttribute("userInfo");
        System.out.println(superAdminId);
        if(adminService.checkSuperAdmin(Long.valueOf(superAdminId))){//是超级管理员
            Integer leval = (Integer) map.get("leval");
            adminService.deleteAdminTreeLvByLv(leval);
            System.out.println("22qwe");
            Map<String,Object> resultMap = adminService.getAdminMenu(Long.valueOf(superAdminId));
//            List<Permissions> permissionList = adminService.getAllPermissions(Long.valueOf(superAdminId));
//            resultMap.put("permissionList",permissionList);
            return new Result().successResult("获取成功！",resultMap);
        }else{
            return new Result().errorResult("您不是超级管理员！");
        }
    }
    @RequestMapping("/saveTask")
    @ResponseBody
    public Result saveTask(@RequestBody Tasks tasks , HttpServletRequest request){
        String adminId = (String) request.getAttribute("userInfo");
        if(adminService.saveTask(tasks)){
            return new Result().successResult("保存成功");
        }
        return new Result().errorResult("保存失败！");
    }
    @RequestMapping("/getTasks")
    @ResponseBody
    public Result getTasks(HttpServletRequest request){
        String adminId = (String) request.getAttribute("userInfo");
        if(adminId!=null){
            List<Tasks> tasksList = tasksService.getAllTasks();
            return new Result().successResult("获取任务成功",tasksList);
        }
        return new Result().errorResult("管理员身份错误！",adminId);
    }
    @RequestMapping("/deleteTask")
    @ResponseBody
    public Result deleteTask(@RequestBody Tasks tasks,HttpServletRequest request){
        String adminId = (String) request.getAttribute("userInfo");
        if(adminService.deleteTasks(tasks.getId())>0){
            return new Result().successResult("删除任务成功");
        }else{
            return new Result().errorResult("删除任务失败");
        }

    }
    @RequestMapping("/getWorks")
    @ResponseBody
    public Result getWorks(HttpServletRequest request){
        String adminId = (String) request.getAttribute("userInfo");
        if(adminService.getAdminById(Long.valueOf(adminId))!=null){
            return new Result().successResult("获取作品成功！",worksService.getAllWorks());
        }
        return null;
    }

    @RequestMapping("/updateWork")
    @ResponseBody
    public Result updateWork(@RequestBody Works works , HttpServletRequest request){
        String adminId = (String) request.getAttribute("userInfo");
        if(adminService.getAdminById(Long.valueOf(adminId))!=null){
            if(worksService.updataWork(works)>0){
                return new Result().successResult("更新作品成功！",works);
            }
            return new Result().errorResult("更新失败！",works);
        }
        return null;
    }
    @RequestMapping("/deleteWork")
    @ResponseBody
    public Result deleteWork(@RequestBody Works works , HttpServletRequest request){
        String adminId = (String) request.getAttribute("userInfo");
        if(adminService.getAdminById(Long.valueOf(adminId))!=null){
            if(worksService.deleteWork(works)>0){
                return new Result().successResult("删除作品成功！");
            }
            return new Result().errorResult("删除失败！",works);
        }
        return null;
    }
}
