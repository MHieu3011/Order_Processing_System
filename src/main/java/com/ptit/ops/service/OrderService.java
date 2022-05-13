package com.ptit.ops.service;

import com.ptit.ops.builder.Response;
import com.ptit.ops.model.request.OrderFormRequest;

public interface OrderService {

    Response create(OrderFormRequest form) throws Exception;

    Response findByCustomerId(OrderFormRequest form) throws Exception;

    Response findAll(String requestUri) throws Exception;
}
