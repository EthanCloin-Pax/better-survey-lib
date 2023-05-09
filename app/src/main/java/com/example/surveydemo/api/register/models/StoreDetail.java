package com.example.surveydemo.api.register.models;

import com.google.gson.annotations.SerializedName;

public class StoreDetail {
    @SerializedName("storeGuid")
    public String storeGuid;

    @SerializedName("storeName")
    public String storeName;

    @SerializedName("storeNum")
    public String storeNum;

    public StoreDetail(String storeGuid, String storeName, String storeNum) {
        this.storeGuid = storeGuid;
        this.storeName = storeName;
        this.storeNum = storeNum;
    }
}
