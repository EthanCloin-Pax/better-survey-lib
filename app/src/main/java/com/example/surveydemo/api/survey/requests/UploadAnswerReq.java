package com.example.surveydemo.api.survey.requests;

import com.google.gson.annotations.SerializedName;
import com.paxus.pay.host.ui.init.surveydemo.api.survey.models.AnswerOption;

import java.util.List;

public class UploadAnswerReq extends BaseSurveyRequest {

    @SerializedName("options")
    List<AnswerOption> options;

    public List<AnswerOption> getOptions() {
        return options;
    }

    public UploadAnswerReq(List<AnswerOption> options, String token, String deviceID, String timestamp, String signatureData) {
        super(deviceID, token, timestamp, signatureData);
        this.options = options;
    }    public UploadAnswerReq(String deviceID, String token, List<AnswerOption> options) {
        super(deviceID, token);
        this.options = options;
    }
}
