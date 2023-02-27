package com.example.iceserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.iceserver.entity.File;
import com.qiniu.storage.model.DefaultPutRet;

import java.io.InputStream;

public interface FileService extends IService<File> {
    public DefaultPutRet uploadQiniuFile(InputStream file, String path);

}
