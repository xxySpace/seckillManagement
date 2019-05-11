package com.xxy.seckill.seckillmanagement.service;

import com.xxy.seckill.seckillmanagement.error.BusinessException;
import com.xxy.seckill.seckillmanagement.service.model.OrderModel;

/**
 * @ClassName: OrderService
 * @Description: 用户下单服务
 * @Author: 13688
 * @Date: 2019/5/11 23:26
 * @Version: v1.0
 **/
public interface OrderService {
    /**
     * 下单
     * @param userId
     * @param itemId
     * @param amount
     * @return
     * @throws BusinessException
     */
    OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException;
}
