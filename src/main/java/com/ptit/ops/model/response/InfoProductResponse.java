package com.ptit.ops.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InfoProductResponse {

    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("price")
    private float price;

    @Expose
    @SerializedName("type")
    private String type;

    @Expose
    @SerializedName("total_quantity")
    private int totalQuantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
