package com.example.bettersurveylib.api.survey;

import com.example.bettersurveylib.api.survey.requests.GetQuestionnairesReq;
import com.example.bettersurveylib.api.survey.requests.GetQuestionsReq;
import com.example.bettersurveylib.api.survey.requests.UploadAnswerReq;
import com.example.bettersurveylib.api.survey.responses.GetQuestionnairesRsp;
import com.example.bettersurveylib.api.survey.responses.GetQuestionsRsp;
import com.example.bettersurveylib.api.survey.responses.RegisterRsp;
import com.example.bettersurveylib.api.survey.responses.UploadAnswerRsp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SurveyInterface {

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("Survey/api/SurveyAPI/Register")
    Call<RegisterRsp> doRegister(@Body RegisterRsp req);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("Survey/api/SurveyAPI/GetQuestionnaires")
    Call<GetQuestionnairesRsp> doGetQuestionnaires(@Body GetQuestionnairesReq req);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("Survey/api/SurveyAPI/GetQuestions")
    Call<GetQuestionsRsp> doGetQuestions(@Body GetQuestionsReq req);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("Survey/api/SurveyAPI/UploadAnswers")
    Call<UploadAnswerRsp> doUploadAnswers(@Body UploadAnswerReq req);

}
