package com.example.surveydemo.api.register.responses;

import com.google.gson.annotations.SerializedName;

public class ConnectStoreRsp extends BaseRegisterResponse {

    @SerializedName("registerRequestEncryptKey")
    public String requestKey;

    @SerializedName("registerResponseEncryptKey")
    public String responseKey;

    public ConnectStoreRsp(String responseCode, String responseMessage) {
        super(responseCode, responseMessage);
    }
}
