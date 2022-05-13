package com.ptit.ops.service;

import com.ptit.ops.builder.Response;
import com.ptit.ops.model.request.CustomerFormRequest;

public interface CustomerService {

    Response create(CustomerFormRequest form) throws Exception;

    Response update(CustomerFormRequest form) throws Exception;

    Response findAll(String requestUri) throws Exception;
}
