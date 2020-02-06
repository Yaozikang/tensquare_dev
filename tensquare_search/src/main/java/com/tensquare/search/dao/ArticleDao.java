package com.tensquare.search.dao;

import com.tensquare.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author 要子康
 * @description ArticleDao
 * @since 2020/1/13 09:25
 */
public interface ArticleDao extends ElasticsearchRepository<Article, String> {

    /**
     * 根据标题或者内容搜索
     * @param title
     * @param content
     * @param pageable
     * @return
     */
    public Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);
}
