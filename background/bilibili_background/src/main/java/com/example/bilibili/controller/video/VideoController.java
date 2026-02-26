package com.example.bilibili.controller.video;

import com.example.bilibili.pojo.CrawlerWithBLOBs;
import com.example.bilibili.service.crawlerVideo.VideosService;
import com.example.bilibili.util.Result;
import com.example.bilibili.util.crawler.WebHtmlCrawer;
import com.example.bilibili.util.file.FileRoot;
import com.example.bilibili.util.file.GetFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/video")
public class VideoController {
    @Autowired
    VideosService videosService;

    /**
     *
     * 播放视频
     * 在接口方法中，将本地的视频文件转换为字节流，并以InputStreamResource的形式返回给前端，使前端可以通过 HTTP 请求获取视频文件作为响应，以实现在线播放功能。
     * @param filename
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/play", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> playVideo(
            @RequestParam("filename") String filename,
            @RequestParam("authorid") Long authorid,
            @RequestParam("groupid") String groupid

    ) throws IOException {
        String filepath = FileRoot.FILE_ROOT+"/mybilibili/uploads/userID" + authorid + "/videogroup/" + groupid +"/videolist/"+filename; // 视频文件路径
        System.out.print("文件路径"+filepath);
        File video = new File(filepath);
        InputStream stream = new FileInputStream(video);
        Resource resource = new InputStreamResource(stream);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + filename)
                .body(resource);
    }
    @GetMapping(value = "/playVideo", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> playVideo(
            @RequestParam("taskid") Long taskID,
            @RequestParam("filename") String fileName,
            @RequestParam("userid") Long userID


    ) throws IOException {
        String filepath = FileRoot.FILE_ROOT+"/mybilibili/uploads/tasks/" + taskID + "/user/"+userID+"/video/" +fileName; // 视频文件路径
        System.out.print("文件路径"+filepath);
        File video = new File(filepath);
        InputStream stream = new FileInputStream(video);
        Resource resource = new InputStreamResource(stream);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + fileName)
                .body(resource);
    }

    @GetMapping ("/getPdf")
    public ResponseEntity<Resource> getPdf(
            @RequestParam("filename") String filename,
            @RequestParam("authorid") Long authorid,
            @RequestParam("groupid") String groupid,
            @RequestParam("type") String type,

            HttpServletRequest request
            ) throws IOException {
        String userId = (String) request.getAttribute("userInfo");
        Map<String,String> map=new HashMap<>();
        map.put("fileName",filename);
        map.put("type",type);//attachement
        map.put("userId",String.valueOf(authorid));
        map.put("groupid",groupid);
        map.put("mode","read");

        File file = new GetFile().getFile(map);
//        String filePath = "/path/to/your/pdf/file.pdf"; // PDF文件的路径
//        File file = new File(filePath);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=file.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    @GetMapping("/anotherVideos")
    @ResponseBody
    public Result anotherVideos(){
        List<CrawlerWithBLOBs> crawlerWithBLOBsList = videosService.getVideos();
        if (crawlerWithBLOBsList.size()>0){
            return new Result().successResult("获取第三方视频成功！",crawlerWithBLOBsList);
        }
        return new Result().errorResult("获取第三方视频失败！");
    }
    @GetMapping("/crawlerVideos")
    @ResponseBody
    public Result crawlerVideos() throws IOException {
        List<CrawlerWithBLOBs> crawlerWithBLOBsList = new WebHtmlCrawer().getVideos();
        int r = videosService.addVideos(crawlerWithBLOBsList);
        if(r>0){
            return new Result().successResult("操作成功！");
        }
        return new Result().errorResult("操作失败！");
    }

}