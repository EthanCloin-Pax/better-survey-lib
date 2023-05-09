package com.example.surveydemo.util;


import static com.example.surveydemo.api.Authenticator.decryptSurveyRequestKey;
import static com.example.surveydemo.ui.register.RegisterActivity.SURVEY_REGISTER_DATA_RECEIVED;
import static com.example.surveydemo.ui.register.RegisterActivity.SURVEY_TERMINAL_REGISTERED;
import static com.example.surveydemo.ui.survey.SurveyActivity.ResultReceiver.EXTRA_SERIALIZABLE;
import static com.example.surveydemo.ui.survey.SurveyActivity.SURVEY_ANSWERS_SUBMITTED;
import static com.example.surveydemo.ui.survey.SurveyActivity.SURVEY_QUESTIONNAIRE_RECEIVED;
import static com.example.surveydemo.ui.survey.SurveyActivity.SURVEY_QUESTION_DATA_RECEIVED;
import static com.example.surveydemo.ui.survey.SurveyActivity.TEMP_SURVEY_QUESTIONNAIRE_RECEIVED;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.example.surveydemo.api.SurveyGateway;
import com.example.surveydemo.api.register.requests.GetRegisterDataReq;
import com.example.surveydemo.api.register.requests.RegisterTerminalReq;
import com.example.surveydemo.api.register.responses.GetRegisterDataRsp;
import com.example.surveydemo.api.register.responses.RegisterTerminalRsp;
import com.example.surveydemo.api.survey.QuestionHolder;
import com.example.surveydemo.api.survey.models.AnswerOption;
import com.example.surveydemo.api.survey.models.Question;
import com.example.surveydemo.api.survey.requests.GetQuestionnairesReq;
import com.example.surveydemo.api.survey.requests.GetQuestionsReq;
import com.example.surveydemo.api.survey.requests.RegisterReq;
import com.example.surveydemo.api.survey.requests.UploadAnswerReq;
import com.example.surveydemo.api.survey.responses.GetQuestionnairesRsp;
import com.example.surveydemo.api.survey.responses.GetQuestionsRsp;
import com.example.surveydemo.api.survey.responses.RegisterRsp;
import com.example.surveydemo.api.survey.responses.UploadAnswerRsp;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurveyUtil {

    private static final SurveyGateway surveyGateway = new SurveyGateway();
    public static final String TAG = "EMC: SurveyUtil - ";

    /**
     * Getters
     */

    public static String getManufacturer() {
        return "PAX";
    }

    public static String getModel() {
//        return ConfigManager.getInstance().getString(DeviceContract.MODEL, null);
        return "A920";
    }

    public static String tempSN = "";

    public static String getSN(Context context) {
//        return ConfigManager.getInstance().getString(DeviceContract.SN, null);
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SURVEY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_SURVEY_SN, RESULT_SURVEY_NO_DATA);
//        return "1718383";
    }

    //FIXME temp
    public static int genRandNum () {
        Random r = new Random( System.currentTimeMillis() );
        return 1000000 + r.nextInt(2000000);
    }
    public static String getDeviceId() {
        return "";
    }

    public static String getCertificate() {
        // TODO: PAXCA DSIG certificate in base64 -> temp: use hardcode values from Neptune API
        return "fake cert";
    }

    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SURVEY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_SURVEY_TOKEN, RESULT_SURVEY_NO_DATA);
