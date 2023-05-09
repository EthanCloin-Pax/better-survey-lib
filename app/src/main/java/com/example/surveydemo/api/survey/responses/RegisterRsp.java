package com.example.surveydemo.api.survey.responses;

import com.google.gson.annotations.SerializedName;

public class RegisterRsp extends BaseSurveyResponse {

    @SerializedName("Token")
    public String token;

    @SerializedName("StoreID")
    public String storeId;

    @SerializedName("DeviceID")
    public String deviceId;

    @SerializedName("RequestEncryptKey")
    private String requestEncryptKey;

    @SerializedName("ResponseEncryptKey")
    private String responseEncryptKey;



    public RegisterRsp(String resultCode, String resultMessage) {
        super(resultCode, resultMessage);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getRequestEncryptKey() {
        return requestEncryptKey;
    }

    public void setRequestEncryptKey(String requestEncryptKey) {
        this.requestEncryptKey = requestEncryptKey;
    }

    public String getResponseEncryptKey() {
        return responseEncryptKey;
    }

    public void setResponseEncryptKey(String responseEncryptKey) {
        this.responseEncryptKey = responseEncryptKey;
    }
}
