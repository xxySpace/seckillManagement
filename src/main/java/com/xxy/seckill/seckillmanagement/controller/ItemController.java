package com.xxy.seckill.seckillmanagement.controller;

import com.xxy.seckill.seckillmanagement.controller.viewobject.ItemVO;
import com.xxy.seckill.seckillmanagement.error.BusinessException;
import com.xxy.seckill.seckillmanagement.error.EmBusinessError;
import com.xxy.seckill.seckillmanagement.response.CommonRetrunType;
import com.xxy.seckill.seckillmanagement.service.ItemService;
import com.xxy.seckill.seckillmanagement.service.model.ItemModel;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: ItemController
 * @Description: 商品控制层
 * @Author: 13688
 * @Date: 2019/5/4 12:12
 * @Version: v1.0
 **/
@Controller("item")
@RequestMapping("/item")
@CrossOrigin(allowCredentials = "true", origins = {"*"})
public class ItemController extends BaseController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 创建商品
     *
     * @param title
     * @param description
     * @param price
     * @param stock
     * @param imgUrl
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/create", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonRetrunType createItem(@RequestParam(name = "id") Integer id,
                                       @RequestParam(name = "title") String title,
                                       @RequestParam(name = "description") String description,
                                       @RequestParam(name = "price") BigDecimal price,
                                       @RequestParam(name = "stock") Integer stock,
                                       @RequestParam(name = "imgUrl") String imgUrl) throws BusinessException {
        //封装service请求用来创建商品
        ItemModel itemModel = new ItemModel();
        itemModel.setId(id);
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);
        ItemModel itemModel1ForReturn = itemService.createItem(itemModel);
        ItemVO itemVO = convertVOFromModel(itemModel1ForReturn);

        return CommonRetrunType.create(itemVO);
    }

    /**
     * 商品详情页浏览
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    @ResponseBody
    public CommonRetrunType getItem(@RequestParam(name = "id") Integer id) throws BusinessException {
        ItemModel itemModel = itemService.getItemById(id);
        ItemVO itemVO = convertVOFromModel(itemModel);
        return CommonRetrunType.create(itemVO);
    }

    @RequestMapping(value = "/list", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonRetrunType listItem(@RequestParam(name = "title") String title) {
        List<ItemModel> itemModelList = itemService.listItem(title);

        //使用Stream api将list内的itemModel转化为itemVo;
        List<ItemVO> itemVOList = itemModelList.stream().map(itemModel -> {
            ItemVO itemVO = this.convertVOFromModel(itemModel);
            return itemVO;
        }).collect(Collectors.toList());
        return CommonRetrunType.create(itemVOList);
    }

    @RequestMapping(value = "/listCount", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonRetrunType listItemCount(@RequestParam(name = "title") String title) {
        Integer queryCount = itemService.listItemCount(title);
        return CommonRetrunType.create(queryCount);
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonRetrunType itemDelete(@RequestParam(name = "idList") String idList) throws BusinessException {
        String[] id = idList.split(",");
        boolean success = itemService.itemDelete(id);
        if (!success){
            throw new BusinessException(EmBusinessError.DELETE_ERROR);
        }
        return CommonRetrunType.create(null);
    }

    private ItemVO convertVOFromModel(ItemModel itemModel) {
        if (null == itemModel) {
            return null;
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel, itemVO);
        if (null != itemModel.getPromoModel()) {
            //说明有即将进行或者正在进行的秒杀活动
            itemVO.setPromoStatus(itemModel.getPromoModel().getStatus());
            itemVO.setPromoId(itemModel.getPromoModel().getId());
            itemVO.setStartDate(itemModel.getPromoModel().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemVO.setEndDate(itemModel.getPromoModel().getEndDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemVO.setPromoPrice(itemModel.getPromoModel().getPromoItemPrice());
        } else {
            itemVO.setPromoStatus(0);
        }
        return itemVO;
    }

    @RequestMapping("/qryItem")
    public String qryItem(Model model, HttpServletResponse response) {
        model.addAttribute("name", "子慕鱼");
        return "qryitem";
    }

    @RequestMapping("/listItem")
    public String listItem(Model model, HttpServletResponse response) {
        model.addAttribute("name", "子慕鱼");
        return "listitem";
    }

    @RequestMapping("/getItem")
    public String getItem(Model model, HttpServletResponse response) {
        model.addAttribute("name", "子慕鱼");
        return "getitem";
    }

    @RequestMapping("/itemAdd")
    public String itemAdd(Model model, HttpServletResponse response) {
        model.addAttribute("name", "子慕鱼");
        return "item-add";
    }
}
