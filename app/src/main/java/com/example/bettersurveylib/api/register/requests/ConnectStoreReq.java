package com.example.bettersurveylib.api.register.requests;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConnectStoreReq extends BaseRegisterRequest {
    @SerializedName("storeGuid")
    public String storeGuid;

    @SerializedName("requestFeature")
    public List<String> requestFeature;

    public ConnectStoreReq(String manufacturer, String model, String terminalSN, String certificate) {
        super(manufacturer, model, terminalSN, certificate);
    }
}
