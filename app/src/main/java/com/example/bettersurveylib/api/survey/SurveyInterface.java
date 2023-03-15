package com.example.bettersurveylib.api.survey;

import com.example.bettersurveylib.api.survey.requests.GetQuestionnairesReq;
import com.example.bettersurveylib.api.survey.responses.GetQuestionnairesRsp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SurveyInterface {

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("Survey/api/SurveyAPI/GetQuestionnaires")
    Call<GetQuestionnairesRsp> doGetQuestionnaires(@Body GetQuestionnairesReq req);

//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("Survey/api/SurveyAPI/GetQuestions")
//    Call<GetQuestionsRsp> doGetQuestions(@Body GetQuestionsReq req);
//
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("Survey/api/SurveyAPI/UploadAnswers")
//    Call<UploadAnswerRsp> doUploadAnswers(@Body UploadAnswerReq req);

}
