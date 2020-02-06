package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author 要子康
 * @description FriendDao
 * @since 2020/1/18 08:12
 */
public interface FriendDao extends JpaRepository<Friend, String> {

    /**
     * 联合主键查询，根据userid跟friendid查询
     * @param userid
     * @param friendid
     * @return
     */
    public Friend findByUseridAndFriendid(String userid, String friendid);

    @Modifying
    @Query(value = "UPDATE tb_friend SET islike = ? WHERE userid = ? AND friendid = ?", nativeQuery = true)
    public void updateIslike(int islike, String userid, String friendid);

    @Modifying
    @Query(value = "DELETE FROM tb_friend WHERE userid = ? AND friendid = ?", nativeQuery = true)
    public void deletefriend(String userid, String friendid);
}
