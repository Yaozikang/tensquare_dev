package com.tensquare.friend.controller;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 要子康
 * @description FriendController
 * @since 2020/1/18 07:49
 */
@CrossOrigin
@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserClient userClient;

    /**
     * 添加好友或非好友
     * @param friendid
     * @param type
     * @return
     */
    @RequestMapping(value = "/like/{friendid}/{type}", method = RequestMethod.PUT)
    public Result addFriend(@PathVariable("friendid") String friendid,
                            @PathVariable("type") String type){
        //验证是否登陆，并拿到当前登陆用户的id
        Claims claims = (Claims) request.getAttribute("claims_user");
        if (claims == null){
            return new Result(false, StatusCode.LOGINERROR, "权限不足");
        }
        //得到当前登陆用户的id
        String userid = claims.getId();

        //判断是否是添加好友还是添加非好友
        if (type != null){
            if (type.equals("1")){
                //添加好友
                int flag = friendService.addFriend(userid, friendid);
                if (flag == 0){
                    return new Result(false, StatusCode.ERROR, "不能重复添加好友");
                }
                if (flag == 1){
                    userClient.updatefanscountandfollowcount(userid, friendid, 1);
                    return new Result(true, StatusCode.OK, "添加成功");
                }
            }else if (type.equals("2")){
                //添加非好友
                int flag = friendService.addNoFriend(userid, friendid);
                if (flag == 0){
                    return new Result(false, StatusCode.ERROR, "不能重复添加非好友");
                }
                if (flag == 1){
                    return new Result(true, StatusCode.OK, "添加成功");
                }
            }
            return new Result(false, StatusCode.ERROR, "参数异常");
    }
        return new Result(false, StatusCode.ERROR, "参数异常");

    }

    /**
     * 删除用户
     * @param friendid
     * @return
     */
    @RequestMapping(value = "/{friendid}", method = RequestMethod.DELETE)
    public Result deleteFriend(@PathVariable("friendid")String friendid){
        //验证是否登陆，并拿到当前登陆用户的id
        Claims claims = (Claims) request.getAttribute("claims_user");
        if (claims == null){
            return new Result(false, StatusCode.LOGINERROR, "权限不足");
        }
        //得到当前登陆用户的id
        String userid = claims.getId();
        friendService.deleteFriend(userid, friendid);
        userClient.updatefanscountandfollowcount(userid, friendid, -1);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
