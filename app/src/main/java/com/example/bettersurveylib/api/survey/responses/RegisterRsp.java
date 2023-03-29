package com.example.bettersurveylib.api.survey.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class RegisterRsp extends BaseSurveyResponse {

    @SerializedName("Token")
    public String token;

    @SerializedName("StoreID")
    public String storeId;

    @SerializedName("DeviceID")
    public String deviceId;


    public RegisterRsp(String resultCode, String resultMessage) {
        super(resultCode, resultMessage);
    }

    public RegisterRsp(String signatureData, String timestamp, String token, String storeId, String deviceId, String resultCode, String resultMessage, Map extData, List extDataList) {
        super(signatureData, timestamp, resultCode, resultMessage, extData, extDataList);
        this.token = token;
        this.storeId = storeId;
        this.storeId = storeId;
        this.deviceId = deviceId;
    }
}
