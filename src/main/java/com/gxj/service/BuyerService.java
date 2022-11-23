package com.gxj.service;

import com.gxj.dto.OrderDTO;

public interface BuyerService {

    OrderDTO findOrderOne(String openid, String orderId);
    OrderDTO cancelOrder(String openid, String orderId);
}
