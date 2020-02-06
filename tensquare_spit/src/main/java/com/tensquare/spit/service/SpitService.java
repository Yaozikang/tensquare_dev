package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.List;

/**
 * @author 要子康
 * @description SpitService
 * @since 2020/1/11 08:02
 */
@Service
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 查询所有吐槽
     * @return
     */
    public List<Spit> findAll(){
        return spitDao.findAll();
    }

    /**
     * 根据ID查询吐槽
     * @param spitId
     * @return
     */
    public Spit findById(String spitId){
        return spitDao.findById(spitId).get();
    }

    /**
     * 添加吐槽
     * @param spit
     */
    public void save(Spit spit){
        spit.set_id(idWorker.nextId()+"");
        spitDao.save(spit);
    }

    /**
     * 更新吐槽
     * @param spit
     */
    public void update(Spit spit){
        spitDao.save(spit);
    }

    /**
     * 根据ID删除吐槽
     * @param spitId
     */
    public void  deleteById(String spitId){
        spitDao.deleteById(spitId);
    }

    /**
     * 根据上级Id分页查询吐槽
     * @param parentId
     * @param page
     * @param size
     * @return
     */
    public Page<Spit> findByParentId(String parentId, int page, int size){
        Pageable pageable = PageRequest.of(page -1 , size);
        return spitDao.findByParentid(parentId, pageable);
    }

    /**
     * 点赞
     * @param spitId
     */
    public void updateThumbup(String spitId){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update = new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query, update, "spit");
    }
}
