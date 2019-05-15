package com.xxy.seckill.seckillmanagement.service;

import com.xxy.seckill.seckillmanagement.service.model.PromoModel;

/**
 * @ClassName: PromoService
 * @Description: 秒杀营销服务
 * @Author: 13688
 * @Date: 2019/5/13 22:56
 * @Version: v1.0
 **/
public interface PromoService {
    /**
     * 获取商品即将进行的或者正在进行的秒杀活动信息
     * @param itemId
     * @return
     */
    PromoModel getPromoByItemId(Integer itemId);
}
