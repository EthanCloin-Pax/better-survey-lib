package com.example.surveydemo.api.register;

import com.paxus.pay.host.ui.init.surveydemo.api.register.requests.ConnectStoreReq;
import com.paxus.pay.host.ui.init.surveydemo.api.register.requests.GetRegisterDataReq;
import com.paxus.pay.host.ui.init.surveydemo.api.register.requests.RegisterTerminalReq;
import com.paxus.pay.host.ui.init.surveydemo.api.register.requests.SearchStoreReq;
import com.paxus.pay.host.ui.init.surveydemo.api.register.responses.ConnectStoreRsp;
import com.paxus.pay.host.ui.init.surveydemo.api.register.responses.GetRegisterDataRsp;
import com.paxus.pay.host.ui.init.surveydemo.api.register.responses.RegisterTerminalRsp;
import com.paxus.pay.host.ui.init.surveydemo.api.register.responses.SearchStoreRsp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * definition of endpoints for TerminalRegister API. attach this to TerminalRegisterClient instance
 */
public interface TerminalRegisterInterface {
    /**
     * Request URL to web form allowing user to register their terminal with Seamless Commerce
     *
     * Returns additional terminal data if already registered.
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("v1/getRegisterData")
    Call<GetRegisterDataRsp> doGetRegisterData(
            @Header("TimeStamp") String timestamp,
            @Header("SignatureData") String signatureData,
            @Body GetRegisterDataReq request
    );

    /**
     * Request data associated with provided terminal
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("v1/TerminalRegister")
    Call<RegisterTerminalRsp> doRegisterTerminal(
            @Header("TimeStamp") String timestamp,
            @Header("SignatureData") String signatureData,
            @Body RegisterTerminalReq request
    );

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("v1/SearchStore")
    Call<SearchStoreRsp> doSearchStore(
            @Header("TimeStamp") String timestamp,
            @Header("SignatureData") String signatureData,
            @Body SearchStoreReq request
    );

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("v1/ConnectStore")
    Call<ConnectStoreRsp> doConnectStore(
            @Header("TimeStamp") String timestamp,
            @Header("SignatureData") String signatureData,
            @Body ConnectStoreReq request
    );


}
