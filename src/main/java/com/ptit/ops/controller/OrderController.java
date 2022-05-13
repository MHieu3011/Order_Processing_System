package com.ptit.ops.controller;

import com.ecyrd.speed4j.StopWatch;
import com.ptit.ops.builder.Response;
import com.ptit.ops.exception.CommonException;
import com.ptit.ops.model.request.OrderFormRequest;
import com.ptit.ops.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    //Create a customer
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> create(
            @RequestParam("customer_id") int customerId,
            @RequestParam("product_id") int productId,
            @RequestParam("amount") int amount,
            @RequestParam("order_date") String orderDate,
            HttpServletRequest request
    ) {
        StopWatch stopWatch = new StopWatch();
        String requestUri = request.getRequestURI() + "?" + getRequestParams(request);
        String strResponse;
        Response serverResponse;
        try {
            OrderFormRequest form = new OrderFormRequest();
            form.setRequestUri(requestUri);
            form.setCustomerId(customerId);
            form.setProductId(productId);
            form.setAmount(amount);
            form.setOrderDate(orderDate);

            serverResponse = orderService.create(form);
            strResponse = gson.toJson(serverResponse, Response.class);
            requestLogger.info("Finish OrderController.create {} in {}", requestUri, stopWatch.stop());
        } catch (CommonException ce) {
            eLogger.error("OrderController Error: {}", ce.getMessage());
            strResponse = buildFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ce.getMessage());
        } catch (Exception e) {
            eLogger.error("OrderController.create error: {}", e.getMessage());
            strResponse = buildFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ERROR_OCCURRED);
        }
        return new ResponseEntity<>(strResponse, HttpStatus.OK);
    }

    //search order info by customerId
    @GetMapping(value = "/id", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByCustomerId(
            @RequestParam("customer_id") int customerId,
            HttpServletRequest request
    ) {
        StopWatch stopWatch = new StopWatch();
        String requestUri = request.getRequestURI() + "?" + getRequestParams(request);
        String strResponse;
        Response serverResponse;
        try {
            OrderFormRequest form = new OrderFormRequest();
            form.setRequestUri(requestUri);
            form.setCustomerId(customerId);
            serverResponse = orderService.findByCustomerId(form);
            strResponse = gson.toJson(serverResponse, Response.class);
            requestLogger.info("Finish OrderController.findByCustomerId {} in {}", requestUri, stopWatch.stop());
        } catch (CommonException ce) {
            eLogger.error("OrderController Error: {}", ce.getMessage());
            strResponse = buildFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ce.getMessage());
        } catch (Exception e) {
            eLogger.error("OrderController.findByCustomerId error: {}", e.getMessage());
            strResponse = buildFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ERROR_OCCURRED);
        }
        return new ResponseEntity<>(strResponse, HttpStatus.OK);
    }

    //find all order
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll(
            HttpServletRequest request
    ) {
        StopWatch stopWatch = new StopWatch();
        String requestUri = request.getRequestURI() + "?" + getRequestParams(request);
        String strResponse;
        Response serverResponse;
        try {

            serverResponse = orderService.findAll(requestUri);
            strResponse = gson.toJson(serverResponse, Response.class);
            requestLogger.info("Finish OrderController.findAll {} in {}", requestUri, stopWatch.stop());
        } catch (CommonException ce) {
            eLogger.error("OrderController Error: {}", ce.getMessage());
            strResponse = buildFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ce.getMessage());
        } catch (Exception e) {
            eLogger.error("OrderController.findAll error: {}", e.getMessage());
            strResponse = buildFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ERROR_OCCURRED);
        }
        return new ResponseEntity<>(strResponse, HttpStatus.OK);
    }
}
