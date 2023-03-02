package com.example.iceserver.dto;

import com.example.iceserver.entity.File;
import lombok.Data;

import java.util.List;

@Data
public class FileListDto  {

    private List<File> list;

    private int total;

//    private int current;

    private int pageSize;
}
