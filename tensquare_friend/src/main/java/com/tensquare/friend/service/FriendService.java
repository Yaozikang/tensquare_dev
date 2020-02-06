package com.tensquare.friend.service;

import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author 要子康
 * @description FriendService
 * @since 2020/1/18 07:59
 */
@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    public int addFriend(String userid, String friendid) {
        //先判断好友表中userid到friendid方向是否有数据, 有就是有数据
        Friend friend = friendDao.findByUseridAndFriendid(userid, friendid);
        if (friend != null){
            return 0;
        }
        //直接添加好友，让好友表userid到friendid方向type为0
        friend = new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike(0);
        friendDao.save(friend);
        //判断friendid到userid方向是否有数据，如果有，把双方数据都改为1
        if (friendDao.findByUseridAndFriendid(friendid, userid) != null){
            friendDao.updateIslike(1,userid,friendid);
            friendDao.updateIslike(1,friendid,userid);
        }
        return 1;
    }

    public int addNoFriend(String userid, String friendid) {

        NoFriend noFriend = noFriendDao.findByUseridAndFriendid(userid, friendid);
        if (noFriend != null){
            return 0;
        }
        noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
        return 1;

    }

    public void deleteFriend(String userid, String friendid) {
        //删除这条数据
        friendDao.deletefriend(userid, friendid);
        //更新friendid到userid的islike为0
        friendDao.updateIslike(0, friendid, userid);
        //非好友表中添加数据
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
    }
}
