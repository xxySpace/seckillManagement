package com.xxy.seckill.seckillmanagement.service;

import com.xxy.seckill.seckillmanagement.service.model.UserModel;

/**
 * @ClassName: UserService
 * @Description: 用户服务接口
 * @Author: 13688
 * @Date: 2019/4/28 22:10
 * @Version: v1.0
 **/
public interface UserService {
    /**
     * 通过用户id获取用户对象的方法
     *
     * @param id
     */
    UserModel getUserById(Integer id);
}
