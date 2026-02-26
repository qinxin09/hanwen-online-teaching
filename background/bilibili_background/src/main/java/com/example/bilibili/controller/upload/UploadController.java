package com.example.bilibili.controller.upload;

import com.example.bilibili.pojo.Works;
import com.example.bilibili.pojo.VideoGroupWithBLOBs;
import com.example.bilibili.service.group.VideoGroupService;
import com.example.bilibili.service.task.WorksService;
import com.example.bilibili.util.JsonUtil.JsonTransObj;
import com.example.bilibili.util.Result;
import com.example.bilibili.util.file.GetFile;
import com.example.bilibili.util.state.WorksState;
import com.example.bilibili.util.state.VideoState;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    VideoGroupService videoGroupService;

    @Autowired
    WorksService worksService;

    /*@PostMapping("/videoGroupCover")
    @ResponseBody
    public Result uploadFiles(@RequestParam("file") MultipartFile file, String type, String groupid, HttpServletRequest request){
        System.out.println("save img");
        String userId = (String) request.getAttribute("userInfo");


        if(userId==null && userId==""){
            return new Result().errorResult("用户信息异常，请重新登录！",null);
        }
        Map<String,String> map = new HashMap<String,String>();
        map.put("type",type);
        map.put("groupid",groupid);
        map.put("userId",userId);
        map.put("mode","write");

        // 获取上传文件的原始文件名和路径变量
        String fileName = file.getOriginalFilename(); // 原始文件名
        String pathDir = FileRoot.FILE_ROOT+"/mybilibili/uploads/userID" + userId; // 文件夹路径

        if(type.equals("icon")){//头像
            pathDir += "/icon";
            fileName = "icon.jpg"; // 对于头像，强制将文件名设置为 icon.jpg
        }else if(type.equals("videoGroupCover")){//合集封面
            pathDir += "/videogroup/" + groupid + "/coverimg";//封面图
        }else if(type.equals("videoGroupList")){
            pathDir += "/videogroup/" + groupid + "/videolist";
        }
        // 存入路径
        try {
            if (file.isEmpty()) { // 判断上传文件是否为空
                throw new RuntimeException("上传文件为空");
            }
            // 创建保存文件的目录
            File uploadDir = new File(pathDir);
            // 如果目录不存在则创建目录
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            // 将上传文件保存到指定目录中
            File dest = new File(uploadDir, fileName);
            file.transferTo(dest);
            System.out.println(uploadDir + "\\" + fileName);
            return new Result().successResult("已收到文件并保存！", null);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result().errorResult("保存失败", new HashMap<String,Object>().put("errorMsg", e));
        }
    }*/

    //上传单个视频的信息，包括时长
    @PostMapping("/addVideo")
    @ResponseBody
    public Result addVideo(@RequestBody Map<String,String> map ) throws JsonProcessingException {
        String title=(String) map.get("title");
        String desc=(String) map.get("desc");
        String index= map.get("index")+"" ;
        String newname=map.get("newname");
        String groupid=map.get("groupid");
        String duration = map.get("duration");

        System.out.println("title="+title+"  desc"+desc+"  index"+index+"  newname"+newname+"  groupid"+groupid);
        System.out.println("duration:"+duration);
        //                videos存入合集中
            VideoGroupWithBLOBs videoGroupWithBLOBs = videoGroupService.getVideoGroupById(groupid);
            String videos = videoGroupWithBLOBs.getVideos();
            List<Map<String,String>> videolist=new ArrayList<>();
            if(videos!=null && videos.length()>0){
//                获取原来的视频列表
                videolist = JsonTransObj.toVideoList(videos);

            }
            Map<String,String> map2= new HashMap<>();
            map2.put("title",title);
            map2.put("desc",desc);
            map2.put("newname",newname);
            map2.put("index",index);
            map2.put("state", VideoState.WAIT+"");
            map2.put("duration",duration);
            videolist.add(map2);
            videos = new ObjectMapper().writeValueAsString(videolist);

            videoGroupWithBLOBs.setVideos(videos);
            if(videoGroupService.updVideoGroupByRecord(videoGroupWithBLOBs)>0){
                return new Result().successResult("success",null);
            }
            return new Result().errorResult("faild",null);

    }
    @PostMapping("/uploadFile")
    @ResponseBody
    public Result uploadFiles(@RequestParam("file") MultipartFile file, String type,String title,String desc,String index ,String newname, String groupid, HttpServletRequest request,Long taskID,Long duration,String uuid){

        String userId = (String) request.getAttribute("userInfo");
        if(userId==null && userId==""){
            return new Result().errorResult("用户信息异常，请重新登录！",null);
        }
        // 获取上传文件的原始文件名和路径变量
        String fileName = file.getOriginalFilename(); // 原始文件名
        Map<String,String> map = new HashMap<String,String>();
        map.put("type",type);
        map.put("groupid",groupid);
        map.put("userId",userId);
        map.put("mode","write");
        map.put("fileName",fileName);



        // 存入路径
        try {
            if (file.isEmpty()) { // 判断上传文件是否为空
                throw new RuntimeException("上传文件为空");
            }
            if(type.equals("videoGroupList")){
                //上传视频，存入相关数据到数据库
//                获取视频名称（uuid)
                map.replace("fileName",newname);
            }else if(type.equals("taskProject")){
                //防止重复
                fileName=UUID.randomUUID()+fileName;
                map.put("fileName",fileName);
                //上传任务的作品id
                map.put("taskID",String.valueOf(taskID));
                System.out.println("这里写入数据库");
                Works works = new Works();
                works.setId(uuid);
                works.setState(WorksState.PROJECT_WAIT);
                works.setTaskid(taskID);
                works.setUrl("/video/playVideo?taskid="+taskID+"&filename="+fileName+"&userid="+userId);
                works.setUserId(Long.valueOf(userId));
                works.setVideoName(fileName);
                works.setVideoDesc(desc);
                works.setVideoLength(String.valueOf(duration));
                if(worksService.addWork(works)==0){
                    return new Result().errorResult("保存失败！");
                }
            }

            // 将上传文件保存到指定目录中
            File dest = new GetFile().getFile(map);//new File(uploadDir, fileName);
            System.out.println(dest.getAbsoluteFile());
            file.transferTo(dest);
            Map<String,String> resultMap = new HashMap<>();
            resultMap.put("fileName",map.get("fileName"));
            return new Result().successResult("已收到文件并保存！", resultMap);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result().errorResult("保存失败", new HashMap<String,Object>().put("errorMsg", e));
        }
    }
}
