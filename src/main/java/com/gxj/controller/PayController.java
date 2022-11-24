package com.gxj.controller;


import com.gxj.dto.OrderDTO;
import com.gxj.enums.ResultEnum;
import com.gxj.exception.SellException;
import com.gxj.service.OrderService;
import com.gxj.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String, Object> map) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        PayResponse payResponse = payService.create(orderDTO);
        map.put("payResponse", payResponse);
        try {
            returnUrl = URLDecoder.decode(returnUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.info("url-{}", returnUrl);
        map.put("returnUrl", returnUrl);
        return new ModelAndView("pay/create", map);
    }

    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData) {
        payService.notify(notifyData);
        return new ModelAndView("pay/success");
    }
}
