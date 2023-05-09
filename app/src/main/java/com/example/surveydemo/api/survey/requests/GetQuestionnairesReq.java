package com.example.surveydemo.api.survey.requests;


public class GetQuestionnairesReq extends BaseSurveyRequest {

  public GetQuestionnairesReq(String token, String deviceID, String timestamp, String signatureData) {
    super(token, deviceID, timestamp, signatureData);
  }
  public GetQuestionnairesReq(String deviceId, String token) {
    super(deviceId, token);
  }
}
