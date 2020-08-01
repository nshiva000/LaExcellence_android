package com.gmail.hyd.laexcellence.Models.Product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("pic")
    @Expose
    private String pic;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("baught")
    @Expose
    private Boolean baught;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getBaught() {
        return baught;
    }

    public void setBaught(Boolean baught) {
        this.baught = baught;
    }

}