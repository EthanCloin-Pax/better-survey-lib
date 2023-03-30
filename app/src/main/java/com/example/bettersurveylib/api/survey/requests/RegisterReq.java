package com.example.bettersurveylib.api.survey.requests;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class RegisterReq {

    @SerializedName("TimeStamp")
    public String timestamp;

    @SerializedName("SignatureData")
    public String signatureData;

    @SerializedName("DeviceID")
    public String deviceID;

    @Nullable
    @SerializedName("DeviceSN")
    public String deviceSN;

    @Nullable
    @SerializedName("AppkeyIdentity")
    public String appKeyIdentity;

    public RegisterReq(String deviceID) {
        this.deviceID = deviceID;
    }

    @Override
    public String toString() {
        return "RegisterReq{" +
                "timestamp='" + timestamp + '\'' +
                ", signatureData='" + signatureData + '\'' +
                ", deviceID='" + deviceID + '\'' +
                ", deviceSN='" + deviceSN + '\'' +
                ", appKeyIdentity='" + appKeyIdentity + '\'' +
                '}';
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

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    @Nullable
    public String getDeviceSN() {
        return deviceSN;
    }

    public void setDeviceSN(@Nullable String deviceSN) {
        this.deviceSN = deviceSN;
    }

    @Nullable
    public String getAppKeyIdentity() {
        return appKeyIdentity;
    }

    public void setAppKeyIdentity(@Nullable String appKeyIdentity) {
        this.appKeyIdentity = appKeyIdentity;
    }
}
