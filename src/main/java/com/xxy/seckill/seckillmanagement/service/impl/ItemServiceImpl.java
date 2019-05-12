package com.xxy.seckill.seckillmanagement.service.impl;

import com.xxy.seckill.seckillmanagement.dao.ItemDAOMapper;
import com.xxy.seckill.seckillmanagement.dao.ItemStockDAOMapper;
import com.xxy.seckill.seckillmanagement.dataobject.ItemDAO;
import com.xxy.seckill.seckillmanagement.dataobject.ItemStockDAO;
import com.xxy.seckill.seckillmanagement.error.BusinessException;
import com.xxy.seckill.seckillmanagement.error.EmBusinessError;
import com.xxy.seckill.seckillmanagement.service.ItemService;
import com.xxy.seckill.seckillmanagement.service.model.ItemModel;
import com.xxy.seckill.seckillmanagement.validator.ValidationResult;
import com.xxy.seckill.seckillmanagement.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: ItemServiceImpl
 * @Description: 商品服务实现
 * @Author: 13688
 * @Date: 2019/5/4 11:36
 * @Version: v1.0
 **/
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private ItemDAOMapper itemDAOMapper;

    @Autowired
    private ItemStockDAOMapper itemStockDAOMapper;

    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        //校验入参
        ValidationResult result = validator.validate(itemModel);
        if (result.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        //转化itemModel -> dataObject，入库
        ItemDAO itemDAO = convertItemDAOFromItemModel(itemModel);
        itemDAOMapper.insertSelective(itemDAO);
        itemModel.setId(itemDAO.getId());
        ItemStockDAO itemStockDAO = convertItemStockDAOFromItemModel(itemModel);
        itemStockDAOMapper.insertSelective(itemStockDAO);

        //返回创建完成的对象

        return this.getItemById(itemModel.getId());
    }

    @Override
    public List<ItemModel> listItem() {
        List<ItemDAO> itemDAOList = itemDAOMapper.listItem();
        List<ItemModel> itemModelList = itemDAOList.stream().map(itemDAO -> {
            ItemStockDAO itemStockDAO = itemStockDAOMapper.selectByItemId(itemDAO.getId());
            ItemModel itemModel = this.convertModelFromDataObject(itemDAO, itemStockDAO);
            return itemModel;
        }).collect(Collectors.toList());
        return itemModelList;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        ItemDAO itemDAO = itemDAOMapper.selectByPrimaryKey(id);
        if (null == itemDAO) {
            return null;
        }
        //操作获取库存数量
        ItemStockDAO itemStockDAO = itemStockDAOMapper.selectByItemId(itemDAO.getId());

        //将dataObject -> model
        ItemModel itemModel = convertModelFromDataObject(itemDAO, itemStockDAO);

        return itemModel;
    }

    @Override
    @Transactional
    public boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException {
        int affectedRow = itemStockDAOMapper.decreaseStock(itemId, amount);
        if (affectedRow > 0){
            //更新库存成功
            return true;
        }else {
            //更新库存失败
            return false;
        }
    }

    @Override
    @Transactional
    public void increaseSales(Integer itemId, Integer amount) throws BusinessException {
        itemDAOMapper.increaseSales(itemId, amount);
    }

    private ItemStockDAO convertItemStockDAOFromItemModel(ItemModel itemModel) {
        if (null == itemModel) {
            return null;
        }
        ItemStockDAO itemStockDAO = new ItemStockDAO();
        itemStockDAO.setStock(itemModel.getStock());
        itemStockDAO.setItemId(itemModel.getId());
        return itemStockDAO;
    }

    private ItemDAO convertItemDAOFromItemModel(ItemModel itemModel) {
        if (null == itemModel) {
            return null;
        }
        ItemDAO itemDAO = new ItemDAO();
        BeanUtils.copyProperties(itemModel, itemDAO);
        itemDAO.setPrice(itemModel.getPrice().doubleValue());
        return itemDAO;
    }

    private ItemModel convertModelFromDataObject(ItemDAO itemDAO, ItemStockDAO itemStockDAO) {
        if (null == itemDAO) {
            return null;
        }
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDAO, itemModel);
        itemModel.setPrice(new BigDecimal(itemDAO.getPrice()));
        if (null != itemStockDAO) {
            itemModel.setStock(itemStockDAO.getStock());
        }
        return itemModel;
    }
}
