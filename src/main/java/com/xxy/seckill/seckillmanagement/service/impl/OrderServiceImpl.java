package com.xxy.seckill.seckillmanagement.service.impl;

import com.xxy.seckill.seckillmanagement.dao.OrderDAOMapper;
import com.xxy.seckill.seckillmanagement.dao.SequenceDAOMapper;
import com.xxy.seckill.seckillmanagement.dataobject.OrderDAO;
import com.xxy.seckill.seckillmanagement.dataobject.SequenceDAO;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @Autowired
    private SequenceDAOMapper sequenceDAOMapper;

    private static final Integer MIIN_AMOUNT = 0;

    private static final Integer MAX_AMOUNT = 99;

    private static final Integer SEQUENCE_MAX_BIT = 6;

    private static final Integer PROMO_IN_PROGRESS = 2;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException {
        //1、校验下单状态，下单商品是否存在，用户是否合法，购买数量是否合法
        ItemModel itemModel = itemService.getItemById(itemId);
        if (null == itemModel) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "商品不存在");
        }

        UserModel userModel = userService.getUserById(userId);
        if (null == userModel) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "用户不存在");
        }
        if (amount <= MIIN_AMOUNT || amount > MAX_AMOUNT) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "购买数量不合法");
        }

        //校验活动信息
        if (null != promoId) {
            //校验对应活动是否存在这个适用商品
            if (promoId.intValue() != itemModel.getPromoModel().getId()) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "活动信息不正确");
                //校验活动是否在进行中
            } else if (itemModel.getPromoModel().getStatus().intValue() != PROMO_IN_PROGRESS) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "活动未开始");
            }
        }

        //2、落单减库存
        boolean result = itemService.decreaseStock(itemId, amount);
        if (!result) {
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }

        //3、订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        if (null != promoId) {
            orderModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());
        } else {
            orderModel.setItemPrice(itemModel.getPrice());
        }
        orderModel.setPromoId(promoId);
        orderModel.setOrderPrice(orderModel.getItemPrice().multiply(new BigDecimal(amount)));

        //生成交易流水号，订单号
        orderModel.setId(generatorOrderNo());
        OrderDAO orderDAO = convertFromOrderModel(orderModel);
        orderDAOMapper.insertSelective(orderDAO);

        //刷新商品销量
        itemService.increaseSales(itemId, amount);

        //4、返回前端
        return orderModel;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String generatorOrderNo() {
        //订单号16位
        StringBuilder stringBuilder = new StringBuilder();
        //前8位为时间信息，年月日
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-", "");
        stringBuilder.append(nowDate);

        //中间6位为自增序列
        int sequence = 0;
        SequenceDAO sequenceDAO = sequenceDAOMapper.getSequenceByName("order_info");
        if (sequenceDAO.getCurrentValue() + sequenceDAO.getStep() <= sequenceDAO.getMaxValue()) {
            sequence = sequenceDAO.getCurrentValue();
            sequenceDAO.setCurrentValue(sequenceDAO.getCurrentValue() + sequenceDAO.getStep());
        } else if (sequenceDAO.getCurrentValue() < sequenceDAO.getMaxValue()) {
            sequence = sequenceDAO.getCurrentValue();
            sequenceDAO.setCurrentValue(0);
        } else {
            sequence = 0;
            sequenceDAO.setCurrentValue(0 + sequenceDAO.getStep());
        }
        sequenceDAOMapper.updateByPrimaryKey(sequenceDAO);
        String sequenceStr = String.valueOf(sequence);
        for (int i = 0; i < SEQUENCE_MAX_BIT - sequenceStr.length(); i++) {
            stringBuilder.append(0);
        }
        stringBuilder.append(sequenceStr);

        //最后2位为分库分表位，暂时写死
        stringBuilder.append("00");

        return stringBuilder.toString();
    }

    private OrderDAO convertFromOrderModel(OrderModel orderModel) {
        if (null == orderModel) {
            return null;
        }
        OrderDAO orderDAO = new OrderDAO();
        BeanUtils.copyProperties(orderModel, orderDAO);
        orderDAO.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderDAO.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        return orderDAO;
    }
}
