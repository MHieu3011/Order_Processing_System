package com.ptit.ops.service.impl;

import com.ptit.ops.builder.Response;
import com.ptit.ops.dao.CustomerDAO;
import com.ptit.ops.entities.CustomerEntity;
import com.ptit.ops.exception.CommonException;
import com.ptit.ops.global.ErrorCode;
import com.ptit.ops.model.request.CustomerFormRequest;
import com.ptit.ops.model.response.InfoCustomerResponse;
import com.ptit.ops.service.CustomerService;
import com.ptit.ops.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl extends AbstractService implements CustomerService {

    @Autowired
    private CustomerDAO customerDAO;

    @Override
    public Response create(CustomerFormRequest form) throws Exception {
        //validate dữ liệu đầu vào
        String name = form.getName();
        String address = form.getAddress();
        String phone = form.getPhone();
        if (CommonUtils.checkEmpty(name)) {
            throw new CommonException(ErrorCode.NAME_CUSTOMER_MUST_NOT_EMPTY, "Name customer must not empty");
        }
        if (CommonUtils.checkEmpty(address)) {
            throw new CommonException(ErrorCode.ADDRESS_CUSTOMER_MUST_NOT_EMPTY, "Address customer must not empty");
        }
        if (CommonUtils.checkEmpty(phone)) {
            throw new CommonException(ErrorCode.PHONE_CUSTOMER_MUST_NOT_EMPTY, "Phone customer must not empty");
        }
        if (!phone.matches("\\d+")) {
            throw new CommonException(ErrorCode.PHONE_INVALID, "Phone customer invalid");
        }

        CustomerEntity entity = new CustomerEntity();
        entity.setName(name);
        entity.setAddress(address);
        entity.setPhone(phone);
        InfoCustomerResponse result = customerDAO.create(entity);

        if (result.getName() != null) {
            return new Response.Builder(1, HttpStatus.OK.value())
                    .buildData(result)
                    .buildMessage("Create customer successfully")
                    .build();
        } else {
            return new Response.Builder(0, HttpStatus.OK.value())
                    .buildMessage("Create customer error")
                    .build();
        }
    }

    @Override
    public Response update(CustomerFormRequest form) throws Exception {
        //validate dữ liệu đầu vào
        int id = form.getId();
        ;
        String name = form.getName();
        String address = form.getAddress();
        String phone = form.getPhone();
        if (id <= 0) {
            throw new CommonException(ErrorCode.ID_INVALID, "ID invalid");
        }
        if (CommonUtils.checkEmpty(name)) {
            throw new CommonException(ErrorCode.NAME_CUSTOMER_MUST_NOT_EMPTY, "Name customer must not empty");
        }
        if (CommonUtils.checkEmpty(address)) {
            throw new CommonException(ErrorCode.ADDRESS_CUSTOMER_MUST_NOT_EMPTY, "Address customer must not empty");
        }
        if (CommonUtils.checkEmpty(phone)) {
            throw new CommonException(ErrorCode.PHONE_CUSTOMER_MUST_NOT_EMPTY, "Phone customer must not empty");
        }
        if (!phone.matches("\\d+")) {
            throw new CommonException(ErrorCode.PHONE_INVALID, "Phone customer invalid");
        }

        CustomerEntity entity = new CustomerEntity();
        entity.setId(id);
        entity.setName(name);
        entity.setAddress(address);
        entity.setPhone(phone);
        InfoCustomerResponse result = customerDAO.update(entity);

        if (result.getName() != null) {
            return new Response.Builder(1, HttpStatus.OK.value())
                    .buildData(result)
                    .buildMessage("Create customer successfully")
                    .build();
        } else {
            return new Response.Builder(0, HttpStatus.OK.value())
                    .buildMessage("Create customer error")
                    .build();
        }
    }

    @Override
    public Response findAll(String requestUri) throws Exception {
        List<InfoCustomerResponse> result = customerDAO.findAll();

        if (!result.isEmpty()) {
            return new Response.Builder(1, HttpStatus.OK.value())
                    .buildData(result)
                    .buildMessage("Find all customer successfully")
                    .build();
        } else {
            return new Response.Builder(0, HttpStatus.OK.value())
                    .buildMessage("Find all customer error")
                    .build();
        }
    }
}
