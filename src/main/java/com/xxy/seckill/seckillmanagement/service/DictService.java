package com.xxy.seckill.seckillmanagement.service;

import com.xxy.seckill.seckillmanagement.error.BusinessException;
import com.xxy.seckill.seckillmanagement.service.model.DictModel;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: DictService
 * @Description: 字典服务
 * @Author: 13688
 * @Date: 2019/6/9 16:14
 * @Version: v1.0
 **/
public interface DictService {

    /**
     * 创建字典
     * @param dictModel
     * @return
     * @throws BusinessException
     */
    DictModel createDict(DictModel dictModel) throws BusinessException;

    /**
     * 字典列表
     * @param dictModel
     * @return
     */
    List<DictModel> listDict(DictModel dictModel);

    /**
     * 字典列表查询数量
     * @param dictModel
     * @return
     */
    Integer listDictCount(DictModel dictModel);

    /**
     * 字典详情
     * @param dictModel
     * @return
     */
    DictModel getDictDetail(DictModel dictModel);

    /**
     * 更新字典
     * @param dictModel
     * @return
     */
    boolean updateDict(DictModel dictModel);

    /**
     * 删除字典
     * @param idList
     * @return
     */
    boolean deleteDict(String[] idList);

    /**
     * 公共方法，获取字典
     * @param catalog 类别
     * @return
     */
    Map<String, String> getCodeDict(String catalog);
}
