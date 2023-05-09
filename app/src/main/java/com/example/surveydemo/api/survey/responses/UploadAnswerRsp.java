package com.example.surveydemo.api.survey.responses;

import java.util.List;
import java.util.Map;

public class UploadAnswerRsp extends BaseSurveyResponse {

    public UploadAnswerRsp(String signatureData, String timestamp, String resultCode, String resultMessage, Map extData, List extDataList) {
        super(signatureData, timestamp, resultCode, resultMessage, extData, extDataList);
    }
}
