package com.example.iceserver.utils;

import cn.hutool.core.lang.UUID;
import com.example.iceserver.entity.Qiniu;
import com.example.iceserver.service.FileService;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;


@Data
public class QiniuUtils {
    public static final String ASSESS_KEY = "64S76RyQxSFey1dxvI6SytG8d9wRqf_WNfEHCdws";
    public static final String SECRET_KEY = "SQyZUiWmofP8cY_Fq5oht-mXlzCrPYHK8NwzsQia";
    public static final String BUCKET = "lmice";
    public static final String DOMAIN = "http://img.netbugs.cn";


    private final FileService fileService;

    public QiniuUtils(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * 获取七牛云上传token
     * @return
     */
    public static Qiniu getToken(){
        Qiniu qiNiu = new Qiniu();
        long expireSeconds = 600;   //过期时间
        StringMap putPolicy = new StringMap();
        Auth auth = Auth.create(ASSESS_KEY, SECRET_KEY);
        String upToken = auth.uploadToken(BUCKET,null, expireSeconds,putPolicy);
        qiNiu.setKey(UUID.randomUUID().toString().replaceAll("\\-", ""));
        qiNiu.setToken(upToken);
        return  qiNiu;
    }

    /**
     * 判断文件是否存在,并上传到服务器返回访问路径
     * @param file
     * @param filodername
     * @return
     */
    public Object fileEquals(MultipartFile file, String filodername){
        if(!file.isEmpty()){
            String fileName=file.getOriginalFilename();
            InputStream inputStream= null;
            try {
                inputStream = file.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Object putRet =fileService.uploadQiniuFile(inputStream,fileName);
            return putRet;
        }
        return null;
    }
}
