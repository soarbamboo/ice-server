package com.example.iceserver.controller;


import com.example.iceserver.common.Result;
import com.example.iceserver.dto.FileDto;
import com.example.iceserver.entity.Article;
import com.example.iceserver.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/article")
public class ArticleController {


    @Autowired
    private ArticleService articleService;

    /**
     * 根据ID 来查询文章详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<String> get(@PathVariable Long id){
        Article article = articleService.getArticleById(id);
        return  Result.success("成功");
    }

    /**
     * 保存文章并且保存文章与标签对应关系
     * @param article
     * @return
     */
    @PostMapping("/save")
    public Result<String> saveArticleAndTag(@RequestBody Article article){
        articleService.saveArticleAndTag(article);
        return Result.success("新增文章成功");
    }



}
