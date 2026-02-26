package com.example.bilibili.service.admin;

import com.example.bilibili.pojo.Admin;
import com.example.bilibili.pojo.Permissions;
import com.example.bilibili.pojo.Tasks;
import com.example.bilibili.util.Result;

import java.util.List;
import java.util.Map;

public interface AdminService {
    Result login(Admin admin);

    Result registDo(Admin admin);

    Result getUserList(Long adminId);

    Admin getAdminById(Long adminId);


    Result editUserState(Long adminId, Long userId, byte status);

    Result getGroupList(Long adminId);

    Result editGroupState(String groupId, int state);

    Map<String, Object> getAdminMenu(Long adminId);

    Result deleteUserById(Long valueOf, Long userId);

    boolean checkDeleteGroupPermission(int lv);

    boolean checkDeleteUserPermission(Integer lv);

    List<Admin> getAdminList(Long adminId);

    boolean checkSuperAdmin(int lv);

    Result updAdmin(Admin admin, Long superAdminId);

    Result deleteAdminById(Long adminId, Long superAdminId);

    List<Permissions> getAllPermissions(Long superAdminId);

    int addPermission(Long superAdminId, Permissions permissions);

    int updPermission(Long superAdminId, Permissions permissions);

    boolean checkSuperAdmin(Long adminID);

    int updateAdminTreeById_LV(Long streeID, String lvs);

    boolean deleteAdminTreeLvByLv(Integer leval);

    boolean saveTask(Tasks tasks);

    int deleteTasks(Long id);
}
