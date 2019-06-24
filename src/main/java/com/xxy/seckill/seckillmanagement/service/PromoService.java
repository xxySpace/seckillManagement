package com.xxy.seckill.seckillmanagement.service;

import com.xxy.seckill.seckillmanagement.error.BusinessException;
import com.xxy.seckill.seckillmanagement.service.model.PromoModel;

import java.util.List;

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

    /**
     * 创建活动
     * @param promoModel
     * @return
     * @throws BusinessException
     */
    PromoModel createPromo(PromoModel promoModel) throws BusinessException;

    /**
     * 查询活动列表
     * @param promoModel
     * @return
     */
    List<PromoModel> listPromo(PromoModel promoModel);

    /**
     * 查询活动数量
     * @param promoModel
     * @return
     */
    Integer listPromoCount(PromoModel promoModel);

    /**
     * 获取活动详情
     * @param id
     * @return
     */
    PromoModel getPromoById(Integer id);

    /**
     * 删除活动
     * @param idList
     * @return
     */
    boolean promoDelete(String[] idList);

    /**
     * 更新活动
     * @return
     */
    boolean promoUpdate(PromoModel promoModel);
}
