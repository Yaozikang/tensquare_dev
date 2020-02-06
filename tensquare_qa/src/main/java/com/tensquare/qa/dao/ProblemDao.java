package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    /**
     * 根据标签ID查询最新问题列表
     * @param labelId
     * @param pageable
     * @return
     */
    @Query(value = "SELECT * FROM tb_problem, tb_pl WHERE" +
            " tb_problem.id = tb_pl.problemid AND labelid = ? ORDER BY tb_problem.replytime DESC", nativeQuery = true)
    public Page<Problem> newList(String labelId, Pageable pageable);

    /**
     * 根据标签ID查询热门回答列表
     * @param labelId
     * @param pageable
     * @return
     */
    @Query(value = "SELECT * FROM tb_problem, tb_pl WHERE" +
            " tb_problem.id = tb_pl.problemid AND labelid = ? ORDER BY tb_problem.reply DESC", nativeQuery = true)
    public Page<Problem> hotList(String labelId, Pageable pageable);

    /**
     * 根据标签ID查询未回复回答列表
     * @param labelId
     * @param pageable
     * @return
     */
    @Query(value = "SELECT * FROM tb_problem, tb_pl WHERE" +
            " tb_problem.id = tb_pl.problemid AND labelid = ? AND tb_problem.reply = 0 ORDER BY tb_problem.createtime DESC", nativeQuery = true)
    public Page<Problem> waitList(String labelId, Pageable pageable);
}
