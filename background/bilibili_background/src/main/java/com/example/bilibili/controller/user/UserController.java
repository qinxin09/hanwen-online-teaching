package com.example.bilibili.controller.user;

import com.example.bilibili.pojo.*;
import com.example.bilibili.pojo.diy.Interaction;
import com.example.bilibili.pojo.diy.VideoGroupInfo;
import com.example.bilibili.service.group.*;
import com.example.bilibili.service.user.UserService;
import com.example.bilibili.util.JsonUtil.JsonTransObj;
import com.example.bilibili.util.JsonUtil.ObjectToJson;
import com.example.bilibili.util.Result;
import com.example.bilibili.util.file.FileUtils;
import com.example.bilibili.util.redis.MyRedisKeys;
import com.example.bilibili.dao.redis.MyRedis;
import com.example.bilibili.util.state.CommentStatus;
import com.example.bilibili.util.state.VideoGroupState;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
//@CrossOrigin("http://localhost:*")
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    VideoGroupService videoGroupService;
    @Autowired
    ConcernService concernService;

    @Autowired
    CommentService commentService;

    @Autowired
    InteractionService interactionService;

    @Autowired
    LikeService likeService;

    @Autowired
    CollectService collectService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    MyRedis myRedis;



    /**
     *
     * 用户登录
     * @param user
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/loginDao")
    @ResponseBody
    public Result loginDao(@RequestBody User user) throws JsonProcessingException {
        System.out.println(new ObjectMapper().writeValueAsString(user));
        return userService.LoginDao(user);
    }

    /**
     * 用户注册
     * @param user
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/registDao")
    @ResponseBody
    public Result registDao(@RequestBody User user) throws JsonProcessingException {
        System.out.println(new ObjectMapper().writeValueAsString(user));
        return userService.RegistDao(user);
    }

    /**
     * 创建合集
     */
    @PostMapping("/addVideoGroup")
    @ResponseBody
    public Result addVideoGroup(@RequestBody VideoGroupWithBLOBs videoGroupWithBLOBs , HttpServletRequest request){
        String userId = (String) request.getAttribute("userInfo");
        System.out.println("用户id"+userId);
        System.out.println("attachementNames "+videoGroupWithBLOBs.getAttachementNames());
        videoGroupWithBLOBs.setAuthorId(Long.valueOf(userId));
        videoGroupWithBLOBs.setCreateTime(new Date());
        videoGroupWithBLOBs.setState(VideoGroupState.WAITTING);
        return videoGroupService.insertVideoGroup(videoGroupWithBLOBs);
    }

    //获取用户合集
    @GetMapping("/getVideoGroup")
    @ResponseBody
    public Result getVideoGroup(HttpServletRequest request){
        String userId = (String) request.getAttribute("userInfo");
        System.out.println("用户id"+userId);
        return videoGroupService.getVideoGroupByAuthorId(Long.valueOf(userId));
    }

    /**
     * 更新用户信息
     * @param usermap 用户信息
     * @param request
     * @return
     */
    @PostMapping("/updUser")
    @ResponseBody
    public Result updUser(@RequestBody Map<String,String> usermap , HttpServletRequest request){
        String userId = (String) request.getAttribute("userInfo");
        User user = userService.selectUserById(Long.valueOf(userId));
        Integer age = Integer.valueOf(usermap.get("age"));
        String name = usermap.get("name");
        user.setAge(age);
        user.setName(name);
        return userService.updUserById(user);
    }

    /**
     * 修改登录密码
     * @param map
     * @param request
     * @return
     */
    @PostMapping("/updPassword")
    @ResponseBody
    public Result updPassword(@RequestBody Map<String,String> map , HttpServletRequest request){
        String userId = (String) request.getAttribute("userInfo");
        System.out.println("updPassword 用户id"+userId+" password="+map.get("password"));
        if(map!=null && map.get("password")!=null){
            return userService.updPasswordById(Long.valueOf(userId),map.get("password"));
//            return new Result().successResult("收到修改密码请求",map);
        }
        return new Result().errorResult("修改密码失败！",null);

    }




    /**
     *
     * 获取合集（首页、个人中心）
     * @param map
     * @param request
     * @return
     */
    @PostMapping("/requestGroup")
    @ResponseBody
    public Result requestGroup(@RequestBody Map<String,String> map , HttpServletRequest request) throws JsonProcessingException {
        String userId = (String) request.getAttribute("userInfo");
        Boolean isSelf = false;

        //用户中心获取合集
        if(map.containsKey("byPerson")){
            isSelf = map.get("isSelf").equals("true");
            //是否查询自己的合集，确定要查询的用户id
            if(!isSelf){
                userId = map.get("userId");
            }
            System.out.println("获取用户合集");
            return videoGroupService.personalRequestGroup(Long.valueOf(userId),isSelf);

        }else if (map.containsKey("isIndex") && (String) map.get("isIndex")=="true") {  //首页获取合集
            Map<String,Object> resultMap = new HashMap<>();
            System.out.println("游客/首页里获取合集");
            Boolean isConnectRedis =false;
            if(myRedis.isRedisConnected()){
                isConnectRedis=true;
            }
            System.out.println("是否已连接到Redis："+isConnectRedis);
            if(isConnectRedis && redisTemplate.hasKey(MyRedisKeys.VIDEO_GROUP_INDEX)){
                System.out.println("！！从 redis缓存中 获取的 首页的合集数据");
                String result =(String) redisTemplate.opsForValue().get(MyRedisKeys.VIDEO_GROUP_INDEX);
                List<VideoGroupInfo> resultList = JsonTransObj.toVideoGroupInfoList(result);
                System.out.println(resultList);
                resultMap.put("indexVideoGroup",resultList);
                return new Result().successResult("已获取合集",resultMap);
            }
            System.out.println("从数据库中获取的首页合集数据");
            List<VideoGroupInfo> videoGroupInfoList = videoGroupService.getVideoGroupInfo();
            resultMap.put("indexVideoGroup",videoGroupInfoList);
            if(isConnectRedis){
                //尝试存入redis
                redisTemplate.opsForValue().set(MyRedisKeys.VIDEO_GROUP_INDEX,ObjectToJson.trans(videoGroupInfoList));
            }
            return new Result().successResult("已获取合集",resultMap);
        }

        return new Result().errorResult("获取合集失败！",map);

    }
    @RequestMapping("/selectVideoGroupInfoByCategory")
    @ResponseBody
    public Result selectVideoGroupInfoByHot(){
        Map<String,Object> resultMap = userService.selectVideoGroupInfoByCategory();
        if(resultMap!=null){
            return new Result().successResult("获取成功",resultMap);
        }
        return new Result().errorResult("获取失败");
    }

