package com.example.bilibili.util.file;

import java.io.File;
import java.util.Map;

public class GetFile {
    public File getFile(Map<String,String> map){
        String fileName = map.get("fileName") ;
        String type = map.get("type");
        String pathDir = FileRoot.FILE_ROOT+"/mybilibili/uploads/userID" + map.get("userId");
        if(type.equals("icon")){//头像
            pathDir += "/icon";
            fileName = "icon.jpg"; // 对于头像，强制将文件名设置为 icon.jpg
        }else if(type.equals("videoGroupCover")){//合集封面
            pathDir += "/videogroup/" + map.get("groupid") + "/coverimg";//封面图
            fileName = "cover.jpg";
        }else if(type.equals("videoGroupList")){
            pathDir += "/videogroup/" + map.get("groupid") + "/videolist";
        }else if (type.equals("groupDir")) {
            //返回路径
            pathDir += "/videogroup/" + map.get("groupid") +"/";
            return new File(pathDir);
        }else if (type.equals("attachement")) {
            pathDir += "/videogroup/" + map.get("groupid") +"/attachement/";
        } else if (type.equals("taskProject")) {
            // taskId/taskID/
            pathDir = FileRoot.FILE_ROOT+"/mybilibili/uploads/tasks/" + map.get("taskID");
            pathDir += "/user/" + map.get("userId") +"/video/";
        }else if(type.equals("crawlerCoverImg")){
            pathDir = FileRoot.FILE_ROOT+"/mybilibili/crawler/img/";
        }

        if (map.get("mode").equals("write")){
            //保存文件需要确保路径
            // 创建保存文件的目录
            File uploadDir = new File(pathDir);
            // 如果目录不存在则创建目录
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
        }
        return new File(pathDir,fileName);
    }
}
