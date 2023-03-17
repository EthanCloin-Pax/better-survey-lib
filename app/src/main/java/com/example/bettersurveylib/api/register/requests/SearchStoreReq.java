package com.example.bettersurveylib.api.register.requests;

import com.google.gson.annotations.SerializedName;

public class SearchStoreReq extends BaseRegisterRequest {

    @SerializedName("storeName")
    public String storeName;

    public SearchStoreReq(String manufacturer, String model, String terminalSN, String certificate) {
        super(manufacturer, model, terminalSN, certificate);
    }

}
