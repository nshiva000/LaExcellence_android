package com.gmail.hyd.laexcellence.Models.RaiseDoubt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RaiseDoubt {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}