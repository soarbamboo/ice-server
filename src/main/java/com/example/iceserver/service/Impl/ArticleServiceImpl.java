package com.example.iceserver.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.iceserver.entity.Article;
import com.example.iceserver.mapper.ArticleMapper;
import com.example.iceserver.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    /**
     * 根据ID 来查询文章详情
     * @param id
     * @return
     */
    @Override
    public Article getArticleById(Long id) {
        return null;
    }

    /**
     * 保存文章并且保存文章与标签对应关系
     * @param article
     */
    @Override
    public void saveArticleAndTag(Article article){
        return ;
    }
}
