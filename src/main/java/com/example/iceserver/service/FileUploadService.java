package com.example.iceserver.service;

import com.example.iceserver.entity.File;

import java.io.InputStream;

public interface FileUploadService extends File {
    public String uploadQiniuFile(InputStream file, String path, String filename);

}
