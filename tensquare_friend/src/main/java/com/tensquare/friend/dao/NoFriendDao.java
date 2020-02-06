package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 要子康
 * @description NoFriendDao
 * @since 2020/1/20 22:39
 */
public interface NoFriendDao extends JpaRepository<NoFriend, String> {

    public NoFriend findByUseridAndFriendid(String userid, String friendid);

}
