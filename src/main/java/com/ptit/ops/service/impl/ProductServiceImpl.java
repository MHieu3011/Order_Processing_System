package com.ptit.ops.service.impl;

import com.ptit.ops.builder.Response;
import com.ptit.ops.cache.local.ResponseLocalCache;
import com.ptit.ops.dao.ProductDAO;
import com.ptit.ops.model.response.InfoProductResponse;
import com.ptit.ops.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl extends AbstractService implements ProductService {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    @Qualifier("responseLocalCache")
    private ResponseLocalCache responseLocalCache;

    @Override
    public Response findAll(String requestUri) throws Exception {
        //gọi cache, nếu cache có trả về client
        List<InfoProductResponse> result = (List<InfoProductResponse>) responseLocalCache.get(requestUri);
        if (result == null) {
            //nếu cache không có thì lấy từ DAO và put cache
            result = productDAO.findAll();
            responseLocalCache.put(requestUri, result);
        }

        if (!result.isEmpty()) {
            return new Response.Builder(1, HttpStatus.OK.value())
                    .buildData(result)
                    .buildMessage("Find all product successfully")
                    .build();
        } else {
            return new Response.Builder(0, HttpStatus.OK.value())
                    .buildMessage("Find all product error")
                    .build();
        }
    }
}
