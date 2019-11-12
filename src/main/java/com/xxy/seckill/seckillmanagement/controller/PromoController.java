package com.xxy.seckill.seckillmanagement.controller;

import com.xxy.seckill.seckillmanagement.controller.viewobject.PromoVO;
import com.xxy.seckill.seckillmanagement.error.BusinessException;
import com.xxy.seckill.seckillmanagement.error.EmBusinessError;
import com.xxy.seckill.seckillmanagement.response.CommonRetrunType;
import com.xxy.seckill.seckillmanagement.service.PromoService;
import com.xxy.seckill.seckillmanagement.service.model.PromoModel;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: PromoController
 * @Description: 营销活动管理控制类
 * @Author: 13688
 * @Date: 2019/6/19 14:31
 * @Version: v1.0
 **/
@Controller("promo")
@RequestMapping("/promo")
@CrossOrigin(allowCredentials = "true", origins = {"*"})
public class PromoController extends BaseController {

    @Autowired
    private PromoService promoService;

    @RequestMapping(value = "/create", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonRetrunType createPromo(@RequestParam(name = "id") Integer id,
                                        @RequestParam("promoName") String promoName,
                                        @RequestParam("startDate") String startDate,
                                        @RequestParam("endDate") String endDate,
                                        @RequestParam("itemId") Integer itemId,
                                        @RequestParam("promoItemPrice") BigDecimal promoItemPrice) throws BusinessException {
        PromoModel promoModel = new PromoModel();
        promoModel.setId(id);
        promoModel.setPromoName(promoName);
        promoModel.setStartDate(convertDateTimeFromString(startDate));
        promoModel.setEndDate(convertDateTimeFromString(endDate));
        promoModel.setItemId(itemId);
        promoModel.setPromoItemPrice(promoItemPrice);
        PromoModel promoModelReturn = promoService.createPromo(promoModel);
        PromoVO promoVO = covertPromoVoFromPromoModel(promoModelReturn);
        return CommonRetrunType.create(promoVO);
    }

    private DateTime convertDateTimeFromString(String time) throws BusinessException {
        DateTime dateTime = null;
        try {
            dateTime = new DateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time + ":00").getTime());
        } catch (ParseException e) {
            throw new BusinessException(EmBusinessError.SYSTEM_ERROR, "时间格式转换错误");
        }
        return dateTime;
    }

    @RequestMapping(value = "/list", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonRetrunType listPromo(@RequestParam("promoName") String promoName,
                                      @RequestParam("startDateBegin") String startDateBegin,
                                      @RequestParam("startDateEnd") String startDateEnd,
                                      @RequestParam("endDateBegin") String endDateBegin,
                                      @RequestParam("endDateEnd") String endDateEnd,
                                      @RequestParam("itemName") String itemName) throws BusinessException {
        PromoModel promoModel = getPromoModel(promoName, startDateBegin, startDateEnd, endDateBegin, endDateEnd, itemName);
        List<PromoModel> promoModelList = promoService.listPromo(promoModel);
        List<PromoVO> promoVOList = promoModelList.stream().map(model -> {
            PromoVO promoVO = covertPromoVoFromPromoModel(model);
            return promoVO;
        }).collect(Collectors.toList());

        return CommonRetrunType.create(promoVOList);
    }

    @RequestMapping(value = "/listCount", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonRetrunType listPromoCount(@RequestParam("promoName") String promoName,
                                           @RequestParam("startDateBegin") String startDateBegin,
                                           @RequestParam("startDateEnd") String startDateEnd,
                                           @RequestParam("endDateBegin") String endDateBegin,
                                           @RequestParam("endDateEnd") String endDateEnd,
                                           @RequestParam("itemName") String itemName) throws BusinessException {
        PromoModel promoModel = getPromoModel(promoName, startDateBegin, startDateEnd, endDateBegin, endDateEnd, itemName);
        Integer queryCount = promoService.listPromoCount(promoModel);
        return CommonRetrunType.create(queryCount);
    }

    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    @ResponseBody
    public CommonRetrunType getPromo(@RequestParam("id") Integer id) {
        PromoModel promoModel = promoService.getPromoById(id);
        PromoVO promoVO = covertPromoVoFromPromoModel(promoModel);
        return CommonRetrunType.create(promoVO);
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonRetrunType promoDelete(@RequestParam(name = "idList") String idList) throws BusinessException {
        String[] id = idList.split(",");
        boolean success = promoService.promoDelete(id);
        if (!success){
            throw new BusinessException(EmBusinessError.DELETE_ERROR);
        }
        return CommonRetrunType.create(null);
    }

    private PromoModel getPromoModel(String promoName, String startDateBegin, String startDateEnd, String endDateBegin,
                                     String endDateEnd, String itemName) throws BusinessException {
        PromoModel promoModel = new PromoModel();
        promoModel.setPromoName(promoName);
        if (StringUtils.isNotEmpty(startDateBegin)) {
            promoModel.setStartDateBegin(convertDateFromString(startDateBegin));
        }
        if (StringUtils.isNotEmpty(startDateEnd)) {
            promoModel.setStartDateEnd(convertDateFromString(startDateEnd));
        }
        if (StringUtils.isNotEmpty(endDateBegin)) {
            promoModel.setEndDateBegin(convertDateFromString(endDateBegin));
        }
        if (StringUtils.isNotEmpty(endDateEnd)) {
            promoModel.setEndDateEnd(convertDateFromString(endDateEnd));
        }
        promoModel.setItemName(itemName);
        return promoModel;
    }

    private Date convertDateFromString(String dateStr) throws BusinessException {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
        } catch (ParseException e) {
            throw new BusinessException(EmBusinessError.SYSTEM_ERROR, "时间格式转换错误！");
        }
    }

    private PromoVO covertPromoVoFromPromoModel(PromoModel promoModelReturn) {
        if (null == promoModelReturn) {
            return null;
        }
        PromoVO promoVO = new PromoVO();
        BeanUtils.copyProperties(promoModelReturn, promoVO);
        promoVO.setStartDate(promoModelReturn.getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
        promoVO.setEndDate(promoModelReturn.getEndDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
        return promoVO;
    }

    @RequestMapping("/listPromo")
    public String listPromo(Model model, HttpServletResponse response) {
        model.addAttribute("name", "子慕鱼");
        return "listpromo";
    }

    @RequestMapping("/promoAdd")
    public String promoAdd(Model model, HttpServletResponse response) {
        model.addAttribute("name", "子慕鱼");
        return "promo-add";
    }
}
