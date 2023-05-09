package com.example.surveydemo.api;

import android.util.Base64;
import android.util.Log;

import com.example.surveydemo.api.survey.requests.BaseSurveyRequest;
import com.example.surveydemo.api.survey.requests.RegisterReq;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Calendar;
import java.util.TimeZone;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;



public class Authenticator {
    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    private static final String TAG = "EMC AUTH: ";

    private static Authenticator instance = null;

    private Authenticator() {
    }

    public static Authenticator getInstance() {
        if (instance == null) {
            instance = new Authenticator();
            return instance;
        }
        return instance;
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
        String bodyToEncode = requestBody.getDeviceID() + requestBody.getToken() + requestBody.getTimestamp();

        return signDataSHA1(bodyToEncode, requestEncryptKey);
    }

    /**
     * return a signature for the register request provided.
     * <p>
     * Timestamp and (DeviceID or DeviceSN) are required
     *
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
            bodyToEncode = requestBody.getDeviceSN() + requestBody.getTimestamp();
        } else {
            bodyToEncode = requestBody.getDeviceID() + requestBody.getTimestamp();
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
     * Uses the responseEncryptKey to decrypt the requestEncryptKey into its usable
     * form
     *
     * @param encryptedRequestEncryptKey returned from the Survey Register call as
     *                                   requestEncryptKey
     * @param responseEncryptKey         returned from the Survey Register call as
     *                                   responseEncryptKey
     * @return
     * @throws Exception
     */
    public static String decryptSurveyRequestKey(String encryptedRequestEncryptKey, String responseEncryptKey) {
        Log.i(TAG, responseEncryptKey);
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS7PADDING");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }

        SecretKeySpec secretKeySpec = new SecretKeySpec(responseEncryptKey.getBytes(), "AES");
        try {
            cipher.init(cipher.DECRYPT_MODE, secretKeySpec);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        byte[] encryptData = Base64.decode(encryptedRequestEncryptKey, Base64.DEFAULT);

        byte[] original = new byte[0];

        try {
            original = cipher.doFinal(encryptData);
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
        return new String(original, StandardCharsets.UTF_8);
    }

    /**
     * create TimeStamp string per SurveyAPI standard
     *
     * @return
     */
    public String generateTimeStamp() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        String TimeStamp = String.format("%d%02d%02d%02d%02d%02d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
        return TimeStamp;
    }

    /**
     * Returns an updated RegisterReq instance that includes the required auth fields:
     * <p>
     * - TimeStamp
     * - Signature
     *
     * @param req
     * @param requestEncryptKey
     * @return
     */
    public RegisterReq addAuthToRegisterRequest(RegisterReq req, String requestEncryptKey) {
        req.setTimestamp(generateTimeStamp());
        String signature = getSurveyRegisterSignature(req, requestEncryptKey);
        Log.i(TAG, "go survey signed as: " + signature);
        req.setSignatureData(signature.trim());
        Log.i(TAG, "body is now " + req);
        return req;
    }

    /**
     * Returns an updated SurveyRequest instance that includes the required auth fields:
     * <p>
     * - TimeStamp
     * - Signature
     *
     * @param req
     * @param requestEncryptKey
     * @return
     */
    public BaseSurveyRequest addAuthToSurveyRequest(BaseSurveyRequest req, String requestEncryptKey) {
        req.setTimestamp(generateTimeStamp());
        String signature = getSurveySignature(req, requestEncryptKey);
        req.setSignatureData(signature.trim());
        return req;
    }

    /**
     * Provide authentication signature for a request to TerminalRegister (PAX) API
     *
     * @param requestBodyToEncode
     * @param key
     * @return
     */
    public String addAuthToTerminalRegisterRequest(String requestBodyToEncode, String key) {
        return "";
    }


}
