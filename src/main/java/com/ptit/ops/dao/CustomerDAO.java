package com.ptit.ops.dao;

import com.ptit.ops.entities.CustomerEntity;
import com.ptit.ops.model.response.InfoCustomerResponse;

public interface CustomerDAO {

    InfoCustomerResponse create(CustomerEntity entity) throws Exception;
}
