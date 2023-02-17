package com.example.iceserver.controller;

import com.example.iceserver.common.Result;
import com.example.iceserver.service.FileUploadService;
import com.example.iceserver.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/file")
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;

    String path = "";
    @PostMapping("/upload")
    public Result<Object> upload(@RequestParam("upfile") MultipartFile upfile){
        String fileName = upfile.getOriginalFilename();
        String voiceName = StringUtils.getRandomImgName(fileName);
        Optional.ofNullable(upfile).ifPresent((j)->{
            InputStream inputStream = null;
            try{
                inputStream = upfile.getInputStream();
            }catch (IOException e){
                throw  new RuntimeException(e);
            }
            path = fileUploadService.uploadQiniuFile(inputStream,voiceName, "xixi");
        });
        log.info("-----图片地址为 ：{}",path);
        return Result.success(path);
    }
}
