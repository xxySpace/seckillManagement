package com.xxy.seckill.seckillmanagement.service.impl;

import com.xxy.seckill.seckillmanagement.dao.PromoDAOMapper;
import com.xxy.seckill.seckillmanagement.dataobject.PromoDAO;
import com.xxy.seckill.seckillmanagement.service.PromoService;
import com.xxy.seckill.seckillmanagement.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @ClassName: PromoServiceImpl
 * @Description: 秒杀营销服务实现
 * @Author: 13688
 * @Date: 2019/5/13 22:58
 * @Version: v1.0
 **/
@Service
public class PromoServiceImpl implements PromoService {
    @Autowired
    private PromoDAOMapper promoDAOMapper;

    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        //获取对应商品的秒杀活动信息
        PromoDAO promoDAO = promoDAOMapper.selectByItemId(itemId);

        //dataObject -> model
        PromoModel promoModel = convertFromDataObject(promoDAO);
        if (null == promoModel) {
            return null;
        }

        //判断当前时间是否为秒杀活动即将开始或者正在进行
        if (promoModel.getStartDate().isAfterNow()) {
            promoModel.setStatus(1);
        } else if (promoModel.getEndDate().isBeforeNow()) {
            promoModel.setStatus(3);
        } else {
            promoModel.setStatus(2);
        }
        return promoModel;
    }

    private PromoModel convertFromDataObject(PromoDAO promoDAO) {
        if (null == promoDAO) {
            return null;
        }
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoDAO, promoModel);
        promoModel.setPromoItemPrice(new BigDecimal(promoDAO.getPromoItemPrice()));
        promoModel.setStartDate(new DateTime(promoDAO.getStartDate()));
        promoModel.setEndDate(new DateTime(promoDAO.getEndDate()));
        return promoModel;
    }
}
