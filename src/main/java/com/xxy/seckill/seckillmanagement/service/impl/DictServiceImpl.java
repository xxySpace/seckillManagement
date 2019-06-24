package com.xxy.seckill.seckillmanagement.service.impl;

import com.xxy.seckill.seckillmanagement.dao.DictDAOMapper;
import com.xxy.seckill.seckillmanagement.dataobject.DictDAO;
import com.xxy.seckill.seckillmanagement.dataobject.DictDAOKey;
import com.xxy.seckill.seckillmanagement.error.BusinessException;
import com.xxy.seckill.seckillmanagement.error.EmBusinessError;
import com.xxy.seckill.seckillmanagement.service.DictService;
import com.xxy.seckill.seckillmanagement.service.model.DictModel;
import com.xxy.seckill.seckillmanagement.validator.ValidationResult;
import com.xxy.seckill.seckillmanagement.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName: DictServiceImpl
 * @Description: 字典服务实现类
 * @Author: 13688
 * @Date: 2019/6/9 17:09
 * @Version: v1.0
 **/
@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private DictDAOMapper dictDAOMapper;

    /**
     * 字典状态 0、失效 1、生效
     */
    private static final Byte STATE_EFFECTIVE = 1;

    @Override
    @Transactional
    public DictModel createDict(DictModel dictModel) throws BusinessException {
        //校验入参
        ValidationResult result = validator.validate(dictModel);
        if (result.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }
        //转化itemModel -> dataObject，入库
        DictDAO dictDAO = convertDictDAOFromDictModel(dictModel);
        if (null == dictModel.getId() || 0 == dictModel.getId()) {
            dictDAOMapper.insertSelective(dictDAO);
        } else {
            boolean success = this.updateDict(dictModel);
            if (!success){
                throw new BusinessException(EmBusinessError.UPDATE_ERROR);
            }
        }
        return this.getDictDetail(dictModel);
    }

    @Override
    public List<DictModel> listDict(DictModel dictModel) {
        DictDAO dao = convertDictDAOFromDictModel(dictModel);
        List<DictDAO> dictDAOList = dictDAOMapper.listDict(dao);
        List<DictModel> dictModelList = dictDAOList.stream().map(dictDAO -> {
            DictModel model = this.convertDictModelFromDictDAO(dictDAO);
            return model;
        }).collect(Collectors.toList());

        return dictModelList;
    }

    @Override
    public Integer listDictCount(DictModel dictModel) {
        DictDAO dao = convertDictDAOFromDictModel(dictModel);
        return dictDAOMapper.listDictCount(dao);
    }

    @Override
    public DictModel getDictDetail(DictModel dictModel) {
        DictDAOKey dictDAOKey = new DictDAOKey();
        BeanUtils.copyProperties(dictModel, dictDAOKey);
        DictDAO dictDAO = dictDAOMapper.selectByPrimaryKey(dictDAOKey);
        //dataObject -> model
        DictModel dictModel1 = this.convertDictModelFromDictDAO(dictDAO);
        return dictModel1;
    }

    @Override
    @Transactional
    public boolean updateDict(DictModel dictModel) {
        DictDAO dictDAO = convertDictDAOFromDictModel(dictModel);
        int affectedRow = dictDAOMapper.updateByPrimaryKeySelective(dictDAO);
        if (affectedRow > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteDict(String[] idList) {
        int affectedRow = dictDAOMapper.deleteDict(idList);
        if (affectedRow > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Map<String, String> getCodeDict(String catalog) {
        Map<String, String> dictMap = new HashMap<>();
        DictModel dictModel = new DictModel();
        dictModel.setCatalog(catalog);
        dictModel.setState(STATE_EFFECTIVE);
        List<DictModel> dictModelList = listDict(dictModel);
        if (null != dictModelList && 0 != dictModelList.size()) {
            for (DictModel model : dictModelList) {
                dictMap.put(model.getValue(), model.getName());
            }
        }
        return dictMap;
    }

    private DictDAO convertDictDAOFromDictModel(DictModel dictModel) {
        if (null == dictModel) {
            return null;
        }
        DictDAO dictDAO = new DictDAO();
        BeanUtils.copyProperties(dictModel, dictDAO);
        return dictDAO;
    }

    private DictModel convertDictModelFromDictDAO(DictDAO dictDAO) {
        if (null == dictDAO) {
            return null;
        }
        DictModel dictModel = new DictModel();
        BeanUtils.copyProperties(dictDAO, dictModel);
        return dictModel;
    }
}
