package com.ptit.ops.service.impl;

import com.ptit.ops.builder.Response;
import com.ptit.ops.dao.OrderDAO;
import com.ptit.ops.entities.OrderEntity;
import com.ptit.ops.exception.CommonException;
import com.ptit.ops.global.ErrorCode;
import com.ptit.ops.model.request.OrderFormRequest;
import com.ptit.ops.model.response.InfoOrderResponse;
import com.ptit.ops.service.OrderService;
import com.ptit.ops.utils.CommonUtils;
import com.ptit.ops.utils.DateTimeUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends AbstractService implements OrderService {

    @Autowired
    private OrderDAO orderDAO;

    @Override
    public Response create(OrderFormRequest form) throws Exception {
        //validate dữ liệu đầu vào
        int customerId = form.getCustomerId();
        int productId = form.getProductId();
        int amount = form.getAmount();
        String orderDate = form.getOrderDate();
        if (customerId <= 0) {
            throw new CommonException(ErrorCode.ID_INVALID, "Customer ID invalid");
        }
        if (productId <= 0) {
            throw new CommonException(ErrorCode.ID_INVALID, "Product ID invalid");
        }
        if (amount <= 0) {
            throw new CommonException(ErrorCode.ID_INVALID, "amount invalid");
        }
        if (CommonUtils.checkEmpty(orderDate)) {
            throw new CommonException(ErrorCode.ORDER_DATE_MUST_NOT_EMPTY, "order date must not empty");
        }
        DateTime d1, d2;
        try {
            d1 = DateTimeFormat.forPattern(DateTimeUtils.DEFAULT_DATE_FORMAT).parseDateTime(orderDate);
            d2 = DateTimeFormat.forPattern(DateTimeUtils.DEFAULT_DATE_FORMAT).parseDateTime(DateTimeUtils.getDateNow());
        } catch (Exception e) {
            throw new CommonException(ErrorCode.ORDER_DATE_FORMAT_INVALID, "date format invalid");
        }
        if (d1.compareTo(d2) > 0) {
            throw new CommonException(ErrorCode.ORDER_DATE_FORMAT_INVALID, "The order date cannot be less than the current date");
        }

        OrderEntity entity = new OrderEntity();
        entity.setCustomerId(customerId);
        entity.setProductId(productId);
        entity.setAmount(amount);
        entity.setOrderDate(DateTimeUtils.getTimeInSecs(orderDate));
        InfoOrderResponse result = orderDAO.create(entity);

        if (result.getOrderDate() != null && !result.getOrderDate().equals("quantity")) {
            return new Response.Builder(1, HttpStatus.OK.value())
                    .buildData(result)
                    .buildMessage("Create order successfully")
                    .build();
        } else if (result.getOrderDate().equals("quantity")) {
            return new Response.Builder(0, ErrorCode.QUANTITY_PRODUCT_INVALID)
                    .buildMessage("quantity product in stock smaller amount product in order")
                    .build();
        } else {
            return new Response.Builder(0, HttpStatus.OK.value())
                    .buildMessage("Create order error")
                    .build();
        }
    }
}
