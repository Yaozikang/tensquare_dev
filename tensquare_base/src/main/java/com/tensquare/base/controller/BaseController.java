package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 要子康
 * @description BaseController
 * @since 2020/1/7 10:23
 */
@RestController
@CrossOrigin
@RequestMapping("/label")
public class BaseController {

    @Autowired
    private LabelService labelService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 查找所有标签
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        String header = request.getHeader("Authorization");

        return new Result(true, StatusCode.OK, "查询成功", labelService.findAll());
    }

    /**
     * 查找单个标签
     * @param labelId
     * @return
     */
    @RequestMapping(value = "/{labelId}", method = RequestMethod.GET)
    public Result findById(@PathVariable("labelId") String labelId){
        return new Result(true, StatusCode.OK, "查询成功", labelService.findById(labelId));
    }

    /**
     * 添加标签
     * @param label
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Label label){
        labelService.save(label);
        return new Result(true, StatusCode.OK, "保存成功");
    }

    /**
     * 修改标签
     * @param labelId
     * @param label
     * @return
     */
    @RequestMapping(value = "/{labelId}", method = RequestMethod.PUT)
    public Result update(@PathVariable("labelId") String labelId,
                         @RequestBody Label label){
        label.setId(labelId);
        labelService.update(label);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除标签
     * @param labelId
     * @return
     */
    @RequestMapping(value = "/{labelId}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String labelId){
        labelService.deleteById(labelId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 查找labelo下所有标签
     * @param label
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Label label){
        List<Label> list = labelService.findSearch(label);
        return new Result(true, StatusCode.OK,"查询成功", list);
    }

    /**
     * 标签分页
     * @param label
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result pageQuery(@RequestBody Label label, @PathVariable int page, @PathVariable int size){
        Page<Label> pageData = labelService.pageQuery(label, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Label>(pageData.getTotalElements(), pageData.getContent()));
    }
}
