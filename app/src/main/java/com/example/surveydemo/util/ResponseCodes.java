package com.example.surveydemo.util;

/**
 * these codes cover the case where we are unable to reach the server and receive a valid response.
 * server will generally provide response code and message in the response object.
 *
 */
public class ResponseCodes {

    public static final String SERVER_UNREACHABLE_MSG = "5000";
    public static final String SERVER_UNREACHABLE_CODE = "Unable to reach server";

    public static final String NULL_RESPONSE_MSG = "Received null from server";
    public static final String NULL_RESPONSE_CODE = "5001";

    public static final String ALREADY_REGISTERED_MSG = "The terminal has already registered a store";
    public static final String ALREADY_REGISTERED_CODE = "4001";

}
