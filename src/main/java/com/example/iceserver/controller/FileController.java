package com.example.iceserver.controller;

import com.example.iceserver.common.Result;
import com.example.iceserver.service.FileService;
import com.example.iceserver.utils.StringUtils;
import com.qiniu.storage.model.DefaultPutRet;
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
public class FileController {
    @Autowired
    private FileService fileService;

    private  DefaultPutRet putRet;
    @PostMapping("/upload")
    public Result<DefaultPutRet> upload(@RequestParam("file") MultipartFile upfile){
        String fileName = upfile.getOriginalFilename();
        String voiceName = StringUtils.getRandomImgName(fileName);
        Optional.ofNullable(upfile).ifPresent((j)->{
            InputStream inputStream = null;
            try{
                inputStream = upfile.getInputStream();
            }catch (IOException e){
                throw  new RuntimeException(e);
            }
            putRet = fileService.uploadQiniuFile(inputStream,voiceName, "xixi");
        });
        log.info("-----图片地址为 ：{}",putRet.key);
        return Result.success(putRet);
    }
}
