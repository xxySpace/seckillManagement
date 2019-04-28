package com.xxy.seckill.seckillmanagement.service.impl;

import com.xxy.seckill.seckillmanagement.dao.UserDAOMapper;
import com.xxy.seckill.seckillmanagement.dao.UserPasswordDAOMapper;
import com.xxy.seckill.seckillmanagement.dataobject.UserDAO;
import com.xxy.seckill.seckillmanagement.dataobject.UserPasswordDAO;
import com.xxy.seckill.seckillmanagement.service.UserService;
import com.xxy.seckill.seckillmanagement.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * @ClassName: UserServiceImpl
 * @Description: 用户服务实现
 * @Author: 13688
 * @Date: 2019/4/28 22:10
 * @Version: v1.0
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAOMapper userDAOMapper;

    @Autowired
    private UserPasswordDAOMapper userPasswordDAOMapper;

    @Override
    public UserModel getUserById(Integer id) {
        //调用UserDAOMapper获取对应的用户dataObject
        UserDAO userDAO = userDAOMapper.selectByPrimaryKey(id);

        if (null == userDAO) {
            return null;
        }
        //通过用户id获取对应的用户加密密码信息
        UserPasswordDAO userPasswordDAO = userPasswordDAOMapper.selectByUserId(userDAO.getId());

        return convertFromDataObject(userDAO, userPasswordDAO);
    }

    private UserModel convertFromDataObject(UserDAO userDAO, UserPasswordDAO userPasswordDAO) {
        if (null == userDAO) {
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDAO, userModel);
        if (null != userPasswordDAO) {
            userModel.setEncrptPassword(userPasswordDAO.getEncrptPassword());
        }

        return userModel;
    }
}