//        return "d3ac05b9b66015f575d88b97146ceee680bcd28d0e2b867b5a8b354f66236b331e6ea46fd62be1e41d1a7053c36e93ab15f8b4d8d4a8f1400d4414935cca4a44";
    }

    private static ArrayList<String> getRequestFeatures() {
        return new ArrayList<>(Arrays.asList("Survey"));
    }
    public static String getRequestEncryptKey(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SURVEY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_SURVEY_REQUEST_ENCRYPT_KEY, RESULT_SURVEY_NO_DATA);
    }

    public static String getRegisterRequestEncryptKey(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SURVEY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_SURVEY_REGISTER_REQUEST_ENCRYPTED_KEY, RESULT_SURVEY_NO_DATA);
    }
    public static String getDecryptedRequestKey(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SURVEY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_SURVEY_REQUEST_DECRYPTED_KEY, RESULT_SURVEY_NO_DATA);
    }

    public static String getTempDecryptedKey(){
        return "1344bb4d96ae2375";
    }

    /**
     * Keys
     */

    private static final String KEY_SURVEY_SHARED_PREFERENCES = "key_survey_shared_preferences";
    private static final String KEY_SURVEY_TOKEN = "key_survey_token";
    private static final String KEY_SURVEY_SN = "key_survey_sn";
    private static final String KEY_SURVEY_DEVICE_ID = "key_survey_device_id";
    private static final String RESULT_SURVEY_NO_DATA = "result_survey_no_data";
    private static final String KEY_SURVEY_REQUEST_ENCRYPT_KEY = "KEY_SURVEY_REQUEST_ENCRYPT_KEY";
    public static final String KEY_SURVEY_REGISTER_REQUEST_ENCRYPTED_KEY = "KEY_SURVEY_REGISTER_REQUEST_ENCRYPTED_KEY";
    public static final String KEY_SURVEY_REGISTER_RESPONSE_ENCRYPTED_KEY = "KEY_SURVEY_REGISTER_RESPONSE_ENCRYPTED_KEY";
    public static final String KEY_SURVEY_REQUEST_DECRYPTED_KEY = "KEY_SURVEY_REQUEST_DECRYPTED_KEY";

    /**
     * REGISTRATION: API Calls
     */
    public static void getRegisterData(Context context) {

        //FIXME temp
        storeData(context, new String[]{KEY_SURVEY_SN}, new String[]{String.valueOf(genRandNum())});

        GetRegisterDataReq getRegisterDataReq = new GetRegisterDataReq(getManufacturer(), getModel(), getSN(context), getCertificate(), getRequestFeatures());
        Callback<GetRegisterDataRsp> requestCallbackExample = new Callback<GetRegisterDataRsp>() {
            @Override
            public void onResponse(Call<GetRegisterDataRsp> call, Response<GetRegisterDataRsp> response) {
                Log.i(TAG, "getRegisterData(): " + response);
                GetRegisterDataRsp rspBody = response.body();

                //TODO: URL can be null when it is already registered
                assert rspBody != null;

                Intent intent = new Intent();
                intent.setAction(SURVEY_REGISTER_DATA_RECEIVED);
                intent.putExtra(Intent.EXTRA_TEXT, rspBody.registerUrl);
                context.sendBroadcast(intent);
            }
            @Override
            public void onFailure(Call<GetRegisterDataRsp> call, Throwable t) {
                Log.i(TAG, "it did not work good: " + t.getLocalizedMessage());
                call.cancel();
            }
        };
        surveyGateway.async_requestRegistrationData(getRegisterDataReq, requestCallbackExample);
    }

    public static void registerTerminal(Context context) {
        RegisterTerminalReq registerTerminalReq = new RegisterTerminalReq(getManufacturer(), getModel(), getSN(context), getRequestFeatures());
        Callback<RegisterTerminalRsp> registerCallback = new Callback<RegisterTerminalRsp>() {
            @Override
            public void onResponse(Call<RegisterTerminalRsp> call, Response<RegisterTerminalRsp> response) {
                Log.i(TAG, "registerTerminal(): " + response);
                RegisterTerminalRsp rspBody = response.body();
                String reqKey = rspBody.requestKey;
                String rspKey = rspBody.responseKey;
                storeData(context,
                        new String[]{KEY_SURVEY_REGISTER_REQUEST_ENCRYPTED_KEY, KEY_SURVEY_REGISTER_RESPONSE_ENCRYPTED_KEY},
                        new String[]{reqKey, rspKey});
                // make register call to survey api
                registerSurveyWithTerminal(context, reqKey, rspKey);
            }
            @Override
            public void onFailure(Call<RegisterTerminalRsp> call, Throwable t) {
                Log.i(TAG, "registerTerminal() onFailure: " + t.getLocalizedMessage());
                call.cancel();
            }
        };
        surveyGateway.async_registerTerminalToStore(registerTerminalReq, registerCallback);
    }

    public static void registerSurveyWithTerminal(Context context, String requestKey, String responseKey) {
        RegisterReq req = new RegisterReq(getSN(context)); //FIXME replace
        req.setDeviceSN(getSN(context));
        Callback<RegisterRsp> registerCallback = new Callback<RegisterRsp>() {
            @Override
            public void onResponse(Call<RegisterRsp> call, Response<RegisterRsp> response) {
                Log.i(TAG, "registerSurveyWithTerminal() successful");
                assert response.body() != null;
                String resultCode = response.body().resultCode;
                if (resultCode.equals("200") || resultCode.equals("0000")) {

                    RegisterRsp body = response.body();
                    Log.i(TAG, "decrypt payload: " + body.getRequestEncryptKey() + " " + responseKey);
                    String decryptedKey = decryptSurveyRequestKey(body.getRequestEncryptKey(), responseKey);
                    Log.i(TAG, "decrypt response: " + decryptedKey);
//                    FIXME
                    storeData(context,
                            new String[]{KEY_SURVEY_TOKEN, KEY_SURVEY_DEVICE_ID, KEY_SURVEY_REQUEST_DECRYPTED_KEY},
                            new String[]{body.token, body.deviceId, decryptedKey});

//                    storeData(context,
//                            new String[]{KEY_SURVEY_TOKEN, KEY_SURVEY_DEVICE_ID},
//                            new String[]{body.token, body.deviceId});
//                    storeData(context,
                    // new String[]{KEY_SURVEY_TOKEN, KEY_SURVEY_DEVICE_ID},
                    // new String[]{body.token, body.deviceId});

                }
                Intent intent = new Intent();
                intent.setAction(SURVEY_TERMINAL_REGISTERED);
                intent.putExtra(Intent.EXTRA_TEXT, response.body().resultCode);
                context.sendBroadcast(intent);
                // TODO: This should save to local device AND call gateway function to give access
            }
            public void onFailure(Call<RegisterRsp> call, Throwable t) {
                // TODO: this too should prevent moving forward in UI Flow
                Log.w(TAG, t.getLocalizedMessage());
                call.cancel();
            }
        };
        surveyGateway.async_registerTerminalToSurvey(req, registerCallback, getRegisterRequestEncryptKey(context));
    }

    /**
     * SURVEY: API CALLS
     */

    public static void getQuestionnairesData(Context context) {

        GetQuestionnairesReq req = new GetQuestionnairesReq(getSN(context), getToken(context)); //FIXME
        Callback<GetQuestionnairesRsp> getQuestionnairesCallback = new Callback<GetQuestionnairesRsp>() {
            @Override
            public void onResponse(Call<GetQuestionnairesRsp> call, Response<GetQuestionnairesRsp> response) {
                // just getting the first questionnaire for now, not sure how we will do it in prod
                assert response.body() != null;

                if (response.body().resultCode.equals("0000") || response.body().resultCode.equals("200")) {
                    Intent intent = new Intent();
                    intent.setAction(SURVEY_QUESTIONNAIRE_RECEIVED);
                    intent.putExtra(EXTRA_SERIALIZABLE, response.body().questionnaires.get(0));
                    context.sendBroadcast(intent);

                    requestQuestionsData(context);

                } else {
                    //FIXME: TEMP
                    Intent intent = new Intent();
                    intent.setAction(TEMP_SURVEY_QUESTIONNAIRE_RECEIVED);
                    context.sendBroadcast(intent);
                }
            }

            @Override
            public void onFailure(Call<GetQuestionnairesRsp> call, Throwable t) {
                Log.w(TAG, "FAILED: " + t.getLocalizedMessage());
                call.cancel();
            }
        };
        surveyGateway.async_requestQuestionnaires(req, getQuestionnairesCallback, getDecryptedRequestKey(context));
//        surveyGateway.async_requestQuestionnaires(req, getQuestionnairesCallback, getTempDecryptedKey());
    }
    private static void requestQuestionsData(Context context) {
        GetQuestionsReq req = new GetQuestionsReq(getSN(context), getToken(context), getSN(context)); //FIXME
        Callback<GetQuestionsRsp> getQuestionsCallback = new Callback<GetQuestionsRsp>() {
            @Override
            public void onResponse(Call<GetQuestionsRsp> call, Response<GetQuestionsRsp> response) {
                QuestionHolder questionHolder = QuestionHolder.getInstance();
                assert response.body() != null;

                questionHolder.setQuestionsList(response.body().questions);

                Intent intent = new Intent();
                intent.setAction(SURVEY_QUESTION_DATA_RECEIVED);
                intent.putExtra(EXTRA_SERIALIZABLE, new ArrayList<>(response.body().questions));
                // intent.putExtra(EXTRA_SERIALIZABLE, (Serializable)
                // response.body().questions);
                // intent.putExtra(EXTRA_SERIALIZABLE, new
                // ArrayList<>(response.body().questions));
                context.sendBroadcast(intent);
            }
            @Override
            public void onFailure(Call<GetQuestionsRsp> call, Throwable t) {
                Log.w(TAG, "FAILED: " + t.getLocalizedMessage());
                call.cancel();
            }
        };
        surveyGateway.async_requestQuestions(req, getQuestionsCallback, getDecryptedRequestKey(context));
//        surveyGateway.async_requestQuestions(req, getQuestionsCallback, getTempDecryptedKey());
    }

    public static void submitAnswers(Context context, List<AnswerOption> selectedAnswers) {

        UploadAnswerReq req = new UploadAnswerReq(getSN(context), getToken(context), selectedAnswers);// FIXME
        Callback<UploadAnswerRsp> callback = new Callback<UploadAnswerRsp>() {
            @Override
            public void onResponse(Call<UploadAnswerRsp> call, Response<UploadAnswerRsp> response) {
                Intent intent = new Intent();
                intent.setAction(SURVEY_ANSWERS_SUBMITTED);
                context.sendBroadcast(intent);
            }

            @Override
            public void onFailure(Call<UploadAnswerRsp> call, Throwable t) {
                t.getLocalizedMessage();
                call.cancel();
            }
        };
        surveyGateway.async_uploadAnswers(req, callback, getDecryptedRequestKey(context));
//        surveyGateway.async_uploadAnswers(req, callback, getTempDecryptedKey());
    }

    /**
     * Utils
     */

    public static void storeData(Context context, String[] keys, String[] data) {

        SharedPreferences sharedPref = context.getSharedPreferences(KEY_SURVEY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        for (int i = 0; i < keys.length; i++) {
            editor.putString(keys[i], data[i]);
        }
        editor.apply();
    }

    public static Bitmap generateQRCode(String url, int width, int height) throws WriterException, IOException {

        String charset = "UTF-8";

        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        BitMatrix bitMatrix = new MultiFormatWriter().encode(
                new String(url.getBytes(charset), charset), BarcodeFormat.QR_CODE, width, height, hashMap);

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bitmap;
    }
}
