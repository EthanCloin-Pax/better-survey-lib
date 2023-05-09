package com.example.surveydemo.api.register.requests;

import com.google.gson.annotations.SerializedName;

public class BaseRegisterRequest {

    @SerializedName("manufacturer")
    public String manufacturer;

    @SerializedName("model")
    public String model;

    @SerializedName("terminalSN")
    public String terminalSN;

    @SerializedName("certificate")
    public String certificate;

    public BaseRegisterRequest(String manufacturer, String model, String terminalSN, String certificate) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.terminalSN = terminalSN;
        this.certificate = certificate;
    }
}
