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
     * @param itemModel
     * @return
     * @throws BusinessException
     */
    ItemModel createItem(ItemModel itemModel) throws BusinessException;

    /**
     * 商品列表浏览
     * @param title 商品名称
     * @return
     */
    List<ItemModel> listItem(String title);

    /**
     * 商品列表查询数量
     * @param title 商品名称
     * @return
     */
    Integer listItemCount(String title);

    /**
     * 商品详情浏览
     *
     * @param id
     * @return
     */
    ItemModel getItemById(Integer id) throws BusinessException;

    boolean itemDelete(String[] idList);

    /**
     * 库存扣减
     * @param itemId
     * @param amount
     * @return
     * @throws BusinessException
     */
    boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException;

    /**
     * 销量增加
     * @param itemId
     * @param amount
     * @throws BusinessException
     */
    void increaseSales(Integer itemId, Integer amount) throws BusinessException;
}
