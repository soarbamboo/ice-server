package com.example.iceserver.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.iceserver.common.Result;
import com.example.iceserver.dto.FileDto;
import com.example.iceserver.entity.File;
import com.example.iceserver.service.FileService;
import com.example.iceserver.utils.QiniuUtils;
import com.example.iceserver.mapper.FileMapper;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;

@Slf4j
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {
    private UploadManager uploadManager;

    private String token;

    private Auth auth;


    private void init() {
        // 构造一个带指定Zone对象的配置类, 注意这里的Zone.zone0需要根据主机选择
        Configuration cf = new Configuration(Zone.zone1());
        uploadManager = new UploadManager(cf);
        auth = Auth.create(QiniuUtils.ASSESS_KEY ,QiniuUtils.SECRET_KEY);
        // 根据命名空间生成的上传token
        token = auth.uploadToken(QiniuUtils.BUCKET);
    }
    @Override
    public FileDto uploadQiniuFile(InputStream file, String path) {
        init();
        try {
            Response res = uploadManager.put(file,path,token,null,null);
            if(!res.isOK()){
                throw new RuntimeException("七牛云上传出错" +res.toString());
            }
            FileDto fileDto = new FileDto();
            DefaultPutRet putRet = new Gson().fromJson(res.bodyString(),DefaultPutRet.class);
            Boolean  aBoolean = saveImage(putRet);
            if(aBoolean){
                fileDto.setKey(putRet.hash);
                fileDto.setPath(QiniuUtils.DOMAIN + "/"+putRet.key);
            }
            return  fileDto;

        }catch (QiniuException e){
            e.printStackTrace();
        }
        return null;
    }

//    @Autowired
//    private FileService fileService;

    private Boolean saveImage(DefaultPutRet putRet){
        // 新建查询条件
        QueryWrapper<File> queryWrapper = new QueryWrapper<>();
        // 先判断hash的唯一性
        queryWrapper.eq("hash", putRet.hash);
        long count = this.count(queryWrapper);
        if (count >1) {
            return false;
        }
       File file = new File();
       file.setLink(QiniuUtils.DOMAIN + "/"+putRet.key);
       file.setHash(putRet.hash);
       this.save(file);
       return true;
    }
}
