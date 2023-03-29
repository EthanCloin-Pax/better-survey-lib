package com.example.bettersurveylib.api.survey.requests;

import com.google.gson.annotations.SerializedName;

public class RegisterReq extends BaseSurveyRequest {

    @SerializedName("DeviceSN")
    public String deviceSN;

    public RegisterReq(String token, String deviceID, String timestamp, String signatureData) {
        super(token, deviceID, timestamp, signatureData);
    }

    public RegisterReq(String token, String deviceID, String timestamp, String signatureData, String deviceSN) {
        super(token, deviceID, timestamp, signatureData);
        this.deviceSN = deviceSN;
    }
}
