package com.xxy.seckill.seckillmanagement.service;

import com.xxy.seckill.seckillmanagement.error.BusinessException;
import com.xxy.seckill.seckillmanagement.service.model.ItemModel;

import java.util.List;

/**
 * @ClassName: ItemService
 * @Description: 商品服务接口
 * @Author: 13688
 * @Date: 2019/5/4 11:31
 * @Version: v1.0
 **/
public interface ItemService {

    /**
     * 创建商品
     *
     * @param itemModel
     * @return
     */
    ItemModel createItem(ItemModel itemModel) throws BusinessException;

    /**
     * 商品列表浏览
     *
     * @return
     */
    List<ItemModel> listItem();

    /**
     * 商品详情浏览
     *
     * @param id
     * @return
     */
    ItemModel getItemById(Integer id);
}
