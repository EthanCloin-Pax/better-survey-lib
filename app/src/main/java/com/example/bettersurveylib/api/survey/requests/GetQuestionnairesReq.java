package com.example.bettersurveylib.api.survey.requests;

/**
 * Request to retrieve a list of questionnaires from Survey API
 */
public class GetQuestionnairesReq extends BaseSurveyRequest {

  public GetQuestionnairesReq(String deviceId, String token) {
    super(deviceId, token);
  }

  public GetQuestionnairesReq(String token, String deviceID, String timestamp, String signatureData) {
    super(token, deviceID, timestamp, signatureData);
  }
}
