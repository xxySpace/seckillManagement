package com.xxy.seckill.seckillmanagement.controller;

import com.xxy.seckill.seckillmanagement.controller.viewobject.DictVO;
import com.xxy.seckill.seckillmanagement.error.BusinessException;
import com.xxy.seckill.seckillmanagement.error.EmBusinessError;
import com.xxy.seckill.seckillmanagement.response.CommonRetrunType;
import com.xxy.seckill.seckillmanagement.service.DictService;
import com.xxy.seckill.seckillmanagement.service.model.DictModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName: DictController
 * @Description: 字典控制类
 * @Author: 13688
 * @Date: 2019/6/10 18:44
 * @Version: v1.0
 **/
@Controller("dict")
@RequestMapping("/dict")
@CrossOrigin(allowCredentials = "true", origins = {"*"})
public class DictController extends BaseController {

    @Autowired
    private DictService dictService;

    /**
     * 操作类型 A、新增 U、更新
     */
    private static final String CREATE = "A";

    private static final String UPDATE = "U";

    /**
     * 创建字典
     *
     * @param catalog
     * @param value
     * @param name
     * @param memo
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/create", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    private CommonRetrunType createDict(@RequestParam(name = "id") Integer id,
                                        @RequestParam(name = "catalog") String catalog,
                                        @RequestParam(name = "value") String value,
                                        @RequestParam(name = "name") String name,
                                        @RequestParam(name = "memo") String memo,
                                        @RequestParam(name = "state") Byte state) throws BusinessException {
        //封装service请求用来创建字典
        DictModel dictModel = new DictModel();
        dictModel.setId(id);
        dictModel.setCatalog(catalog);
        dictModel.setValue(value);
        dictModel.setName(name);
        dictModel.setMemo(memo);
        dictModel.setState(state);
        DictModel dictModel1ForReturn = dictService.createDict(dictModel);
        DictVO dictVO = convertVOFromModel(dictModel1ForReturn, CREATE);

        return CommonRetrunType.create(dictVO);

    }

    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    @ResponseBody
    private CommonRetrunType getDict(@RequestParam(name = "id") Integer id,
                                     @RequestParam(name = "catalog") String catalog,
                                     @RequestParam(name = "value") String value,
                                     @RequestParam(name = "opType") String opType) {
        DictModel dictModel = new DictModel();
        dictModel.setId(id);
        dictModel.setCatalog(catalog);
        dictModel.setValue(value);
        DictModel dictModel1ForReturn = dictService.getDictDetail(dictModel);
        DictVO dictVO = convertVOFromModel(dictModel1ForReturn, opType);

        return CommonRetrunType.create(dictVO);
    }

    @RequestMapping(value = "/list", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    private CommonRetrunType listDict(@RequestParam(name = "catalog") String catalog,
                                      @RequestParam(name = "state") Byte state) {
        DictModel dictModel = new DictModel();
        if (StringUtils.isNotEmpty(catalog)) {
            dictModel.setCatalog(catalog);
        }
        dictModel.setState(state);
        List<DictModel> dictModelList = dictService.listDict(dictModel);
        //使用Stream api将list内的dictModel转化为dictVo;
        List<DictVO> dictVOList = dictModelList.stream().map(dictModel1 -> {
            DictVO dictVO = this.convertVOFromModel(dictModel1, CREATE);
            return dictVO;

        }).collect(Collectors.toList());

        return CommonRetrunType.create(dictVOList);
    }

    @RequestMapping(value = "/listCount", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    private CommonRetrunType listDictCount(@RequestParam(name = "catalog") String catalog,
                                           @RequestParam(name = "state") Byte state) {
        DictModel dictModel = new DictModel();
        if (StringUtils.isNotEmpty(catalog)) {
            dictModel.setCatalog(catalog);
        }
        dictModel.setState(state);
        Integer queryCount = dictService.listDictCount(dictModel);

        return CommonRetrunType.create(queryCount);
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    private CommonRetrunType deleteDict(@RequestParam(name = "idList") String idList) throws BusinessException {
        String[] id = idList.split(",");
        boolean success = dictService.deleteDict(id);
        if (!success) {
            throw new BusinessException(EmBusinessError.DELETE_ERROR);
        }

        return CommonRetrunType.create(null);
    }

    @RequestMapping(value = "/update", method = {RequestMethod.GET})
    @ResponseBody
    private CommonRetrunType updateDict(@RequestParam(name = "id") Integer id,
                                        @RequestParam(name = "catalog") String catalog,
                                        @RequestParam(name = "value") String value,
                                        @RequestParam(name = "name") String name,
                                        @RequestParam(name = "memo") String memo) throws BusinessException {
        DictModel dictModel = new DictModel();
        dictModel.setId(id);
        dictModel.setCatalog(catalog);
        dictModel.setValue(value);
        dictModel.setName(name);
        dictModel.setMemo(memo);
        boolean success = dictService.updateDict(dictModel);
        if (!success) {
            throw new BusinessException(EmBusinessError.UPDATE_ERROR);
        }

        return CommonRetrunType.create(null);
    }

    private DictVO convertVOFromModel(DictModel dictModel, String opType) {
        if (null == dictModel) {
            return null;
        }
        Map<String, String> stateMap = dictService.getCodeDict("STATE");
        DictVO dictVO = new DictVO();
        BeanUtils.copyProperties(dictModel, dictVO);
        String state = String.valueOf(dictModel.getState());
        if (null != opType && CREATE.equals(opType)) {
            state = stateMap.get(state);
        }
        dictVO.setState(state);

        return dictVO;
    }

    @RequestMapping("/dictAdd")
    public String dictAdd(Model model, HttpServletResponse response) {
        model.addAttribute("name", "子慕鱼");
        return "dict-add";
    }
}
