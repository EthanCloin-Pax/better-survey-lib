package com.example.surveydemo.api.survey;

import com.paxus.pay.host.ui.init.surveydemo.api.survey.requests.GetQuestionnairesReq;
import com.paxus.pay.host.ui.init.surveydemo.api.survey.requests.GetQuestionsReq;
import com.paxus.pay.host.ui.init.surveydemo.api.survey.requests.RegisterReq;
import com.paxus.pay.host.ui.init.surveydemo.api.survey.requests.UploadAnswerReq;
import com.paxus.pay.host.ui.init.surveydemo.api.survey.responses.GetQuestionnairesRsp;
import com.paxus.pay.host.ui.init.surveydemo.api.survey.responses.GetQuestionsRsp;
import com.paxus.pay.host.ui.init.surveydemo.api.survey.responses.RegisterRsp;
import com.paxus.pay.host.ui.init.surveydemo.api.survey.responses.UploadAnswerRsp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SurveyInterface {

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("Survey/api/SurveyAPI/Register")
    Call<RegisterRsp> doRegister(@Body RegisterReq req);

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
