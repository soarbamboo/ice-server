package com.example.iceserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.iceserver.entity.Article;

public interface ArticleService extends IService<Article> {


    // 根据ID查询 文章详情
    public Article getArticleById(Long id);

    // 保存文章 以及 文章与标签对应关系
    public void saveArticleAndTag(Article article);
}
