package com.example.bettersurveylib.api.survey.requests;

import com.example.bettersurveylib.api.survey.models.AnswerOption;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UploadAnswerReq extends BaseSurveyRequest {

    @SerializedName("options")
    List<AnswerOption> options;

    public List<AnswerOption> getOptions() {
        return options;
    }

    public UploadAnswerReq(List<AnswerOption> options, String token, String deviceID, String timestamp, String signatureData) {
        super(token, deviceID, timestamp, signatureData);
        this.options = options;
    }
}
