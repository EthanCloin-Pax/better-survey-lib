package com.example.bettersurveylib.api.responses;

import com.google.gson.annotations.SerializedName;

public class GetRegisteredKeysRsp {

    @SerializedName("responseCode")
    public String responseCode;

    @SerializedName("responseMessage")
    public String responseMessage;

    @SerializedName("registerRequestEncryptKey")
    public String requestKey;

    @SerializedName("registerResponseEncryptKey")
    public String responseKey;

}
