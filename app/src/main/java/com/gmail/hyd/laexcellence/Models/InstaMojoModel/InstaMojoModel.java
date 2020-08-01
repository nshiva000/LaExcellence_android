package com.gmail.hyd.laexcellence.Models.InstaMojoModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InstaMojoModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("txnId")
    @Expose
    private String txnId;
    @SerializedName("paymentId")
    @Expose
    private String paymentId;
    @SerializedName("token")
    @Expose
    private String token;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}