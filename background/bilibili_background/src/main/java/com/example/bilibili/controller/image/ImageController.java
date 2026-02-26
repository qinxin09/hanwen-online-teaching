package com.example.bilibili.controller.image;

import com.example.bilibili.util.file.GetFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/image")
public class ImageController {
    @RequestMapping("/getImg")
    public void getImg(String userid , String groupid ,String fileName,  String type, HttpServletResponse response){
//        System.out.println("save img");
//        System.out.println(String.format("userid=%s  groupid=%s type=%s",userid,groupid,type));
        InputStream fis = null;
        OutputStream os = null;

        //设置默认封面
        String defaultName="";

        Map<String,String> map = new HashMap<String,String>();
        map.put("type",type);
        map.put("groupid",groupid);
        map.put("userId",userid);
        map.put("mode","read");
        map.put("fileName",fileName);

        try {
            String tempPath="";
            if(type.equals("image")){//请求的是文章附带图片时
                defaultName="./defaultImg.jpg";//默认封面
            }else if(type.equals("icon")){//请求的是用户头像时
                defaultName="./defaultUserIcon.png";//默认头像
            }

            File file=new GetFile().getFile(map);

            System.out.println("访问图像路径："+file.getPath());
            if(!file.exists()){
                // 未找到该路径则使用项目中预定义的一张默认图片
                System.out.println("此图片路径不存在，使用默认的图片");
                URI mPath= ClassLoader.getSystemClassLoader().getResource(defaultName).toURI();
                file=new File(mPath);
            }

            fis = new FileInputStream(file);//服务器本地图片地址
            os = response.getOutputStream();

            // 将图片文件通过字节输入输出流发送给前端
            int count = 0;
            byte[] buffer = new byte[1024 * 8];
            while ((count = fis.read(buffer)) != -1) {//将输入流中的数据拷贝到输出流中
                os.write(buffer, 0, count);
                os.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 在finally块中关闭输入输出流避免内存泄漏。
            try {
                if(fis != null){
                    fis.close();
                }
                if(os != null){
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
