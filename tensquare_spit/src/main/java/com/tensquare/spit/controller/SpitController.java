package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import util.IdWorker;

import java.util.Date;
import java.util.List;

/**
 * @author 要子康
 * @description SpitController
 * @since 2020/1/11 08:10
 */
@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IdWorker idWorker;

    /**
     * 添加吐槽
     * @param spit
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result addSpit(@RequestBody Spit spit){
        spit.set_id(idWorker.nextId()+"");
        spit.setPublishtime(new Date());
        spit.setVisits(0);
        spit.setThumbup(0);
        spit.setShare(0);
        spit.setComment(0);
        spit.setState("1");

        if (spit.getParentid() != null && !"".equals(spit.getParentid())){
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query, update, "spit");
        }

        spitService.save(spit);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /**
     * 查找所有吐槽
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        List<Spit> list = spitService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    /**
     * 查找单个吐槽
     * @param spitId
     * @return
     */
    @RequestMapping(value = "/{spitId}")
    public Result findOne(@PathVariable String spitId){
        return new Result(true, StatusCode.OK, "查询成功", spitService.findById(spitId));
    }

    /**
     * 删除单个吐槽
     * @param spitId
     * @return
     */
    @RequestMapping(value = "/{spitId}", method = RequestMethod.DELETE)
    public Result deleteOne(@PathVariable String spitId){
        spitService.deleteById(spitId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 更新吐槽
     * @param spitId
     * @param spit
     * @return
     */
    @RequestMapping(value = "/{spitId}", method = RequestMethod.PUT)
    public Result updateOne(@PathVariable String spitId, @RequestBody Spit spit){
        spit.set_id(spitId);
        spitService.update(spit);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 根据上级ID 查询吐槽列表
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/comment/{parentid}/{page}/{size}", method = RequestMethod.GET)
    public Result findByParentId(@PathVariable String parentid,
                                 @PathVariable int page,
                                 @PathVariable int size){
        Page<Spit> pageList = spitService.findByParentId(parentid, page, size);
        return new Result(true, StatusCode.OK, "查找成功",
                new PageResult<Spit>(pageList.getTotalElements(),pageList.getContent()));
    }

    /**
     * 吐槽点赞
     * @param spitId
     * @return
     */
    @RequestMapping(value = "/thumbup/{spitId}", method = RequestMethod.PUT)
    public Result updateThumbup(@PathVariable String spitId){

        String userid = "123";
        if (redisTemplate.opsForValue().get("thumbup_" + userid) != null){
            return new Result(false, StatusCode.REPERROR, "您已经点过赞了");
        }

        spitService.updateThumbup(spitId);
        redisTemplate.opsForValue().set("thumbup_" + userid, 1);
        return new Result(true, StatusCode.OK, "点赞成功");
    }

}
