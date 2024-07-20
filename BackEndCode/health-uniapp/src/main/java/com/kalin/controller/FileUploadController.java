package com.kalin.controller;

import com.kalin.pojo.ao.Result;
import com.kalin.utils.AliOss;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
public class FileUploadController {
    @Autowired
    AliOss aliOss;

    @PostMapping("/uploadFile")
    public Result uploadFile(MultipartFile file) throws Exception {
        String fileUrl = "";
        String originalFilename = "";
        //把文件的内容存储到本地磁盘上
        if (file != null) {
            // 执行文件上传操作
            originalFilename = file.getOriginalFilename();
            // 其他处理逻辑
        } else {
            // 处理 multipartFile 为 null 的情况
            System.out.println("文件为空");
            return Result.error("文件为空");
        }
        //保证文件的名字是唯一的,从而防止文件覆盖
        String filename = "article/" + UUID.randomUUID() +originalFilename.substring(originalFilename.lastIndexOf("."));

        fileUrl = aliOss.uploadFile(filename,file.getInputStream());
        System.out.println(fileUrl);
        return Result.success(fileUrl);
    }
}
