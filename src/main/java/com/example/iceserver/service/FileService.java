package com.example.iceserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.iceserver.dto.FileDto;
import com.example.iceserver.entity.File;

import java.io.InputStream;
import java.util.Map;

public interface FileService extends IService<File> {
    public FileDto uploadQiniuFile(InputStream file, String path);

    public Map<String, Object> gitFileList(int current, int pageSize);

    public Boolean deleteQiniuFile(String[] keyList);

    public Boolean deleteFile(String[] ids);
}
