package com.tensquare.article.controller;

import com.tensquare.article.pojo.Comment;
import com.tensquare.article.service.CommentService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 要子康
 * @description CommentController
 * @since 2020/1/11 15:57
 */
@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 添加文章评论
     * @param comment
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result addComment(@RequestBody Comment comment){
        commentService.addComment(comment);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /**
     * 查询文章评论
     * @param articleId
     * @return
     */
    @RequestMapping(value = "/article/{articleId}", method = RequestMethod.GET)
    public Result findByArticleid(@PathVariable String articleId){
        return  new Result(true, StatusCode.OK,"查询成功",commentService.findByAritcleId(articleId));
    }
}
