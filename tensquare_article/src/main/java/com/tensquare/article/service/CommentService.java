package com.tensquare.article.service;

import com.tensquare.article.dao.CommentDao;
import com.tensquare.article.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.List;

/**
 * @author 要子康
 * @description CommentService
 * @since 2020/1/11 15:52
 */
@Service
public class CommentService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private CommentDao commentDao;

    /**
     * 添加评论
     * @param comment
     */
    public void addComment(Comment comment){
        comment.set_id(idWorker + "");
        commentDao.save(comment);
    }

    public void deleteComment(String commentId){
        commentDao.deleteById(commentId);
    }

    /**
     * 根据文章ID查询评论
     * @param articleId
     * @return
     */
    public List<Comment> findByAritcleId(String articleId){
        return commentDao.findByArticleid(articleId);
    }
}
