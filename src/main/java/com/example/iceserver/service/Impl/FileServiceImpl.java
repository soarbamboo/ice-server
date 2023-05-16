package com.example.iceserver.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.iceserver.dto.FileDto;
import com.example.iceserver.entity.File;
import com.example.iceserver.service.FileService;
import com.example.iceserver.utils.QiniuUtils;
import com.example.iceserver.mapper.FileMapper;
import com.example.iceserver.utils.StringUtils;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {
    private UploadManager uploadManager;

    private BucketManager bucketManager;

    private String token;

    private Auth auth;

    public FileServiceImpl() {
        init();
    }

    private void init() {
        // 构造一个带指定Zone对象的配置类, 注意这里的Zone.zone0需要根据主机选择
        Configuration cf = new Configuration(Zone.zone1());
        // 创建一个上传管理类
        uploadManager = new UploadManager(cf);
        auth = Auth.create(QiniuUtils.ASSESS_KEY ,QiniuUtils.SECRET_KEY);
        // 根据命名空间生成的上传token
        token = auth.uploadToken(QiniuUtils.BUCKET);
        // 创建一个空间管理类
        bucketManager = new BucketManager(auth,cf);
    }
    @Override
    public FileDto uploadQiniuFile(InputStream file, String path) {
        try {
            Response res = uploadManager.put(file,path,token,null,null);
            if(!res.isOK()){
                throw new RuntimeException("七牛云上传出错" +res.toString());
            }
            FileDto fileDto = new FileDto();
            DefaultPutRet putRet = new Gson().fromJson(res.bodyString(),DefaultPutRet.class);
            Boolean  aBoolean = saveFile(putRet);
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
    @Override
    public Map<String, Object> gitFileList (int current, int pageSize) {
        QueryWrapper<File> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("update_time");
        // 分页构造器
        Page<File> pageInfo = new Page<>(current,pageSize);
        // 执行查询
        this.page(pageInfo,queryWrapper);

        // 分页的数据
        List<File> data = pageInfo.getRecords();
            // 条数
        long total = pageInfo.getTotal();
        // 页数
        long pagesize = pageInfo.getPages();
        // 当前页数
        long currents = pageInfo.getCurrent();
        Map<String,Object> map = new HashMap<>();
        map.put("data",data);
        map.put("total",total);
        map.put("pageSize",pagesize);
        map.put("current",currents);
        return map;
    }


    private Boolean saveFile(DefaultPutRet putRet){
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
       file.setFileName(putRet.key);
       this.save(file);
       return true;
    }

    @Override
    public Boolean deleteQiniuFile(List<Long> keyList) {
        // 根据传入的id 查询文件名
       List<File> files =  this.listByIds(keyList);
       String[] keys = new String[keyList.size()];
        for (int i = 0; i < files.size(); i++) {
            String name = files.get(i).getFileName();
            String link = files.get(i).getLink();
            String filename= name==null ? link.replace(QiniuUtils.DOMAIN+"/",""):name;
            keys[i] =filename;
        }

        try {
            BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
            batchOperations.addDeleteOp(QiniuUtils.BUCKET, keys);
            Response response = bucketManager.batch(batchOperations);
            BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
            for (int i = 0; i < keys.length; i++) {
                BatchStatus status = batchStatusList[i];
                String key = keys[i];
                System.out.print(key + "\t");
                if (status.code == 200) {
                    System.out.println("delete success");
                    return true;
                } else {
                    System.out.println(status.data.error);
                    return false;
                }
            }
        }catch (QiniuException e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Boolean deleteFile(List<Long> ids) {
        this.removeByIds(ids);
        return null;
    }

}
