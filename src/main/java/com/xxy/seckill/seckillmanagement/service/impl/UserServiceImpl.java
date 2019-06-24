package com.xxy.seckill.seckillmanagement.service.impl;

import com.xxy.seckill.seckillmanagement.dao.UserDAOMapper;
import com.xxy.seckill.seckillmanagement.dao.UserPasswordDAOMapper;
import com.xxy.seckill.seckillmanagement.dataobject.UserDAO;
import com.xxy.seckill.seckillmanagement.dataobject.UserPasswordDAO;
import com.xxy.seckill.seckillmanagement.error.BusinessException;
import com.xxy.seckill.seckillmanagement.error.EmBusinessError;
import com.xxy.seckill.seckillmanagement.service.UserService;
import com.xxy.seckill.seckillmanagement.service.model.UserModel;
import com.xxy.seckill.seckillmanagement.validator.ValidationResult;
import com.xxy.seckill.seckillmanagement.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private ValidatorImpl validator;

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

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if (null == userModel) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        ValidationResult result = validator.validate(userModel);
        if (result.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        //实现model -> dataObject方法
        UserDAO userDAO = convertFromModel(userModel);
        try {
            userDAOMapper.insertSelective(userDAO);
        } catch (DuplicateKeyException ex) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "该手机号已注册，不能重复注册");
        }

        userModel.setId(userDAO.getId());

        UserPasswordDAO userPasswordDAO = convertPasswordFromModel(userModel);
        userPasswordDAOMapper.insertSelective(userPasswordDAO);
        return;
    }

    @Override
    public UserModel validateLogin(String telphone, String encrptPassword) throws BusinessException {
        //通过用户手机号获取用户信息
        UserDAO userDAO = userDAOMapper.selectByTelphone(telphone);
        if (null == userDAO) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        UserPasswordDAO userPasswordDAO = userPasswordDAOMapper.selectByUserId(userDAO.getId());
        UserModel userModel = convertFromDataObject(userDAO, userPasswordDAO);
        //比对用户信息的加密密码和页面传输密码是否匹配
        if (!StringUtils.equals(encrptPassword, userModel.getEncrptPassword())) {
            throw new BusinessException((EmBusinessError.USER_LOGIN_FAIL));
        }
        return userModel;
    }

    private UserPasswordDAO convertPasswordFromModel(UserModel userModel) {
        if (null == userModel) {
            return null;
        }
        UserPasswordDAO userPasswordDAO = new UserPasswordDAO();
        userPasswordDAO.setEncrptPassword(userModel.getEncrptPassword());
        userPasswordDAO.setUserId(userModel.getId());
        return userPasswordDAO;
    }

    private UserDAO convertFromModel(UserModel userModel) {
        if (null == userModel) {
            return null;
        }
        UserDAO userDAO = new UserDAO();
        BeanUtils.copyProperties(userModel, userDAO);
        return userDAO;
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
