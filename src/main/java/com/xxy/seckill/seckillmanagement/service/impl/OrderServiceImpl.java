package com.xxy.seckill.seckillmanagement.service.impl;

import com.sun.tools.corba.se.idl.constExpr.Or;
import com.xxy.seckill.seckillmanagement.dao.OrderDAOMapper;
import com.xxy.seckill.seckillmanagement.dataobject.OrderDAO;
import com.xxy.seckill.seckillmanagement.error.BusinessException;
import com.xxy.seckill.seckillmanagement.error.EmBusinessError;
import com.xxy.seckill.seckillmanagement.service.ItemService;
import com.xxy.seckill.seckillmanagement.service.OrderService;
import com.xxy.seckill.seckillmanagement.service.UserService;
import com.xxy.seckill.seckillmanagement.service.model.ItemModel;
import com.xxy.seckill.seckillmanagement.service.model.OrderModel;
import com.xxy.seckill.seckillmanagement.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @ClassName: OrderServiceImpl
 * @Description: 用户下单服务实现
 * @Author: 13688
 * @Date: 2019/5/11 23:29
 * @Version: v1.0
 **/
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDAOMapper orderDAOMapper;

    private static final Integer MIIN_AMOUNT = 0;

    private static final Integer MAX_AMOUNT = 99;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException {
        //1、校验下单状态，下单商品是否存在，用户是否合法，购买数量是否合法
        ItemModel itemModel = itemService.getItemById(itemId);
        if (null == itemModel){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "商品不存在");
        }

        UserModel userModel = userService.getUserById(userId);
        if (null == userModel){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "用户不存在");
        }
        if (amount <= MIIN_AMOUNT || amount > MAX_AMOUNT){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "购买数量不合法");
        }

        //2、落单减库存
        boolean result = itemService.decreaseStock(itemId, amount);
        if (result){
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }

        //3、订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        orderModel.setItemPrice(itemModel.getPrice());
        orderModel.setOrderPrice(itemModel.getPrice().multiply(new BigDecimal(amount)));

        //生成交易流水号，订单号
        OrderDAO orderDAO = convertFromOrderModel(orderModel);
        orderDAOMapper.insertSelective(orderDAO);

        //4、返回前端
        return null;
    }

    private String generatorOrderNo(){
        //订单号16位
        //前8位为时间信息，年月日

        //中间6位为自增序列

        //最后2位为分库分表位

        return null;
    }

    private OrderDAO convertFromOrderModel(OrderModel orderModel){
        if (null == orderModel){
            return null;
        }
        OrderDAO orderDAO = new OrderDAO();
        BeanUtils.copyProperties(orderModel, orderDAO);
        orderDAO.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderDAO.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        return orderDAO;
    }
}
