package com.tensquare.search.service;

import com.tensquare.search.dao.ArticleDao;
import com.tensquare.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import util.IdWorker;

/**
 * @author 要子康
 * @description ArticleService
 * @since 2020/1/13 09:26
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 增加文章
     * @param article
     */
    public void save(Article article){
        article.setId(idWorker.nextId() + "");
        articleDao.save(article);
    }

    /**
     * 搜索文章
     * @param key
     * @param page
     * @param size
     * @return
     */
    public Page<Article> findByKey(String key, int page, int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        return articleDao.findByTitleOrContentLike(key, key, pageable);
    }

}
