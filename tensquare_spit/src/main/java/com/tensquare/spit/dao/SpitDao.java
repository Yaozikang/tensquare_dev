package com.tensquare.spit.dao;

import com.tensquare.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author 要子康
 * @description SpitDao
 * @since 2020/1/11 08:01
 */
public interface SpitDao extends MongoRepository<Spit, String> {

    /**
     * 根据上级id分页查询
     * @param parentId
     * @param pageable
     * @return
     */
    public Page<Spit> findByParentid(String parentId, Pageable pageable);
}
