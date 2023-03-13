package com.example.bettersurveylib.api.requests;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Request URL to web form allowing user to register their terminal with Seamless Commerce
 */
public class GetRegisterUrlReq {

    @SerializedName("manufacturer")
    public String manufacturer;

    @SerializedName("model")
    public String model;

    @SerializedName("terminalSN")
    public String terminalSN;

    @SerializedName("certificate")
    public String certificate;

    @SerializedName("requestFeature")
    public List<String> requestFeatures;

    public GetRegisterUrlReq(String manufacturer, String model, String terminalSN, List<String> requestFeatures) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.terminalSN = terminalSN;
        this.requestFeatures = requestFeatures;
        this.certificate = "dummy cert";
    }
}
