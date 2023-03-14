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

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("{");
        b.append("\"manufacturer\": ").append(manufacturer).append("\",");
        b.append("\"model\": ").append(model).append("\",");
        b.append("\"terminalSN\": ").append(terminalSN).append("\",");
        b.append("\"requestFeatures\": ").append(requestFeatures).append("\",");
        b.append("\"certificate\": ").append(certificate).append("\",");

        return b.toString();
    }
}
