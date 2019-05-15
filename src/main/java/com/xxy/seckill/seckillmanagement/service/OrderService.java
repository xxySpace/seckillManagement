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
     * 1、通过前端url上传过来的秒杀活动id,然后下单接口内校验对应id是否属于对应商品且活动正在进行
     * 2、直接在下单接口内判断对应的商品是否存在秒杀活动，若存在进行中的则以秒杀价格下单
     * 比较两者，选1
     *
     * @param userId
     * @param itemId
     * @param amount
     * @return
     * @throws BusinessException
     */
    OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException;
}
