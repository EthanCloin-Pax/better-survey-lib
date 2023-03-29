package com.example.bettersurveylib.api.survey.requests;

import com.google.gson.annotations.SerializedName;

/**
 * contains the four common parameters for every request to Survey API
 */
public class BaseSurveyRequest {

    @SerializedName("Token")
    private String token;

    @SerializedName("DeviceID")
    private String deviceID;

    @SerializedName("TimeStamp")
    private String timestamp;

    @SerializedName("SignatureData")
    private String signatureData;

    public String getToken() {
        return token;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getSignatureData() {
        return signatureData;
    }

    public void setSignatureData(String signatureData) {
        this.signatureData = signatureData;
    }

    public BaseSurveyRequest(String token, String deviceID, String timestamp, String signatureData) {
        this.token = token;
        this.deviceID = deviceID;
        this.timestamp = timestamp;
        this.signatureData = signatureData;
    }

    @Override
    public String toString() {
        return "BaseSurveyRequest{" +
                "token='" + token + '\'' +
                ", deviceID='" + deviceID + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", signatureData='" + signatureData + '\'' +
                '}';
    }
}
