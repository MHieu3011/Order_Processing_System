package com.ptit.ops.dao;

import com.ptit.ops.model.response.InfoProductResponse;

import java.util.List;

public interface ProductDAO {

    List<InfoProductResponse> findAll() throws Exception;
}
