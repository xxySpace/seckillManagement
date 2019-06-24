package com.xxy.seckill.seckillmanagement.service.impl;

import com.xxy.seckill.seckillmanagement.dao.ItemDAOMapper;
import com.xxy.seckill.seckillmanagement.dao.PromoDAOMapper;
import com.xxy.seckill.seckillmanagement.dataobject.ItemDAO;
import com.xxy.seckill.seckillmanagement.dataobject.PromoDAO;
import com.xxy.seckill.seckillmanagement.error.BusinessException;
import com.xxy.seckill.seckillmanagement.error.EmBusinessError;
import com.xxy.seckill.seckillmanagement.service.ItemService;
import com.xxy.seckill.seckillmanagement.service.PromoService;
import com.xxy.seckill.seckillmanagement.service.model.ItemModel;
import com.xxy.seckill.seckillmanagement.service.model.PromoModel;
import com.xxy.seckill.seckillmanagement.validator.ValidationResult;
import com.xxy.seckill.seckillmanagement.validator.ValidatorImpl;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private ItemDAOMapper itemDAOMapper;

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

    @Override
    public PromoModel createPromo(PromoModel promoModel) throws BusinessException {
        //校验入参
        ValidationResult result = validator.validate(promoModel);
        if (result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }
        //转化Model -> dataObject，入库
        if (null == promoModel.getId() || 0 == promoModel.getId()) {
            PromoDAO promoDAO = convertPromoDAOFromPromoModel(promoModel);
            promoDAOMapper.insertSelective(promoDAO);
            promoModel.setId(promoDAO.getId());
        } else {
            boolean success = this.promoUpdate(promoModel);
            if (!success){
                throw new BusinessException(EmBusinessError.UPDATE_ERROR);
            }
        }

        //返回创建完成的对象
        return this.getPromoById(promoModel.getId());
    }

    @Override
    public List<PromoModel> listPromo(PromoModel promoModel) {
        List<PromoDAO> promoDAOList = promoDAOMapper.listPromo(promoModel);
        List<PromoModel> promoModelList = promoDAOList.stream().map(promoDAO -> {
           PromoModel model = this.convertFromDataObject(promoDAO);
           return model;
        }).collect(Collectors.toList());
        return promoModelList;
    }

    @Override
    public Integer listPromoCount(PromoModel promoModel) {
        return promoDAOMapper.listPromoCount(promoModel);
    }

    @Override
    public PromoModel getPromoById(Integer id) {
        PromoDAO promoDAO = promoDAOMapper.selectByPrimaryKey(id);
        PromoModel promoModel = convertFromDataObject(promoDAO);
        return promoModel;
    }

    @Override
    public boolean promoDelete(String[] idList) {
        int result = promoDAOMapper.deletePromo(idList);
        if (result > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean promoUpdate(PromoModel promoModel) {
        PromoDAO promoDAO = convertPromoDAOFromPromoModel(promoModel);
        int result = promoDAOMapper.updateByPrimaryKeySelective(promoDAO);
        if (result > 0) {
            return true;
        }
        return false;
    }


    private PromoDAO convertPromoDAOFromPromoModel(PromoModel promoModel) {
        if (null == promoModel){
            return null;
        }
        PromoDAO promoDAO = new PromoDAO();
        BeanUtils.copyProperties(promoModel, promoDAO);
        promoDAO.setPromoItemPrice(promoModel.getPromoItemPrice().doubleValue());
        promoDAO.setStartDate(promoModel.getStartDate().toDate());
        promoDAO.setEndDate(promoModel.getEndDate().toDate());
        return promoDAO;
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
        ItemDAO itemDAO = itemDAOMapper.selectByPrimaryKey(promoModel.getItemId());
        promoModel.setItemName(itemDAO.getTitle());
        return promoModel;
    }
}
