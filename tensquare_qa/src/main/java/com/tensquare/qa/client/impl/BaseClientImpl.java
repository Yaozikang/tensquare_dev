package com.tensquare.qa.client.impl;

import com.tensquare.qa.client.BaseClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

/**
 * @author 要子康
 * @description BaseClientImpl
 * @since 2020/1/29 11:50
 */
@Component
public class BaseClientImpl implements BaseClient {
    @Override
    public Result findById(String labelId) {
        return new Result(false, StatusCode.ERROR,"熔断器启动了");
    }
}
