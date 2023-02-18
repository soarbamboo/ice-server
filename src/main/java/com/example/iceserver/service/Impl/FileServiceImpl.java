package com.example.iceserver.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import com.qiniu.util.StringMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {
    private UploadManager uploadManager;

    private String token;

    private Auth auth;

    public  FileServiceImpl() {
        init();
    }

    private void init() {
        // 构造一个带指定Zone对象的配置类, 注意这里的Zone.zone0需要根据主机选择
        Configuration cf = new Configuration(Zone.zone0());
        uploadManager = new UploadManager(cf);
        auth = Auth.create(QiniuUtils.ASSESS_KEY ,QiniuUtils.SECRET_KEY);
        // 根据命名空间生成的上传token
        token = auth.uploadToken(QiniuUtils.BUCKET);
        log.info("token->>::{}",token);
    }
    @Override
    public DefaultPutRet uploadQiniuFile(InputStream file, String path, String filename) {
        try {
            Response res = uploadManager.put(file,path,token,null,null);
            log.info("res::{}",res);
            if(!res.isOK()){
                throw new RuntimeException("七牛云上传出错" +res.toString());
            }
            DefaultPutRet putRet = new Gson().fromJson(res.bodyString(),DefaultPutRet.class);
            return putRet;
        }catch (QiniuException e){
            e.printStackTrace();
        }
        return null;
    }
}
