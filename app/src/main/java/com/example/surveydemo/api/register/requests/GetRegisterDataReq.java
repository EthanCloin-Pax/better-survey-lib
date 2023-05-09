package com.example.surveydemo.api.register.requests;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Request URL to web form allowing user to register their terminal with Seamless Commerce
 */
public class GetRegisterDataReq extends BaseRegisterRequest {

    @SerializedName("requestFeature")
    public List<String> requestFeatures;

    public GetRegisterDataReq(String manufacturer, String model, String terminalSN, String certificate, List<String> requestFeatures) {
        super(manufacturer, model, terminalSN, certificate);
        this.requestFeatures = requestFeatures;
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
