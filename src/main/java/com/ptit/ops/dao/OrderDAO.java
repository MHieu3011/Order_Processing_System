package com.ptit.ops.dao;

import com.ptit.ops.entities.OrderEntity;
import com.ptit.ops.model.response.InfoOrderResponse;

import java.util.List;

public interface OrderDAO {

    InfoOrderResponse create(OrderEntity entity) throws Exception;

    List<InfoOrderResponse> findByCustomerId(int customerId) throws Exception;

    List<InfoOrderResponse> findAll() throws Exception;
}
