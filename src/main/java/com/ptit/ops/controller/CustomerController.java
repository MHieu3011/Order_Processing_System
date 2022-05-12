package com.ptit.ops.controller;

import com.ecyrd.speed4j.StopWatch;
import com.ptit.ops.builder.Response;
import com.ptit.ops.exception.CommonException;
import com.ptit.ops.model.request.CustomerFormRequest;
import com.ptit.ops.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/customer")
public class CustomerController extends BaseController {

    @Autowired
    private CustomerService customerService;

    //Create a customer
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> create(
            @RequestParam("name") String name,
            @RequestParam("address") String address,
            @RequestParam("phone") String phone,
            HttpServletRequest request
    ) {
        StopWatch stopWatch = new StopWatch();
        String requestUri = request.getRequestURI() + "?" + getRequestParams(request);
        String strResponse;
        Response serverResponse;
        try {
            CustomerFormRequest form = new CustomerFormRequest();
            form.setRequestUri(requestUri);
            form.setName(name);
            form.setAddress(address);
            form.setPhone(phone);

            serverResponse = customerService.create(form);
            strResponse = gson.toJson(serverResponse, Response.class);
            requestLogger.info("Finish CustomerController.create {} in {}", requestUri, stopWatch.stop());
        } catch (CommonException ce) {
            eLogger.error("CustomerController Error: {}", ce.getMessage());
            strResponse = buildFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ce.getMessage());
        } catch (Exception e) {
            eLogger.error("CustomerController.create error: {}", e.getMessage());
            strResponse = buildFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ERROR_OCCURRED);
        }
        return new ResponseEntity<>(strResponse, HttpStatus.OK);
    }

    //Update a customer
    @PutMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> update(
            @RequestParam("id") int id,
            @RequestParam("name") String name,
            @RequestParam("address") String address,
            @RequestParam("phone") String phone,
            HttpServletRequest request
    ) {
        StopWatch stopWatch = new StopWatch();
        String requestUri = request.getRequestURI() + "?" + getRequestParams(request);
        String strResponse;
        Response serverResponse;
        try {
            CustomerFormRequest form = new CustomerFormRequest();
            form.setRequestUri(requestUri);
            form.setId(id);
            form.setName(name);
            form.setAddress(address);
            form.setPhone(phone);

            serverResponse = customerService.update(form);
            strResponse = gson.toJson(serverResponse, Response.class);
            requestLogger.info("Finish CustomerController.create {} in {}", requestUri, stopWatch.stop());
        } catch (CommonException ce) {
            eLogger.error("CustomerController Error: {}", ce.getMessage());
            strResponse = buildFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ce.getMessage());
        } catch (Exception e) {
            eLogger.error("CustomerController.create error: {}", e.getMessage());
            strResponse = buildFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ERROR_OCCURRED);
        }
        return new ResponseEntity<>(strResponse, HttpStatus.OK);
    }
}
