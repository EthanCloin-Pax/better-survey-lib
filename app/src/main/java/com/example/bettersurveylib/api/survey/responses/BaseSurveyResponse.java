package com.example.bettersurveylib.api.survey.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class BaseSurveyResponse {
    @SerializedName("SignatureData")
    public String signatureData;

    @SerializedName("TimeStamp")
    public String timestamp;

    @SerializedName("ResultCode")
    public String resultCode;

    @SerializedName("ResultMessage")
    public String resultMessage;

    @SerializedName("ExtData")
    public Map ExtData;

    @SerializedName("ExtDataList")
    public List ExtDataList;

    public BaseSurveyResponse(String signatureData, String timestamp, String resultCode, String resultMessage, Map extData, List extDataList) {
        this.signatureData = signatureData;
        this.timestamp = timestamp;
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        ExtData = extData;
        ExtDataList = extDataList;
    }
}
