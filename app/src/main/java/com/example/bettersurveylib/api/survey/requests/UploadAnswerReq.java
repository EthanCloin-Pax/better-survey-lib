package com.example.bettersurveylib.api.survey.requests;

import com.example.bettersurveylib.api.survey.models.AnswerOption;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Request to submit answers to Survey API
 */
public class UploadAnswerReq extends BaseSurveyRequest {

    @SerializedName("options")
    public List<AnswerOption> options;

    public UploadAnswerReq(String deviceID, String token, List<AnswerOption> options) {
        super(deviceID, token);
        this.options = options;
    }

    public UploadAnswerReq(List<AnswerOption> options, String token, String deviceID, String timestamp, String signatureData) {
        super(deviceID, token, timestamp, signatureData);
        this.options = options;
    }
}
