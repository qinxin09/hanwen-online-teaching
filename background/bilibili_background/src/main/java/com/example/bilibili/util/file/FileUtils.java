package com.example.bilibili.util.file;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileUtils {
    public static boolean deleteFolder(File folder) { //testok
        if (folder == null || !folder.exists()) {
            // 如果文件夹不存在，则直接返回
            return true;
        }

        File[] files = folder.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 递归调用自身
                    deleteFolder(file);
                } else {
                    boolean result = file.delete();
                    if (result) {
                        System.out.println("删除文件：" + file.getAbsolutePath() + " 成功！");
                    } else {
                        System.err.println("删除文件：" + file.getAbsolutePath() + " 失败！");
                    }
                }
            }
        }

        boolean result = folder.delete();
        if (result) {
            System.out.println("删除目录：" + folder.getAbsolutePath() + " 成功！");
            return true;
        } else {
            System.err.println("删除目录：" + folder.getAbsolutePath() + " 失败！");
            return false;
        }
    }


    public static boolean deleteFolderStart(Map<String,String> map) {

        File dir = new GetFile().getFile(map);
        if(!dir.exists()){
            System.out.println("文件（夹）不存在");
            return true;
        }
        if(map.get("isFile")!=null){
            if(dir.isFile()){
                System.out.println("删除文件："+dir.getAbsolutePath());
                return dir.delete();
            }
        }
        return deleteFolder(dir);
//        String path=FileRoot.FILE_ROOT+"/uploads/userID"+userid+"/articles/" + articleid +"/";
//
//        return deleteFolder(new File(path));
    }

}
