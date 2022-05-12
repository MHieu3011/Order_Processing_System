package com.ptit.ops.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InfoOrderResponse {

    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("customer_id")
    private int customerId;

    @Expose
    @SerializedName("product_id")
    private int productId;

    @Expose
    @SerializedName("amount")
    private int amount;

    @Expose
    @SerializedName("order_date")
    private String orderDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
