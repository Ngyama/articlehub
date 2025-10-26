package com.ngymkana.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ngymkana.pojo.Result;
import com.ngymkana.utils.AliOssUtil;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws Exception {
        // Store file content to local disk
        String originalFilename = file.getOriginalFilename();
        // Ensure unique filename to prevent overwriting
        String filename = UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
        //file.transferTo(new File("C:\\Users\\Administrator\\Desktop\\files\\"+filename));
        String url = aliOssUtil.uploadFile(filename,file.getInputStream());
        return Result.success(url);
    }
}
