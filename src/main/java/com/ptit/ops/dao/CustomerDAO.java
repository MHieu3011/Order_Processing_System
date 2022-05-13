package com.ptit.ops.dao;

import com.ptit.ops.entities.CustomerEntity;
import com.ptit.ops.model.response.InfoCustomerResponse;

import java.util.List;

public interface CustomerDAO {

    InfoCustomerResponse create(CustomerEntity entity) throws Exception;

    InfoCustomerResponse update(CustomerEntity entity) throws Exception;

    List<InfoCustomerResponse> findAll() throws Exception;
}
