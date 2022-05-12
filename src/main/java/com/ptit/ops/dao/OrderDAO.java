package com.ptit.ops.dao;

import com.ptit.ops.entities.OrderEntity;
import com.ptit.ops.model.response.InfoOrderResponse;

public interface OrderDAO {

    InfoOrderResponse create(OrderEntity entity) throws Exception;
}
