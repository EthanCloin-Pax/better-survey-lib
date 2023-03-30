package com.example.bettersurveylib.api;


import android.util.Base64;
import android.util.Log;

import com.example.bettersurveylib.api.survey.requests.BaseSurveyRequest;
import com.example.bettersurveylib.api.survey.requests.RegisterReq;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Authenticator {
    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    private static final String TAG = "EMC AUTH: ";


    public String generateTimeStamp() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        String TimeStamp = String.format("%d%02d%02d%02d%02d%02d",
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DATE), calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
        return TimeStamp;
    }

    public RegisterReq addAuthToRegisterRequest(RegisterReq req, String requestEncryptKey) {
        req.setTimestamp(generateTimeStamp());
        String signature = getSurveyRegisterSignature(req, requestEncryptKey);
        Log.i(TAG, "go survey signed as: "  + signature);
        req.setSignatureData(signature.trim());
        Log.i(TAG, "body is now " + req);
        return req;
    }

    /**
     * Provide authentication signature for a request to Survey (SoundPayments) API
     * <p>
     * Requires the DeviceID (or DeviceSN) AND Timestamp to be present in requestBody and the registerRequestKey as key
     *
     * @param requestBody
     * @param requestEncryptKey
     * @return SignatureData which should be included in the Request
     */
    private static String getSurveySignature(BaseSurveyRequest requestBody, String requestEncryptKey) {
        if (requestBody.getTimestamp() == null || requestBody.getDeviceID() == null || requestBody.getToken() == null) {
            Log.w(TAG, "Missing required info for Signature Generation");
        }
        String bodyToEncode = requestBody.getTimestamp() + requestBody.getDeviceID() + requestBody.getToken();

        return signDataSHA1(bodyToEncode, requestEncryptKey);
    }

    /**
     * return a signature for the register request provided.
     *
     * Timestamp and (DeviceID or DeviceSN) are required
     * @param requestBody
     * @param requestEncryptKey
     * @return
     */
    private static String getSurveyRegisterSignature(RegisterReq requestBody, String requestEncryptKey) {
        String bodyToEncode = "";
        if (requestBody.getDeviceID() == null) {
            if (requestBody.getDeviceSN() == null) {
                Log.w(TAG, "missing required info for signature generation");
                return "fail";
            }
            bodyToEncode = requestBody.getTimestamp() + requestBody.getDeviceSN();
        } else {
            bodyToEncode = requestBody.getTimestamp() + requestBody.getDeviceID();
        }
        Log.i(TAG, "should be fine! encoding this body: " + bodyToEncode);
        return signDataSHA1(bodyToEncode, requestEncryptKey);
    }

    /**
     * encodes request body using SHA1 algorithm.
     *
     * @param data
     * @param key
     * @return
     */
    private static String signDataSHA1(String data, String key) {
        Log.i(TAG, "Got data: " + data);
        byte[] result = null;

        try {
            SecretKeySpec signinKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signinKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());

            result = Base64.encode(rawHmac, Base64.DEFAULT);

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            System.err.println(e.getMessage());
        }
        if (null != result) {
            Log.i(TAG, "return result: " + new String(result));
            return new String(result);
        } else {
            return null;
        }

    }

    private static String signDataSHA256(String data, String key) {
        byte[] result = null;

        try {
            SecretKeySpec signinKey = new SecretKeySpec(key.getBytes(), HMAC_SHA256_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            mac.init(signinKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());
            // had issues importing the pax market api for security, hoing
            // the android default encoding works the same, both are RFC2045

            result = Base64.encode(rawHmac, Base64.DEFAULT);

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            System.err.println(e.getMessage());
        }
        if (null != result) {
            return new String(result);
        } else {
            return null;
        }

    }

    /**
     * Provide authentication signature for a request to TerminalRegister (PAX) API
     *
     * @param requestBodyToEncode
     * @param key
     * @return
     */
    public String registerTerminalSignature(String requestBodyToEncode, String key) {
        return signDataSHA256(requestBodyToEncode, key);
    }

}
