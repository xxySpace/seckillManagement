package com.xxy.seckill.seckillmanagement.controller;

import com.xxy.seckill.seckillmanagement.error.BusinessException;
import com.xxy.seckill.seckillmanagement.error.EmBusinessError;
import com.xxy.seckill.seckillmanagement.response.CommonRetrunType;
import com.xxy.seckill.seckillmanagement.service.OrderService;
import com.xxy.seckill.seckillmanagement.service.model.OrderModel;
import com.xxy.seckill.seckillmanagement.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: OrderController
 * @Description: 下单控制类
 * @Author: 13688
 * @Date: 2019/5/12 14:24
 * @Version: v1.0
 **/
@Controller("order")
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true", origins = {"*"})
public class OrderController extends BaseController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping(value = "/createorder", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonRetrunType createOrder(@RequestParam(name = "itemId") Integer itemId,
                                        @RequestParam(name = "amount") Integer amount,
                                        @RequestParam(name = "promoId", required = false) Integer promoId) throws BusinessException {
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (null == isLogin || !isLogin.booleanValue()) {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN, "用户未登录，不能下单");
        }
        //获取用户登录信息
        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");

        //封装service请求用来下单
        OrderModel orderModel = orderService.createOrder(userModel.getId(), itemId, promoId, amount);

        return CommonRetrunType.create(null);
    }
}
