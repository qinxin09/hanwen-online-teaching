package com.example.bilibili.controller.download;

import com.example.bilibili.service.user.UserService;
import com.example.bilibili.util.Result;
import com.example.bilibili.util.file.GetFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

@Controller
@RequestMapping("/download")
public class DownLoadController {
    @Autowired
    UserService userService;
    @RequestMapping("/file")
    @ResponseBody
    public void downloadFile(@RequestBody Map<String,String> map, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = (String) request.getAttribute("userInfo");
        if(userId!=null){
            //检查该用户是否为会员(已在本网站注册过账号的用户）
            if(userService.checkVip(Long.valueOf(userId))){
                map.put("userId",(String)map.get("authorId"));
                map.put("mode","read");
                File file = new GetFile().getFile(map);
                if(file.exists()){
                    InputStream inputStream=new FileInputStream(file);
                    response.setContentType("application/octet-stream");
                    response.setHeader("Content-Disposition", "attachment; filename=" + map.get("fileName"));
                    OutputStream outputStream= response.getOutputStream();
                    try{
                        int count=0;
                        byte[] buffer = new byte[1024 * 8];
                        while((count = inputStream.read(buffer)) !=-1 ){
                            outputStream.write(buffer,0,count);
                            outputStream.flush();
                        }
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } finally {
                        if(inputStream!=null){
                            inputStream.close();
                        }
                        if (outputStream!=null){
                            outputStream.close();
                        }

                    }

                }
            }
        }
    }
}
