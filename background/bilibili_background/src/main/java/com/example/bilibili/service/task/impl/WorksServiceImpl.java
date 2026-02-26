package com.example.bilibili.service.task.impl;

import com.example.bilibili.dao.VotesMapper;
import com.example.bilibili.dao.WorksMapper;
import com.example.bilibili.dao.diy.MyWorksMapper;
import com.example.bilibili.pojo.Votes;
import com.example.bilibili.pojo.VotesExample;
import com.example.bilibili.pojo.Works;
import com.example.bilibili.pojo.WorksExample;
import com.example.bilibili.pojo.diy.MyWorks;
import com.example.bilibili.service.task.WorksService;
import com.example.bilibili.util.file.FileUtils;
import com.example.bilibili.util.state.WorksState;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorksServiceImpl implements WorksService {
    @Resource
    WorksMapper worksMapper;
    @Resource
    MyWorksMapper myWorksMapper;

    @Resource
    VotesMapper votesMapper;


    @Override
    public List<MyWorks> getActiveWorks() {
//        WorksExample worksExample=new WorksExample();
//        worksExample.createCriteria().andStateEqualTo(WorksState.PROJECT_PASSED);
//        return worksMapper.selectByExample(worksExample);
        List<MyWorks> myWorksList = myWorksMapper.getActiveWorks();
        System.out.println("myWorksList.size()="+ myWorksList.size());
        this.sortMyWorksList(myWorksList);
        return myWorksList;
    }

    private void sortMyWorksList(List<MyWorks> myWorksList) {
        //统计每个作品的投票总数
        for(MyWorks myWorks : myWorksList){
            int sum = 0;
            try{
                System.out.println(new ObjectMapper().writeValueAsString(myWorks.getVoteList()));
            }catch (Exception e){}
            for(Votes votes : myWorks.getVoteList()){
                sum += votes.getNum()==null ? 0 : votes.getNum();
            }
            myWorks.setSum(sum);
        }
        //按投票总数降序
        myWorksList.sort((works1,works2) -> works2.getSum() - works1.getSum());
    }

    @Override
    public List<MyWorks> getAllWorks() {
        List<MyWorks> myWorksList = myWorksMapper.getAllWorks();
        sortMyWorksList(myWorksList);
        return myWorksList;
    }

    @Override
    public int addWork(Works works) {
        return worksMapper.insert(works);
    }

    @Override
    public int updataWork(Works works) {
        return worksMapper.updateByPrimaryKey(works);
    }

    @Override
    public int setVotes(Votes votes) {
        return votesMapper.insert(votes);
    }

    @Override
    public List<Votes> getVotes(Votes votes) {
        VotesExample votesExample = new VotesExample();
        VotesExample.Criteria criteria = votesExample.createCriteria();
        criteria.andDateEqualTo(votes.getDate());
        criteria.andTouristIdEqualTo(votes.getTouristId());
        criteria.andWorksIdEqualTo(votes.getWorksId());
        return votesMapper.selectByExample(votesExample);
    }

    @Override
    public int updVotes(Votes votes) {
        VotesExample votesExample = new VotesExample();
        VotesExample.Criteria criteria = votesExample.createCriteria();
        criteria.andWorksIdEqualTo(votes.getWorksId());
        criteria.andDateEqualTo(votes.getDate());
        criteria.andTouristIdEqualTo(votes.getTouristId());
        return votesMapper.updateByExample(votes,votesExample);
    }

    @Override
    public List<Votes> countTouristVotes(String touristId) {
        VotesExample votesExample = new VotesExample();
        votesExample.createCriteria().andTouristIdEqualTo(touristId);
        return votesMapper.selectByExample(votesExample);
    }

    @Override
    public int deleteWork(Works works) {
        if(myWorksMapper.deleteWorks(works.getId())>0){
            //删除文件
            Map<String,String> map = new HashMap<>();
            map.put("type","taskProject");
            map.put("taskID",String.valueOf(works.getTaskid()));
            map.put("userId",String.valueOf(works.getUserId()));
            map.put("fileName",works.getVideoName());
            map.put("mode","read");
            map.put("isFile","true");
            Boolean file_deleted = FileUtils.deleteFolderStart(map);
            if(file_deleted){
                return 1;
            }
            System.out.print("数据库中已删除，但磁盘中的视频文件 删除失败！");//
        }
        System.out.println("删除失败！");
        return 0;
    }

}
