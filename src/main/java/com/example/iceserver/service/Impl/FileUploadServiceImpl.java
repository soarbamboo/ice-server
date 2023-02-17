package com.example.iceserver.service.Impl;

import com.example.iceserver.utils.QiniuUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Slf4j
@Service
public class FileUploadServiceImpl {
    private UploadManager uploadManager;

    private String token;

    private Auth auth;

    public void FieeFileServiceImpl() {
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
    public String uploadQiniuFile(InputStream file, String path, String filename) {
        try {
            Response res = uploadManager.put(file,path,token,null,null);
            if(!res.isOK()){
                throw new RuntimeException("七牛云上传出错" +res.toString());
            }
            DefaultPutRet putRet = new Gson().fromJson(res.bodyString(),DefaultPutRet.class);

            String  filepath = QiniuUtils.DOMAIN+ "/" + putRet.key;

            return filepath;
        }catch (QiniuException e){
            e.printStackTrace();
        }
        return "";
    }
}
