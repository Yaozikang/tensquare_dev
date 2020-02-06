package com.tensquare.search.controller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author 要子康
 * @description ArticleController
 * @since 2020/1/13 09:26
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 文章添加
     * @param article
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result saveArticle(@RequestBody Article article){
        articleService.save(article);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @RequestMapping(value = "/{key}/{page}/{size}", method = RequestMethod.GET)
    public Result findByKey(@PathVariable String key,
                            @PathVariable int page,
                            @PathVariable int size){
        Page<Article> articleData =  articleService.findByKey(key, page, size);
        return new Result(true, StatusCode.OK, "查找成功",
                new PageResult<Article>(articleData.getTotalElements(), articleData.getContent()));
    }


}
