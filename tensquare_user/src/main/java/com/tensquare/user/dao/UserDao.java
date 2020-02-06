package com.tensquare.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.user.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{

    /**
     * 根据账号查询
     * @param mobile
     * @return
     */
    public User findByMobile(String mobile);

    @Modifying
    @Query(value = "update tb_user set followcount=followcount + ? where id = ? ", nativeQuery = true)
    public void updatefollowcount(int x , String userid);

    @Modifying
    @Query(value = "update tb_user set fanscount=fanscount + ? where id = ? ", nativeQuery = true)
    public void updatefanscount(int x, String friendid);

}
