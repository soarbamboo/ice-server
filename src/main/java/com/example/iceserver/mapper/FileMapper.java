package com.example.iceserver.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.iceserver.entity.File;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper extends BaseMapper<File> {
}
