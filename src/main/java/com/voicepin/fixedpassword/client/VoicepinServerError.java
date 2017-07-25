package com.voicepin.fixedpassword.client;

/**
 * @author mckulpa
 */
public class VoicepinServerError extends VoicepinClientException {
    private final int statusCode;
    private final String statusDescription;

    VoicepinServerError(int statusCode, String statusDescription) {
        super("VoicePIN Server returned ERROR: " + statusCode + " " + statusDescription);
        this.statusCode = statusCode;
        this.statusDescription = statusDescription;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }
}
