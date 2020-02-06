package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


/**
 * @author 要子康
 * @description LabelService
 * @since 2020/1/7 10:53
 */
@Service
@Transactional
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查找所有标签
     * @return
     */
    public List<Label> findAll(){
        return labelDao.findAll();
    }

    /**
     * 根据ID查找标签
     * @param labelId
     * @return
     */
    public Label findById(String labelId){
        return labelDao.findById(labelId).get();
    }

    /**
     * 保存标签
     * @param label
     */
    public void save(Label label){
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }

    /**
     * 修改标签
     * @param label
     */
    public void update(Label label){
        labelDao.save(label);
    }

    /**
     * 根据ID删除标签
     * @param labelId
     */
    public void deleteById(String labelId){
        labelDao.deleteById(labelId);
    }

    /**
     * 查找label下所有标签
     * @param label
     * @return
     */
    public List<Label> findSearch(Label label){
        return labelDao.findAll(new Specification<Label>() {

            List<Predicate> list = new ArrayList<>();

            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (label.getLabelname() != null && !"".equals(label.getLabelname())){
                    Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(predicate);
                }

                if (label.getState() != null && !"".equals(label.getState())){
                    Predicate predicate = cb.like(root.get("state").as(String.class), label.getState());
                    list.add(predicate);
                }

                Predicate[] pre = new Predicate[list.size()];
                list.toArray(pre);

                return cb.and(pre);
            }
         });
    }

    /**
     * 标签分页
     * @param label
     * @param page
     * @param size
     * @return
     */
    public Page<Label> pageQuery(Label label, int page, int size){
        Pageable pageable = PageRequest.of(page-1, size);
        return labelDao.findAll(new Specification<Label>() {
            List<Predicate> list = new ArrayList<>();

            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (label.getLabelname() != null && !"".equals(label.getLabelname())){
                    Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(predicate);
                }

                if (label.getState() != null && !"".equals(label.getState())){
                    Predicate predicate = cb.like(root.get("state").as(String.class), label.getState());
                    list.add(predicate);
                }

                Predicate[] pre = new Predicate[list.size()];
                list.toArray(pre);

                return cb.and(pre);
            }
        },pageable);
    }
}
