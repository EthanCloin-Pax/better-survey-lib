package com.example.bettersurveylib.api.survey.requests;

import com.google.gson.annotations.SerializedName;

/**
 * contains the four common parameters for every request to Survey API
 * <p>
 * Expect to provide DeviceID and Token on each request, but for TimeStamp and SignatureData
 * to be generated at runtime by the Authenticator.
 */
public class BaseSurveyRequest {

    @SerializedName("DeviceID")
    private String deviceID;

    @SerializedName("Token")
    private String token;

    @SerializedName("TimeStamp")
    private String timestamp;

    @SerializedName("SignatureData")
    private String signatureData;

    public BaseSurveyRequest(String deviceID, String token) {
        this.deviceID = deviceID;
        this.token = token;
    }

    public BaseSurveyRequest(String deviceID, String token, String timestamp, String signatureData) {
        this.token = token;
        this.deviceID = deviceID;
        this.timestamp = timestamp;
        this.signatureData = signatureData;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignatureData() {
        return signatureData;
    }

    public void setSignatureData(String signatureData) {
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
