package com.example.iceserver.controller;

import com.example.iceserver.common.Result;
import com.example.iceserver.dto.FileDto;
import com.example.iceserver.service.FileService;
import com.example.iceserver.utils.QiniuUtils;
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

    @PostMapping("/upload")
    public Result<FileDto> upload(@RequestParam("file") MultipartFile upfile ){
        String fileName = upfile.getOriginalFilename();
        String voiceName = StringUtils.getRandomImgName(fileName);
        final FileDto[] fileDto = {new FileDto()};
        Optional.ofNullable(upfile).ifPresent((j)->{
            InputStream inputStream = null;
            try{
                inputStream = upfile.getInputStream();
            }catch (IOException e){
                throw  new RuntimeException(e);
            }finally {
                fileDto[0] = fileService.uploadQiniuFile(inputStream,  voiceName);
            }
        });
        if (fileDto[0].getKey() == null | fileDto[0].getPath() == null){
            return  Result.error(400,"上传失败或当前文件已存在");
        }
        return Result.success(fileDto[0]);
    }
}
