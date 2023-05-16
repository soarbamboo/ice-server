package com.example.iceserver.controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.iceserver.common.Result;
import com.example.iceserver.dto.FileDto;
import com.example.iceserver.dto.FileListDto;
import com.example.iceserver.entity.Article;
import com.example.iceserver.entity.File;
import com.example.iceserver.service.FileService;
import com.example.iceserver.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/file")
public class FileController {
    @Autowired
    private FileService fileService;

    /**
     * 上传图片
     * @param upfile
     * @return
     */
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


    /**
     * 分页获取图片列表
     * @param current
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    public Result<Map<String, Object> > getList(@RequestParam(name = "current", defaultValue = "1") int current,
                                @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ){
        Map<String, Object>  pageInfo = fileService.gitFileList(current,pageSize);
        return  Result.success(pageInfo);
    }

    @PostMapping ("/remove")
    public Result<Map<String,Object>> deleteFile(@RequestBody Map parmas){
        List<Long> idList = (List<Long>) parmas.get("ids");
        if(idList.size() > 100){
            return  Result.error(400,"删除失败！单次删除不得超过100条！");
        }
        Boolean result = fileService.deleteQiniuFile(idList);
        if(!result){
            return  Result.error(400,"删除失败！");
        }
        fileService.deleteFile(idList);
        Map<String,Object> map = new HashMap<>();
        map.put("msg","删除成功");
        return  Result.success(map);
    }

}
