package com.example.bettersurveylib.api.register.responses;

import androidx.annotation.Nullable;

import com.example.bettersurveylib.api.register.requests.GetRegisterDataReq;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetRegisterDataRsp extends BaseRegisterResponse {

    @SerializedName("registerURL")
    public String registerUrl;

    // below fields are optional, only included if device already registered
    @SerializedName("supportFeature")
    @Nullable
    public List<String> supportFeature;

    @SerializedName("registerRequestEncryptKey")
    @Nullable
    public String registerRequestEncryptKey;

    @SerializedName("responseRequestEncryptKey")
    @Nullable
    public String responseRequestEncryptKey;

//    public GetRegisterDataReq(String responseCode, String responseMessage){
//        super(responseCode, responseMessage);
//    }

    public GetRegisterDataRsp(String responseCode, String responseMessage) {
        super(responseCode, responseMessage);
    }

    public GetRegisterDataRsp(String responseCode, String responseMessage, String registerUrl, @Nullable List<String> supportFeature, @Nullable String registerRequestEncryptKey, @Nullable String responseRequestEncryptKey) {
        super(responseCode, responseMessage);
        this.registerUrl = registerUrl;
        this.supportFeature = supportFeature;
        this.registerRequestEncryptKey = registerRequestEncryptKey;
        this.responseRequestEncryptKey = responseRequestEncryptKey;
    }
}
