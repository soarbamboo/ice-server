package com.example.iceserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.iceserver.entity.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
