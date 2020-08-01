package com.gmail.hyd.laexcellence.Models.CreateNewTestModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateNewTestModel {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("test_id")
    @Expose
    private Integer testId;

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

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

}