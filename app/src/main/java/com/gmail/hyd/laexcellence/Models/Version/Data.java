package com.gmail.hyd.laexcellence.Models.Version;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("android_version")
    @Expose
    private Double androidVersion;

    public Double getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(Double androidVersion) {
        this.androidVersion = androidVersion;
    }

}