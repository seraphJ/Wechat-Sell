package com.gxj.service;

import com.gxj.dto.OrderDTO;

public interface PushMessageService {
    void orderStatus(OrderDTO orderDTO);
}
