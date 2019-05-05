package com.xxy.seckill.seckillmanagement.controller;

import com.xxy.seckill.seckillmanagement.controller.viewobject.ItemVO;
import com.xxy.seckill.seckillmanagement.error.BusinessException;
import com.xxy.seckill.seckillmanagement.response.CommonRetrunType;
import com.xxy.seckill.seckillmanagement.service.ItemService;
import com.xxy.seckill.seckillmanagement.service.model.ItemModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @ClassName: ItemController
 * @Description: 商品控制层
 * @Author: 13688
 * @Date: 2019/5/4 12:12
 * @Version: v1.0
 **/
@Controller("item")
@RequestMapping("/item")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class ItemController extends BaseController {

    @Autowired
    private ItemService itemService;

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
    public CommonRetrunType createItem(@RequestParam(name = "title") String title,
                                       @RequestParam(name = "description") String description,
                                       @RequestParam(name = "price") BigDecimal price,
                                       @RequestParam(name = "stock") Integer stock,
                                       @RequestParam(name = "imgUrl") String imgUrl) throws BusinessException {
        //封装service请求用来创建商品
        ItemModel itemModel = new ItemModel();
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
    @RequestMapping(value = "/get", method = {RequestMethod.GET}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonRetrunType getItem(@RequestParam(name = "id") Integer id) {
        ItemModel itemModel = itemService.getItemById(id);

        ItemVO itemVO = convertVOFromModel(itemModel);

        return CommonRetrunType.create(itemVO);
    }

    private ItemVO convertVOFromModel(ItemModel itemModel) {
        if (null == itemModel) {
            return null;
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel, itemVO);
        return itemVO;
    }
}
