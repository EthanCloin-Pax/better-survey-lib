package com.example.bettersurveylib.api.survey;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SurveyInterface {

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("Survey/api/SurveyAPI/GetQuestionnaires")
    Observable<GetQuestionnairesRsp> post_GetQuestionnaires(@Body GetQuestionnairesReq req);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("Survey/api/SurveyAPI/GetQuestions")
    Observable<GetQuestionsRsp> post_GetQuestions(@Body GetQuestionsReq req);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("Survey/api/SurveyAPI/UploadAnswers")
    Observable<UploadAnswerRsp> post_UploadAnswers(@Body UploadAnswerReq req);

}
