package com.gxj.service;

import com.gxj.dto.OrderDTO;
import com.lly835.bestpay.model.PayResponse;

public interface PayService {
    PayResponse create(OrderDTO orderDTO);
}
