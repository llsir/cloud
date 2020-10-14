package com.boss.filesystem.controller;

import com.boss.filesystem.service.FileService;
import com.boss.framework.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: lpb
 * @create: 2020-07-21 15:54
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping("/upload")
    public ResponseResult uploadFile(@RequestParam(value = "file",required = true) MultipartFile file){
        return fileService.uploadFile(file);
    }

    @PostMapping("/download")
    public String getFile(@RequestParam(value = "fileName",required = true)String objectKey){
        return fileService.getFile(objectKey);
    }




}
