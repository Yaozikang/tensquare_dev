package com.tensquare.article.dao;

import com.tensquare.article.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author 要子康
 * @description CommentDao
 * @since 2020/1/11 15:45
 */
public interface CommentDao extends MongoRepository<Comment, String >{

    /**
     * 根据id查询文章评论
     * @param articleId
     * @return
     */
    public List<Comment> findByArticleid(String articleId);
}
