package com.gmail.hyd.laexcellence.Models.UserSingleDoubts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Discussion {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("d_id")
    @Expose
    private String dId;
    @SerializedName("res")
    @Expose
    private String res;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("admin_id")
    @Expose
    private String adminId;
    @SerializedName("date")
    @Expose
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDId() {
        return dId;
    }

    public void setDId(String dId) {
        this.dId = dId;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}