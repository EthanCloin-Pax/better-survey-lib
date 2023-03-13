package com.example.bettersurveylib.api.requests;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Request data associated with provided terminal
 */
public class GetTerminalInfoReq {

    @SerializedName("manufacturer")
    public String manufacturer;

    @SerializedName("model")
    public String model;

    @SerializedName("terminalSN")
    public String terminalSN;

    @SerializedName("certificate")
    public String certificate;


    public GetTerminalInfoReq(String manufacturer, String model, String terminalSN) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.terminalSN = terminalSN;

        this.certificate = "dummy cert";
    }
}
