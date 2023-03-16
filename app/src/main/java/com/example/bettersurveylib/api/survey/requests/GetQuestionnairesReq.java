package com.example.bettersurveylib.api.survey.requests;


public class GetQuestionnairesReq extends BaseSurveyRequest {

  public GetQuestionnairesReq(String token, String deviceID, String timestamp, String signatureData) {
    super(token, deviceID, timestamp, signatureData);
  }
}
