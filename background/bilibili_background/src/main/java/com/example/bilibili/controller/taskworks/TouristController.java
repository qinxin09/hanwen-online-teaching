package com.example.bilibili.controller.taskworks;

import com.example.bilibili.pojo.Tasks;
import com.example.bilibili.pojo.Votes;
import com.example.bilibili.pojo.Works;
import com.example.bilibili.pojo.diy.MyWorks;
import com.example.bilibili.service.task.TasksService;
import com.example.bilibili.service.task.WorksService;
import com.example.bilibili.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/tourist")
public class TouristController {
    @Autowired
    WorksService worksService;
    @Autowired
    TasksService tasksService;

    @RequestMapping("/getTasks")
    @ResponseBody
    public Result getTasks(){
        List<Tasks> tasksList = tasksService.getTasks();
        return new Result().successResult("获取任务成功",tasksList);
    }


    @RequestMapping("/getWorks")
    @ResponseBody
    public Result getWorks(){
        List<MyWorks> worksList = worksService.getActiveWorks();
        System.out.println("worksList.size()="+ worksList.size());
        if(worksList.size()>0){
            return new Result().successResult("获取作品成功",worksList);
        }
        return new Result().errorResult("获取作品失败");
    }
    @RequestMapping("/setVotes")
    @ResponseBody
    public Result setVotes(@RequestBody Votes votes){
        //1 处理失败的
        boolean allVoteMoreTimesOnSampleWorks=false;//是否允许一天中对同一个作品多次修改投票数量
        if(votes.getTouristId()==null || votes.getWorksId()==null){
            return new Result().errorResult("数据错误！",votes);
        }
        // 1.1 超过5个的
        List<Votes> touristVoteList = worksService.countTouristVotes(votes.getTouristId());

        LocalDate currentDate = LocalDate.now();
        // 创建日期格式化对象
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 将LocalDate格式化为字符串
        String dateStr = currentDate.format(formatter);

        votes.setDate(dateStr);

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("date",dateStr);

        //1.2 一天只能投一个作品
        boolean todayVote = false;
        String todayWorks="";
        int oldVoteNum=0;
        for(Votes v : touristVoteList){
            if(v.getDate().equals(dateStr)){
                if(votes.getWorksId().equals(v.getWorksId())){
                    oldVoteNum = v.getNum();//游客今天点击的第一次作品原来的投票数量
                }
                if(!allVoteMoreTimesOnSampleWorks){
                    resultMap.put("num",oldVoteNum);
                    return new Result().errorResult("投票失败！您今天已投过票了！",resultMap);
                }
                todayVote=true;
                todayWorks=v.getWorksId();
                break;
            }
        }
        if(touristVoteList.size() >= 5) {
            return new Result().errorResult("您在活动活动期间的投票次数已达上限5次！",resultMap);
        }
        resultMap.put("num",oldVoteNum);
        if( !todayVote ) {//今天，指定作品从未投过票的
            if (worksService.setVotes(votes) > 0) {
                return new Result().successResult("投票成功！",resultMap);
            }

        //今天的，同一个游客，同一个作品，是否允许修改投票票数
        }else if(allVoteMoreTimesOnSampleWorks && todayWorks.equals(votes.getWorksId())){
            if (worksService.updVotes(votes) > 0) {
                return new Result().successResult("重新投票成功！",resultMap);
            }
        }else{
            //不允许重新投票或再次为其它作品投票
            return new Result().errorResult("投票失败！您今天已投过票了！",resultMap);
        }
        return new Result().errorResult("获取作品失败",resultMap);
    }

}
