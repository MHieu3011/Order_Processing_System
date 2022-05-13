package com.ptit.ops.service;

import com.ptit.ops.builder.Response;

public interface ProductService {

    Response findAll(String requestUri) throws Exception;
}
