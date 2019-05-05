package com.xxy.seckill.seckillmanagement.service;

import com.xxy.seckill.seckillmanagement.error.BusinessException;
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

    /**
     * 用户注册
     *
     * @param userModel
     */
    void register(UserModel userModel) throws BusinessException;

    /**
     * 校验用户端登录是否合法
     *
     * @param telphone       用户注册手机号
     * @param encrptPassword 用户加密后的密码
     */
    UserModel validateLogin(String telphone, String encrptPassword) throws BusinessException;
}
