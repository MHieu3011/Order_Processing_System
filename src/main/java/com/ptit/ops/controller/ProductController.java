package com.ptit.ops.controller;

import com.ecyrd.speed4j.StopWatch;
import com.ptit.ops.builder.Response;
import com.ptit.ops.exception.CommonException;
import com.ptit.ops.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/product")
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll(
            HttpServletRequest request
    ) {
        StopWatch stopWatch = new StopWatch();
        String requestUri = request.getRequestURI() + "?" + getRequestParams(request);
        String strResponse;
        Response serverResponse;
        try {

            serverResponse = productService.findAll(requestUri);
            strResponse = gson.toJson(serverResponse, Response.class);
            requestLogger.info("Finish ProductController.findAll {} in {}", requestUri, stopWatch.stop());
        } catch (CommonException ce) {
            eLogger.error("ProductController Error: {}", ce.getMessage());
            strResponse = buildFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ce.getMessage());
        } catch (Exception e) {
            eLogger.error("ProductController.findAll error: {}", e.getMessage());
            strResponse = buildFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ERROR_OCCURRED);
        }
        return new ResponseEntity<>(strResponse, HttpStatus.OK);
    }
}
