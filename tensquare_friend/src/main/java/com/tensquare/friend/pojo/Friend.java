package com.tensquare.friend.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author 要子康
 * @description Friend
 * @since 2020/1/18 08:12
 */
@Entity
@Table(name = "tb_friend")
@IdClass(Friend.class)
public class Friend implements Serializable {

    @Id
    private String userid;

    @Id
    private String friendid;

    private int islike;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFriendid() {
        return friendid;
    }

    public void setFriendid(String friendid) {
        this.friendid = friendid;
    }

    public int getIslike() {
        return islike;
    }

    public void setIslike(int islike) {
        this.islike = islike;
    }
}