//    @DeleteMapping("/deleteGroup/{groupid}")
//    @ResponseBody
//    public Result deleteGroup(@PathVariable String groupid , HttpServletRequest request){
//        String userId = (String) request.getAttribute("userInfo");
//        System.out.println("updPassword 用户id"+userId+" groupId="+groupid);
//        return new Result().successResult("收到参数",groupid);
//    }

    /**
     *
     * 删除合集
     * @param groupid
     * @param request
     * @return
     */
    @DeleteMapping("/deleteGroup")
    @ResponseBody
    public Result deleteGroup(@RequestParam("groupid") String groupid ,HttpServletRequest request){
        String userId = (String) request.getAttribute("userInfo");

//        System.out.println("userId"+userId+" groupId="+groupid);
//        if(FileUtils.deleteFolderStart(groupid,userId)){
//            int r = videoGroupService.deleteVideoGroupById(groupid);
//            if(r>0){
//                Boolean deleteDone = myRedis.deleteByKey(redisTemplate,MyRedisKeys.VIDEO_GROUP_INDEX);
//                if(deleteDone){
//                    System.out.println("删除合集，已清除redis缓存:"+deleteDone);
//                }
//                return new Result().successResult("删除成功",null);
//            }
//
//        }
//        return new Result().errorResult("删除失败",null);
        int r = videoGroupService.deleteVideoGroupById(groupid,Long.valueOf(userId));
        if(r>0){
            Boolean deleteDone = myRedis.deleteByKey(redisTemplate,MyRedisKeys.VIDEO_GROUP_INDEX);
            if(deleteDone){
                System.out.println("删除合集，已清除redis缓存:"+deleteDone);
            }
            return new Result().successResult("删除成功",null);
        }
        return new Result().errorResult("删除失败",null);

    }

    /**
     *
     * 关注
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/concern")
    @ResponseBody
    public Result Concern(@RequestBody Map<String,Object> map, HttpServletRequest request){
        String sUserId = (String) request.getAttribute("userInfo");
        if(sUserId==null){
            return new Result().errorResult("未登录！",null);
        }
        Long userId = Long.valueOf(sUserId);
        Long authorId = Long.valueOf((Integer) map.get("authorId"));
        return concernService.doConcern(Long.valueOf(userId),authorId);
    }

    /**
     *
     * 获取用户或作者的统计信息：获赞、粉丝、关注
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/getStatistics")
    @ResponseBody
    public Result getStatistics(@RequestBody Map<String,Object> map , HttpServletRequest request){
        String sUserId = (String) request.getAttribute("userInfo");
        if(sUserId==null){
            return new Result().errorResult("未登录！",null);
        }
        Long userId = Long.valueOf(sUserId);
        Long authorId = Long.valueOf((Integer) map.get("authorId"));
        Boolean concernState = concernService.checkConcern(userId,authorId);

        Map<String,Object> resultMap = new HashMap<>();
        ArrayList<HashMap<String,Object>> statistics = new ArrayList<>();

        //获赞
        Long beLike = userService.getBeLike(authorId);
        HashMap<String,Object> zan = new HashMap<>();
        zan.put("title","获赞");
        zan.put("num",beLike);
        statistics.add(zan);

        //粉丝
        Long fensNum = userService.getFens(authorId);
        HashMap<String,Object> fens = new HashMap<>();
        fens.put("title","粉丝");
        fens.put("num",fensNum);
        statistics.add(fens);

        //关注
        Long concernNum = userService.getConcernNumById(authorId);//作者作为用户，已其id查询他的关注数量
        HashMap<String,Object> gz = new HashMap<>();
        gz.put("title","关注");
        gz.put("num",concernNum);
        statistics.add(gz);
        resultMap.put("concernState",concernState);
        resultMap.put("statistics",statistics);
        return new Result().successResult("",resultMap);
    }

    /**
     * 用户点赞
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/like")
    @ResponseBody
    public Result Like(@RequestBody Map<String,Object> map, HttpServletRequest request){
        String sUserId = (String) request.getAttribute("userInfo");
        if(sUserId==null){
            return new Result().errorResult("未登录！",null);
        }
        Long userId = Long.valueOf(sUserId);
        String groupId = (String) map.get("groupId");
        if(userId==null){
            return new Result().errorResult("获取用户信息失败，请重新登录！",null);
        }
        MyLikeKey likeKey = likeService.getLikeById(userId,groupId);
        Boolean liked = likeKey!=null;
        Map<String,Object> map2 = new HashMap<>();
        //查询
        System.out.println(map.get("onlySelect")+"       "+(Boolean)map.get("onlySelect"));
        if(map.get("onlySelect")!=null && (Boolean)map.get("onlySelect")){
            map2.put("isLiked",liked);
            map2.put("count",likeService.countByGroupId(groupId));
            return new Result().successResult("查询结果",map2);
        }
        //点过赞的取消
        if(liked){
            if(likeService.delLikeById(likeKey)>0){
                map2.put("isLiked",!liked);
                map2.put("count",likeService.countByGroupId(groupId));
                return new Result().successResult("取消赞",map2);
            }
        }else{
            //写入赞
            if(likeService.insertLike(userId,groupId) > 0){
                map2.put("isLiked",!liked);
                map2.put("count",likeService.countByGroupId(groupId));
                return new Result().successResult("赞",map2);
            }
        }
        return new Result().errorResult("操作失败",null);

    }

    /**
     * 用户收藏操作
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/collect")
    @ResponseBody
    public Result Collect(@RequestBody Map<String,Object> map, HttpServletRequest request){
        String sUserId = (String) request.getAttribute("userInfo");
        if(sUserId==null){
            return new Result().errorResult("未登录！",null);
        }
        Long userId = Long.valueOf(sUserId);
        String groupId = (String) map.get("groupId");
        if(userId==null){
            return new Result().errorResult("获取用户信息失败，请重新登录！",null);
        }
        MyCollectKey myCollectKey = collectService.getCollectById(userId,groupId);
        Boolean collected = myCollectKey!=null;
        Map<String,Object> map2 = new HashMap<>();
        //查询
        System.out.println(map.get("onlySelect")+"       "+(Boolean)map.get("onlySelect"));
        if(map.get("onlySelect")!=null && (Boolean)map.get("onlySelect")){
            map2.put("isCollected",collected);
            map2.put("count",collectService.countByGroupId(groupId));
            return new Result().successResult("查询结果",map2);
        }
        //点过收藏的取消
        if(collected){
            if(collectService.delCollectById(myCollectKey)>0){
                map2.put("isLiked",!collected);
                map2.put("count",collectService.countByGroupId(groupId));
                return new Result().successResult("取消收藏",map2);
            }
        }else{
            //写入收藏
            if(collectService.insertCollect(userId,groupId) > 0){
                map2.put("isCollected",!collected);
                map2.put("count",collectService.countByGroupId(groupId));
                return new Result().successResult("收藏",map2);
            }
        }
        return new Result().errorResult("操作失败",null);

    }


    /**
     *
     * 播放视频页面获取评论信息以及是否已关注该用户
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/getInteraction")
    @ResponseBody
    public Result getInteraction(@RequestBody Map<String,Object> map, HttpServletRequest request){
        String sUserId = (String) request.getAttribute("userInfo");
        if(sUserId==null){
            return new Result().errorResult("未登录！",null);
        }
        Long userId = Long.valueOf(sUserId);

        String groupId = (String) map.get("groupid");
        Long authorId = Long.valueOf((Integer) map.get("authorid"));
        Interaction interaction = interactionService.getInteraction(userId,authorId,groupId);
        return new Result().successResult("获取interaction",interaction);
    }

    /**
     *
     * 用户提交评论
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/sendComment")
    @ResponseBody
    public Result sendCommend(@RequestBody Map<String,Object> map,HttpServletRequest request){
        String userId = (String) request.getAttribute("userInfo");
        String groupId = (String) map.get("groupId");
        String content = (String) map.get("content");
        map.put("userId",userId);
        Comment comment = new Comment();
        comment.setUserId(Long.valueOf(userId));
        comment.setGroupId(groupId);
        comment.setContent(content);
        comment.setStatus(CommentStatus.WAITTING);
        comment.setCreateTime(new Date());
        return commentService.sendComment(comment);
    }

    /**
     *
     * 作者详情页，获取作者信息
     * @param map
     * @return
     */
    @RequestMapping("/getAuthor")
    @ResponseBody
    public Result getAuthor(@RequestBody  Map<String,Object> map){
        Long authorId = Long.valueOf((Integer) map.get("authorId"));
        if(authorId!=null){
            User user = userService.selectUserById(authorId);
            if(user!=null){
                user.setPassword(null);//清空密码
                return new Result().successResult("获取作者信息成功！",user);
            }else{
                return new Result().errorResult("没有查询到作者信息：",user);
            }
        }
        return new Result().errorResult("获取作者信息失败！",map);
    }

    @RequestMapping("/addPlayCount")
    @ResponseBody
    public Result addPlayCount(@RequestBody Map<String,Object> map,HttpServletRequest request) {
        String userId = (String) request.getAttribute("userInfo");
        String groupId = (String) map.get("groupId");
        int num = 1;//增加一次播放量
        if(videoGroupService.addPlayCount(groupId,num)>0){
            return new Result().successResult("success","addPlayCount success");
        }
        return new Result().errorResult("error","addPlayCount faild");
    }

}
